package backend.card.io;

import backend.BEObject;
import backend.BEServletInterfase;
import backend.catalogs2.db.DB2CatList;
import backend.card.def.DefDBCardSuc;
import backend.security.SecuritySession;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.shared.objects.card.piz.MCEjecutivo;
import mx.logipax.shared.objects.card.piz.MCEjecutivos;
import mx.logipax.shared.objects.card.piz.MCEjecutivoIn;
import mx.logipax.utility.MD5;
import mx.logipax.utility.Utility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class BEMCEjecutivos implements BEObject {

    final BEServletInterfase servlet;
    DefDBCardSuc db_ejecutivo;

    public BEMCEjecutivos(BEServletInterfase servlet) {
        this.servlet = servlet;
        db_ejecutivo = new DefDBCardSuc(servlet);
    }

    @Override
    public boolean match(String command) {
        return command.equals("mcejecutivo");
    }

    @Override
    public String getMime() {
        return "text";
    }

    @Override
    public String[] responseText(String command, JSONObject input) {
        MCEjecutivoIn in = new MCEjecutivoIn(input);
        SecuritySession session = (SecuritySession) servlet.securitySession(in.getTicket());
        if (session == null) {
            return new String[]{null, null, "Desconectado"};
        }
        String function = in.getFunction();
        if (function == null || function.length() == 0) {
            return new String[]{null, null, "Función nula"};
        }
        String semanaId = in.ejecutivo.getSemanaId();
        String ejecutivoId = in.ejecutivo.getEjecutivoId();
        String rutaId = in.ejecutivo.ruta.getSucursalId();
        String rutacreditoId = in.ejecutivo.ruta.rutacredito.getCreditoId();
//        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, db_ruta);
//        MCEjecutivo asignacion = in.ejecutivo;
        if (function.equals(DlgProcessing.EDT)) {
 //           return updateMCEjecutivo(session, semanaId, creditoId, ejecutivoId, asignacion, db_cat);
        } else if (function.equals(DlgProcessing.ADD)) {
 //           return updateMCEjecutivo(session, semanaId, creditoId, ejecutivoId, asignacion, db_cat);
        } else if (function.equals(DlgProcessing.DEL)) {
 //           return updateMCEjecutivo(session, semanaId, creditoId, ejecutivoId, asignacion, db_cat);
        } else if (function.equals(DlgProcessing.SHW)) {
 //           return getMCEjecutivo(semanaId, ejecutivoId, asignacion, db_cat);
        } else 
        if (function.equals(DlgProcessing.LST)) {
            MCEjecutivos mesacontrol = getMCEjecutivos(semanaId, ejecutivoId, rutaId, rutacreditoId);
            return new String[]{mesacontrol.json().toString(), null, null};
        }
        return new String[]{null, null, "Función invalida"};
    }
    
    public MCEjecutivos getMCEjecutivos(String semanaId, String ejecutivoId, String rutaId, String rutacreditoId) {
        MCEjecutivos out = new MCEjecutivos(new JSONObject());
        out.setEjecutivoId(ejecutivoId);
        String where = "";
        if (ejecutivoId != null && ejecutivoId.length() > 0) {
            where += "a.ejecutivo_id = \'" + ejecutivoId + "\' and a.semana_id = \'" + semanaId + "\'";
        } else {
            return out;
        }
////        String tables = db_ruta.tableModelName+" a";
////        String tabla = "ejecutivo_"+ejecutivoId+"_"+semanaId;
////        String keyId = tabla + "_" + MD5.getMD5(tables + where);
////        byte[] bresult = servlet.getCache("credits", keyId);
////        if (bresult != null) {
////            String jsontxt = Utility.toString(bresult);
////            JSONObject jobject = new JSONObject(new JSONTokener(jsontxt));
////            return new MCEjecutivos(jobject);
////        }
//        String fields[] = new String[]{"a.credito_id", "a.sucursal_id", "a.segmento", "a.cap_vencido", "a.int_vencido", "a.int_moratorio"};
//        Class classes[] = new Class[]{String.class, String.class, String.class, Double.class, Double.class, Double.class};
//        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, tables, where);
//        java.util.Vector data2 = db_list2.getPage();
//        int size2 = data2.size();
//        JSONArray oejecutivos = new JSONArray();
//        for (int i = 0; i < size2; i++) {
//            Object row[] = (Object[]) data2.elementAt(i);
//            int inx = 0;
//            MCEjecutivo ejecutivo = new MCEjecutivo(new JSONObject());
//            ejecutivo.setSemanaId(semanaId);
//            ejecutivo.setEjecutivoId(ejecutivoId);
//            ejecutivo.setCreditoId((String) row[inx++]);
//            ejecutivo.setSucursalId((String) row[inx++]);
//            ejecutivo.setSegmento((String) row[inx++]);
//            ejecutivo.setCapVencido((Double) row[inx++]);
//            ejecutivo.setIntVencido((Double) row[inx++]);
//            ejecutivo.setIntMoratorio((Double) row[inx++]);
//            JSONArray orutas = new JSONArray();
//            ejecutivo.update(orutas);
//            oejecutivos.put(ejecutivo.json());
//        }
//        out.update(oejecutivos);
//        servlet.setCache("credits", keyId, Utility.toBytes(out.json().toString()));
        return out;
    }
//    public MCEjecutivos getCreditosSucursal(String semanaId, String sucursalId) {
//        MCEjecutivos out = new MCEjecutivos(new JSONObject());
//        out.setSucursalId(sucursalId);
//        out.setEjecutivoId("00000000");
//        String where = "a.sucursal_id = \'" + sucursalId + "\'";
//        String tables = db_ejecutivo.tableModelName+" a left join "+db_ruta.tableModelName+" b on b.credito_id = a.credito_id and b.semana_id = \'"+semanaId+"\'";
//        String tabla = "sucursal_"+sucursalId+"_"+semanaId;
//        String keyId = tabla + "_" + MD5.getMD5(tables + where);
//        byte[] bresult = servlet.getCache("credits", keyId);
//        if (bresult != null) {
//            String jsontxt = Utility.toString(bresult);
//            JSONObject jobject = new JSONObject(new JSONTokener(jsontxt));
//            return new MCEjecutivos(jobject);
//        }
//   
//        String fields[] = new String[]{"a.credito_id", "b.ejecutivo_id", "b.segmento", "b.cap_vencido", "b.int_vencido", "b.int_moratorio"};
//        Class classes[] = new Class[]{String.class, String.class, String.class, Double.class, Double.class, Double.class};
//        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, tables, where);
//        java.util.Vector data2 = db_list2.getPage();
//        int size2 = data2.size();
//        JSONArray oejecutivos = new JSONArray();
//        for (int i = 0; i < size2; i++) {
//            Object row[] = (Object[]) data2.elementAt(i);
//            int inx = 0;
//            MCEjecutivo asignacion = new MCEjecutivo(new JSONObject());
//            asignacion.setCreditoId((String) row[inx++]);
//            asignacion.setEjecutivoId((String) row[inx++]);
//            asignacion.setSemanaId(semanaId);
//            asignacion.setSucursalId(sucursalId);
//            asignacion.setSegmento((String) row[inx++]);
//            asignacion.setCapVencido((Double) row[inx++]);
//            asignacion.setIntVencido((Double) row[inx++]);
//            asignacion.setIntMoratorio((Double) row[inx++]);
//            asignacion.update();
//            oejecutivos.put(asignacion.json());
//        }
//        out.update(oejecutivos);
//        servlet.setCache("credits", keyId, Utility.toBytes(out.json().toString()));
//        return out;
//    }

    @Override
    public byte[] responseBytes(String command, JSONObject input) {
        return null;
    }
}
