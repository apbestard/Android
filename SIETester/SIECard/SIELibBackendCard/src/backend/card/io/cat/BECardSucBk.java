package backend.card.io.cat;

import backend.BEObject;
import backend.BEServletInterfase;
import backend.card.def.DefDBCardSuc;
import backend.card.def.DefDBCardSucPersona;
import backend.card.def.DefDBCardSucEquipo;
import backend.card.io.DBInterfase;
import backend.catalogs2.db.DB2CatList;
import backend.catalogs2.db.DB2CatUpd;
import backend.security.SecuritySession;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.shared.objects.card.cat.CardSuc;
import mx.logipax.shared.objects.card.cat.CardSucIn;
import mx.logipax.shared.objects.card.cat.CardSucs;
import mx.logipax.shared.objects.card.cat.CardSucPersona;
import mx.logipax.shared.objects.card.cat.CardSucEquipo;
import mx.logipax.utility.Dates;
import org.json.JSONArray;
import org.json.JSONObject;

public class BECardSucBk implements BEObject {

    final BEServletInterfase servlet;
    DefDBCardSuc db_card;
    DefDBCardSucEquipo db_equipo;
    DefDBCardSucPersona db_persona;

    public BECardSucBk(BEServletInterfase servlet) {
        this.servlet = servlet;
        db_card = new DefDBCardSuc(servlet);
        db_equipo = new DefDBCardSucEquipo(servlet);
        db_persona = new DefDBCardSucPersona(servlet);
    }

    @Override
    public boolean match(String command) {
        return command.equals("cardsuc");
    }

    @Override
    public String getMime() {
        return "text";
    }

    @Override
    public String[] responseText(String command, JSONObject input) {
        CardSucIn in = new CardSucIn(input);
        SecuritySession session = (SecuritySession) servlet.securitySession(in.getTicket());
        if (session == null) {
            return new String[]{null, null, "Desconectado"};
        }
        String function = in.getFunction();
        if (function == null || function.length() == 0) {
            return new String[]{null, null, "Función nulo"};
        }
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, db_card);
        DB2CatUpd db_cat1 = new DB2CatUpd(servlet.db(), "local", 0, db_equipo);
        DB2CatUpd db_cat2 = new DB2CatUpd(servlet.db(), "local", 0, db_persona);
        DB2CatUpd db_cat3 = new DB2CatUpd(servlet.db(), "local", 0, db_equipo);
        DB2CatUpd db_cat4 = new DB2CatUpd(servlet.db(), "local", 0, db_equipo);
        CardSuc card = in.card;
        if (function.equals(DlgProcessing.EDT)) {
            return updateCardSuc(session, card, db_cat, db_cat1, db_cat2, db_cat3, db_cat4);
        } else if (function.equals(DlgProcessing.ADD)) {
            return newCardSuc(session, card, db_cat, db_cat1, db_cat2, db_cat3, db_cat4);
        } else if (function.equals(DlgProcessing.DEL)) {
            return removeCardSuc(session, card, db_cat, db_cat1, db_cat2, db_cat3, db_cat4);
        } else if (function.equals(DlgProcessing.SHW)) {
            return getCardSuc(card, db_cat, db_cat1, db_cat2, db_cat3, db_cat4);
        } else if (function.equals(DlgProcessing.LST)) {
            CardSucs cards = getCardSucs();
            cards.update();
            return new String[]{cards.json().toString(), null, null};
        }
        return new String[]{null, null, "Función invalida"};
    }

    public String[] getCardSuc(CardSuc card, DB2CatUpd db_cat, DB2CatUpd db_cat1, DB2CatUpd db_cat2, DB2CatUpd db_cat3, DB2CatUpd db_cat4) {
        CardSucs cards = new CardSucs(new JSONObject());
        if (getCardSuc(card.getCardLevel(), card.getCardId(), db_cat)) {
            Object[] fields = getCardSucFields(new String[]{
                "CARD_LEVEL",
                "CARD_ID",
                "CARD_NOMBRE",
                "RESPONSABLE_ID",
                "RESPONSABLE_NOMBRE",
                "USERVISOR_ID",
                "USERVISOR_NOMBRE",
                "EMAIL",
                "FORMATO_ID",
                "MISMAS_ID",
                "FECHA_APERTURA",
                "ESTATUSTRN"}, db_cat);
            if (fields == null) {
                return new String[]{null, null, "Error en leer datos: " + card.getCardId()};
            }
            int inx = 0;
            cards.record.setCardLevel((int) fields[inx++]);
            cards.record.setCardId((String) fields[inx++]);
            cards.record.setCardNombre((String) fields[inx++]);
            cards.record.setResponsableId((String) fields[inx++]);
            cards.record.setResponsableNombre((String) fields[inx++]);
            cards.record.setVisorId((String) fields[inx++]);
            cards.record.setVisorNombre((String) fields[inx++]);
            cards.record.setEMail((String) fields[inx++]);
            cards.record.setFormatoId((String) fields[inx++]);
            cards.record.setMismaId((String) fields[inx++]);
            cards.record.setFecha((java.util.Date) fields[inx++]);
            cards.record.setEstatus((String) fields[inx++]);
            cards.record.setEquipos(getEquipos(card.getCardLevel(), card.getCardId()));
            cards.record.setPersonal(getPersonal(card.getCardLevel(), card.getCardId()));
            cards.record.update();
            cards.update();
        } else {
            return new String[]{null, null, "Error en leer datos: " + card.getCardId()};
        }
        return new String[]{cards.json().toString(), null, null};
    }

    private CardSucEquipo[] getEquipos(int cardLevel, String cardId) {
        String fields[] = new String[db_equipo.getKeyName().length + db_equipo.getFieldsName().length];
        System.arraycopy(db_equipo.getKeyName(), 0, fields, 0, db_equipo.getKeyName().length);
        System.arraycopy(db_equipo.getFieldsName(), 0, fields, db_equipo.getKeyName().length, db_equipo.getFieldsName().length);
        Class classes[] = new Class[db_equipo.getKeyClasses().length + db_equipo.getFieldsClasses().length];
        System.arraycopy(db_equipo.getKeyClasses(), 0, classes, 0, db_equipo.getKeyClasses().length);
        System.arraycopy(db_equipo.getFieldsClasses(), 0, classes, db_equipo.getKeyClasses().length, db_equipo.getFieldsClasses().length);

        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_equipo.tableModelName, "card_level = " + cardLevel + " and card_id = '\" + cardId + \"' and estatus = \'A\'", "secuencia");

        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        CardSucEquipo[] equipos = new CardSucEquipo[size2];
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            equipos[i] = new CardSucEquipo(new JSONObject());
            equipos[i].setCardLevel((int) row[inx++]);
            equipos[i].setCardId((String) row[inx++]);
            equipos[i].setSecuencia((Integer) row[inx++]);
            equipos[i].setDeptoId((String) row[inx++]);
            equipos[i].setDeptoNombre((String) row[inx++]);
            equipos[i].setEquipoId((String) row[inx++]);
            equipos[i].setEquipoNombre((String) row[inx++]);
            equipos[i].setEquipos((int) row[inx++]);
            equipos[i].setVacantes((int) row[inx++]);
            equipos[i].setEstatus((String) row[inx++]);
            equipos[i].update();
        }
        return equipos;
    }

    private CardSucPersona[] getPersonal(int cardLevel, String cardId) {
        String fields[] = new String[db_persona.getKeyName().length + db_persona.getFieldsName().length];
        System.arraycopy(db_persona.getKeyName(), 0, fields, 0, db_persona.getKeyName().length);
        System.arraycopy(db_persona.getFieldsName(), 0, fields, db_persona.getKeyName().length, db_persona.getFieldsName().length);
        Class classes[] = new Class[db_persona.getKeyClasses().length + db_persona.getFieldsClasses().length];
        System.arraycopy(db_persona.getKeyClasses(), 0, classes, 0, db_persona.getKeyClasses().length);
        System.arraycopy(db_persona.getFieldsClasses(), 0, classes, db_persona.getKeyClasses().length, db_persona.getFieldsClasses().length);

        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_equipo.tableModelName, "card_level = " + cardLevel + " and card_id = '\" + cardId + \"' and estatus = \'A\'", "secuencia");

        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        CardSucPersona[] personas = new CardSucPersona[size2];
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            personas[i] = new CardSucPersona(new JSONObject());
            personas[i].setCardLevel((int) row[inx++]);
            personas[i].setCardId((String) row[inx++]);
            personas[i].setSecuencia((Integer) row[inx++]);
            personas[i].setDeptoId((String) row[inx++]);
            personas[i].setDeptoNombre((String) row[inx++]);
            personas[i].setPuestoId((String) row[inx++]);
            personas[i].setPuestoNombre((String) row[inx++]);
            personas[i].setPersonas((int) row[inx++]);
            personas[i].setVacantes((int) row[inx++]);
            personas[i].setEstatus((String) row[inx++]);
            personas[i].update();
        }
        return personas;
    }
    
    public String[] newCardSuc(SecuritySession session, CardSuc card, DB2CatUpd db_cat, DB2CatUpd db_cat1, DB2CatUpd db_cat2, DB2CatUpd db_cat3, DB2CatUpd db_cat4) {
        CardSucs cards = new CardSucs(new JSONObject());
        if (appendCardSuc(session, card, db_cat)) {
            updateEquipos(session, card, db_cat1);
            updatePersonal(session, card, db_cat2);
            cards = getCardSucs();
            cards.record = card;
            cards.update();
        } else {
            return new String[]{null, null, "Error al crear un datos: " + card.getCardId()};
        }
        return new String[]{cards.json().toString(), null, null};
    }

    public String[] updateCardSuc(SecuritySession session, CardSuc card, DB2CatUpd db_cat, DB2CatUpd db_cat1, DB2CatUpd db_cat2, DB2CatUpd db_cat3, DB2CatUpd db_cat4) {
        CardSucs cards = new CardSucs(new JSONObject());
        if (updateCardSuc(session, card.getCardLevel(), card.getCardId(), new String[]{
              "CARD_NOMBRE",
                "RESPONSABLE_ID",
                "RESPONSABLE_NOMBRE",
                "USERVISOR_ID",
                "USERVISOR_NOMBRE",
                "EMAIL",
                "FORMATO_ID",
                "MISMAS_ID",
                "FECHA_APERTURA",
                "ESTATUSTRN"},
                new Object[]{card.getCardNombre(), 
                    card.getResponsableId(), 
                    card.getResponsableNombre(),
                    card.getVisorId(), 
                    card.getVisorNombre(),
                    card.getEMail(), 
                    card.getFormatoId(), card.getMismaId(), card.getFecha(), card.getEstatus()}, db_cat)) {
            updateEquipos(session, card, db_cat1);
            updatePersonal(session, card, db_cat2);
            cards = getCardSucs();
            cards.record = card;
            cards.update();
        } else {
            return new String[]{null, null, "Error al actualizar un datos: " + card.getCardId()};
        }
        return new String[]{cards.json().toString(), null, null};
    }

    public boolean updateEquipos(SecuritySession session, CardSuc card, DB2CatUpd db_cat) {
        CardSucEquipo integrantes[] = card.getEquipos();
        db_cat.deleteAll("card_level = '" + card.getCardLevel() + " and card_id = '" + card.getCardId() + "'");
        for (int i = 0; i < integrantes.length; i++) {
            integrantes[i].setSecuencia(i+1);
            if (!appendCardSucEquipo(session, integrantes[i], db_cat)) {
                return false;
            }
        }
        return true;
    }

    public boolean appendCardSucEquipo(SecuritySession session, CardSucEquipo record, DB2CatUpd db_cat) {
        db_cat.init(new Object[]{new Integer(record.getCardLevel()), record.getCardId(), new Integer(record.getSecuencia())},
                new Object[]{record.getDeptoId(), record.getDeptoNombre(), 
                    record.getEquipoId(), record.getEquipoNombre(), 
                    new Integer(record.getEquipos()), new Integer(record.getVacantes()), record.getEstatus()},
                "A", session.userId, Dates.todayTimestamp());
        boolean result = db_cat.insert();
        return result;
    }
    
    public boolean updatePersonal(SecuritySession session, CardSuc card, DB2CatUpd db_cat) {
        CardSucPersona integrantes[] = card.getPersonal();
        db_cat.deleteAll("card_level = '" + card.getCardLevel() + " and card_id = '" + card.getCardId() + "'");
        for (int i = 0; i < integrantes.length; i++) {
            integrantes[i].setSecuencia(i+1);
            integrantes[i].update();
            if (!appendCardSucPersona(session, integrantes[i], db_cat)) {
                return false;
            }
        }
        return true;
    }

    public boolean appendCardSucPersona(SecuritySession session, CardSucPersona record, DB2CatUpd db_cat) {
        db_cat.init(new Object[]{new Integer(record.getCardLevel()), record.getCardId(), new Integer(record.getSecuencia())},
                new Object[]{record.getDeptoId(), record.getDeptoNombre(), 
                    record.getPuestoId(), record.getPuestoNombre(), 
                    new Integer(record.getPersonas()), new Integer(record.getVacantes()), record.getEstatus()},
                "A", session.userId, Dates.todayTimestamp());
        boolean result = db_cat.insert();
        return result;
    }
    
    public String[] removeCardSuc(SecuritySession session, CardSuc card, DB2CatUpd db_cat, DB2CatUpd db_cat1, DB2CatUpd db_cat2, DB2CatUpd db_cat3, DB2CatUpd db_cat4) {
        CardSucs cards = new CardSucs(new JSONObject());
        if (!deleteCardSuc(session, card.getCardLevel(), card.getCardId(), db_cat)) {
            return new String[]{null, null, "Error al eliminar un datos: " + card.getCardId()};
        }
        db_cat2.deleteAll("card_level = '" + card.getCardLevel() + " and card_id = '" + card.getCardId() + "'");
        cards = getCardSucs();
        return new String[]{cards.json().toString(), null, null};
    }

    public String[] getCardSucResult() {
        CardSucs cards = getCardSucs();
        return new String[]{cards.json().toString(), null, null};
    }

    public boolean appendCardSuc(SecuritySession session, CardSuc card, DB2CatUpd db_cat) {
        db_cat.init(new Object[]{new Integer(card.getCardLevel()), card.getCardId()},
                new Object[]{
                    card.getCardNombre(), 
                    card.getResponsableId(), 
                    card.getResponsableNombre(), 
                    card.getVisorId(), 
                    card.getVisorNombre(), 
                    card.getEMail(), 
                    card.getFormatoId(), 
                    card.getMismaId(), 
                    card.getFecha(), 
                    card.getEstatus()},
                "A", session.userId, Dates.todayTimestamp());
        boolean result = db_cat.insert();
        servlet.clearCache("cardtables", "card");
        return result;
    }

    public boolean getCardSuc(int keyLevel, String keyId, DB2CatUpd db_cat) {
        db_cat.init();
        if (!db_cat.get(new Object[]{new Integer(keyLevel), keyId})) {
            return false;

        }
        return true;
    }

    public Object[] getCardSucFields(String names[], DB2CatUpd db_cat) {
        String[] knames = db_card.getKeyName();
        Object[] kvalues = db_cat.getKeys();
        if (kvalues == null || knames == null) {
            return null;
        }
        String[] fnames = db_card.getFieldsName();
        Object[] fvalues = db_cat.getFields();
        if (fvalues == null || fnames == null) {
            return null;
        }
        return DBInterfase.getFields(names, knames, kvalues, fnames, fvalues);
    }

    public boolean updateCardSuc(SecuritySession session, int keyLevel, String keyId, String names[], Object values[], DB2CatUpd db_cat) {
        if (!getCardSuc(keyLevel, keyId, db_cat)) {
            return false;
        }
        String[] fnames = db_card.getFieldsName();
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
        servlet.clearCache("cardtables", "card");
        return result;
    }

    public boolean deleteCardSuc(SecuritySession session, int keyLevel, String keyId, DB2CatUpd db_cat) {
        if (!getCardSuc(keyLevel, keyId, db_cat)) {
            return false;
        }
        db_cat.init(db_cat.getKeys(), db_cat.getFields(), "B", db_cat.getUser(), db_cat.getChanged());
        boolean result = db_cat.update();
        servlet.clearCache("cardtables", "card");
        return result;
    }

    public CardSucs getCardSucs() {
        CardSucs cards = new CardSucs(new JSONObject());
        JSONArray ocards = new JSONArray();
        String fields[] = new String[db_card.getKeyName().length + db_card.getFieldsName().length];
        System.arraycopy(db_card.getKeyName(), 0, fields, 0, db_card.getKeyName().length);
        System.arraycopy(db_card.getFieldsName(), 0, fields, db_card.getKeyName().length, db_card.getFieldsName().length);
        Class classes[] = new Class[db_card.getKeyClasses().length + db_card.getFieldsClasses().length];
        System.arraycopy(db_card.getKeyClasses(), 0, classes, 0, db_card.getKeyClasses().length);
        System.arraycopy(db_card.getFieldsClasses(), 0, classes, db_card.getKeyClasses().length, db_card.getFieldsClasses().length);
        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_card.tableModelName, "estatus = \'A\'");
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        for (int i = 0; i < size2; i++) {
            Object row[] = (Object[]) data2.elementAt(i);
            int inx = 0;
            CardSuc card = new CardSuc(new JSONObject());
            card.setCardLevel((int) row[inx++]);
            card.setCardId((String) row[inx++]);
            card.setCardNombre((String) row[inx++]);
            card.setResponsableId((String) row[inx++]);
            card.setResponsableNombre((String) row[inx++]);
            card.setVisorId((String) row[inx++]);
            card.setVisorNombre((String) row[inx++]);
            card.setEMail((String) row[inx++]);
            card.setFormatoId((String) row[inx++]);
            card.setMismaId((String) row[inx++]);
            card.setFecha((java.util.Date) row[inx++]);
            card.setEstatus((String) row[inx++]);
            card.update();
            ocards.put(card.json());
        }
        cards.update(ocards);
        return cards;
    }

    @Override
    public byte[] responseBytes(String command, JSONObject input) {
        return null;
    }
}
