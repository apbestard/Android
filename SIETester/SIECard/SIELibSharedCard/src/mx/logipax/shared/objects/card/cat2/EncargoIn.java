package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;

public class EncargoIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    public String sucursalId;
    public Encargo encargo;
    
    public EncargoIn(JSONObject jobject) {
        //Comentario prueba GIT
        //segundo comentario de prueba GIT
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
                sucursalId = "";
            if (jobject.has("sucursalId")) {
                sucursalId = jobject.getString("sucursalId");;
            }
            encargo = new Encargo(new JSONObject());
            if (jobject.has("encargo")) {
                JSONObject jencargo = jobject.getJSONObject("encargo");
                encargo = new Encargo(jencargo);
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

    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }
    
    public void update(JSONObject encargo) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("sucursalId", sucursalId);
        jobject.put("encargo", encargo);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
