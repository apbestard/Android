package mx.logipax.shared.objects.card.min;

import org.json.JSONObject;

public class MinParticipante {

    private JSONObject jobject;
    private String minutaId;
    private int secuencia;
    private String ejecutivoId;
    private String estatus;
   
    public MinParticipante(JSONObject jobject) {
        minutaId = "";
        secuencia = 1;
        ejecutivoId = "";
        estatus = "";
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("minutaId")) {
                minutaId = jobject.getString("minutaId");
            }
            if (jobject.has("secuencia")) {
                secuencia = jobject.getInt("secuencia");
            }
            if (jobject.has("ejecutivoId")) {
                ejecutivoId = jobject.getString("ejecutivoId");
            }
            if (jobject.has("estatus")) {
                estatus = jobject.getString("estatus");
            }
        } catch (Exception ex) {
            System.err.println("GpoEjecutivo:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public String getMinutaId() {
        return minutaId;
    }

    public void setMinutaId(String minutaId) {
        this.minutaId = minutaId;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public String getEjecutivoId() {
        return ejecutivoId;
    }

    public void setEjecutivoId(String ejecutivoId) {
        this.ejecutivoId = ejecutivoId;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void update() {
        jobject.put("minutaId", minutaId);
        jobject.put("secuencia", secuencia);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("estatus", estatus);
    }

    public JSONObject json() {
        return jobject;
    }
}
