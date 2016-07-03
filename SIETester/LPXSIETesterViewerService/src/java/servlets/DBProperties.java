/*
 Derechos Reservados (c)
 Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
 2009, 2010, 2011, 2012, 2013, 2014
 logipax Marca Registrada (R)  2003, 2014
 */
package servlets;

public class DBProperties {

    final String path;
    final java.util.Properties properties;

    public DBProperties(String[][] values) {
        path = "";
        properties = new java.util.Properties();
        if (values == null) {
            return;
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null || values[i].length < 2) {
                continue;
            }
            properties.setProperty(values[i][0], values[i][1]);
        }
    }

    public DBProperties(String path) {
        this.path = path;
        java.util.Properties dbProps = new java.util.Properties();
        properties = load(dbProps, path);
    }

    public DBProperties(java.util.Properties dbProps, String path) {
        this.path = path;
        properties = load(dbProps, path);
    }

    public String getId() {
        String id = properties.getProperty("id");
        if (id == null) {
            id = "NULL";
        }
        return (id);
    }

    public String getName() {
        String name = properties.getProperty("name");
        if (name == null) {
            name = "NULL";
        }
        String user = properties.getProperty("local.user");
        if (user == null) {
            user = "NULL";
        }
        return (name + "(" + user + ")");
    }

    public java.util.Properties getProperties() {
        return (properties);
    }

    public static java.util.Properties load(java.util.Properties dbProps, String path) {
        java.io.InputStream is = null;
        //System.out.println("load db.properties="+path);
        try {
            is = new java.io.FileInputStream(new java.io.File(path));
            java.util.Properties props = new java.util.Properties();
            props.load(is);
            //System.out.println("load db.properties");
            for (java.util.Enumeration e = props.keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                String value = props.getProperty(key);
                //System.out.println("key="+key+" value="+value);
                //System.setProperty(key, value);
                dbProps.setProperty(key, value);
            }
        } catch (Exception ex) {
            System.err.println("Can't read the properties file. "
                    + "Make sure .properties is in the CLASSPATH" + path);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                }
            }
        }
        //System.out.println("logipax.properties loaded");
        return (dbProps);
    }

}
