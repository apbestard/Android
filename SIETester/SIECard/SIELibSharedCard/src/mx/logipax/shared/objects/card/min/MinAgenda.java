package mx.logipax.shared.objects.card.min;

import org.json.JSONObject;

public class MinAgenda {

    private JSONObject jobject;
    private String minutaId;
    private int secuencia;
    private String descripcion;
    private String texto;
    private String estatus;

    public MinAgenda(JSONObject jobject) {
        minutaId = "";
        secuencia = 1;
        descripcion = "";
        texto = "";
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
            if (jobject.has("descripcion")) {
                descripcion = jobject.getString("descripcion");
            }
            if (jobject.has("texto")) {
                texto = jobject.getString("texto");
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

    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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
        jobject.put("descripcion", descripcion);
        jobject.put("texto", texto);
        jobject.put("estatus", estatus);
    }

    public JSONObject json() {
        return jobject;
    }
}
