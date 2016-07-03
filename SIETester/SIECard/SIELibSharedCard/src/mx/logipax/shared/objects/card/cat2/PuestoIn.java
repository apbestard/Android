package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;

public class PuestoIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public Puesto puesto;

    public PuestoIn(JSONObject jobject) {
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
                puesto = new Puesto(new JSONObject());
            if (jobject.has("puesto")) {
                JSONObject jpuesto = jobject.getJSONObject("puesto");
                puesto = new Puesto(jpuesto);
            }
        } catch (Exception ex) {
            System.err.println("PuestoIn:" + ex.toString());
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

    public void update(JSONObject puesto) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("puesto", puesto);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
