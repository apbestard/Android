package backend.card.io.cat;

import backend.BEObject;
import backend.BEServletInterfase;
import backend.card.BECardDispatcher;
import backend.card.def.DefDBCardSuc;
import backend.card.io.DBInterfase;
import backend.catalogs2.db.DB2CatList;
import backend.catalogs2.db.DB2CatUpd;
import backend.security.SecuritySession;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.shared.objects.card.cat.CardSuc;
import mx.logipax.shared.objects.card.cat.CardSucIn;
import mx.logipax.shared.objects.card.cat.CardSucs;
import mx.logipax.utility.Dates;
import mx.logipax.utility.Strings;
import org.json.JSONArray;
import org.json.JSONObject;

public class BECATCardSuc implements BEObject {

    final BEServletInterfase servlet;
    DefDBCardSuc db_card;
    BECATCardSucPersonal bepersonal;
    BECATCardSucEquipos beequipos;
 
    
    public BECATCardSuc(BEServletInterfase servlet, BECardDispatcher card) {
        this.servlet = servlet;
        db_card = new DefDBCardSuc(servlet);
        bepersonal = new BECATCardSucPersonal(servlet);
        beequipos = new BECATCardSucEquipos(servlet);
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
        String[] functions = Strings.tokenizer(function, "|");
        function = functions[0];
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, db_card);
        CardSuc card = in.card;
        if (function.equals(DlgProcessing.EDT)) {
            return updateCardSuc(session, card, functions.length == 1?"":functions[1], db_cat);
        } else if (function.equals(DlgProcessing.ADD)) {
            return newCardSuc(session, card, db_cat);
        } else if (function.equals(DlgProcessing.DEL)) {
            return removeCardSuc(session, card, db_cat);
        } else if (function.equals(DlgProcessing.SHW)) {
            return getCardSuc(card, db_cat);
        } else if (function.equals(DlgProcessing.LST)) {
            CardSucs cards = getCardSucs();
            cards.update();
            return new String[]{cards.json().toString(), null, null};
        }
        return new String[]{null, null, "Función invalida"};
    }

    public String[] getCardSuc(CardSuc card, DB2CatUpd db_cat) {
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
            cards.record.setEquipos(beequipos.getEquipos(card.getCardLevel(), card.getCardId()));
            cards.record.setPersonal(bepersonal.getPersonal(card.getCardLevel(), card.getCardId()));
            cards.record.update();
            cards.update();
        } else {
            return new String[]{null, null, "Error en leer datos: " + card.getCardId()};
        }
        return new String[]{cards.json().toString(), null, null};
    }

    public String[] newCardSuc(SecuritySession session, CardSuc card, DB2CatUpd db_cat) {
        CardSucs cards = new CardSucs(new JSONObject());
        if (appendCardSuc(session, card, db_cat)) {
            cards = getCardSucs();
            cards.record = card;
            cards.update();
        } else {
            return new String[]{null, null, "Error al crear un datos: " + card.getCardId()};
        }
        return new String[]{cards.json().toString(), null, null};
    }

    public String[] updateCardSuc(SecuritySession session, CardSuc card, String options, DB2CatUpd db_cat) {
        boolean main = options.indexOf("main") >= 0;
        CardSucs cards = new CardSucs(new JSONObject());
        boolean update = false;
        if (main) {
            update = updateCardSuc(session, card.getCardLevel(), card.getCardId(), new String[]{
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
                    card.getFormatoId(), 
                    card.getMismaId(), 
                    card.getFecha(), 
                    card.getEstatus()}, db_cat);
        } else {
            update = updateCardSuc(session, card.getCardLevel(), card.getCardId(), new String[]{
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
                    card.getFormatoId(), 
                    card.getMismaId(), 
                    card.getFecha(), 
                    card.getEstatus()}, db_cat);
        }
        if (update) {
            if (options.indexOf("equipos") >= 0) {
                beequipos.updateEquipos(session, card, beequipos.db_cat());
            }
            if (options.indexOf("personal") >= 0) {
                bepersonal.updatePersonal(session, card, bepersonal.db_cat());
            }
            cards = getCardSucs();
            cards.record = card;
            cards.update();
        } else {
            return new String[]{null, null, "Error al actualizar un datos: " + card.getCardId()};
        }
        return new String[]{cards.json().toString(), null, null};
    }
    
    public String[] removeCardSuc(SecuritySession session, CardSuc card, DB2CatUpd db_cat) {
        CardSucs cards = new CardSucs(new JSONObject());
        if (!deleteCardSuc(session, card.getCardLevel(), card.getCardId(), db_cat)) {
            return new String[]{null, null, "Error al eliminar un datos: " + card.getCardId()};
        }
        beequipos.removeCardSucEquipo(session, card, beequipos.db_cat());
        bepersonal.removeCardSucPersona(session, card, bepersonal.db_cat());
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
