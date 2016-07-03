package backend.card.io.cat;

import backend.BEObject;
import backend.BEServletInterfase;
import backend.card.def.min.DefDBMinAcuerdo;
import backend.card.def.min.DefDBMinAgenda;
import backend.card.def.min.DefDBMinParticipante;
import backend.card.def.min.DefDBMinParticipanteEx;
import backend.card.def.min.DefDBMinuta;
import backend.card.io.DBInterfase;
import backend.catalogs2.db.DB2CatList;
import backend.catalogs2.db.DB2CatUpd;
import backend.security.SecuritySession;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.shared.objects.card.min.MinAcuerdo;
import mx.logipax.shared.objects.card.min.MinAgenda;
import mx.logipax.shared.objects.card.min.MinParticipante;
import mx.logipax.shared.objects.card.min.MinParticipanteEx;
import mx.logipax.shared.objects.card.min.Minuta;
import mx.logipax.shared.objects.card.min.MinutaIn;
import mx.logipax.shared.objects.card.min.Minutas;
import mx.logipax.utility.Dates;
import org.json.JSONArray;
import org.json.JSONObject;

public class BEMinuta implements BEObject {

    final BEServletInterfase servlet;
    DefDBMinuta db_minuta;
    DefDBMinParticipante db_participante;
    DefDBMinParticipanteEx db_externa;
    DefDBMinAgenda db_agenda;
    DefDBMinAcuerdo db_acuerdo;

    public BEMinuta(BEServletInterfase servlet) {
        this.servlet = servlet;
        db_minuta = new DefDBMinuta(servlet);
        db_participante = new DefDBMinParticipante(servlet);
        db_externa = new DefDBMinParticipanteEx(servlet);
        db_agenda = new DefDBMinAgenda(servlet);
        db_acuerdo = new DefDBMinAcuerdo(servlet);
    }

    @Override
    public boolean match(String command) {
        return command.equals("minuta");
    }

    @Override
    public String getMime() {
        return "text";
    }

    @Override
    public String[] responseText(String command, JSONObject input) {
        MinutaIn in = new MinutaIn(input);
        SecuritySession session = (SecuritySession) servlet.securitySession(in.getTicket());
        if (session == null) {
            return new String[]{null, null, "Desconectado"};
        }
        String function = in.getFunction();
        if (function == null || function.length() == 0) {
            return new String[]{null, null, "Función nulo"};
        }
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, db_minuta);
        DB2CatUpd db_cat1 = new DB2CatUpd(servlet.db(), "local", 0, db_participante);
        DB2CatUpd db_cat2 = new DB2CatUpd(servlet.db(), "local", 0, db_externa);
        DB2CatUpd db_cat3 = new DB2CatUpd(servlet.db(), "local", 0, db_agenda);
        DB2CatUpd db_cat4 = new DB2CatUpd(servlet.db(), "local", 0, db_acuerdo);
        Minuta minuta = in.minuta;
        if (function.equals(DlgProcessing.EDT)) {
            return updateMinuta(session, minuta, db_cat, db_cat1, db_cat2, db_cat3, db_cat4);
        } else if (function.equals(DlgProcessing.ADD)) {
            return newMinuta(session, minuta, db_cat, db_cat1, db_cat2, db_cat3, db_cat4);
        } else if (function.equals(DlgProcessing.DEL)) {
            return removeMinuta(session, minuta, db_cat, db_cat1, db_cat2, db_cat3, db_cat4);
        } else if (function.equals(DlgProcessing.SHW)) {
            return getMinuta(minuta, db_cat, db_cat1, db_cat2, db_cat3, db_cat4);
        } else if (function.equals(DlgProcessing.LST)) {
            Minutas minutas = getMinutas();
            minutas.update();
            return new String[]{minutas.json().toString(), null, null};
        }
        return new String[]{null, null, "Función invalida"};
    }

    public String[] getMinuta(Minuta minuta, DB2CatUpd db_cat, DB2CatUpd db_cat1, DB2CatUpd db_cat2, DB2CatUpd db_cat3, DB2CatUpd db_cat4) {
        Minutas minutas = new Minutas(new JSONObject());
        if (getMinuta(minuta.getMinutaId(), db_cat)) {
            Object[] fields = getMinutaFields(new String[]{
                "MINUTA_ID",
                "NOMBRE",
                "GPOTRABAJO_ID",
                "FECHA",
                "TEXTO",
                "ESTATUSTRN"}, db_cat);
            if (fields == null) {
                return new String[]{null, null, "Error en leer datos: " + minuta.getMinutaId()};
            }
            int inx = 0;
            minutas.minuta.setMinutaId((String) fields[inx++]);
            minutas.minuta.setNombre((String) fields[inx++]);
            minutas.minuta.setGpoTrabajoId((String) fields[inx++]);
            minutas.minuta.setFecha((java.util.Date) fields[inx++]);
            minutas.minuta.setTexto((String) fields[inx++]);
            minutas.minuta.setEstatus((String) fields[inx++]);
            minutas.minuta.setParticipantes(getParticipantes(minuta.getMinutaId()));
            minutas.minuta.setParticipanteExs(getParticipanteExs(minuta.getMinutaId()));
            minutas.minuta.setAgendas(getAgendas(minuta.getMinutaId()));
            minutas.minuta.setAcuerdos(getAcuerdos(minuta.getMinutaId()));
            minutas.minuta.update();
            minutas.update();
        } else {
            return new String[]{null, null, "Error en leer datos: " + minuta.getMinutaId()};
        }
        return new String[]{minutas.json().toString(), null, null};
    }

    private MinParticipante[] getParticipantes(String grptrabajoId) {
        String fields[] = new String[db_participante.getKeyName().length + db_participante.getFieldsName().length];
        System.arraycopy(db_participante.getKeyName(), 0, fields, 0, db_participante.getKeyName().length);
        System.arraycopy(db_participante.getFieldsName(), 0, fields, db_participante.getKeyName().length, db_participante.getFieldsName().length);
        Class classes[] = new Class[db_participante.getKeyClasses().length + db_participante.getFieldsClasses().length];
        System.arraycopy(db_participante.getKeyClasses(), 0, classes, 0, db_participante.getKeyClasses().length);
        System.arraycopy(db_participante.getFieldsClasses(), 0, classes, db_participante.getKeyClasses().length, db_participante.getFieldsClasses().length);

        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_participante.tableModelName, "minuta_id = '" + grptrabajoId + "' and estatus = \'A\'", "secuencia");

        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        MinParticipante[] participantes = new MinParticipante[size2];
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            participantes[i] = new MinParticipante(new JSONObject());
            participantes[i].setMinutaId((String) row[inx++]);
            participantes[i].setSecuencia((Integer) row[inx++]);
            participantes[i].setEjecutivoId((String) row[inx++]);
            participantes[i].setEstatus((String) row[inx++]);
            participantes[i].update();
        }
        return participantes;
    }

    private MinParticipanteEx[] getParticipanteExs(String grptrabajoId) {
        String fields[] = new String[db_participante.getKeyName().length + db_participante.getFieldsName().length];
        System.arraycopy(db_participante.getKeyName(), 0, fields, 0, db_participante.getKeyName().length);
        System.arraycopy(db_participante.getFieldsName(), 0, fields, db_participante.getKeyName().length, db_participante.getFieldsName().length);
        Class classes[] = new Class[db_participante.getKeyClasses().length + db_participante.getFieldsClasses().length];
        System.arraycopy(db_participante.getKeyClasses(), 0, classes, 0, db_participante.getKeyClasses().length);
        System.arraycopy(db_participante.getFieldsClasses(), 0, classes, db_participante.getKeyClasses().length, db_participante.getFieldsClasses().length);

        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_participante.tableModelName, "minuta_id = '" + grptrabajoId + "' and estatus = \'A\'", "secuencia");

        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        MinParticipanteEx[] externas = new MinParticipanteEx[size2];
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            externas[i] = new MinParticipanteEx(new JSONObject());
            externas[i].setMinutaId((String) row[inx++]);
            externas[i].setSecuencia((Integer) row[inx++]);
            externas[i].setNombre((String) row[inx++]);
            externas[i].setEMail((String) row[inx++]);
            externas[i].setEstatus((String) row[inx++]);
            externas[i].update();
        }
        return externas;
    }

    private MinAgenda[] getAgendas(String grptrabajoId) {
        String fields[] = new String[db_participante.getKeyName().length + db_participante.getFieldsName().length];
        System.arraycopy(db_participante.getKeyName(), 0, fields, 0, db_participante.getKeyName().length);
        System.arraycopy(db_participante.getFieldsName(), 0, fields, db_participante.getKeyName().length, db_participante.getFieldsName().length);
        Class classes[] = new Class[db_participante.getKeyClasses().length + db_participante.getFieldsClasses().length];
        System.arraycopy(db_participante.getKeyClasses(), 0, classes, 0, db_participante.getKeyClasses().length);
        System.arraycopy(db_participante.getFieldsClasses(), 0, classes, db_participante.getKeyClasses().length, db_participante.getFieldsClasses().length);

        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_participante.tableModelName, "minuta_id = '" + grptrabajoId + "' and estatus = \'A\'", "secuencia");

        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        MinAgenda[] agendas = new MinAgenda[size2];
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            agendas[i] = new MinAgenda(new JSONObject());
            agendas[i].setMinutaId((String) row[inx++]);
            agendas[i].setSecuencia((Integer) row[inx++]);
            agendas[i].setDescripcion((String) row[inx++]);
            agendas[i].setTexto((String) row[inx++]);
            agendas[i].setEstatus((String) row[inx++]);
            agendas[i].update();
        }
        return agendas;
    }
    private MinAcuerdo[] getAcuerdos(String grptrabajoId) {
        String fields[] = new String[db_participante.getKeyName().length + db_participante.getFieldsName().length];
        System.arraycopy(db_participante.getKeyName(), 0, fields, 0, db_participante.getKeyName().length);
        System.arraycopy(db_participante.getFieldsName(), 0, fields, db_participante.getKeyName().length, db_participante.getFieldsName().length);
        Class classes[] = new Class[db_participante.getKeyClasses().length + db_participante.getFieldsClasses().length];
        System.arraycopy(db_participante.getKeyClasses(), 0, classes, 0, db_participante.getKeyClasses().length);
        System.arraycopy(db_participante.getFieldsClasses(), 0, classes, db_participante.getKeyClasses().length, db_participante.getFieldsClasses().length);

        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_participante.tableModelName, "minuta_id = '" + grptrabajoId + "' and estatus = \'A\'", "secuencia");

        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        MinAcuerdo[] acuerdos = new MinAcuerdo[size2];
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            acuerdos[i] = new MinAcuerdo(new JSONObject());
            acuerdos[i].setMinutaId((String) row[inx++]);
            acuerdos[i].setSecuencia((Integer) row[inx++]);
            acuerdos[i].setAcuerdoId((String) row[inx++]);
            acuerdos[i].setSolicitaId((String) row[inx++]);
            acuerdos[i].setResponsableId((String) row[inx++]);
            acuerdos[i].setObjetivo((String) row[inx++]);
            acuerdos[i].setFecha((java.util.Date) row[inx++]);
            acuerdos[i].setTexto((String) row[inx++]);
            acuerdos[i].setEstatus((String) row[inx++]);
            acuerdos[i].update();
        }
        return acuerdos;
    }
    
    public String[] newMinuta(SecuritySession session, Minuta minuta, DB2CatUpd db_cat, DB2CatUpd db_cat1, DB2CatUpd db_cat2, DB2CatUpd db_cat3, DB2CatUpd db_cat4) {
        Minutas minutas = new Minutas(new JSONObject());
        if (appendMinuta(session, minuta, db_cat)) {
            updateParticipantes(session, minuta, db_cat1);
            updateParticipanteExs(session, minuta, db_cat2);
            updateAgendas(session, minuta, db_cat3);
            updateAcuerdos(session, minuta, db_cat4);
            minutas = getMinutas();
            minutas.minuta = minuta;
            minutas.update();
        } else {
            return new String[]{null, null, "Error al crear un datos: " + minuta.getMinutaId()};
        }
        return new String[]{minutas.json().toString(), null, null};
    }

    public String[] updateMinuta(SecuritySession session, Minuta minuta, DB2CatUpd db_cat, DB2CatUpd db_cat1, DB2CatUpd db_cat2, DB2CatUpd db_cat3, DB2CatUpd db_cat4) {
        Minutas minutas = new Minutas(new JSONObject());
        if (updateMinuta(session, minuta.getMinutaId(), new String[]{
                "NOMBRE",
                "GPOTRABAJO_ID",
                "FECHA",
                "TEXTO",
                "ESTATUSTRN"},
                new Object[]{minuta.getNombre(), minuta.getGpoTrabajoId(), minuta.getFecha(), minuta.getTexto(), minuta.getEstatus()}, db_cat)) {
            updateParticipantes(session, minuta, db_cat1);
            updateParticipanteExs(session, minuta, db_cat2);
            updateAgendas(session, minuta, db_cat3);
            updateAcuerdos(session, minuta, db_cat4);
            minutas = getMinutas();
            minutas.minuta = minuta;
            minutas.update();
        } else {
            return new String[]{null, null, "Error al actualizar un datos: " + minuta.getMinutaId()};
        }
        return new String[]{minutas.json().toString(), null, null};
    }

    public boolean updateParticipantes(SecuritySession session, Minuta minuta, DB2CatUpd db_cat) {
        MinParticipante integrantes[] = minuta.getParticipantes();
        db_cat.deleteAll("minuta_id = '" + minuta.getMinutaId() + "'");
        for (int i = 0; i < integrantes.length; i++) {
            integrantes[i].setSecuencia(i+1);
            if (!appendParticipante(session, integrantes[i], db_cat)) {
                return false;
            }
        }
        return true;
    }

    public boolean appendParticipante(SecuritySession session, MinParticipante participante, DB2CatUpd db_cat) {
        db_cat.init(new Object[]{participante.getMinutaId(), new Integer(participante.getSecuencia())},
                new Object[]{participante.getEjecutivoId(), participante.getEstatus()},
                "A", session.userId, Dates.todayTimestamp());
        boolean result = db_cat.insert();
        return result;
    }

    public boolean updateParticipanteExs(SecuritySession session, Minuta minuta, DB2CatUpd db_cat) {
        MinParticipanteEx integrantes[] = minuta.getParticipanteExs();
        db_cat.deleteAll("minuta_id = '" + minuta.getMinutaId() + "'");
        for (int i = 0; i < integrantes.length; i++) {
            integrantes[i].setSecuencia(i+1);
            if (!appendParticipanteEx(session, integrantes[i], db_cat)) {
                return false;
            }
        }
        return true;
    }

    public boolean appendParticipanteEx(SecuritySession session, MinParticipanteEx participante, DB2CatUpd db_cat) {
        db_cat.init(new Object[]{participante.getMinutaId(), new Integer(participante.getSecuencia())},
                new Object[]{participante.getNombre(), participante.getEMail(), participante.getEstatus()},
                "A", session.userId, Dates.todayTimestamp());
        boolean result = db_cat.insert();
        return result;
    }
    
    public boolean updateAgendas(SecuritySession session, Minuta minuta, DB2CatUpd db_cat) {
        MinAgenda integrantes[] = minuta.getAgendas();
        db_cat.deleteAll("minuta_id = '" + minuta.getMinutaId() + "'");
        for (int i = 0; i < integrantes.length; i++) {
            integrantes[i].setSecuencia(i+1);
            integrantes[i].update();
            if (!appendAgenda(session, integrantes[i], db_cat)) {
                return false;
            }
        }
        return true;
    }

    public boolean appendAgenda(SecuritySession session, MinAgenda participante, DB2CatUpd db_cat) {
        db_cat.init(new Object[]{participante.getMinutaId(), new Integer(participante.getSecuencia())},
                new Object[]{participante.getDescripcion(), participante.getTexto(), participante.getEstatus()},
                "A", session.userId, Dates.todayTimestamp());
        boolean result = db_cat.insert();
        return result;
    }

    
    public boolean updateAcuerdos(SecuritySession session, Minuta minuta, DB2CatUpd db_cat) {
        MinAcuerdo integrantes[] = minuta.getAcuerdos();
        db_cat.deleteAll("minuta_id = '" + minuta.getMinutaId() + "'");
        for (int i = 0; i < integrantes.length; i++) {
            integrantes[i].setSecuencia(i+1);
            integrantes[i].update();
            if (!appendAcuerdo(session, integrantes[i], db_cat)) {
                return false;
            }
        }
        return true;
    }

    public boolean appendAcuerdo(SecuritySession session, MinAcuerdo participante, DB2CatUpd db_cat) {
        db_cat.init(new Object[]{participante.getMinutaId(), new Integer(participante.getSecuencia())},
                new Object[]{participante.getAcuerdoId(), participante.getSolicitaId(), participante.getResponsableId(), participante.getObjetivo(), participante.getFecha(), participante.getTexto(), participante.getEstatus()},
                "A", session.userId, Dates.todayTimestamp());
        boolean result = db_cat.insert();
        return result;
    }
    
    public String[] removeMinuta(SecuritySession session, Minuta minuta, DB2CatUpd db_cat, DB2CatUpd db_cat1, DB2CatUpd db_cat2, DB2CatUpd db_cat3, DB2CatUpd db_cat4) {
        Minutas minutas = new Minutas(new JSONObject());
        if (!deleteMinuta(session, minuta.getMinutaId(), db_cat)) {
            return new String[]{null, null, "Error al eliminar un datos: " + minuta.getMinutaId()};
        }
        db_cat2.deleteAll("minuta_id = '" + minuta.getMinutaId() + "'");
        minutas = getMinutas();
        return new String[]{minutas.json().toString(), null, null};
    }

    public String[] getMinutaResult() {
        Minutas minutas = getMinutas();
        return new String[]{minutas.json().toString(), null, null};
    }

    public boolean appendMinuta(SecuritySession session, Minuta minuta, DB2CatUpd db_cat) {
        db_cat.init(new Object[]{minuta.getMinutaId()},
                new Object[]{minuta.getNombre(), minuta.getFecha(), minuta.getEstatus()},
                "A", session.userId, Dates.todayTimestamp());
        boolean result = db_cat.insert();
        servlet.clearCache("cardtables", "minuta");
        return result;
    }

    public boolean getMinuta(String posicionId, DB2CatUpd db_cat) {
        db_cat.init();
        if (!db_cat.get(new Object[]{posicionId})) {
            return false;

        }
        return true;
    }

    public Object[] getMinutaFields(String names[], DB2CatUpd db_cat) {
        String[] knames = db_minuta.getKeyName();
        Object[] kvalues = db_cat.getKeys();
        if (kvalues == null || knames == null) {
            return null;
        }
        String[] fnames = db_minuta.getFieldsName();
        Object[] fvalues = db_cat.getFields();
        if (fvalues == null || fnames == null) {
            return null;
        }
        return DBInterfase.getFields(names, knames, kvalues, fnames, fvalues);
    }

    public boolean updateMinuta(SecuritySession session, String posicionId, String names[], Object values[], DB2CatUpd db_cat) {
        if (!getMinuta(posicionId, db_cat)) {
            return false;
        }
        String[] fnames = db_minuta.getFieldsName();
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
        servlet.clearCache("cardtables", "minuta");
        return result;
    }

    public boolean deleteMinuta(SecuritySession session, String posicionId, DB2CatUpd db_cat) {
        if (!getMinuta(posicionId, db_cat)) {
            return false;
        }
        db_cat.init(db_cat.getKeys(), db_cat.getFields(), "B", db_cat.getUser(), db_cat.getChanged());
        boolean result = db_cat.update();
        servlet.clearCache("cardtables", "minuta");
        return result;
    }

    public Minutas getMinutas() {
        Minutas minutas = new Minutas(new JSONObject());
        JSONArray ominutas = new JSONArray();
        String fields[] = new String[db_minuta.getKeyName().length + db_minuta.getFieldsName().length];
        System.arraycopy(db_minuta.getKeyName(), 0, fields, 0, db_minuta.getKeyName().length);
        System.arraycopy(db_minuta.getFieldsName(), 0, fields, db_minuta.getKeyName().length, db_minuta.getFieldsName().length);
        Class classes[] = new Class[db_minuta.getKeyClasses().length + db_minuta.getFieldsClasses().length];
        System.arraycopy(db_minuta.getKeyClasses(), 0, classes, 0, db_minuta.getKeyClasses().length);
        System.arraycopy(db_minuta.getFieldsClasses(), 0, classes, db_minuta.getKeyClasses().length, db_minuta.getFieldsClasses().length);
        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_minuta.tableModelName, "estatus = \'A\'");
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            Minuta minuta = new Minuta(new JSONObject());
            minuta.setMinutaId((String) row[inx++]);
            minuta.setNombre((String) row[inx++]);
            minuta.setFecha((String) row[inx++]);
            minuta.setEstatus((String) row[inx++]);
            minuta.update();
            ominutas.put(minuta.json());
        }
        minutas.update(ominutas);
        return minutas;
    }

    @Override
    public byte[] responseBytes(String command, JSONObject input) {
        return null;
    }
}
