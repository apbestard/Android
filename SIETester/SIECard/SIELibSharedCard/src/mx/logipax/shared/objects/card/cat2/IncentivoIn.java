package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;

public class IncentivoIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public Incentivo incentivo;

    public IncentivoIn(JSONObject jobject) {
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
                incentivo = new Incentivo(new JSONObject());
            if (jobject.has("incentivo")) {
                JSONObject jincentivo = jobject.getJSONObject("incentivo");
                incentivo = new Incentivo(jincentivo);
            }
        } catch (Exception ex) {
            System.err.println("IncentivoIn:" + ex.toString());
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

    public void update(JSONObject incentivo) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("incentivo", incentivo);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
