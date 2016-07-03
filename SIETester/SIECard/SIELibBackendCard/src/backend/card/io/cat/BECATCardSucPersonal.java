package backend.card.io.cat;

import backend.BEServletInterfase;
import backend.card.def.DefDBCardSucPersona;
import backend.catalogs2.db.DB2CatList;
import backend.catalogs2.db.DB2CatUpd;
import backend.security.SecuritySession;
import mx.logipax.shared.objects.card.cat.CardSuc;
import mx.logipax.shared.objects.card.cat.CardSucPersona;
import mx.logipax.utility.Dates;
import org.json.JSONObject;

public class BECATCardSucPersonal {

    final BEServletInterfase servlet;
    DefDBCardSucPersona db_persona;

    public BECATCardSucPersonal(BEServletInterfase servlet) {
        this.servlet = servlet;
        db_persona = new DefDBCardSucPersona(servlet);
    }
    
    public DB2CatUpd db_cat() {
        return new DB2CatUpd(servlet.db(), "local", 0, db_persona);
    }

    public CardSucPersona[] getPersonal(int cardLevel, String cardId) {
        String fields[] = new String[db_persona.getKeyName().length + db_persona.getFieldsName().length];
        System.arraycopy(db_persona.getKeyName(), 0, fields, 0, db_persona.getKeyName().length);
        System.arraycopy(db_persona.getFieldsName(), 0, fields, db_persona.getKeyName().length, db_persona.getFieldsName().length);
        Class classes[] = new Class[db_persona.getKeyClasses().length + db_persona.getFieldsClasses().length];
        System.arraycopy(db_persona.getKeyClasses(), 0, classes, 0, db_persona.getKeyClasses().length);
        System.arraycopy(db_persona.getFieldsClasses(), 0, classes, db_persona.getKeyClasses().length, db_persona.getFieldsClasses().length);

        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_persona.tableModelName, "card_level = " + cardLevel + " and card_id = \'" + cardId + "\' and estatus = \'A\'", "secuencia");

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
    
    public boolean updatePersonal(SecuritySession session, CardSuc card, DB2CatUpd db_cat) {
        CardSucPersona registro[] = card.getPersonal();
        removeCardSucPersona(session, card, db_cat);
        for (int i = 0; i < registro.length; i++) {
            registro[i].setSecuencia(i+1);
            registro[i].update();
            if (!appendCardSucPersona(session, registro[i], db_cat)) {
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
    
    public boolean removeCardSucPersona(SecuritySession session, CardSuc card, DB2CatUpd db_cat) {
        db_cat.deleteAll("card_level = '" + card.getCardLevel() + " and card_id = '" + card.getCardId() + "'");
        return true;
    }
}
