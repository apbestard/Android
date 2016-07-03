package mx.logipax.shared.objects.card.min;

import org.json.JSONObject;

public class MinutaIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public Minuta minuta;
    
    public MinutaIn(JSONObject jobject) {
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
            minuta = new Minuta(new JSONObject());
            if (jobject.has("minuta")) {
                JSONObject jminuta = jobject.getJSONObject("minuta");
                minuta = new Minuta(jminuta);
            }
        } catch (Exception ex) {
            System.err.println("EncargoIn:" + ex.toString());
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
    
    public void update(JSONObject minuta) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("minuta", minuta);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
