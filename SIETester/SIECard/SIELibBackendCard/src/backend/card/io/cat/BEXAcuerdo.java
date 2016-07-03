package backend.card.io.cat;

import backend.BEObject;
import backend.BEServletInterfase;
import backend.catalogs2.db.DB2CatList;
import backend.catalogs2.db.DB2CatUpd;
import backend.card.def.DefDBCardSuc;
import backend.card.io.DBInterfase;
import backend.security.SecuritySession;
import java.util.Date;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.shared.objects.card.cat2.Ejecutivo;
import mx.logipax.shared.objects.card.cat2.Ejecutivos;
import mx.logipax.shared.objects.card.cat2.EjecutivoIn;
import mx.logipax.utility.Dates;
import org.json.JSONArray;
import org.json.JSONObject;

public class BEXAcuerdo implements BEObject {

    final BEServletInterfase servlet;
    DefDBCardSuc db_ejecutivo;
    public BEXAcuerdo(BEServletInterfase servlet) {
        this.servlet = servlet;
        db_ejecutivo = new DefDBCardSuc(servlet);
    }
    @Override
    public boolean match(String command) {
        return command.equals("ejecutivo");
    }

    @Override
    public String getMime() {
        return "text";
    }

    @Override
    public String[] responseText(String command, JSONObject input) {
        EjecutivoIn in = new EjecutivoIn(input);
        SecuritySession session = (SecuritySession) servlet.securitySession(in.getTicket());
        if (session == null) {
            return new String[]{null, null, "Desconectado"};
        }
        String function = in.getFunction();
        if (function == null || function.length() == 0) {
            return new String[]{null, null, "Función nulo"};
        }
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, db_ejecutivo);
        Ejecutivo ejecutivo = in.ejecutivo;
        if (function.equals(DlgProcessing.EDT)) {
            return updateEjecutivo(session, ejecutivo, db_cat);
        } else if (function.equals(DlgProcessing.ADD)) {
            return newEjecutivo(session, ejecutivo, db_cat);
        } else if (function.equals(DlgProcessing.DEL)) {
            return removeEjecutivo(session, ejecutivo, db_cat);
        } else if (function.equals(DlgProcessing.SHW)) {
            return getEjecutivo(ejecutivo, db_cat);
        } else if (function.equals(DlgProcessing.LST)) {
            Ejecutivos ejecutivos = getEjecutivos();
            ejecutivos.update();
            return new String[]{ejecutivos.json().toString(), null, null};
        }
        return new String[]{null, null, "Función invalida"};
    }
    
    
    public String[] getEjecutivo(Ejecutivo ejecutivo, DB2CatUpd db_cat) {
        Ejecutivos ejecutivos = new Ejecutivos(new JSONObject());
        if (getEjecutivo(ejecutivo.getEjecutivoId(), db_cat)) {
            Object[] fields = getEjecutivoFields(new String[]{
                "EJECUTIVO_ID",
                "NOMBRE",
                "PUESTO_ID",
                "SUCURSAL_ID",
                "SUELDO",
                "FECHA_INGRESO"}, db_cat);
            if (fields == null) {
                return new String[]{null, null, "Error en leer datos: " + ejecutivo.getEjecutivoId()};
            }
//            ejecutivos = getEjecutivos();
            int inx = 0;
            ejecutivos.ejecutivo.setEjecutivoId((String) fields[inx++]);
            ejecutivos.ejecutivo.setNombre((String) fields[inx++]);
            ejecutivos.ejecutivo.setPuestoId((String) fields[inx++]);
            ejecutivos.ejecutivo.setSucursalId((String) fields[inx++]);
            ejecutivos.ejecutivo.setSueldo((Double) fields[inx++]);
            ejecutivos.ejecutivo.setFechaIngreso((Date)fields[inx++]);
            ejecutivos.ejecutivo.update();
            ejecutivos.update();
        } else {
            return new String[]{null, null, "Error en leer datos: " + ejecutivo.getEjecutivoId()};
        }
        return new String[]{ejecutivos.json().toString(), null, null};
    }

    public String[] newEjecutivo(SecuritySession session, Ejecutivo ejecutivo, DB2CatUpd db_cat) {
        Ejecutivos ejecutivos = new Ejecutivos(new JSONObject());
        if (appendEjecutivo(session, ejecutivo, db_cat)) {
            ejecutivos = getEjecutivos();
            ejecutivos.ejecutivo = ejecutivo;
            ejecutivos.update();
        } else {
            return new String[]{null, null, "Error al crear un datos: " + ejecutivo.getEjecutivoId()};
        }
        return new String[]{ejecutivos.json().toString(), null, null};
    }

    public String[] updateEjecutivo(SecuritySession session, Ejecutivo ejecutivo, DB2CatUpd db_cat) {
        Ejecutivos ejecutivos = new Ejecutivos(new JSONObject());
        if (updateEjecutivo(session, ejecutivo.getEjecutivoId(), new String[]{"NOMBRE", "PUESTO_ID", "SUCURSAL_ID", "SUELDO", "FECHA_INGRESO"},
                new Object[]{ejecutivo.getNombre(), ejecutivo.getPuestoId(), ejecutivo.getSucursalId(), ejecutivo.getSueldo(), ejecutivo.getFechaIngreso()}, db_cat)) {
            ejecutivos = getEjecutivos();
            ejecutivos.ejecutivo = ejecutivo;
            ejecutivos.update();
        } else {
            return new String[]{null, null, "Error al actualizar un datos: " + ejecutivo.getEjecutivoId()};
        }
        return new String[]{ejecutivos.json().toString(), null, null};
    }

    public String[] removeEjecutivo(SecuritySession session, Ejecutivo ejecutivo, DB2CatUpd db_cat) {
        Ejecutivos ejecutivos = new Ejecutivos(new JSONObject());
        if (!deleteEjecutivo(session, ejecutivo.getEjecutivoId(), db_cat)) {
            return new String[]{null, null, "Error al eliminar un datos: " + ejecutivo.getEjecutivoId()};
        }
        ejecutivos = getEjecutivos();
        return new String[]{ejecutivos.json().toString(), null, null};
    }

    public String[] getEjecutivosResult() {
        Ejecutivos ejecutivos = getEjecutivos();
        return new String[]{ejecutivos.json().toString(), null, null};
    }
////////

    public boolean appendEjecutivo(SecuritySession session, Ejecutivo ejecutivo, DB2CatUpd db_cat) {
        db_cat.init(new Object[]{ejecutivo.getEjecutivoId()},
                    new Object[]{ejecutivo.getNombre(), ejecutivo.getPuestoId(), ejecutivo.getSucursalId(), ejecutivo.getSueldo(), ejecutivo.getFechaIngreso()},
                    "A", session.userId, Dates.todayTimestamp());
        boolean result = db_cat.insert();
        servlet.clearCache("cardtables", "ejecutivo");
        return result;
    }

    public boolean getEjecutivo(String ejecutivoId, DB2CatUpd db_cat) {
        db_cat.init();
        if (!db_cat.get(new Object[]{ejecutivoId})) {
            return false;

        }
        return true;
    }

    public Object[] getEjecutivoFields(String names[], DB2CatUpd db_cat) {
        String[] knames = db_ejecutivo.getKeyName();
        Object[] kvalues = db_cat.getKeys();
        if (kvalues == null || knames == null) {
            return null;
        }
        String[] fnames = db_ejecutivo.getFieldsName();
        Object[] fvalues = db_cat.getFields();
        if (fvalues == null || fnames == null) {
            return null;
        }
        return DBInterfase.getFields(names, knames, kvalues, fnames, fvalues);
    }

    public boolean updateEjecutivo(SecuritySession session, String ejecutivoId, String names[], Object values[], DB2CatUpd db_cat) {
        if (!getEjecutivo(ejecutivoId, db_cat)) {
            return false;
        }
        String[] fnames = db_ejecutivo.getFieldsName();
        Object[] fvalues = db_cat.getFields();
        if (fvalues == null || fnames == null) {
            return false;
        }
        for (int i = 0; i < names.length; i++) {
            for (int f = 0; f < fnames.length; f++) {
                if (fnames[f].equals(names[i])) {
                    fvalues[f] = values[i];
                    break;
                }
            }
        }
        db_cat.init(db_cat.getKeys(), fvalues, db_cat.getStatus(), session.userId, db_cat.getChanged()); //Dates.todayTimestamp()
        boolean result = db_cat.update();
        servlet.clearCache("cardtables", "ejecutivo");
        return result;
    }

    public boolean deleteEjecutivo(SecuritySession session, String ejecutivoId, DB2CatUpd db_cat) {
        if (!getEjecutivo(ejecutivoId, db_cat)) {
            return false;
        }
        db_cat.init(db_cat.getKeys(), db_cat.getFields(), "B", db_cat.getUser(), db_cat.getChanged());
        boolean result = db_cat.update();
        servlet.clearCache("cardtables", "ejecutivo");
        return result;
    }
    
    public Ejecutivos getEjecutivos() {
        Ejecutivos ejecutivos = new Ejecutivos(new JSONObject());
        ejecutivos.region = "";
        JSONArray oejecutivos = new JSONArray();
        String fields[] = new String[db_ejecutivo.getKeyName().length + db_ejecutivo.getFieldsName().length];
        System.arraycopy(db_ejecutivo.getKeyName(), 0, fields, 0, db_ejecutivo.getKeyName().length);
        System.arraycopy(db_ejecutivo.getFieldsName(), 0, fields, db_ejecutivo.getKeyName().length, db_ejecutivo.getFieldsName().length);
        Class classes[] = new Class[db_ejecutivo.getKeyClasses().length + db_ejecutivo.getFieldsClasses().length];
        System.arraycopy(db_ejecutivo.getKeyClasses(), 0, classes, 0, db_ejecutivo.getKeyClasses().length);
        System.arraycopy(db_ejecutivo.getFieldsClasses(), 0, classes, db_ejecutivo.getKeyClasses().length, db_ejecutivo.getFieldsClasses().length);
        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_ejecutivo.tableModelName, "estatus = \'A\'");
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            Ejecutivo ejecutivo = new Ejecutivo(new JSONObject());
            ejecutivo.setEjecutivoId((String) row[inx++]);
            ejecutivo.setNombre((String) row[inx++]);
            ejecutivo.setPuestoId((String) row[inx++]);
            ejecutivo.setSucursalId((String) row[inx++]);
            ejecutivo.setSueldo((Double) row[inx++]);
            ejecutivo.setFechaIngreso((Date)(row[inx++]));
            ejecutivo.update();
            oejecutivos.put(ejecutivo.json());
        }
        ejecutivos.update(oejecutivos);
        return ejecutivos;
    }

    @Override
    public byte[] responseBytes(String command, JSONObject input) {
        return null;
    }
}
