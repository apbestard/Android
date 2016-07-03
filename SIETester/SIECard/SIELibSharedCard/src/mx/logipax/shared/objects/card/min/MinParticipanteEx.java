package mx.logipax.shared.objects.card.min;

import org.json.JSONObject;

public class MinParticipanteEx {

    private JSONObject jobject;
    private String minutaId;
    private int secuencia;
    private String nombre;
    private String email;
    private String estatus;

    public MinParticipanteEx(JSONObject jobject) {
        minutaId = "";
        secuencia = 1;
        nombre = "";
        email = "";
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
            if (jobject.has("nombre")) {
                nombre = jobject.getString("nombre");
            }
            if (jobject.has("email")) {
                email = jobject.getString("email");
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEMail() {
        return email;
    }

    public void setEMail(String email) {
        this.email = email;
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
        jobject.put("nombre", nombre);
        jobject.put("email", email);
        jobject.put("estatus", estatus);
    }

    public JSONObject json() {
        return jobject;
    }
}
