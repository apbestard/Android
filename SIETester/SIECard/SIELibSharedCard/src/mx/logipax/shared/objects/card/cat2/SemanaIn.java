package mx.logipax.shared.objects.card.cat2;

import org.json.JSONArray;
import org.json.JSONObject;

public class SemanaIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public Semana semana;

    public SemanaIn(JSONObject jobject) {
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
                semana = new Semana(new JSONObject());
            if (jobject.has("semana")) {
                JSONObject jsemana = jobject.getJSONObject("semana");
                semana = new Semana(jsemana);
            }
        } catch (Exception ex) {
            System.err.println("SemanaIn:" + ex.toString());
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

    public void update(JSONObject semana) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("semana", semana);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
