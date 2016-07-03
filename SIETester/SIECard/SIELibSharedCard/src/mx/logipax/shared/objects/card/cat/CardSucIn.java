package mx.logipax.shared.objects.card.cat;

import org.json.JSONObject;

public class CardSucIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public CardSuc card;
    
    public CardSucIn(JSONObject jobject) {
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
            card = new CardSuc(new JSONObject());
            if (jobject.has("card")) {
                JSONObject jcard = jobject.getJSONObject("card");
                card = new CardSuc(jcard);
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
    
    public void update(JSONObject card) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("card", card);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
