package mx.logipax.shared.objects.card.zz;

import org.json.JSONObject;

public class AsignacionIn {

    private JSONObject jobject;
    private String ticket;
    private String function;
    private String semanaId;
    private String sucursalId;
    private String ejecutivoId;
    public Asignacion asignacion;

    public AsignacionIn(JSONObject jobject) {
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
            if (jobject.has("semanaId")) {
                semanaId = jobject.getString("semanaId");
            }
            if (jobject.has("sucursalId")) {
                sucursalId = jobject.getString("sucursalId");
            }
            if (jobject.has("ejecutivoId")) {
                ejecutivoId = jobject.getString("ejecutivoId");
            }
            asignacion = new Asignacion(new JSONObject());
            if (jobject.has("asignacion")) {
                JSONObject jasignacion = jobject.getJSONObject("asignacion");
                asignacion = new Asignacion(jasignacion);
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

    public String getSemanaId() {
        return semanaId;
    }

    public void setSemanaId(String semanaId) {
        this.semanaId = semanaId;
    }

    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getEjecutivoId() {
        return ejecutivoId;
    }

    public void setEjecutivoId(String ejecutivoId) {
        this.ejecutivoId = ejecutivoId;
    }

    public Asignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

    public void update(JSONObject asignacion) {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("semanaId", semanaId);
        jobject.put("sucursalId", sucursalId);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("asignacion", asignacion);
    }

    public JSONObject json() {
        return jobject;
    }
}
