package mx.logipax.shared.objects.card.zz;

import org.json.JSONObject;

public class AsignacionEncargo {
    private JSONObject jobject;
    private String semanaId;
    private String encargoId;
    private String encargoNombre;
    private String sucursalId;
    private String ejecutivoId;
    private String ejecutivoNombre;
    private String incentivoId;
    
    
    public AsignacionEncargo(JSONObject jobject) {
        reload(jobject);
    }
    
    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("semanaId")) {
                setSemanaId(jobject.getString("semanaId"));
            }
            if (jobject.has("encargoId")) {
                setEncargoId(jobject.getString("encargoId"));
            }
            if (jobject.has("encargoNombre")) {
                setEncargoNombre(jobject.getString("encargoNombre"));
            }
            if (jobject.has("sucursalId")) {
                setSucursalId(jobject.getString("sucursalId"));
            }
            if (jobject.has("ejecutivoId")) {
                setEjecutivoId(jobject.getString("ejecutivoId"));
            }
            if (jobject.has("ejecutivoNombre")) {
                setEjecutivoNombre(jobject.getString("ejecutivoNombre"));
            }
            if (jobject.has("incentivoId")) {
                setEjecutivoId(jobject.getString("incentivoId"));
            }
        }catch (Exception ex) {
            System.err.println("Asignacion encargo:" + ex.toString());
        }
    }
    
    public void dispose() {
    }

    
    
    public String getSemanaId() {
        return semanaId;
    }

    public void setSemanaId(String semanaId) {
        this.semanaId = semanaId;
    }

    public String getEncargoId() {
        return encargoId;
    }

    public void setEncargoId(String encargoId) {
        this.encargoId = encargoId;
    }

    public String getEncargoNombre() {
        return encargoNombre;
    }

    public void setEncargoNombre(String encargoNombre) {
        this.encargoNombre = encargoNombre;
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

    public String getEjecutivoNombre() {
        return ejecutivoNombre;
    }

    public void setEjecutivoNombre(String ejecutivoNombre) {
        this.ejecutivoNombre = ejecutivoNombre;
    }
    
    public String getIncentivoId() {
        return incentivoId;
    }

    public void setIncentivoId(String incentivoId) {
        this.incentivoId = incentivoId;
    }

    public void update() {
        jobject.put("sucursalId", getSucursalId());
        jobject.put("encargoId", getEncargoId());
        jobject.put("encargoNombre", getEncargoNombre());
        jobject.put("ejecutivoId", getEjecutivoId());
        jobject.put("ejecutivoNombre", getEjecutivoNombre());
        jobject.put("incentivoId", getIncentivoId());
        jobject.put("semanaId", getSemanaId());
    }
    
    public JSONObject json() {
        update();
        return jobject;
    }

}
