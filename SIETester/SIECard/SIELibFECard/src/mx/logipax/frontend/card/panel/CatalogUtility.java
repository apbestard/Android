package mx.logipax.frontend.card.panel;

import java.util.HashMap;
import javax.swing.JOptionPane;
import mx.logipax.ide.IDELogipax;
import mx.logipax.ide.utility.EditCombo;
import mx.logipax.interfase.SIE;
import mx.logipax.shared.backend.BEDispatcher;
import mx.logipax.shared.objects.cat.Tabla;
import mx.logipax.shared.objects.cat.TablaIn;
import org.json.JSONObject;

public class CatalogUtility {

    private static HashMap<String, String> val0;

    public static Tabla loadTabla(IDELogipax frame, String url, BEDispatcher dispatcher, String ticket, boolean nombres, String tabla, String nivel1, String nivel2) {
        TablaIn in = new TablaIn(new JSONObject());
        in.setTicket(ticket);
        in.setTabla(tabla);
        in.setNombres(nombres);
        in.setNivel1(nivel1);
        in.setNivel2(nivel2);
        in.update();
        JSONObject jresult = SIE.interfase(frame.getClass(), url, dispatcher, "cardtable", in.json());
        String errorStr = null;
        if (jresult.has("exception")) {
            JSONObject exception = jresult.getJSONObject("exception");
            errorStr = exception.getString("error");
        }
        if (jresult.has("error")) {
            errorStr = jresult.getString("error");
        }
        if (errorStr != null) {
            return null;
        }
        Tabla out = new Tabla(jresult);
        String[] values = new String[out.getIds().length];
        for (int i = 0; i < values.length; i++) {
           if (nombres && out.getNombres().length > i) {
                values[i] = out.getIds()[i] + " - " + out.getNombres()[i];
            } else {
                values[i] = out.getIds()[i];
            }
        }
        return out;
    }

    public static boolean loadTabla(IDELogipax frame, String url, BEDispatcher dispatcher, String ticket, boolean nombres, String tabla, String nivel1, String nivel2, EditCombo combo, javax.swing.JLabel error) {
        HashMap<String, String> val = new HashMap<String, String>();
        TablaIn in = new TablaIn(new JSONObject());
        in.setTicket(ticket);
        in.setTabla(tabla);
        in.setNombres(nombres);
        in.setNivel1(nivel1);
        in.setNivel2(nivel2);
        in.update();
        JSONObject jresult = SIE.interfase(frame.getClass(), url, dispatcher, "cardtabla", in.json());
        String errorStr = null;
        if (jresult.has("exception")) {
            JSONObject exception = jresult.getJSONObject("exception");
            errorStr = exception.getString("error");
        }
        if (jresult.has("error")) {
            errorStr = jresult.getString("error");
        }
        if (errorStr != null) {
            if (error == null) {
                JOptionPane.showMessageDialog(frame.getFrame(), errorStr, "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                error.setText(errorStr);
            }
            return false;
        }
        Tabla out = new Tabla(jresult);
        String[] values = new String[out.getIds().length];
        for (int i = 0; i < values.length; i++) {
            if (nombres && out.getNombres().length > i) {
                val.put(out.getIds()[i] + " - " + out.getNombres()[i], out.getIds()[i]);
                values[i] = out.getIds()[i] + " - " + out.getNombres()[i];
            } else {
                val.put(out.getIds()[i], out.getIds()[i]);
                values[i] = out.getIds()[i];
            }
        }
        combo.values(values);
        combo.setMap(val);
        return true;
    }

    public static String getKeySel(String key) {
        return key + " - ";
    }

    public static String getLine(String key, String name) {
        return key + " - " + name;
    }

    public static String getKey(String line) {
        if (line == null) {
            return "";
        }
        int inx = line.indexOf(" - ");
        if (inx < 0) {
            return line;
        }
        return line.substring(0, inx);
    }
    
    public static String getName(String line) {
        if (line == null) {
            return "";
        }
        int inx = line.indexOf(" - ");
        if (inx < 0) {
            return line;
        }
        return line.substring(inx+3);
    }
}
