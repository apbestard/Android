/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package frontend;

import java.awt.Color;
import javax.swing.JWindow;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class SIEMainDesktopStub extends JWindow {

    final static String VERSION = "1.20.c";
    static String NOVERSION = "false";
    static String SURL = "http://tester.logipax.com.mx:8080";
    static String SHOME = "_sietesterdesktop/";
    static String SENV = "";
    static String SPATH = "SIETesterDesktop";
    static String SNAME = "SIE";
    static String SDATA = "SIE";
    static String SSYNC = "/SIELogipaxAWSV1Sync";
    static String SSYNCDOWNLOAD = "/Download";
    static String SEXE = "SIETesterDesktop.jar";
    static String SPARAMETERS = "";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        SPARAMETERS = "";
        HashMap<String, String> parameters = get(args);
        Set<Map.Entry<String, String>> rfset = parameters.entrySet();
        for (Map.Entry<String, String> item : rfset) {
            String name = (String) item.getKey();
            String value = (String) item.getValue();
            if (name.equals("noversion")) {
                NOVERSION = value;
            } else if (name.equals("url")) {
                SURL = value;
                SPARAMETERS += " " + name + "=" + value;
            } else if (name.equals("program")) {
                SPATH = value;
                SPARAMETERS += " " + name + "=" + value;
            } else if (name.equals("home")) {
                SHOME = value;
                SPARAMETERS += " " + name + "=" + value;
            } else if (name.equals("env")) {
                SENV = value;
                SPATH = value + SPATH;
                SPARAMETERS += " " + name + "=" + value;
            } else if (name.equals("name")) {
                SNAME = value;
                SPARAMETERS += " " + name + "=" + value;
            } else if (name.equals("dispatcher")) {
                SPARAMETERS += " " + name + "=" + value;
            } else if (name.equals("sync")) {
                SSYNC = value;
                SPARAMETERS += " " + name + "=" + value;
            }
        }
        if (NOVERSION.equals("true")) {
            SPARAMETERS += " stub=none";
        } else {
            SPARAMETERS += " stub=" + VERSION;
        }
        SPARAMETERS = SURL;
        final SIEMainDesktopStub starting = new SIEMainDesktopStub();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                starting.processing();
            }
        });
    }
    final SIEMainDesktopStub starting;
    final ImageIcon image;
    final Font font;
    String message = "";
    final Color cfrm[];
    final Color cbk;
    final Color ntxt;
    final Color ctxt;
    public String error = null;
    public boolean connected = false;

    public SIEMainDesktopStub() {
        starting = this;
        image = new javax.swing.ImageIcon(getClass().getResource("/images/logipax.png"));

        cfrm = new Color[2];
        cfrm[0] = new Color(0x0f,0x8f,0xdf);
        cfrm[1] = new Color(0x3f,0xaf,0xff);
        cbk = Color.WHITE;
        ntxt = new Color(0xf7,0xf7,0xf7);
        ctxt = new Color(0x50,0x50,0x50);
        font = new Font("Sans-Serif", Font.BOLD, 10);
        //setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/libertad.png")).getImage());
        setSize(image.getIconWidth() + 4, image.getIconHeight() + 2 * font.getSize() + 4);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getSize().width) / 2;
        int y = (screenSize.height - getSize().height) / 2;
        setLocation(x, y);
        message = "Cargando...";
        setVisible(true);
    }

    public void processing() {
        Download pdownload = new Download();
        error = pdownload.load(2, SURL + SSYNC + SSYNCDOWNLOAD);
        if (error == null && pdownload.path_program == null) {
            error = "No hay programa de ejecución";
        }
        boolean exists = (pdownload.path_program != null && new java.io.File(pdownload.path_program).exists());
        if (error != null) {
            String header = "Actualización de Versión";
            if (!exists) {
                error += "\nNo puede continuar.";
                JOptionPane.showMessageDialog(this, error, header, JOptionPane.ERROR_MESSAGE);
            } else {
                error += "\n¿Quiere continuar fuera de linea?";
                int response = JOptionPane.showConfirmDialog(this, error, header, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (response != JOptionPane.YES_OPTION) {
                    System.exit(-1);
                }
            }
        }
        if (!exists) {
            System.exit(-1);
        }
        String separator = "\"";
        String exe = "";
        String java_home = System.getProperty("java.home");
        if (java_home == null || java_home.length() == 0) {
            exe = "java.exe";
        } else {
            exe = separator + java_home + "/bin/java.exe" + separator;
        }
        int os = SystemID.getOS();
        if (os == SystemID.OS_MAC_OS_X || os == SystemID.OS_LINUX) {
            exe = java_home + "/bin/java";
            separator = "";
        }
        String systemIds[] = SystemID.getIDs(SystemID.ZENDID, SystemID.ALLID);
        String lparams = "-jar -Xmx512m " + separator + pdownload.path_program + separator;
        String[] xparams = new String[]{null, "-jar", "-Xmx512m", separator + pdownload.path_program + separator, SURL};
        if (pdownload.path_startup_program != null) {
            lparams = "-jar -Xmx512m " + separator + pdownload.path_startup_program + separator + " exe=" + pdownload.path_program;
            xparams = new String[]{null, "-jar", "-Xmx512m", separator + pdownload.path_startup_program + separator, SURL};
        }
        String md5 = "";
        message("Ejecutando programa... " + exe);
        lparams += SPARAMETERS;
        if (error != null) {
            lparams += " offline=true";
            String[] xparams2 = new String[xparams.length+1];
            System.arraycopy(xparams, 0, xparams2, 0, xparams.length);
            xparams2[xparams.length] = "offline=true";
            xparams = xparams2;
        }
//        for (int i = 0; i < systemIds.length; i++) {
//            md5 += systemIds[i];
//            lparams += " MD5_" + i + "=" + systemIds[i];
//            String[] xparams2 = new String[xparams.length+1];
//            System.arraycopy(xparams, 0, xparams2, 0, xparams.length);
//            xparams2[xparams.length] = "MD5_" + i + "=" + systemIds[i];
//            xparams = xparams2;
//        }
//        String idmd5 = "";
//        try {
//            idmd5 = SystemID.getEncrypted(md5.getBytes("UTF-8"));
//        } catch (Exception ex) {
//            idmd5 = SystemID.getEncrypted(md5.getBytes());
//        }
//        lparams += " CRC=" + idmd5;
        message(exe + " " + lparams);
        //error = SystemID.tryExecFirstLine(exe, lparams);
        xparams[0] = exe;
        error = SystemID.tryExecFirstLine(xparams);
        if (error == null) {
      System.out.println("ok:"+exe);
              try {
                Thread.sleep(2000);
            } catch (Exception ex) {
            }
            System.exit(0);
        }
        System.out.println(error+" "+exe);
        System.exit(-1);
    }

    public void ok() {
        connected = true;
    }

    public void error(String error) {
        this.error = error;
    }

    public void message(String message) {
        this.message = message;
        if (error == null) {
            update(this.getGraphics());
        }
    }
    boolean firsttime = true;

    public void paint(Graphics g) {
        //super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        FontRenderContext frc = g2.getFontRenderContext();
        int second = (int) ((System.currentTimeMillis() / 1000) % 2);
        if (firsttime) {
            //firsttime = false;
            g.setColor(cfrm[second]);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(cbk);
            g.fillRect(2, 2, this.getWidth() - 4, this.getHeight() - 4);
            g.drawImage(image.getImage(), 2, 2, this);
        } else {
            g.setColor(cbk);
            int h = image.getImage().getHeight(null);
            g.fillRect(2, 2 + h, this.getWidth() - 4, this.getHeight() - 4 - h);
        }
        g.setFont(font);
        if (true) {
            g.setColor(ntxt);
            g.drawString(VERSION, 8, 2 + image.getIconHeight() + font.getSize() / 2 + 10);
            Rectangle2D bounds = font.getStringBounds(VERSION, frc);
            g.setColor(ctxt);
            g.drawString(message, (int) bounds.getWidth() + 10, 2 + image.getIconHeight() + font.getSize() / 2 + 10);
        } else {
            g.setColor(ctxt);
            g.drawString(message, 8, 2 + image.getIconHeight() + font.getSize() / 2 + 10);
        }
    }

    String updatePath(String name) {
        char[] name2 = name.toCharArray();
        for (int i=0;i<name2.length;i++) {
            if (name2[i] == '\\')
            name2[i] = '/';
        }
        return String.copyValueOf(name2);
    }
    
    class Download {

        final String path_library;
        String path_startup_program = null;
        String path_program = null;
        HashMap<String, String> modules = new HashMap<String, String>();

        Download() {
            String home = System.getProperty("user.home");
            if (!home.endsWith("/") && !home.endsWith("\\")) {
                home += "/";
            }
            path_library = home + SHOME;
            path_program = path_library + SPATH + "/" + SEXE;
        }

        String load(int trys, String libraryUrl) {
            //path_program = null;
            message("Leyendo directorio de " + libraryUrl + "...");
            byte[][] data = getServerResponseBytes(libraryUrl, "id=" + urlEncode(SPATH), null, null, -1);
            if (data == null || data.length != 2 || data[0] == null || data[1] == null) {
                if (trys > 0) {
                    load(trys - 1, libraryUrl);
                }
                System.out.println("No list data:" + libraryUrl);
                return ("No pudo validar versión en Internet!\nNo hay respuesta del servidor");
            }
            String type = new String(data[0]);
            if (!type.startsWith("text")) {
                System.out.println("No list type: " + libraryUrl + " " + type);
                return ("No pudo validar versión en Internet!\nRespuesta incorrecta del servidor");
            }
            String list[] = tokenizer(new String(data[1]), "\n");
            for (int ii = 0; ii < list.length; ii++) {
                if (list[ii].length() == 0) {
                    continue;
                }
                if (list[ii].startsWith(".")) {
                    continue;
                }
                String[] row = tokenizer(list[ii], "|");
                if (row[1].equals("0")) {
                    continue;
                }
                row[0] = updatePath(row[0]);
                if (row[0].endsWith(".jar")) {
                    if (row[0].startsWith("Startup")) {
                        path_startup_program = path_library + row[0];
                    } else {
                        path_program = path_library + row[0];
                    }
                }
            }
            if (path_program == null) {
                path_program = path_library + SEXE;
                //return ("No hay programa de ejecución!\nError en lista");
            }
            String error = load(libraryUrl, SPATH);
            if (error != null) {
                return (error);
            }
            message("Depurando módulos...");
            deleteFromDisk();
            return (null);
        }

        String load(String libraryUrl, String dir) {
            message("Leyendo módulos...");
            mkdir(dir);
            String error = loadFromDisk(dir);
            if (error != null) {
                return ("Leyendo módulos: " + error);
            }
            message("Leyendo directorio...");
            byte[][] data = getServerResponseBytes(libraryUrl, "id=" + urlEncode(dir) + "&subdir=true", null, null, -1);
            if (data == null || data.length != 2 || data[0] == null || data[1] == null) {
                System.out.println("No list data:" + libraryUrl + " " + dir);
                return ("No pudo actualizar versión!\nError en respuesta del servidor");
            }
            String type = new String(data[0]);
            if (!type.startsWith("text")) {
                System.out.println("No list type: " + libraryUrl + " " + dir + " " + type);
                return ("No pudo actualizar versión!\nError en lista");
            }
            String list[] = tokenizer(new String(data[1]), "\n");
            for (int ii = 0; ii < list.length; ii++) {
                if (list[ii].length() == 0) {
                    continue;
                }
                if (list[ii].startsWith(".")) {
                    continue;
                }
                String[] row = tokenizer(list[ii], "|");
                if (row[1].equals("0")) {
                    continue;
                }
                row[0] = updatePath(row[0]);
                if (row[0].endsWith("/")) {
                    modules.remove(row[0]);
                    //load(libraryUrl, row[0]);
                    continue;
                }
                message("Leyendo " + row[0] + " de " + libraryUrl + "...");
                String filename = path_library + row[0];
                error = loadFile(row[0], row[3], filename, libraryUrl);
                if (error != null) {
                    return ("No pudo actualizar versión!\nError en " + row[0] + "\n" + error);
                }
            }
            return (null);
        }

        private String loadFromDisk(String dirn) {
            try {
                java.io.File dir = new java.io.File(path_library + dirn);
                if (dir.isDirectory()) {
                    java.io.File list[] = dir.listFiles();
                    for (int i = 0; i < list.length; i++) {
                        if (list[i].isFile()) {
                            String md5 = getMD5(list[i].getAbsolutePath());
                            String name = list[i].getAbsolutePath().substring(path_library.length());
                            modules.put(pathName(name), md5);
                            System.out.println("Leyendo módulo: " + name + " " + md5);
                        } else if (list[i].isDirectory()) {
                            String name = list[i].getAbsolutePath().substring(path_library.length());
                            modules.put(pathName(name + "/"), name);
                            String result = loadFromDisk(pathName(name) + "/");
                            if (result != null) {
                                return result;
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                return (ex.toString());
            }
            return (null);
        }

        private void deleteFromDisk() {
            message("Depurando módulos...");
            Set<Map.Entry<String, String>> rfset = modules.entrySet();
            for (Map.Entry<String, String> item : rfset) {
                String name = (String) item.getKey();
                String value = (String) item.getValue();
                delete(name);
            }
        }

        String loadFile(String id, String hmd5, String filename, String libraryUrl) {
            message("Validando módulo:" + id + "...");
            String md5 = (String) modules.get(pathName(id));
            if (md5 != null && md5.equals(hmd5)) {
                System.out.println("No changes: " + id + " " + md5 + " " + hmd5);
                modules.remove(id);
                return (null);
            }
            if (md5 != null) {
                System.out.println("delete : " + id + " " + md5 + " " + hmd5);
                delete(id);
                modules.remove(id);
            } else {
                md5 = "";
            }
            message("Descargando módulo:" + id + "...");
            System.out.println("download : " + id + " " + hmd5 + " " + md5);
            byte[][] file = getServerResponseBytes(libraryUrl, "id=" + urlEncode(id) + "&get=" + urlEncode("true"), "image", filename, -1);
            if (file == null || file.length != 2 || file[0] == null || file[1] == null) {
                System.out.println("No download: " + id);
                return ("Respuesta en datos invalida!");
            }
            String ftype = new String(file[0]);
            if (!ftype.startsWith("image")) {
                System.out.println("No download type: " + id + " " + ftype);
                return ("Identificador en datos invalido!");
            }
            return (null);
        }

        private String[] tokenizer(String value, String delimiter) {
            java.util.StringTokenizer st = new java.util.StringTokenizer(value, delimiter);
            String lines[] = new String[st.countTokens()];
            int counter = 0;
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                lines[counter++] = token;
            }
            return (lines);
        }

        String getMD5(String name) {
            java.io.InputStream is = null;
            try {
                java.io.File file = new java.io.File(name);
                if (file.isDirectory()) {
                    return ("");
                } else {
                    is = new java.io.FileInputStream(file);
                    byte[] hash = new byte[0];
                    java.security.MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
                    byte data[] = new byte[0xffff];
                    for (;;) {
                        int c = is.read(data);
                        if (c < 0) {
                            break;
                        }
                        digest.update(data, 0, c);
                    }
                    hash = digest.digest();
                    String result = "";
                    for (int i = 0; i < hash.length; i++) {
                        int val = 0xff & hash[i];
                        String valStr = Integer.toHexString(val);
                        while (valStr.length() < 2) {
                            valStr = "0" + valStr;
                        }
                        result += valStr;
                    }
                    return (result);
                }
            } catch (Exception ex) {
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception ex) {
                    }
                }
            }
            return ("");
        }
        private static final String HEXDIGITS = "0123456789ABCDEF";

        private String urlEncode(String str) {
            if ((str == null) || (str.length() == 0)) {
                return "";
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c)) {
                    // Letter or number. No need to encode.
                    sb.append(c);
                } else {
                    // Encode it.
                    int cc = (int) c;
                    if (cc < 0 || cc > 255) {
                        sb.append('?'); //XXX
                    } else {
                        sb.append('%');
                        sb.append(HEXDIGITS.charAt((cc >> 4) & 0x0f));
                        sb.append(HEXDIGITS.charAt(cc & 0x0f));
                    }
                }
            }
            return sb.toString();
        }

        private byte[][] getServerResponseBytes(String xurl, String id2, String filetype, String filename, int to) {
            byte buffer[][] = new byte[2][];
            String id = "ticket=logipax&" + id2;
            try {
                //java.net.URL url = new java.net.URL(xurl+"?"+urlEncode(id));
//System.out.println("url="+xurl+"?"+id);
                java.net.URL url = new java.net.URL(xurl);
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                if (to > 0) {
                    connection.setConnectTimeout(to);
                }
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent", "SIELogipaxV0 1.0");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                System.out.println(xurl+"?"+id);
                byte[] bcontent = id.getBytes();
                connection.setRequestProperty("Content-Length", Integer.toString(bcontent.length));
                connection.setDoOutput(true);
                java.io.OutputStream os = connection.getOutputStream();
                java.io.DataOutputStream out = new java.io.DataOutputStream(os);
                out.write(bcontent);
                out.close();
                String type = connection.getContentType();
                if (type == null) {
                    type = "";
                }
                buffer[0] = type.getBytes();
                java.io.DataInputStream in = new java.io.DataInputStream(connection.getInputStream());
                byte[] data = new byte[0];
                byte buffer2[] = new byte[1024];
                int count = 0;
                if (filename != null) {
                    java.io.File file = new java.io.File(filename);
                    file.getParentFile().mkdirs();
                    os = new java.io.DataOutputStream(new FileOutputStream(filename));
                }
                for (;;) {
                    int c = in.read(buffer2, 0, buffer2.length);
                    if (c < 0) {
                        break;
                    }
                    if (filename != null) {
                        os.write(buffer2, 0, c);
                    } else {
                        byte tmp[] = new byte[count + c];
                        System.arraycopy(data, 0, tmp, 0, count);
                        System.arraycopy(buffer2, 0, tmp, count, c);
                        data = tmp;
                    }
                    count += c;
                }
                if (filename != null) {
                    os.flush();
                    os.close();
                }
                buffer[1] = data;
                in.close();
            } catch (Exception e) {
                System.out.println("FEStarting=" + e.toString());
                return (new byte[0][]);
            }
            return (buffer);
        }

        private void delete(String name) {
            try {
                new java.io.File(path_library + name).delete();
            } catch (Exception ex) {
            }
        }

        private void mkdir(String name) {
            try {
                new java.io.File(path_library + name).mkdirs();
            } catch (Exception ex) {
            }
        }
    }

    public static String pathName(String dir) {
        if (dir == null || dir.length() == 0) {
            return "";
        }
        try {
            char[] cdir = dir.toCharArray();
            for (int i = 0; i < cdir.length; ++i) {
                if (cdir[i] == '\\') {
                    cdir[i] = '/';
                }
            }
            return String.copyValueOf(cdir);
        } catch (Exception ex) {
            String error = ex.toString();
            System.err.println(error);
        }
        return dir;
    }

    private static HashMap<String, String> get(String args[]) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        for (int i = 0; i < args.length; ++i) {
            int inx = args[i].indexOf("=");
            String key = Integer.toString(i);
            String value = args[i];
            if (inx > 0) {
                key = args[i].substring(0, inx);
                value = args[i].substring(inx + 1);
            }
            parameters.put(key, value);
        }
        return parameters;

    }
}
