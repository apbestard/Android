package servlets;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FileServices {

    public static HashMap<String, HashMap<String, String>> tables = new HashMap<String, HashMap<String, String>>();
    public static HashMap<String, Long> tablesUsed = new HashMap<String, Long>();
    public String home = null;
    public String bkhome = null;
    public HashMap<String, String> md5 = new HashMap<String, String>();
    public static final String EXT = "._$_";
    public static final String DATETIME_FORMAT = "yyyyMMdd_HHmmssSSS";
    final java.text.DateFormat datetimeFormat;

    private FileServices(String home, String bkhome, HashMap<String, String> md5) {
        this.home = home;
        this.bkhome = bkhome;
        this.md5 = md5;
        datetimeFormat = new java.text.SimpleDateFormat(DATETIME_FORMAT);
    }

    public static FileServices get(String home, String bkhome, String uniqueId) {
        if (uniqueId == null) {
            uniqueId = "unique";
        }
        HashMap<String, String> hash = tables.get(uniqueId);
        if (hash == null) {
            timeout();
            hash = new HashMap<String, String>();
            tables.put(uniqueId, hash);
        }
        tablesUsed.put(uniqueId, new Long(System.currentTimeMillis()));
        return new FileServices(home, bkhome, hash);
    }

    public static void timeout() {
        long timer = System.currentTimeMillis() - 10 * 60000;
        Set set = tablesUsed.entrySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            Long value = (Long) entry.getValue();
            if (value.longValue() < timer) {
                tables.remove(key);
                tablesUsed.remove(key);
            }
        }
    }

    public void restart() {
        md5.clear();
    }

    public boolean sendFile(String id, OutputStream os) {
        java.io.InputStream is = null;
        try {
            File file = new java.io.File(home + id);
            if (file.isFile()) {
                is = new java.io.FileInputStream(file);
                byte data[] = new byte[0xffff];
                for (;;) {
                    int c = is.read(data);
                    if (c < 0) {
                        break;
                    }
                    os.write(data, 0, c);
                }
                os.flush();
                os.close();
            }
        } catch (Exception ex) {
            return false;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                }
            }
        }
        return true;
    }

    public String getFilesFrom(String result, String id, boolean subdir, long fromNo, boolean odirs) {
        File file = new java.io.File(home + id);
        if (result.length() == 0 && !file.exists()) {
            File file2 = new java.io.File(home + id.substring(0, id.length()-1)+EXT);
            if (file2.exists()) {
                FileInfo finfo2 = new FileInfo(FilesMngt.getFile(file2));
                result += id + finfo2.value();
            }
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            long timer = 0;
            for (int i = 0; i < files.length; i++) {
                String name = files[i].getAbsolutePath().substring(home.length());
                if (name.endsWith(EXT)) {
                    FileInfo finfo2 = new FileInfo(FilesMngt.getFile(files[i]));
                    if (finfo2.deleted) {
                        String name2 = name.substring(0, name.length()-EXT.length());
                        File file2 = new File(home+name2);
                        if (file2.exists()) {
                            files[i].delete();
                        } else {
                            if (fromNo == 0 || fromNo < finfo2.timer) {
                                if (files[i].isDirectory()) {
                                    name2 += "/";
                                }
                                if (file2.isDirectory() || !odirs) {
                                    result += name2 + finfo2.value();
                                }
                                if (!file2.isDirectory() && timer < finfo2.timer) {
                                    timer = finfo2.timer;
                                }
                            }
                        }
                    }
                    continue;
                }
                    if (files[i].isDirectory()) {
                        name += "/";
                    }
                    FileInfo finfo = new FileInfo(FilesMngt.getFileInfo(files[i], 0));
                    if (!odirs && (fromNo == 0 || fromNo < finfo.timer)) {
                        result += name + finfo.value();
                    }
                    if (timer < finfo.timer) {
                        timer = finfo.timer;
                    }

            }
            if (odirs) {
                result += id + "|1|"+Long.toString(timer)+"|\n";
            }
            for (int i = 0; i < files.length; i++) {
                String name = files[i].getAbsolutePath().substring(home.length());
                if (name.endsWith(EXT)) {
                    continue;
                }
                if (files[i].isDirectory()) {
                    name += "/";
                }
                if (subdir && files[i].isDirectory()) {
                    result = getFilesFrom(result, name, subdir, fromNo, odirs);
                }
            }

        } else {
            FileInfo finfoi = new FileInfo(file, 0);
            if (fromNo == 0 || fromNo < finfoi.timer) {
                result += id + finfoi.value();
            }
        }
        return result;
    }

    /*

    public String getMD5(String key) {
    String result = (String) md5.get(key);
    if (result == null) {
    java.io.InputStream is = null;
    try {
    File file = new java.io.File(home + key);
    if (file.isDirectory()) {
    String[] names = file.list();
    result = "";
    for (int i = 0; i < names.length; i++) {
    result += names[i];
    try {
    File nfile = new java.io.File(file.getPath() + "/" + names[i]);
    if (nfile.isDirectory()) {
    result += "/";
    }
    } catch (Exception ex) {
    }
    result += "\n";
    }
    } else if (file.isFile()) {
    is = new java.io.FileInputStream(file);
    byte[] hash = new byte[0];
    java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
    byte data[] = new byte[0xffff];
    int count = 0;
    for (;;) {
    int c = is.read(data);
    if (c < 0) {
    break;
    }
    digest.update(data, 0, c);
    }
    hash = digest.digest();
    result = "";
    for (int i = 0; i < hash.length; i++) {
    int val = 0xff & hash[i];
    String valStr = Integer.toHexString(val);
    while (valStr.length() < 2) {
    valStr = "0" + valStr;
    }
    result += valStr;
    }
    putMD5(key, result);
    } else {
    result = ".";
    }
    } catch (Exception ex) {
    result = "ERROR: " + ex.toString();
    } finally {
    if (is != null) {
    try {
    is.close();
    } catch (Exception ex) {
    }
    }
    }
    }
    return result;
    }

    public void putMD5(String key, String md5data) {
    md5.put(key, md5data);
    }

    public static long getUpd(java.io.File file) {
    byte[] dataNo = FilesMngt.getFile(file);
    if (dataNo == null) {
    return (0);
    }
    long timer = Strings.getLong(new String(dataNo));
    return timer;
    }



     */
    public String remove(String id) {
        File file = new java.io.File(home + id);
        if (file.isDirectory()) {
            FilesMngt.removeDir(home, bkhome, id, 0);
        } else if (file.isFile()) {
            FilesMngt.removeFile(home, bkhome, id, 0);
        }
        return "OK: " + file.getPath();
    }

    public String create(String id, int datalength, InputStream in) {
        DataOutputStream os = null;
        try {
            File file = new java.io.File(home + id);
            File dir = new File(file.getParent());
            dir.mkdirs();
            os = new DataOutputStream(new FileOutputStream(file));
            byte[] hash = new byte[0];
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            int bytesRead = 0;
            byte[] buffer = new byte[1024 * 64];
            while (bytesRead < datalength) {
                int count = in.read(buffer);
                if (count == -1) {
                    return "ERROR: Data size";
                }
                digest.update(buffer, 0, count);
                os.write(buffer, 0, count);
                bytesRead += count;
            }
            hash = digest.digest();
            String fmd5 = "";
            for (int i = 0; i < hash.length; i++) {
                int val = 0xff & hash[i];
                String valStr = Integer.toHexString(val);
                while (valStr.length() < 2) {
                    valStr = "0" + valStr;
                }
                fmd5 += valStr;
            }
            os.flush();
            FilesMngt.createFileInfo(file, fmd5, 0);
            return "OK: " + fmd5;
        } catch (Exception ex) {
            return "ERROR: " + ex.toString();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}
