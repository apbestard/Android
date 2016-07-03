package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;

public class ZonaIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public Zona zona;
    
    public ZonaIn(JSONObject jobject) {
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
                zona = new Zona(new JSONObject());
            if (jobject.has("zona")) {
                JSONObject jzona = jobject.getJSONObject("zona");
                zona = new Zona(jzona);
            }
        } catch (Exception ex) {
            System.err.println("ZonaIn:" + ex.toString());
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

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    
    public void update(JSONObject zona) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("zona", zona);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
