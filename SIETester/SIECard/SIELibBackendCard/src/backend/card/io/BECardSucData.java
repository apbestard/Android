/*
 Derechos Reservados (c)
 Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
 2009, 2010, 2011, 2012, 2013, 2014
 logipax Marca Registrada (R)  2003, 2014
 */
package backend.card.io;

import backend.BEObject;
import backend.BEServletInterfase;
import backend.card.BECardDispatcher;
import backend.card.def.vwr.cat.DefDBVCard;
import backend.card.def.vwr.cat.DefDBVNivel;
import backend.security.SecuritySession;
import java.io.OutputStream;
import mx.logipax.utility.Dates;
import mx.logipax.utility.ZipUtility;
import java.sql.Connection;
import java.sql.PreparedStatement;
import mx.logipax.shared.DBDefaults;
import mx.logipax.shared.objects.RepoLevel;
import mx.logipax.shared.objects.card.cat.CardSuc;
import mx.logipax.shared.objects.card.Cards;
import mx.logipax.shared.objects.viewer.ReportIn;
import mx.logipax.utility.Strings;
import mx.logipax.utility.Utility;
import org.json.JSONObject;

public class BECardSucData implements BEObject {

    static final String SERVLETNAME = "carddata";
    final BEServletInterfase servlet;
    final BECardDispatcher card;

    public BECardSucData(BEServletInterfase servlet, BECardDispatcher card) {
        this.servlet = servlet;
        this.card = card;
    }

    public boolean match(String url) {
        return (url.equals(SERVLETNAME));
    }

    public String getMime() {
        return ("image/xml");
    }

    public void insertLog(String reportId, String dataId, String rank1Id, String rank2Id, String userId, String level, long time, long dbtime) {
        //if (true) return;
        Connection conn = null;
        java.util.Vector data2 = new java.util.Vector();
        try {
            conn = servlet.newDirectConnection(DBDefaults.DBVWRDATAPOOL, new String[][]{{"socketTimeout", Integer.toString(60000 * 30)}});
            conn.setAutoCommit(true);
            String insertStatement
                    = "insert into "+/*OWNER.*/"sie_sys_vlogs "
                    + "(fecha, hora, user_id, report_id,data_id, rank1_id, rank2_id, level_id, rpdelay, dbdelay) "
                    + "values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement prepStmt = conn.prepareStatement(insertStatement);
            int inx = 0;
            prepStmt.setDate(++inx, new java.sql.Date(Dates.today().getTime()));
            prepStmt.setString(++inx, Dates.getDateString(System.currentTimeMillis(), "HH:mm:ss"));
            prepStmt.setString(++inx, userId);
            prepStmt.setString(++inx, reportId);
            prepStmt.setString(++inx, dataId);
            prepStmt.setString(++inx, rank1Id);
            prepStmt.setString(++inx, rank2Id);
            prepStmt.setString(++inx, level);
            prepStmt.setInt(++inx, (int) time);
            prepStmt.setInt(++inx, (int) dbtime);
//System.out.println("SQL:"+insertStatement+"nombre:"+nombre+" recorded:"+hashCode+" orden:"+ckey.orden+" parent:"+ckey.parent+" nivel:"+ckey.nivel);
            prepStmt.executeUpdate();
            prepStmt.close();
        } catch (Exception ex) {
            System.err.println(ex.toString());
        } finally {
            if (conn != null) {
                servlet.freeDirectConnection(DBDefaults.DBVWRDATAPOOL, conn);
            }
        }
    }

    public byte[] getDataDef(Object[] key) {
        if (true) return null;
        String keyId = key[0].toString();
        for (int i = 1; i < key.length; i++) {
            keyId += "_" + key[i];
        }
        byte[] bresult = servlet.getCache("carddatas", keyId);
        return bresult;
    }

    public boolean updateDataDef(Object[] key, byte[] data) {
        String keyId = key[0].toString();
        for (int i = 1; i < key.length; i++) {
            keyId += "_" + key[i];
        }
        //if (keyId.startsWith("MCOL_")) return true;
        servlet.setCache("carddatas", keyId, data);
        return true;
    }

    private int levels(int levelNo, RepoLevel level) {
        if (level.levels.length == 0) {
            return levelNo;
        }
        return levels(levelNo+1, level.levels[0]);
    } 
   public byte[] responseBytes(String command, JSONObject jobject) {
        ReportIn in = new ReportIn(jobject);
        SecuritySession session = (SecuritySession) servlet.securitySession(in.ticket);
        if (session == null) {
            return null;
        }
        String reportId = in.id;
        String extId = in.extid;
        if (extId == null) {
            extId = "";
        }
        if (extId.equals("3")) {
            extId = "0";
        }
        String nodes0No[] = Strings.tokenizer(in.level01No, "|");
        String nodes0Id[] = Strings.tokenizer(in.level01Id, "|");
        String periodId = in.periodId;
        if (nodes0Id == null || nodes0Id.length == 0) {
            nodes0Id = new String[]{"0"};
        }
        if (nodes0No.length != nodes0Id.length) {
            return null;
        }
        String user = session.userId;
        String level = session.profile + BECardSuc.getNotNull(session.level) + BECardSuc.getNotNull(session.levelvalue);
        long time = 0;
        long dbtime = 0;
        long start = System.currentTimeMillis();
        Cards cards = card.dbcard.getReport(DBDefaults.DBVWRDATAPOOL, session.profile, BECardSuc.getNotNull(session.level), BECardSuc.getNotNull(session.levelvalue));
        if (cards == null) {
            return null;
        }
        String dataId = cards.report.dataId;
        String rank1Id = "";
        if (cards.report.levelIds.length > 0) {
            rank1Id = cards.report.levelIds[0];
        }
        String rank1 = rank1Id + ":" + in.level01No + in.level01Id;
        String rank2 = "";
        int node0No = Strings.getInt(nodes0No[0]);
        String node0Id = nodes0Id[0];
        if (reportId == null || reportId.length() == 0
                || node0Id == null || node0Id.length() == 0) {
            return null;
        }
        long startdata = System.currentTimeMillis();
        String level0Id = rank1Id;
        int levelNo = levels(0, cards.report.levels[0]);
        int nivel0No = (node0No == levelNo)?node0No:node0No+1;
        String nodo0Id = (node0No == levelNo)?node0Id:null;
        String nodo0PadreId = (node0No == levelNo)?null:node0Id;
        byte[] zdata = getDataDef(new Object[]{dataId, extId, rank1Id, node0No, node0Id});
        if (zdata == null) {
            String pool = cards.report.pool.length() > 0 ? cards.report.pool : DBDefaults.DBVWRDATAPOOL;
            CardSuc rcard  = card.dbdata.getCardSuc(pool, level0Id, nivel0No, nodo0Id, nodo0PadreId);
            rcard.update();
            cards.card = rcard.json();
            cards.updateNoReport();
            dbtime += System.currentTimeMillis() - startdata;
            String sdata = cards.json().toString();
            byte[] bdata = Utility.toBytes(sdata);
            zdata = ZipUtility.zip(bdata, "data");
            updateDataDef(new Object[]{dataId, extId, rank1Id, node0No, node0Id, periodId}, zdata);
            insertLog(reportId, dataId, rank1, rank2, user, level, time, dbtime);
        }
        return zdata;
    }

     public String[] responseText(String command, JSONObject jobject) {
        return null;
    }

    private String listId(String[] list) {
        String id = "";
        for (int i = 0; i < list.length; i++) {
            if (id.length() > 0) {
                id += "|";
            }
            id += list[i];
        }
        return id;
    }

    private static String list(String names[]) {
        String str = "";
        if (names == null) {
            return str;
        }
        for (int i = 0; i < names.length; i++) {
            if (str.length() > 0) {
                str += "|";
            }
            str += names[i];
        }
        return str;
    }

    public boolean responseStream(String uId, String params[], String values[], OutputStream ou) {
        return (false);
    }

    public String[] responseText(String uId, String params[], String values[]) {
        return new String[]{null, null, null};
    }
}
