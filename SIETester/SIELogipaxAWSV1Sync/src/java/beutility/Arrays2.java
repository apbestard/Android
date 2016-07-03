package beutility;

public class Arrays2 {
    
    public Arrays2() {
    }
    
////////////////////////////
    public static int getIndexList(int[] list, int value) {
        int index = -1;
        for (int i=0;i<list.length;i++) {
            if (value == list[i]) {
                index = i;
                break;
            }
        }
        return(index);
    }
    
    public static int[] append(int[] list, int value) {
        int list2[] = new int[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = value;
        return(list2);
    }

    public static boolean[] append(boolean[] list, boolean value) {
        boolean list2[] = new boolean[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = value;
        return(list2);
    }
    
    public static String[] append(String[] list, String value) {
        String list2[] = new String[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = value;
        return(list2);
    }

    public static int[][] append(int[][] list, int[] value) {
        int list2[][] = new int[list.length+1][];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = value;
        return(list2);
    }
    
    public static String[][] append(String[][] list, String[] value) {
        String list2[][] = new String[list.length+1][];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = value;
        return(list2);
    }

    public static Class[] append(Class[] list, Class value) {
        Class list2[] = new Class[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = value;
        return(list2);
    }
    
    public static Object[] append(Object[] list, Object value) {
        Object list2[] = new Object[list.length+1];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = value;
        return(list2);
    }

    public static Object[][] append(Object[][] list, Object[] value) {
        Object list2[][] = new Object[list.length+1][];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = value;
        return(list2);
    }

    public static String[][][] append(String[][][] list, String[][] value) {
        String list2[][][] = new String[list.length+1][][];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length] = value;
        return(list2);
    }
    
    public static int[][] append2(int[][] list, int id, int id2) {
        for (int i=0;i<list.length;i++) {
            if ((list[i][0] == id) && (list[i][1] == id2))  {
                return(list);
            }
        }
        int list2[][] = new int[list.length+1][2];
        System.arraycopy(list, 0, list2, 0, list.length);
        list2[list.length][0] = id;
        list2[list.length][1] = id2;
        return(list2);
    }

    
    public static String[] join(String[] list, String[] value) {
        String list2[] = new String[list.length+value.length];
        System.arraycopy(list, 0, list2, 0, list.length);
        System.arraycopy(value, 0, list2, list.length, value.length);
        return(list2);
    }
    
    
    public static int[] createList(boolean check[], String names[]) {
        if (check.length != names.length) {
            int list[] = new int[names.length];
            for (int i=0;i<names.length;i++) {
                list[i] = i;
            }
            return(list);
        } 
        int list[] = new int[0];
        for (int i=0;i<names.length;i++) {
            if (check[i]) {
                list = Arrays2.append(list, i);
            }
        }
        return(list);
    }
    
    
}
