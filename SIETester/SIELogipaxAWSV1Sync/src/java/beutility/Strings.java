package beutility;

import java.io.*;

public class Strings {

    public static final String zeros = "000000000000000000000000000000";

    public static String bytestoString(byte data[]) {
        if (data == null)
            return("");
        char dataChar[] = new char[data.length];
        for (int i=0;i<data.length;i++) {
            dataChar[i]= (char)data[i];
        }
        return(String.copyValueOf(dataChar));
    }

    public static byte [] stringtoBytes(String data) {
        if ((data == null) || (data.length() == 0))
            return(null);
        byte dataByte[] = new byte[data.length()];
        for (int i=0;i<data.length();i++) {
            dataByte[i]= (byte)data.charAt(i);
        }
        return(dataByte);
    }
    
    public static String toString(int v, int size) {
        String vStr = Integer.toString(v);
        if (vStr.length() < size) {
            vStr = zeros.subSequence(0, size-vStr.length())+vStr;
        }
        return(vStr);
    }
    
    public static String toString(long v, int size) {
        String vStr = Long.toString(v);
        if (vStr.length() < size) {
            vStr = zeros.subSequence(0, size-vStr.length())+vStr;
        }
        return(vStr);
    }

    public static String toString(double v, int desc) {
        String vStr = Double.toString(v);
        int index = vStr.indexOf('.');
        if (index >= 0) {
            int d = vStr.length()-index; //xx
        }
        return(vStr);
    }
    
    public static String getNumericString(String val) {
        String val2 = val;
        if (val == null)
            val2 = "0";
        while ((val2.length() > 0) && (val2.charAt(0) == ' ')) {
            val2 = val2.substring(1);
        }
        while ((val2.length() > 0) && (val2.charAt(val2.length()-1) == ' ')) {
            val2 = val2.substring(0, val2.length()-1);
        }
        if (val2.length() == 0) {
            val2 = "0";
        }
        return(val2);
    }
    
    public static int getInt(String val) {
        int valInt = 0;
        try {
            valInt = Integer.parseInt(getNumericString(val));
        } catch (Exception ex) {
            System.err.println("parse err="+ex.toString());
        }
        return(valInt);
    }
    
    public static long getLong(String val) {
        long valLong = 0;
        try {
            valLong = Long.parseLong(getNumericString(val));
        } catch (Exception ex) {
            System.err.println("parse err="+ex.toString());
        }
        return(valLong);
    }
    public static double getDouble(String val) {
        double valDouble = 0;
        try {
            valDouble = Double.parseDouble(getNumericString(val));
        } catch (Exception ex) {
            System.err.println("parse err="+ex.toString());
        }
        return(valDouble);
    }
            
    public static boolean getBoolean(String val) {
        if (val == null)
            return(false);
        return(val.equalsIgnoreCase("true") || val.equalsIgnoreCase("1"));
    }
    
    public static String getString(String value) {
        if (value == null)
            return("");
        return value;
    }
    
    public static String[] tokenizer(String value, String delimiter) {
        java.util.StringTokenizer st = new java.util.StringTokenizer(value, delimiter);
        String lines[] = new String[st.countTokens()];
        int counter = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token != null) {
                if (token.equals("_NULL"))
                    token = null;
                else
                if (token.equals("_BLANK"))
                    token = "";
            }
            lines[counter++] = token;
        }
        return(lines);
    }            
    
    public static int[] getInts(String valuesStr[]) {
        int values[] = new int[valuesStr.length];
        for (int i=0;i<values.length;i++) {
            values[i] = getInt(valuesStr[i]);
        }
        return(values);
    }
    
    public static double[] getDoubles(String valuesStr[]) {
        double values[] = new double[valuesStr.length];
        for (int i=0;i<values.length;i++) {
            values[i] = getDouble(valuesStr[i]);
        }
        return(values);
    }

    public static boolean[] getBooleans(String valuesStr[]) {
        boolean values[] = new boolean[valuesStr.length];
        for (int i=0;i<values.length;i++) {
            values[i] = getBoolean(valuesStr[i]);
        }
        return(values);
    }
    
/////////////////////
    public static String[] getLines(String message) {
        String message0 = message.replaceAll("\r\n", "\n");
        String message1 = message0.replaceAll("\r", "\n");
        java.util.StringTokenizer st = new java.util.StringTokenizer(message1, "\n");
        String lines[] = new String[st.countTokens()];
        int counter = 0;
        while (st.hasMoreTokens()) {
            lines[counter++] = st.nextToken();
        }
        return(lines);
    }
    
    public static String[] appendMinLines(String[] message, int size) {
        if (message.length >= size)
            return(message);
        String[] message2 = new String[size];
        System.arraycopy(message, 0, message2, 0, message.length);
        return(message2);
    }
    
    // elimina lineas sin inicio :
    
    public static String[] trimBodyNullLines(String[] message) {
        for (int i=0;i<message.length;i++) {
            if (message[i] != null) {
                while ((message[i].length() > 0) && (message[i].charAt(message[i].length()-1) == ' ')) {
                    message[i] = message[i].substring(0, message[i].length()-1);
                }
            } 
            if ((message[i] != null) && (message[i].length() > 0) && 
                (i > 0) && (message[i].charAt(0) != ':')) {
                message[i-1] += " "+message[i];
                message[i] = "";
            }
            if ((message[i] == null) || (message[i].length() == 0)) {
                String message2[] = new String[message.length-1];
                if (i > 0) {
                    System.arraycopy(message, 0, message2, 0, i);
                }
                if ((message.length-i)-1 > 0) {
                    System.arraycopy(message, i+1, message2, i, (message.length-i)-1);
                }
                message = message2;
                --i;
            }
        }
        return(message);
    }

    public static java.util.Properties loadProperties(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
        java.util.Properties propertiesConfiguration= new java.util.Properties();
        try {
            propertiesConfiguration.load(dis);
        } catch (IOException e) {
        }
        finally {
            try { dis.close(); } catch (Exception e) { }
            try { bais.close(); } catch (Exception e) { }
        }
        return(propertiesConfiguration);
    }
///////////////
    public static char charEq(char ch) {
//        switch (ch) {
//        case '@': return('-');
//        case '&': return('-');
////        
////        case '???': return('a');
////        case '???': return('e');
////        case '???': return('i');
////        case '???': return('o');
////        case '???': return('u');
////        case '???': return('n');
////
////        case 'A': return('A');
////        case '???': return('E');
////        case 'I': return('I');
////        case 'O': return('O');
////        case 'U': return('U');
////        case '???': return('N');
//        }
//        if (ch < ' ') {
//            return('*');
//        }
//        if (ch > 'z') {
//            return('*');
//        }
        return(ch);
    }
    
    public static String validString(String value) {
        int length = value.length();
        char chars[] = new char[length];
        for (int i=0;i<length;i++) {
            chars[i] = charEq(value.charAt(i));
        }
        return(String.valueOf(chars));
    }

    public static char[] appendString(char chars[], int inx, String value) {
        char chars2[] = new char[chars.length+value.length()-1];
        System.arraycopy(chars, 0, chars2, 0, chars.length);
        char val[] = value.toCharArray();
        System.arraycopy(val, 0, chars2, inx, val.length);
        return(chars2);
    }
    
    public static String validToXML(String value) {
        char chars[] = value.toCharArray();
        char chars2[] = new char[chars.length];
        int inx = 0;
        for (int i=0;i<chars.length;i++) {
            String val = null;
            switch(chars[i]) {
            case '<':
                        val = "&lt;";
                        break;
            case '&':
                        val = "&amp;";
                        break;
            case '>':
                        val = "&gt;";
                        break;
            case '\"':
                        val = "&quot;";
                        break;
            case '\'':
                        val = "&apos;";
                        break;
/*
            case '<':
                        val = "(1234)";
                        break;
            case '&':
                        val = "(2345)";
                        break;
            case '>':
                        val = "(3456)";
                        break;
            case '\"':
                        val = "(4567)";
                        break;
            case '\'':
                        val = "(5678)";
                        break;
*/                
            default:
                        chars2[inx++] = chars[i];
                        break;
            }
            if (val != null) {
                chars2 = appendString(chars2, inx, val);
                inx += val.length();
            }
        }
        return(String.valueOf(chars2));
    }

    public static String validFromXML(String value) {
        char chars[] = value.toCharArray();
        char chars2[] = new char[chars.length];
        int inx = 0;
        for (int i=0;i<chars.length;i++) {
            if ((chars[i] == '(') && (i+5 < chars.length)) {
                String val = new String(chars, i, 6);
                i += 5;
                if (val.equals("(1234)"))
                    chars2[inx++] = '<';
                else if (val.equals("(2345)"))
                    chars2[inx++] = '&';
                else if (val.equals("(3456)"))
                    chars2[inx++] = '>';
                else if (val.equals("(4567)"))
                    chars2[inx++] = '\"';
                else if (val.equals("(4567)"))
                    chars2[inx++] = '\'';
                else if (val.equals("&apos"))
                    chars2[inx++] = '\'';
                else {
                    chars2[inx++] = chars[i];
                    i += 5;
                } 
            } else {
                chars2[inx++] = chars[i];
            }
        }
        return(String.valueOf(chars2, 0, inx));
    }
    public static String getString(byte[] data) {
        String str = new String(data);
        try {
            str = new String(data, "UTF-8");
        } catch(Exception ex) {
            
        }
        return(str);
    }
    public static String getString(Object data) {
        if (data == null)
            return("");
        if (data instanceof byte[])
            return(getString((byte[])data));
        if (data instanceof String)
            return((String)data);
        return(data.toString());
    }
    public static byte[] getBytes(String data) {
        byte[] byt = data.getBytes();
        try {
            byt = data.getBytes("UTF-8");
        } catch(Exception ex) {
        }
        return(byt);
    }
    
    private static final String lineSeparator = "#";
    private static final String itemSeparator = "$";
    
    public static java.util.Properties getPropertiesFromObj(Object propertiesObj) {
        String propertiesStr = "";
        if (propertiesObj == null) {
            propertiesObj = "";
        }
        if (propertiesObj instanceof byte[]) {
            try {
                propertiesStr = new String((byte[])propertiesObj, "UTF-8");
            } catch (java.io.UnsupportedEncodingException ex) {
                propertiesStr = new String((byte[])propertiesObj);
            }
            //return(Strings.loadProperties((byte[])propertiesObj));
        } else if (propertiesObj instanceof String) {
            propertiesStr = (String)propertiesObj;
        } else {
            propertiesStr = propertiesObj.toString();
        }
        java.util.Properties propertiesSelectionRec;
        if ((propertiesStr != null) && (propertiesStr.length() > 0)) {
            if (propertiesStr.indexOf("\n") < 0) {
                propertiesStr = propertiesStr.replaceAll(lineSeparator,"\n");
            }
            propertiesSelectionRec = Strings.loadProperties(propertiesStr.getBytes());        
        } else {
            propertiesSelectionRec = new java.util.Properties();
        }
        return(propertiesSelectionRec);
    }
    
    public static  Object getPropertiesToObj(java.util.Properties properties) {
        String data = "";
        for (java.util.Enumeration e = properties.keys(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            String value = properties.getProperty(key);
            if (value != null) {
                data += key+"="+value+"\n";
            }
        }
        byte[] bdata;
        try {
            bdata = data.getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException ex) {
            bdata = data.getBytes();
        }
        return(bdata);
    }

    public static  String getPropertiesToString(Object properties) {
        return(getPropertiesToString(getPropertiesFromObj(properties)));
    }
    
    public static  String getPropertiesToString(java.util.Properties properties) {
        String data = "";
        for (java.util.Enumeration e = properties.keys(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            String value = properties.getProperty(key);
            if (value != null) {
                data += key+"="+value+lineSeparator;
            }
        }
        return(data);
    }
    
    
    public static String getArray(String values[]) {
        String value = "";
        for (int ii=0;ii<values.length;ii++) {
            if (ii > 0) {
                value += itemSeparator;
            }
            if (values[ii] == null)
                value += "_NULL";
            else
            if (values[ii].length() == 0)
                value += "_BLANK";
            else
            value += values[ii];
        }
        return(value);
    }

    public static String getArray(int values[]) {
        String value = "";
        for (int ii=0;ii<values.length;ii++) {
            if (ii > 0) {
                value += itemSeparator;
            }
            value += Integer.toString(values[ii]);
        }
        return(value);
    }

    public static String getArray(boolean values[]) {
        String value = "";
        for (int ii=0;ii<values.length;ii++) {
            if (ii > 0) {
                value += itemSeparator;
            }
            value += values[ii]?"1":"0";
        }
        return(value);
    }
    
    public static String[] toArray(String value) {
        return(Strings.tokenizer(value, itemSeparator));        
    }
    
    public static String getDoubleString(double value, String formatStr) {
        java.text.DecimalFormat df = new java.text.DecimalFormat(formatStr);
        return(df.format(value));
    }
    
}
