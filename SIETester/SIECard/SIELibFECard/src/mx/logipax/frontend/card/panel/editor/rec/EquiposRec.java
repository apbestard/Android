package mx.logipax.frontend.card.panel.editor.rec;

import mx.logipax.ide.SHRObjects;

public class EquiposRec {
    private SHRObjects frame;
    private EquipoRec[] data;
    private static String headers[] = new String[]{"Departamento", "Equipo", "No Equipos"};
    private static int headersJustify[] = new int[]{-1, -1, 1};;

    public EquiposRec(SHRObjects frame) {
        this.frame = frame;
        data = new EquipoRec[0];
    }
    
    private EquipoRec[] append(EquipoRec item, EquipoRec[] list) {
        EquipoRec list2[] = new EquipoRec[list.length + 1];
        int index = list.length;
        System.arraycopy(list, 0, list2, 0, index);
        list2[index] = item;
        System.arraycopy(list, index, list2, index + 1, list.length - index);
        return list2;
    }
    
    public void dispose() {
        data = new EquipoRec[0];
    }
    public EquipoRec[] getRecords() {
        return(data);
    }
    public void move(int from, int to) {
        int index = from;
        EquipoRec page = data[from];
        EquipoRec pages2[] = new EquipoRec[data.length - 1];
        System.arraycopy(data, 0, pages2, 0, index);
        System.arraycopy(data, index + 1, pages2, index, data.length - (index + 1));
        data = pages2;

        index = to;
        pages2 = new EquipoRec[data.length + 1];
        System.arraycopy(data, 0, pages2, 0, index);
        pages2[index] = page;
        System.arraycopy(data, index, pages2, index + 1, data.length - index);
        data = pages2;
    }
    public String[] getHeaders() {
        return(headers);
    }
    public int[] getHeadersJustify() {
        return(headersJustify);
    }
    public String getValue(EquipoRec c, int col) {
        return c.getValue(col);
    }
    public void setValue(EquipoRec c, int col, Object value) {
        c.setValue(col, value);
    }
    
    public void initialize(EquipoRec m[]) {
        data = new EquipoRec[0];
        for (int i=0;i<m.length;i++) {
            insertRecord(m[i]);
        } 
        
    }
   
    public int insertRecord(EquipoRec m) {
        if (data == null) {
            return -1;
        }
        return (insertRecord(m, data.length));
    }

    public int insertRecord(EquipoRec m, int index) {
        if (data == null) {
            return -1;
        }
        if (m == null) {
            return data.length;
        }
        EquipoRec cregions2[] = new EquipoRec[data.length + 1];
        System.arraycopy(data, 0, cregions2, 0, index);
        cregions2[index] = m;
        System.arraycopy(data, index, cregions2, index + 1, data.length - index);
        data = cregions2;
        return (index);
    }

    public int find(EquipoRec m) {
        if (data == null || m == null) {
            return (-1);
        }
        for (int i = 0; i < data.length; i++) {
            if (m.getRecord().getDeptoId().equals(data[i].getRecord().getDeptoId()) &&
                m.getRecord().getEquipoId().equals(data[i].getRecord().getEquipoId())) {
                return (i);
            }
        }
        return (-1);
    }

    public int deleteRecord(EquipoRec m) {
        if (data == null) {
            return -1;
        }
        int index = find(m);
        if (index < 0) {
            return (0);
        }
        EquipoRec cregions2[] = new EquipoRec[data.length - 1];
        System.arraycopy(data, 0, cregions2, 0, index);
        System.arraycopy(data, index + 1, cregions2, index, data.length - (index + 1));
        data = cregions2;
        deleteRecord(m);
        return (index);
    }

    public int updateRecord(EquipoRec m) {
        if (data == null) {
            return -1;
        }
        int index = find(m);
        if (index < 0) {
            return (0);
        }
        data[index] = m;
        return (index);
    }
}
