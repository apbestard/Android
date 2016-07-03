package backend.card.interfases;

import backend.BEServletInterfase;
import backend.security.SecuritySession;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import mx.logipax.shared.objects.Login;
import mx.logipax.shared.objects.LoginIn;
import mx.logipax.utility.Arrays2;
import mx.logipax.utility.Base64Coder;
import mx.logipax.utility.MD5;
import mx.logipax.utility.Resources;
import mx.logipax.utility.Strings;
import mx.logipax.utility.Utility;
import mx.logipax.utility.ZipUtility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RemoteService {
 
    private static String urlCoreServices;
    private static String urlCloudServices;
    private static String ticket;
    private static Properties prop;
    private static BEServletInterfase servlet;
    private static SecuritySession session;
    
    public RemoteService(BEServletInterfase servlet) {
        try{
            // STANDALONE
//            String path = RemoteService.class.getProtectionDomain().getCodeSource().getLocation().getPath()+"backend/db.properties";
//            InputStream input = RemoteService.class.getResourceAsStream("backend/db.properties");
//            String path = "/Users/mislibertad/Desktop/MISDesktop/MISLibBackend/build/classes/backend/db.properties";
//            InputStream input = new FileInputStream(path);
            
            // WEB
            byte[] dproperties = Resources.loadResource(RemoteService.class, "/backend/db.properties");
            ByteArrayInputStream input = new ByteArrayInputStream(dproperties);
//            java.util.Properties properties = Strings.loadProperties(dproperties);
            
            prop = new Properties();
            prop.load(input);
            urlCoreServices = prop.getProperty("coreService.server");
            urlCloudServices = prop.getProperty("coreService.cloud.server");
            this.servlet = servlet;
//            
//            
//            
//            BESemana bs = new BESemana(servlet);
//            semanaActual = bs.getSemanaActual();
            session = new SecuritySession(servlet,"", "admin", "Administrador", "", "183986083ce01e4d9a9953c4c3673abe", "Administrador", "00000001", "", "", "");
            
            
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public static JSONObject interfase(String server, String command, JSONObject json) {
        String jsontxt = getHTTPRequestRemote(  server, 
                                                new String[]{"command", "certificate", "encryption", "zip", "input"}, 
                                                new String[]{command, "false", "false", "false", Utility.jsonEncode(false, "false", "false", json.toString())});
        JSONObject response = new JSONObject(new JSONTokener(jsontxt));
        return response;
    }
    
    public static JSONObject interfaseCloud(String server, String command, JSONObject json) {
        String jsontxt = getHTTPRequestRemote(  server, 
                                                new String[]{"command", "input"}, 
                                                new String[]{command, Utility.jsonEncode(false, "false", "false", json.toString())});
        JSONObject response = new JSONObject(new JSONTokener(jsontxt));
        return response;
    }
    
    private static void login(String server, String usr, String pwd) {
        LoginIn in = new LoginIn(new JSONObject());
        in.setUser(usr);
        in.setPassword(pwd);
        in.setTerminalId("0");
        in.update();
        String jsontxt = getHTTPRequestRemote(server,
                new String[]{"command", "certificate", "encryption", "zip", "input"},
                new String[]{"login", "false", "false", "false", Utility.jsonEncode(false, "false", "false", in.json().toString())});
        JSONObject response = new JSONObject(new JSONTokener(jsontxt));
        String error = null;
        if (response.has("exception")) {
            JSONObject exception = response.getJSONObject("exception");
            error = exception.getString("error");
        }
        if (response.has("error")) {
            error = response.getString("error");
        }
        if (error != null) {
            System.out.println("Error login:"+error);
            return;
        }
        Login login = new Login(response);
        ticket = login.getTicket();
    }
    
    private static void loginCloud(String server, String usr, String pwd) {
        LoginIn in = new LoginIn(new JSONObject());
        in.setUser(usr);
        in.setPassword( MD5.getMD5(pwd) );
        in.setTerminalId("0");
        in.update();
        String jsontxt = getHTTPRequestRemote(server,
                new String[]{"command", "input"},
                new String[]{"login", Utility.jsonEncode(false, "false", "false", in.json().toString())});
        JSONObject response = new JSONObject(new JSONTokener(jsontxt));
        
        
        
        
        String error = null;
        if (response.has("exception")) {
            JSONObject exception = response.getJSONObject("exception");
            error = exception.getString("error");
        }
        if (response.has("error")) {
            error = response.getString("error");
        }
        if (error != null) {
            System.out.println("Error login:"+error);
            return;
        }
        Login login = new Login(response);
        ticket = login.getTicket();
    }
    
    public static String getHTTPRequestRemote(String server, String params[], String values[]) {
        byte bytes[] = null;
        String error = null;
        StringBuilder sb = new StringBuilder();
        params = Arrays2.append(params, "json");
        values = Arrays2.append(values, "true");
        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                sb.append("&");
            }
            sb.append(params[i]);
            sb.append("=");
            sb.append(urlEncode(values[i]));
        }
        String content = sb.toString();
        System.out.println( String.format("Server: [%s]",server) );
        System.out.println("send=" + content);
        try {
            java.net.URL url = new java.net.URL(server);
            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "MIS 1.0");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte[] bcontent = toBytes(content);
            connection.setRequestProperty("Content-Length", Integer.toString(bcontent.length));
            connection.setDoOutput(true);
            java.io.OutputStream os = connection.getOutputStream();
            java.io.DataOutputStream out = new java.io.DataOutputStream(os);
            out.write(bcontent);
            out.flush();
            String type = connection.getContentType();
            java.io.DataInputStream in = new java.io.DataInputStream(connection.getInputStream());
            int size = connection.getContentLength();
            bytes = new byte[0];
            byte buffer2[] = new byte[1024];
            int count = 0;
            for (;;) {
                int c = in.read(buffer2, 0, buffer2.length);
                if (c < 0) {
                    break;
                }
                byte[] tmpdata = buffer2;
                if (c < buffer2.length) {
                    tmpdata = new byte[c];
                    System.arraycopy(buffer2, 0, tmpdata, 0, c);
                }
                byte tmp[] = new byte[count + c];
                System.arraycopy(bytes, 0, tmp, 0, count);
                System.arraycopy(buffer2, 0, tmp, count, c);
                bytes = tmp;
                count += c;
            }
            in.close();
            if (type != null && type.indexOf("text") >= 0) {
                try {
                    String data = toString(bytes);
                    System.out.println("xml recv=" + data);
                    return (data);
                } catch (Exception ex) {
                    throw new java.io.IOException("Conexión no compatible");
                }
            }
            if (type != null && type.indexOf("octet-stream") >= 0) {
                try {
                    byte[] uzresult = ZipUtility.unzip(bytes, "data");
                    String data = toString(uzresult);
                    System.out.println("xml recv=" + data);
                    return (data);
                } catch (Exception ex) {
                    throw new java.io.IOException("Conexión no compatible");
                }
            }
            String reponse = toString(bytes);
            System.out.println("recv=" + reponse);
            return (reponse);
        } catch (IOException e) {
            error = e.toString();
            System.out.println("URL Exception =" + e.toString());
            String reponse = "{\"" + "exception" + "\":{\"error\":\"" + error + "\"}}";
            System.out.println("recv=" + reponse);
            return (reponse);
        } finally {
        }
    }
    
    public static String getTicket() {
        login(urlCoreServices, prop.getProperty("coreService.user"), dec(prop.getProperty("coreService.password")) );
        if(ticket==null) {
        }
        return ticket;
    }
    
    public static String getTicketCloud() {
        loginCloud(urlCloudServices, prop.getProperty("coreService.cloud.user"), dec(prop.getProperty("coreService.cloud.password")) );
        if(ticket==null) {
        }
        return ticket;
    }
    
    public static String getParameter(String param, String params[], String values[]) {
        for (int i = 0; i < params.length; i++) {
            if (params[i].equals(param)) {
                return values[i];
            }
        }
        return null;
    }

    public static int getParameterInt(String param, String params[], String values[]) {
        for (int i = 0; i < params.length; i++) {
            if (params[i].equals(param)) {
                return Strings.getInt(values[i]);
            }
        }
        return 0;
    }

    public static long getParameterLong(String param, String params[], String values[]) {
        for (int i = 0; i < params.length; i++) {
            if (params[i].equals(param)) {
                return Strings.getLong(values[i]);
            }
        }
        return 0;
    }

    public static boolean getParameterBoolean(String param, String params[], String values[]) {
        for (int i = 0; i < params.length; i++) {
            if (params[i].equals(param)) {
                return values[i].equalsIgnoreCase("true");
            }
        }
        return false;
    }

    public static java.util.Date getParameterDate(String param, String params[], String values[]) {
        for (int i = 0; i < params.length; i++) {
            if (params[i].equals(param)) {
                return new java.util.Date(Strings.getLong(values[i]));
            }
        }
        return null;
    }
    
    public static String toString(byte[] data) {
        String str = null;
        try {
            str = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            str = new String(data);
        }
        return (str);
    }

    private static final String HEXDIGITS = "0123456789ABCDEF";

    public static String urlEncode(String str) {
        if ((str == null) || (str.length() == 0)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
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

    public static byte[] toBytes(String str) {
        byte[] data = null;
        try {
            data = str.getBytes("UTF-8");
        } catch (Exception ex) {
            data = str.getBytes();
        }
        return (data);

    }
    
    public Connection getConnection() throws SQLException {
        Connection conn = null;
        String url = prop.getProperty("coreService.url");
        String dbuser = prop.getProperty("coreService.dbuser");
        String dbpassword = dec( prop.getProperty("coreService.dbpassword") );
        conn = DriverManager.getConnection(url,dbuser,dbpassword);
        
        return conn;
    }
    
    public int obtieneUltimoFolio() {
        int ultimoFolio = 0;
        try {
            Connection con = getConnection();
            String  qry1 = "SELECT MAX(FOLIO) ";
            qry1 += "FROM MIS_COB_INVESTIGACION ";
            PreparedStatement ps1 = con.prepareStatement(qry1);
            try {
                ResultSet rs = ps1.executeQuery();
                while(rs.next()) {
                    ultimoFolio = rs.getInt(1);
                }
            }catch(SQLException er){
                er.printStackTrace();
            }
            ps1.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return ultimoFolio;
    }
    
    private static byte[] cipherStart() {
        byte[] keyBytes = null;
        try {
            final MessageDigest md = MessageDigest.getInstance("md5");
            final byte[] digestOf = md.digest("183986083ce01e4d9a9953c4c3673abe".getBytes("utf-8"));
            keyBytes = Arrays.copyOf(digestOf, 24);
            for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return keyBytes;
    }
    
    public static String enc(String in) {
        String res = null;
        try {
            final SecretKey key = new SecretKeySpec(cipherStart(), "DESede");
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            final byte[] plainTextBytes = in.getBytes("utf-8");
            final byte[] cipherText = cipher.doFinal(plainTextBytes);
            res = new String( Base64Coder.encode(cipherText) );
        }catch(Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public static String dec(String in) {
        String res = null;
        try {
            final SecretKey key = new SecretKeySpec(cipherStart(), "DESede");
            final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            final Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            final byte[] plainTextBytes = Base64Coder.decode(in);;
            final byte[] cipherText = cipher.doFinal(plainTextBytes);
            res = new String( cipherText );
        }catch(Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}