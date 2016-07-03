package backend.card.io.cat;

import backend.BEObject;
import backend.BEServletInterfase;
import backend.card.def.DefDBCardSuc;
import backend.card.def.DefDBCardSucEquipo;
import backend.card.io.DBInterfase;
import backend.catalogs2.db.DB2CatList;
import backend.catalogs2.db.DB2CatUpd;
import backend.security.SecuritySession;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.shared.objects.card.cat.CardSuc;
import mx.logipax.shared.objects.card.cat.CardSucIn;
import mx.logipax.shared.objects.card.cat.CardSucs;
import mx.logipax.shared.objects.card.cat.CardSucEquipo;
import mx.logipax.utility.Dates;
import org.json.JSONArray;
import org.json.JSONObject;

public class BECATCardSucEquipos {

    final BEServletInterfase servlet;
    DefDBCardSucEquipo db_equipo;

    public BECATCardSucEquipos(BEServletInterfase servlet) {
        this.servlet = servlet;
        db_equipo = new DefDBCardSucEquipo(servlet);
    }
    
    public DB2CatUpd db_cat() {
        return new DB2CatUpd(servlet.db(), "local", 0, db_equipo);
    }

    public CardSucEquipo[] getEquipos(int cardLevel, String cardId) {
        String fields[] = new String[db_equipo.getKeyName().length + db_equipo.getFieldsName().length];
        System.arraycopy(db_equipo.getKeyName(), 0, fields, 0, db_equipo.getKeyName().length);
        System.arraycopy(db_equipo.getFieldsName(), 0, fields, db_equipo.getKeyName().length, db_equipo.getFieldsName().length);
        Class classes[] = new Class[db_equipo.getKeyClasses().length + db_equipo.getFieldsClasses().length];
        System.arraycopy(db_equipo.getKeyClasses(), 0, classes, 0, db_equipo.getKeyClasses().length);
        System.arraycopy(db_equipo.getFieldsClasses(), 0, classes, db_equipo.getKeyClasses().length, db_equipo.getFieldsClasses().length);

        DB2CatList db_list2 = new DB2CatList(servlet.db(), "local", fields, classes, db_equipo.tableModelName, "card_level = " + cardLevel + " and card_id = \'" + cardId + "\' and estatus = \'A\'", "secuencia");

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
    
    public boolean updateEquipos(SecuritySession session, CardSuc card, DB2CatUpd db_cat) {
        CardSucEquipo registro[] = card.getEquipos();
        removeCardSucEquipo(session, card, db_cat);
        for (int i = 0; i < registro.length; i++) {
            registro[i].setSecuencia(i+1);
            registro[i].update();
            if (!appendCardSucEquipo(session, registro[i], db_cat)) {
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
    
    public boolean removeCardSucEquipo(SecuritySession session, CardSuc card, DB2CatUpd db_cat) {
        db_cat.deleteAll("card_level = '" + card.getCardLevel() + " and card_id = '" + card.getCardId() + "'");
        return true;
    }
}
