package mx.logipax.shared.objects.card.zz;

import org.json.JSONObject;

public class AsignacionEncargoIn {
    private JSONObject jobject;
    private String ticket;
    private String function;
    private String ejecutivoId;
    private String sucursalId;
    private String semanaId;
    private String nivel1;
    private String nivel2; 
    public AsignacionEncargo asignacionEncargo;
    
    public AsignacionEncargoIn(JSONObject jobject) {
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
            if (jobject.has("ejecutivoId")) {
                ejecutivoId = jobject.getString("ejecutivoId");
            }
            if (jobject.has("sucursalId")) {
                sucursalId = jobject.getString("sucursalId");
            }
            if (jobject.has("semanaId")) {
                semanaId = jobject.getString("semanaId");
            }
            if (jobject.has("nivel1")) {
                nivel1 = jobject.getString("nivel1");
            }
            if (jobject.has("nivel2")) {
                nivel2 = jobject.getString("nivel2");
            }
            asignacionEncargo = new AsignacionEncargo(new JSONObject());
            if (jobject.has("asignacionEncargo")) {
                JSONObject jasignacionEncargo = jobject.getJSONObject("asignacionEncargo");
                asignacionEncargo = new AsignacionEncargo(jasignacionEncargo);
            }
        } catch (Exception ex) {
            System.err.println("Module:" + ex.toString());
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

    public String getEjecutivoId() {
        return ejecutivoId;
    }

    public void setEjecutivoId(String ejecutivoId) {
        this.ejecutivoId = ejecutivoId;
    }

    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }
    
    public String getNivel1() {
        return nivel1;
    }

    public void setNivel1(String nivel1) {
        this.nivel1 = nivel1;
    }

    public String getNivel2() {
        return nivel2;
    }

    public void setNivel2(String nivel2) {
        this.nivel2 = nivel2;
    }

    public String getSemanaId() {
        return semanaId;
    }

    public void setSemanaId(String semanaId) {
        this.semanaId = semanaId;
    }
    
    public void update() {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("sucursalId", sucursalId);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("semanaId", semanaId);
        jobject.put("nivel1", nivel1);
        jobject.put("nivel2", nivel2);
        jobject.put("asignacionEncargo", asignacionEncargo.json());
    }

    public JSONObject json() {
        update();
        return jobject;
    }
}
