package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;

public class EjecutivoIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public Ejecutivo ejecutivo;

    public EjecutivoIn(JSONObject jobject) {
        ticket = "";
        function = "";
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("ticket")) {
                ticket = jobject.getString("ticket");
            }
            if (jobject.has("function")) {
                function = jobject.getString("function");
            }
            ejecutivo = new Ejecutivo(new JSONObject());
            if (jobject.has("ejecutivo")) {
                JSONObject jejecutivo = jobject.getJSONObject("ejecutivo");
                ejecutivo = new Ejecutivo(jejecutivo);
            }
        } catch (Exception ex) {
            System.err.println("EjecutivoIn:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
    
    public void update(JSONObject ejecutivo) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("ejecutivo", ejecutivo);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
