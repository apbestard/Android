package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;

public class RegionIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public Region region;

    public RegionIn(JSONObject jobject) {
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
                region = new Region(new JSONObject());
            if (jobject.has("region")) {
                JSONObject jregion = jobject.getJSONObject("region");
                region = new Region(jregion);
            }
        } catch (Exception ex) {
            System.err.println("RegionIn:" + ex.toString());
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

    public void update(JSONObject region) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("region", region);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
