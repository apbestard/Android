package backend.card.io;

import mx.logipax.utility.Arrays2;


public class DBInterfase {
    
    
    public static Object[] getFields(String fields[], String[] kfields, Object[] kvalues, String[] ffields, Object[] fvalues) {
        if (kvalues == null || kfields == null) {
            return null;
        }
        if (fvalues == null || ffields == null) {
            return null;
        }
        
        Object[] values = new Object[0];
        for (int i = 0; i < fields.length; i++) {
            boolean found = false;
            for (int k = 0; k < kfields.length; k++) {
                if (kfields[k].equals(fields[i])) {
                    values = Arrays2.append(values, kvalues[k]);
                    found = true;
                    break;
                }
            }
            if (found) {
                continue;
            }
            for (int f = 0; f < ffields.length; f++) {
                if (ffields[f].equals(fields[i])) {
                    values = Arrays2.append(values, fvalues[f]);
                    found = true;
                    break;
                }
            }
            if (!found) {
                values = Arrays2.append(values, null);
            }
        }
        return values;
    }
    
    public static Object[][] append(Object[] values[], Object[] value) {
        Object[][]values2 = new Object[values.length + 1][];
        System.arraycopy(values, 0, values2, 0, values.length);
        values2[values.length] = value;
        return (values2);

    }
    
    public static java.util.Hashtable<String, String>[] append(java.util.Hashtable<String, String> values[], java.util.Hashtable<String, String> value) {
        java.util.Hashtable<String, String> values2[] = new java.util.Hashtable[values.length + 1];
        System.arraycopy(values, 0, values2, 0, values.length);
        values2[values.length] = value;
        return (values2);

    }
}
