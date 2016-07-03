package mx.logipax.shared.objects.card.zz;

import org.json.JSONObject;

public class LevelEncargoIn {

    private JSONObject jobject;
    private String ticket;
    private String estructuraId;

    public LevelEncargoIn(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("ticket")) {
                ticket = jobject.getString("ticket");
            }
            if (jobject.has("estructuraId")) {
                estructuraId = jobject.getString("estructuraId");
            }
        } catch (Exception e) {
            System.err.println("Creditos:" + e.toString());
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

    public String getEstructuraId() {
        return estructuraId;
    }

    public void setEstructuraId(String estructuraId) {
        this.estructuraId = estructuraId;
    }
    
    public void update() {
        jobject.put("ticket", ticket);
        jobject.put("estructuraId", estructuraId);
    }

    public JSONObject json() {
        return jobject;
    }
}
