package backend.card.io;

import backend.BEObject;
import backend.BEServletInterfase;
import backend.card.def.cat.DefDBCATDepartamento;
import backend.card.def.cat.DefDBCATEquipo;
import backend.card.def.cat.DefDBCATFormato;
import backend.card.def.cat.DefDBCATMismas;
import backend.card.def.cat.DefDBCATPuesto;
import backend.card.def.cat.DefDBCATResponsable;
import backend.card.def.cat.DefDBCATUserVisor;
import backend.catalogs2.db.DB2CatList;
import backend.database.def.DefDBBase;
import backend.security.SecuritySession;
import backend.security.def.DefDBProfile;
import mx.logipax.shared.objects.cat.Tabla;
import mx.logipax.shared.objects.cat.TablaIn;
import mx.logipax.utility.MD5;
import mx.logipax.utility.Utility;
import java.util.Hashtable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class BECardTabla implements BEObject {

    final BEServletInterfase servlet;
    //DefDBZona db_tabla;

    public BECardTabla(BEServletInterfase servlet) {
        this.servlet = servlet;
        //db_tabla = new DefDBZona(servlet);
    }

    @Override
    public boolean match(String command) {
        return command.equals("cardtabla");
    }

    @Override
    public String getMime() {
        return "text";
    }

    @Override
    public String[] responseText(String command, JSONObject input) {
        TablaIn in = new TablaIn(input);
        SecuritySession session = (SecuritySession) servlet.securitySession(in.getTicket());
        if (session == null) {
            return new String[]{null, null, "Desconectado"};
        }
        String tabla = in.getTabla();
        boolean nombres = in.getNombres();
        String nivel1 = in.getNivel1();
        String nivel2 = in.getNivel2();
        String semanaId = in.getSemanaId();
        String sucursalId = in.getSucursalId();
        if (tabla == null || tabla.length() == 0) {
            return new String[]{null, null, "Tabla nulo"};
        }
        Tabla out = null;
//        if (tabla.equals("ejecutivo")) {
//            if (Strings.getInt(nivel1) > 0 && nivel2 != null && nivel2.length() > 0) {
//                if (Strings.getInt(nivel1) == 1) {
//                    out = getEjecutivosZona(nivel2, new DefDBEjecutivo(servlet), new DefDBEjecutivoSucursal(servlet), new DefDBSucursal(servlet), new DefDBCreditoAsigna(servlet));
//                } else {
//                    out = getEjecutivosSucursal(nivel2, new DefDBEjecutivo(servlet), new DefDBEjecutivoSucursal(servlet), new DefDBCreditoAsigna(servlet));
//                }
//            } else {
//                out = getEjecutivos(new DefDBEjecutivo(servlet), new DefDBCreditoAsigna(servlet));
//            }
//            return new String[]{out.json().toString(), null, null};
//        } else if(tabla.equals("ejecutivoEncargo")){
//            out = getEjecutivoEncargo(new DefDBEncargo(servlet), new DefDBSemana(servlet), sucursalId, new DefDBEjecutivo(servlet), new DefDBEncargoSemana(servlet));
//            return new String[]{out.json().toString(), null, null};
//        }else if (tabla.equals("puesto")) {
//            out = getTabla(nombres, tabla, 0, 0, "estatus = \'A\'", new DefDBPuesto(servlet));
//            return new String[]{out.json().toString(), null, null};
//        } else 
        if (tabla.equals("nivel")) {
            out = getMemTabla(nombres, tabla, new String[][]{
                {"NA", "Todo"},
                {"REGIONAL", "Regional"},
                {"SUCURSAL", "Sucursal"},
            });
            return new String[]{out.json().toString(), null, null};
        } else if (tabla.equals("nivelvalor")) {
            out = getMemTabla(nombres, tabla, new String[][]{
                {"NA", "Todo"},
                {"01", "Sucursal 01"},
                {"02", "Sucursal 02"},
                {"03", "Sucursal 03"},
                {"04", "Sucursal 04"},
            });
            return new String[]{out.json().toString(), null, null};
        } else if (tabla.equals("responsable")) {
            out = getTabla(nombres, tabla, 0, 1, "estatus = \'A\'", new DefDBCATResponsable(servlet));
            return new String[]{out.json().toString(), null, null};
        } else if (tabla.equals("uservisor")) {
            out = getTabla(nombres, tabla, 0, 1, "estatus = \'A\'", new DefDBCATUserVisor(servlet));
            return new String[]{out.json().toString(), null, null};
        } else if (tabla.equals("formato")) {
            out = getTabla(nombres, tabla, 0, 1, "estatus = \'A\'", new DefDBCATFormato(servlet));
            return new String[]{out.json().toString(), null, null};
        } else if (tabla.equals("mismas")) {
            out = getTabla(nombres, tabla, 0, 1, "estatus = \'A\'", new DefDBCATMismas(servlet));
            return new String[]{out.json().toString(), null, null};
        } else if (tabla.equals("departamento")) {
            out = getTabla(nombres, tabla, 0, 1, "estatus = \'A\'", new DefDBCATDepartamento(servlet));
            return new String[]{out.json().toString(), null, null};
        } else if (tabla.equals("puesto")) {
            out = getTabla(nombres, tabla, 0, 1, "estatus = \'A\'", new DefDBCATPuesto(servlet));
            return new String[]{out.json().toString(), null, null};
        } else if (tabla.equals("equipo")) {
            out = getTabla(nombres, tabla, 0, 1, "estatus = \'A\'", new DefDBCATEquipo(servlet));
            return new String[]{out.json().toString(), null, null};
        }
//        
//        if (tabla.equals("ejecutivo")) {
//            if (Strings.getInt(nivel1) > 0 && nivel2 != null && nivel2.length() > 0) {
//                if (Strings.getInt(nivel1) == 1) {
//                    out = getEjecutivosZona(nivel2, new DefDBEjecutivo(servlet), new DefDBEjecutivoSucursal(servlet), new DefDBSucursal(servlet), new DefDBCreditoAsigna(servlet));
//                } else {
//                    out = getEjecutivosSucursal(nivel2, new DefDBEjecutivo(servlet), new DefDBEjecutivoSucursal(servlet), new DefDBCreditoAsigna(servlet));
//                }
//            } else {
//                out = getEjecutivos(new DefDBEjecutivo(servlet), new DefDBCreditoAsigna(servlet));
//            }
//            return new String[]{out.json().toString(), null, null};
//        } else if(tabla.equals("ejecutivoEncargo")){
//            out = getEjecutivoEncargo(new DefDBEncargo(servlet), new DefDBSemana(servlet), sucursalId, new DefDBEjecutivo(servlet), new DefDBEncargoSemana(servlet));
//            return new String[]{out.json().toString(), null, null};
//        }else if (tabla.equals("puesto")) {
//            out = getTabla(nombres, tabla, 0, 0, "estatus = \'A\'", new DefDBPuesto(servlet));
//            return new String[]{out.json().toString(), null, null};
//        } else if (tabla.equals("region")) {
//            out = getTabla(nombres, tabla, 0, 0, "estatus = \'A\'", new DefDBRegion(servlet));
//            return new String[]{out.json().toString(), null, null};
//        } else if (tabla.equals("sucursal")) {
//            out = getTabla(nombres, tabla, 0, 0, "sucursal_tipo_id = \'S\' and estatus = \'A\'", new DefDBSucursal(servlet));
//            return new String[]{out.json().toString(), null, null};
//        } else if (tabla.equals("zona")) {
//            out = getTabla(nombres, tabla, 0, 0, "estatus = \'A\'", new DefDBZona(servlet));
//            return new String[]{out.json().toString(), null, null};
//        }
        if (out != null) {
            return new String[]{out.json().toString(), null, null};
        } else {
            return new String[]{null, null, "Tabla invalida"};
        }
    }

    public Tabla getMemTabla(boolean nombres, String tabla, String[][] valores) {
        Tabla out = new Tabla(new JSONObject());
        JSONArray jids = new JSONArray();
        JSONArray jnombres = new JSONArray();
        out.setTabla(tabla);
        for (int i = 0; i < valores.length; i++) {
            int inx = 0;
            jids.put(valores[i][0]);
            if (nombres) {
                jnombres.put(valores[i][1]);
            }
        }
        out.update(jids, jnombres);
        return out;
    }
    
    public Tabla getTabla(boolean nombres, String tabla, int kinx, int finx, String where, DefDBBase db_tabla) {
        String keyId = tabla + "_" + MD5.getMD5(where);
        byte[] bresult = servlet.getCache("dtablas", keyId);
        if (bresult != null) {
            String jsontxt = Utility.toString(bresult);
            JSONObject jobject = new JSONObject(new JSONTokener(jsontxt));
            return new Tabla(jobject);
        }
        String fields[] = new String[nombres ? 2 : 1];
        fields[0] = db_tabla.getKeyName()[kinx];
        if (nombres) {
            fields[1] = db_tabla.getFieldsName()[finx];
        }
        Class classes[] = new Class[nombres ? 2 : 1];
        classes[0] = db_tabla.getKeyClasses()[kinx];
        if (nombres) {
            classes[1] = db_tabla.getFieldsClasses()[finx];
        }
        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_tabla.tableModelName, where, fields[0]);
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        Tabla out = new Tabla(new JSONObject());
        JSONArray jids = new JSONArray();
        JSONArray jnombres = new JSONArray();
        out.setTabla(tabla);
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            jids.put(row[inx++].toString());
            if (nombres) {
                jnombres.put(row[inx++].toString());
            }
        }
        out.update(jids, jnombres);
        servlet.setCache("dtablas", keyId, Utility.toBytes(out.json().toString()));
        return out;
    }

    public Tabla getEjecutivosSucursal(String sucursalId, DefDBBase db_ejecutivo, DefDBBase db_ejecutivosuc, DefDBBase db_creditoasigna) {
        String where = "b.sucursal_id = \'" + sucursalId + "\' and a.ejecutivo_id is not null and a.estatus = \'A\'";
        String tables = db_ejecutivosuc.tableModelName + " b left join " + db_ejecutivo.tableModelName + " a on a.ejecutivo_id = b.ejecutivo_id";
        String tabla = "ejecutivo_";
        String keyId = tabla + "_" + MD5.getMD5(tables + where);
        byte[] bresult = servlet.getCache("dtablas", keyId);
        if (bresult != null) {
            String jsontxt = Utility.toString(bresult);
            JSONObject jobject = new JSONObject(new JSONTokener(jsontxt));
            return new Tabla(jobject);
        }
        String fields[] = new String[2];
        fields[0] = "a.ejecutivo_id";
        fields[1] = "a.nombre";
        Class classes[] = new Class[2];
        classes[0] = String.class;
        classes[1] = String.class;
        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, tables, where, fields[0]);
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        Tabla out = new Tabla(new JSONObject());
        JSONArray jids = new JSONArray();
        JSONArray jnombres = new JSONArray();
        Hashtable<String, String> ids = new Hashtable();
        out.setTabla(tabla);
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            String id = row[inx++].toString();
            String name = row[inx++].toString();
            if (ids.get(id) != null) continue;
            ids.put(id, id);
            jids.put(id);
            jnombres.put(name);
        }
        out.update(jids, jnombres);
        servlet.setCache("dtablas", keyId, Utility.toBytes(out.json().toString()));
        return out;
    }

//    public Tabla getEjecutivoEncargo(DefDBBase db_encargo, DefDBBase db_semana, String sucursalId, DefDBBase db_ejecutivo){
//        
//        BESemana bs = new BESemana(servlet);
//        String semanaId = bs.getSemanaActual();
//        
//        System.out.println("SemanaID: " + semanaId);
//        
//        String where = "a.semana_id = \'" + semanaId + "\'"; //and b.sucursal_id = \'" + sucursalId +"\'
//        String tables = db_encargoSem.tableModelName + " a inner join " + db_ejecutivo.tableModelName + " b on (a.ejecutivo_id = b.ejecutivo_id)";
//        String tabla = "ejecutivo_";
//        String keyId = tabla + "_" + MD5.getMD5(tables + where);
//        byte[] bresult = servlet.getCache("dtablas", keyId);
////        if (bresult != null) {
////            String jsontxt = Utility.toString(bresult);
////            JSONObject jobject = new JSONObject(new JSONTokener(jsontxt));
////            return new Tabla(jobject);
////        }
//        String fields[] = new String[3];
//        fields[0] = "a.encargo_id";
//        fields[1] = "b.ejecutivo_id";
//        fields[2] = "b.nombre";
//        Class classes[] = new Class[3];
//        classes[0] = String.class;
//        classes[1] = String.class;
//        classes[2] = String.class;
//                DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, tables, where, fields[0]);
//        java.util.Vector data2 = db_list2.getPage();
//        int size2 = data2.size();
//        Tabla out = new Tabla(new JSONObject());
//        JSONArray jids = new JSONArray();
//        JSONArray jnombres = new JSONArray();
//        Hashtable<String, String> ids = new Hashtable();
//        out.setTabla(tabla);
//        for (int i = 0; i < size2; i++) {
//            Object row[] = (Object[]) data2.elementAt(i);
//            int inx = 0;
//            String id = row[inx++].toString();
//            String name = row[inx++].toString();
//            String name2 = row[inx++].toString();
//            if (ids.get(id) != null) continue;
//            ids.put(id, id);
//            jids.put(id);
//            jnombres.put(name + " " + name2);
//        }
//        out.update(jids, jnombres);
//        servlet.setCache("dtablas", keyId, Utility.toBytes(out.json().toString()));
//        return out;
//    }
    
    public Tabla getEjecutivosZona(String zonaId, DefDBBase db_ejecutivo, DefDBBase db_ejecutivosuc, DefDBBase db_sucursal, DefDBBase db_creditoasigna) {
        String where = "c.zona_id = \'" + zonaId + "\' and a.ejecutivo_id is not null and a.estatus = \'A\'";
        String tables = db_sucursal.tableModelName + " c left join " + db_ejecutivosuc.tableModelName + " b on b.sucursal_id = c.sucursal_id left join " + db_ejecutivo.tableModelName + " a on a.ejecutivo_id = b.ejecutivo_id";
        String tabla = "ejecutivo_";
        String keyId = tabla + "_" + MD5.getMD5(tables + where);
        byte[] bresult = servlet.getCache("dtablas", keyId);
        if (bresult != null) {
            String jsontxt = Utility.toString(bresult);
            JSONObject jobject = new JSONObject(new JSONTokener(jsontxt));
            return new Tabla(jobject);
        }
        String fields[] = new String[2];
        fields[0] = "a.ejecutivo_id";
        fields[1] = "a.nombre";
        Class classes[] = new Class[2];
        classes[0] = String.class;
        classes[1] = String.class;
        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, tables, where, fields[0]);
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        Tabla out = new Tabla(new JSONObject());
        JSONArray jids = new JSONArray();
        JSONArray jnombres = new JSONArray();
        Hashtable<String, String> ids = new Hashtable();
        out.setTabla(tabla);
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            String id = row[inx++].toString();
            String name = row[inx++].toString();
            if (ids.get(id) != null) continue;
            ids.put(id, id);
            jids.put(id);
            jnombres.put(name);
        }
        out.update(jids, jnombres);
        servlet.setCache("dtablas", keyId, Utility.toBytes(out.json().toString()));
        return out;
    }
    public Tabla getEjecutivos(DefDBBase db_ejecutivo, DefDBBase db_creditoasigna) {
        String where = "a.ejecutivo_id != \'00000000\' and a.estatus = \'A\'";
        String tables = db_ejecutivo.tableModelName + " a";
        String tabla = "ejecutivo_";
        String keyId = tabla + "_" + MD5.getMD5(tables + where);
        byte[] bresult = servlet.getCache("dtablas", keyId);
        if (bresult != null) {
            String jsontxt = Utility.toString(bresult);
            JSONObject jobject = new JSONObject(new JSONTokener(jsontxt));
            return new Tabla(jobject);
        }
        String fields[] = new String[2];
        fields[0] = "a.ejecutivo_id";
        fields[1] = "a.nombre";
        Class classes[] = new Class[2];
        classes[0] = String.class;
        classes[1] = String.class;
        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, tables, where, fields[0]);
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        Tabla out = new Tabla(new JSONObject());
        JSONArray jids = new JSONArray();
        JSONArray jnombres = new JSONArray();
        Hashtable<String, String> ids = new Hashtable();
        out.setTabla(tabla);
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            String id = row[inx++].toString();
            String name = row[inx++].toString();
            if (ids.get(id) != null) continue;
            ids.put(id, id);
            jids.put(id);
            jnombres.put(name);
        }
        out.update(jids, jnombres);
        servlet.setCache("dtablas", keyId, Utility.toBytes(out.json().toString()));
        return out;
    }

    @Override
    public byte[] responseBytes(String command, JSONObject input) {
        return null;
    }
}
