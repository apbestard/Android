package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FilesMngt {

    public static final String EXT = "._$_";
    public static final String DATETIME_FORMAT = "yyyyMMdd_HHmmssSSS";
    public static final long NOTIMER = 0xf000f000;

    public static void removeDir(String home, String bkhome, String id, long dtimer) {
        File file = new File(home + id);
        String id0 = file.getAbsolutePath().substring(home.length());
        if (!id0.endsWith("/")) {
            id0 += "/";
        }
        String id2 = file.getName();
        moveDir(home + id0, bkhome + id0 + dateTime() + "/", "");
        removeDirInfo(new File(home + id), dtimer);
    }

    public static void removeFile(String home, String bkhome, String id, long dtimer) {
        File file = new java.io.File(home + id);
        File file2 = new java.io.File(bkhome + id + "." + dateTime());
        copy(file, file2);
        remove(file);
        removeFileInfo(file, dtimer);
    }

/////////
    public static byte[] getFile(java.io.File file) {
        try {
            java.io.DataInputStream reader = new java.io.DataInputStream(new java.io.FileInputStream(file.getAbsoluteFile()));
            byte result[] = new byte[0];
            byte data[] = new byte[4096];
            int size = reader.read(data);
            String str = "";
            while (size > 0) {
                byte result2[] = new byte[result.length + size];
                System.arraycopy(result, 0, result2, 0, result.length);
                System.arraycopy(data, 0, result2, result.length, size);
                result = result2;
                char chs[] = new char[size];
                for (int i = 0; i < size; i++) {
                    chs[i] = (char) data[i];
                }
                size = reader.read(data);
            }
            reader.close();
            return (result);
        } catch (Exception ex) {
            System.err.println("getFile:" + ex.toString());
        }
        return (null);
    }

    public static boolean create(String data, File to) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(to);
            out.write(data.getBytes("UTF-8"));
            out.flush();
            return (true);
        } catch (Exception ex) {
            //System.err.println("create string error=" + ex.toString());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ex) {
                }
            }
        }
        return (false);
    }

    public static boolean copy(File from, File to) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            if (from.getAbsolutePath().equals(to.getAbsolutePath())) {
                return (true);
            }
            to.getParentFile().mkdirs();
            in = new FileInputStream(from);
            out = new FileOutputStream(to);
            int len;
            byte[] buffer = new byte[1024 * 8];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            return (true);
        } catch (Exception ex) {
            System.err.println("copy file error=" + ex.toString());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception ex) {
                }
            }
        }
        return (false);
    }

    public static void remove(File file) {
        file.delete();
    }

    public static String getMD5(java.io.File file) {
        String result = "";
        java.io.InputStream is = null;
        try {
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
            for (int i = 0; i < hash.length; i++) {
                int val = 0xff & hash[i];
                String valStr = Integer.toHexString(val);
                while (valStr.length() < 2) {
                    valStr = "0" + valStr;
                }
                result += valStr;
            }
        } catch (Exception ex) {
            result = "";
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                }
            }
        }
        return result;

    }
/////////

    private static String dateTime() {
        java.text.DateFormat datetimeFormat = new java.text.SimpleDateFormat(DATETIME_FORMAT);
        return datetimeFormat.format(System.currentTimeMillis() - java.util.TimeZone.getDefault().getOffset(System.currentTimeMillis()));
    }

    public static boolean copyFile(String h0, String h2, String id) {
        File from = new File(h0 + id);
        File to = new File(h2 + id);
        return copy(from, to);
    }

    public static void moveDir(String h0, String h2, String id) {
        if (!id.endsWith("/")) {
            id += "/";
        }
        File dir = new File(h0 + id);
        File files[] = dir.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                moveDir(h0, h2, id + files[i].getName());
            } else {
                if (!files[i].getName().endsWith(EXT)) {
                    copyFile(h0, h2, id + files[i].getName());
                }
                remove(files[i]);
            }
        }
        remove(dir);
    }
////////////////

    public static void createFileInfo(java.io.File file, String md5, long dtimer) {
        File file2 = new java.io.File(file.getAbsolutePath() + EXT);
        String txt = "1";
        if (dtimer == NOTIMER)
            txt += "|" + Long.toString(-1);
        else
            txt += "|" + Long.toString(System.currentTimeMillis() - java.util.TimeZone.getDefault().getOffset(System.currentTimeMillis()));
        txt += "|" + md5;
        create(txt, file2);
    }

    public static void removeFileInfo(java.io.File file, long dtimer) {
        File file2 = new java.io.File(file.getAbsolutePath() + EXT);
        String txt = "0";
        if (dtimer == NOTIMER)
            txt += "|" + Long.toString(-1);
        else
            txt += "|" + Long.toString(System.currentTimeMillis() - java.util.TimeZone.getDefault().getOffset(System.currentTimeMillis()));
        txt += "|";
        create(txt, file2);
    }

    public static void createDirInfo(java.io.File file, long dtimer) {
        File file2 = new java.io.File(file.getAbsolutePath() + EXT);
        String txt = "1";
        if (dtimer == NOTIMER)
            txt += "|" + Long.toString(-1);
        else
            txt += "|" + Long.toString(System.currentTimeMillis() - java.util.TimeZone.getDefault().getOffset(System.currentTimeMillis()));
        txt += "|";
        create(txt, file2);
    }

    public static void removeDirInfo(java.io.File file, long dtimer) {
        File file2 = new java.io.File(file.getAbsolutePath() + EXT);
        String txt = "0";
        if (dtimer == NOTIMER)
            txt += "|" + Long.toString(-1);
        else
            txt += "|" + Long.toString(System.currentTimeMillis() - java.util.TimeZone.getDefault().getOffset(System.currentTimeMillis()));
        txt += "|";
        create(txt, file2);
    }

    public static String[] getFileInfo(java.io.File file, long dtimer) {
        java.io.File file2 = new java.io.File(file.getAbsolutePath() + EXT);
        if (!file2.exists() && file.exists()) {
            if (file.isDirectory()) {
                createDirInfo(file, dtimer);
            } else {
                createFileInfo(file, getMD5(file), dtimer);
            }
        } else if (!file2.exists() && !file.exists()) {
            return new String[]{"0","-1",""};
        }
        byte[] bdata = getFile(file2);
        if (bdata == null) {
            return new String[0];
        }
        String data = new String(bdata);
        return tokenizer(data);
    }

    public static String[] tokenizer(String value) {
        java.util.StringTokenizer st = new java.util.StringTokenizer(value, "|");
        String lines[] = new String[st.countTokens()];
        int counter = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            lines[counter++] = token;
        }
        return (lines);
    }
///////
}
