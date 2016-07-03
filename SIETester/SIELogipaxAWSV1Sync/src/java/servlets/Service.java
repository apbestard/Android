package servlets;

import common.security.xml.XMLBEDirectories;
import common.security.xml.XMLFEDirectories;
import beutility.Base64Coder;

public class Service {

    private static final String HEXDIGITS = "0123456789ABCDEF";

    public static String urlEncode(String str) {
        if ((str == null) || (str.length() == 0)) return "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c)) {
                // Letter or number. No need to encode.
                sb.append(c);
            }
            else {
                // Encode it.
                int cc = (int)c;
                if (cc < 0 || cc > 255) {
                    sb.append('?'); //XXX
                }
                else {
                    sb.append('%');
                    sb.append(HEXDIGITS.charAt((cc >> 4) & 0x0f));
                    sb.append(HEXDIGITS.charAt(cc & 0x0f));
                }
            }
        }
        return sb.toString();
    }


    private static String toString(byte[] data) {
        String str = null;
        try {
            str = new String(data, "UTF-8");
        } catch(Exception ex) {
            str = new String(data);
        }
        return(str);
    }

    private static byte[] toBytes(String str) {
        byte[] data = null;
        try {
            data = str.getBytes("UTF-8");
        } catch(Exception ex) {
            data = str.getBytes();
        }
        return(data);

    }
    private static String xmlEncode(String xml, String pos) {
        if (pos == null || pos.length() == 0) {
            return(xml);
        }
//System.out.println("encode="+xml);
        byte[] data = toBytes(xml);
        byte[] xorbase = toBytes(pos);
        for (int i=0;i<data.length;i++) {
            data[i] = (byte)((int)data[i] ^ (int)xorbase[i % xorbase.length] & 0xff);
        }
        String result = String.valueOf(Base64Coder.encode(data));
//System.out.println("encode="+xml+"->"+result);
        return(result);
    }

    private static String xmlDecode(String params, String pos) {
        if (pos == null || pos.length() == 0) {
            return(params);
        }
//System.out.println("decode="+params);
        byte[] data = Base64Coder.decode(params);
        byte[] xorbase = toBytes(pos);
        for (int i=0;i<data.length;i++) {
            data[i] = (byte)((int)data[i] ^ (int)xorbase[i % xorbase.length] & 0xff);
        }
        String result = toString(data);
//System.out.println("decode="+params+"->"+result);
        return(result);
    }
    public static final String HTTP = "http://localhost:8080";
    
    static String xurl = HTTP+"/LSFSIEDW3aV0Service/DW";
    static String dispatcher = XMLBEDirectories.SERVLETNAME;
    static String version = "0.10";
    private static String getHTTPDirect(String id, String params) {
        char chars[] = null;
        byte bytes[] = null;
        String error = null;
System.out.println("xml send="+params);

        String xml = xmlEncode(params, id);
        String content =
            "version=" + urlEncode(version)+
            "&id=" + urlEncode(id)+
            "&pos=" + urlEncode(id)+
            "&command=" + urlEncode(dispatcher)+
            "&params=" + urlEncode(xml);
        try {
//System.out.println("send="+params);
                java.net.URL url = new java.net.URL(xurl+"?"+content);
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("User-Agent","SIVETA 1.0");
                connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                byte[] bcontent = toBytes(content);
                connection.setRequestProperty( "Content-Length", Integer.toString(bcontent.length));
                connection.setDoOutput(true);
                java.io.OutputStream os = connection.getOutputStream();
                java.io.DataOutputStream out = new java.io.DataOutputStream(os);
                out.write(bcontent);
                out.flush();
                String type = connection.getContentType();
                java.io.DataInputStream in = new java.io.DataInputStream(connection.getInputStream());
                byte buffer2[] = new byte[0x1ffff];
                int count = 0;
                for (;;) {
                    int c = in.read(buffer2, count, buffer2.length - count);
                    if (c < 0) break;
                    count += c;
                    if (count >= buffer2.length) break;
                }
                bytes = new byte[count];
                System.arraycopy(buffer2, 0, bytes, 0, count);
                in.close();
                if (type != null && type.indexOf("text") >= 0) {
                    try {
                        String data = xmlDecode(toString(bytes), id);
System.out.println("xml recv="+data);
                        return(data);
                    } catch(Exception ex) {
                        throw new java.io.IOException("Conexion no compatible");
                    }
                }
                return(toString(bytes));
            } catch (Exception e) {
                error = e.toString();
                System.out.println("URL Exception ="+e.toString());
                return(null);
            } finally {
            }
    }

    public static String[] getDirectories(String ticket) {
        if (ticket == null || ticket.length() == 0) {
            return(new String[0]);
        }
	String responseStr = getHTTPDirect(ticket, XMLBEDirectories.getXMLDirectories(ticket));
        XMLFEDirectories xmldata = new XMLFEDirectories();
        xmldata.parse(responseStr.toCharArray());
        String error = xmldata.getError();
        if (error != null) {
            return(new String[0]);
        }
        return(xmldata.getAccounts());//new String[]{"Datos/*"});
    }
}
