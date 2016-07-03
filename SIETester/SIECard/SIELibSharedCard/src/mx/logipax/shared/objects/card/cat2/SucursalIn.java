package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;

public class SucursalIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public Sucursal sucursal;

    public SucursalIn(JSONObject jobject) {
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
                sucursal = new Sucursal(new JSONObject());
            if (jobject.has("sucursal")) {
                JSONObject jsucursal = jobject.getJSONObject("sucursal");
                sucursal = new Sucursal(jsucursal);
            }
        } catch (Exception ex) {
            System.err.println("SucursalIn:" + ex.toString());
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
    
    public void update(JSONObject sucursal) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("sucursal", sucursal);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
