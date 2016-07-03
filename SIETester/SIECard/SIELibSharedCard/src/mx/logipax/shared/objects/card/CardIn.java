package mx.logipax.shared.objects.card;

import org.json.JSONObject;

public class CardIn {

    private JSONObject jobject;
    public String ticket;
    private String function;
    public Cards cards;
    
    public CardIn(JSONObject jobject) {
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
            cards = new Cards(new JSONObject());
            if (jobject.has("cards")) {
                JSONObject jbudget = jobject.getJSONObject("cards");
                cards = new Cards(jbudget);
            }
        } catch (Exception ex) {
            System.err.println("CardsIn:" + ex.toString());
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
    
    public void update() {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("cards", cards.json());
    }
    
    public JSONObject json() {
        return jobject;
    }
}
