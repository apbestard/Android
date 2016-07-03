/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.io;

import backend.BEObject;
import backend.BEServletInterfase;
import backend.security.SecuritySession;
import backend.card.BECardDispatcher;
import java.io.OutputStream;
import mx.logipax.shared.DBDefaults;
import mx.logipax.shared.DlgProcessing;
import mx.logipax.shared.objects.card.CardIn;
import org.json.JSONObject;

public class BECardSuc implements BEObject {

    static final String SERVLETNAME = "card";
    final BEServletInterfase servlet;
    final BECardDispatcher card;

    public BECardSuc(BEServletInterfase servlet, BECardDispatcher card) {
        this.servlet = servlet;
        this.card = card;
    }

    public boolean match(String url) {
        return (url.equals(SERVLETNAME));
    }

    public String getMime() {
        return ("text/xml");
    }

    public byte[] responseBytes(String command, JSONObject jobject) {
        return (new byte[4]);
    }
    
    public String[] responseText(String command, JSONObject jobject) {
        CardIn in = new CardIn(jobject);
        SecuritySession session = (SecuritySession) servlet.securitySession(in.getTicket());
        if (session == null) {
            return new String[]{null, null, "Desconectado"};
        }
        String function = in.getFunction();
        if (function == null || function.length() == 0) {
            return new String[]{null, null, "Función nula"};
        }
        if (function.equals(DlgProcessing.EDT)) {
        } else if (function.equals(DlgProcessing.ADD)) {
            
        } else if (function.equals(DlgProcessing.DEL)) {
        } else if (function.equals(DlgProcessing.SHW)) {
        } else 
        if (function.equals(DlgProcessing.LST)) {
            String[] result = card.dbcard.get(DBDefaults.DBVWRDATAPOOL, session.profile, getNotNull(session.level), getNotNull(session.levelvalue));
            if (result == null) {
                return new String[]{null, null, "Función invalida"};
            }
            return result;
        }
        return new String[]{null, null, "Función invalida"};
    }
    
    public byte[] responseBytes(String uId, String params[], String values[]) {
        return (new byte[4]);
    }

    public boolean responseStream(String uId, String params[], String values[], OutputStream ou) {
        return (false);
    }

    public static String getNotNull(String value) {
        if (value == null || value.length() == 0)
            return "NA";
        return value;
    }
    public static boolean isNull(String value) {
        return (value == null || value.length() == 0 || value.equals("NA"));
    }
}
