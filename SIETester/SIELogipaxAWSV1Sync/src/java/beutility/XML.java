package beutility;

public class XML {

    public static String getToXML(String value) {
        if (value == null)
            return("_NULL");
        if (value.length() == 0)
            return("_BLANK");
        return(validToXML(value));
    }

    public static String getToXML(Class clase, Object value) {
        if (value == null)
            return("_NULL");
        if (clase == Integer.class)
            return(((Integer)value).toString());
        if (clase == Double.class)
            return(((Double)value).toString());
        if (clase == java.util.Date.class)
            return(Long.toString(((java.util.Date)value).getTime()));
        if (clase == java.sql.Timestamp.class)
            return(Long.toString(((java.sql.Timestamp)value).getTime()));
        if (clase == byte.class) {
            if (value instanceof byte[]) {
                char[] value2 = Base64Coder.encode((byte[])value);
                if (value2.length == 0)
                    return("_BLANK");
                return(String.valueOf(value2));
            } else 
            if (value instanceof String) {
                char[] value2 = Base64Coder.encode(Strings.getBytes((String)value));
                return(String.valueOf(value2));
            } else {
                char[] value2 = Base64Coder.encode(Strings.getBytes(value.toString()));
                return(String.valueOf(value2));
            }
        }
        return(getToXML(value.toString()));
    }
    
    public static String getFromXML(String value) {
        if (value.equals("_NULL"))
            return(null);
        if (value.equals("_BLANK"))
            return("");
        return(value);
    }

    public static String[] getFromXML(String value, String list[]) {
        String list2[] = new String[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = getFromXML(value);
        return(list2);
    }
    
    public static Object getFromXML(Class clase, String value) {
        if (clase == Integer.class) {
            return(new Integer(getInt(value)));
        }
        if (clase == Long.class) {
            return(new Long(getLong(value)));
        }
        if (clase == Double.class) {
            return(new Double(getDouble(value)));
        }
        if (clase == java.util.Date.class) {
            return(new java.util.Date(getLong(value)));
        }
        if (clase == java.sql.Timestamp.class) {
            return(new java.sql.Timestamp(getLong(value)));
        }
        if (clase == byte.class) {
            if (value.equals("_NULL"))
                return(null);
            if (value.equals("_BLANK"))
                return(new byte[0]);
            return(Base64Coder.decode(value));
        }
        return(getFromXML(value));
    }

    
    public static Object[] getFromXML(Class clase, String value, Object list[]) {
        Object list2[] = new Object[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = getFromXML(clase, value);
        return(list2);
    }
    
    public static int getInt(String value) {
        if (value.equals("_NULL"))
            return(0);
        if (value.equals("_BLANK"))
            return(0);
        return(beutility.Strings.getInt(value));
    }

    public static int[] getInt(String value, int[] list) {
        int list2[] = new int[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = getInt(value);
        return(list2);
    }
    public static long getLong(String value) {
        if (value.equals("_NULL"))
            return(0);
        if (value.equals("_BLANK"))
            return(0);
        return(beutility.Strings.getLong(value));
    }

    public static long[] getLong(String value, long[] list) {
        long list2[] = new long[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = getLong(value);
        return(list2);
    }

    public static double getDouble(String value) {
        if (value.equals("_NULL"))
            return(0);
        if (value.equals("_BLANK"))
            return(0);
        return(beutility.Strings.getDouble(value));
    }

    public static double[] getDouble(String value, double[] list) {
        double list2[] = new double[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = getDouble(value);
        return(list2);
    }
    
    public static boolean getBoolean(String value) {
        if (value.equals("_NULL"))
            return(false);
        if (value.equals("_BLANK"))
            return(false);
        return(beutility.Strings.getBoolean(value));
    }

    public static boolean[] getBoolean(String value, boolean[] list) {
        boolean list2[] = new boolean[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = getBoolean(value);
        return(list2);
    }
    
    public static Class getClassFromXML(String value) {
        if (value.indexOf("INTEGER") >= 0) {
            return(Integer.class);
        } else
        if (value.indexOf("LONG") >= 0) {
            return(Long.class);
        } else
        if (value.indexOf("DOUBLE") >= 0) {
            return(Double.class);
        } else
        if (value.indexOf("STRING") >= 0) {
            return(String.class);
        } else
        if (value.indexOf("BYTE") >= 0) {
            return(byte.class);
        } else
        if (value.indexOf("TIME") >= 0) {
            return(java.sql.Timestamp.class);
        } else
        if (value.indexOf("DATE") >= 0) {
            return(java.util.Date.class);
        }
        return(String.class);
    }

    public static Class[] getClassFromXML(String value, Class list[]) {
        Class list2[] = new Class[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = getClassFromXML(value);
        return(list2);
    }

    
    public static String getClassToXML(Class clase) {
        if (clase == Integer.class) {
            return("INTEGER");
        } 
        if (clase == Long.class) {
            return("LONG");
        } 
        if (clase == Double.class) {
            return("DOUBLE");
        } 
        if (clase == java.util.Date.class) {
            return("DATE");
        } 
        if (clase == java.sql.Timestamp.class) {
            return("TIME");
        } 
        if (clase == byte.class) {
            return("BYTE");
        } 
        if (clase == String.class) {
            return("STRING");
        } 
        return("STRING");
    }
    
    public static void noprocess(String element[], String chars) {
        String unsel = "unselected ";
        for (int i=0;i<element.length;i++) {
        unsel += "-"+element[i];
        }
        unsel += "="+chars;
        System.err.println(unsel);
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
                chars2 = beutility.Strings.appendString(chars2, inx, val);
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
                else if (val.equals("&lt;"))
                    chars2[inx++] = '<';
                else if (val.equals("&amp;"))
                    chars2[inx++] = '&';
                else if (val.equals("&gt;"))
                    chars2[inx++] = '>';
                else if (val.equals("&quot;"))
                    chars2[inx++] = '\"';
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
    
    
}
