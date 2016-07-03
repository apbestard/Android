package mx.logipax.shared.objects.card.cat;

import org.json.JSONObject;

public class CardSucPersona {

    private JSONObject jobject;
    private int cardLevel;
    private String cardId;
    private int secuencia;
    private String deptoId;
    private String deptoNombre;
    private String puestoId;
    private String puestoNombre;
    private int personas;
    private int vacantes;
    private String estatus;

    public CardSucPersona(JSONObject jobject) {
        cardLevel = 0;
        cardId = "";
        secuencia = 0;
        deptoId = "";
        deptoNombre = "";
        puestoId = "";
        puestoNombre = "";
        personas = 0;
        vacantes = 0;
        estatus = "";
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("cardLevel")) {
                setCardLevel(jobject.getInt("cardLevel"));
            }
            if (jobject.has("cardId")) {
                setCardId(jobject.getString("cardId"));
            }
            if (jobject.has("secuencia")) {
                setSecuencia(jobject.getInt("secuencia"));
            }
            if (jobject.has("deptoId")) {
                setDeptoId(jobject.getString("deptoId"));
            }
            if (jobject.has("deptoNombre")) {
                setDeptoNombre(jobject.getString("deptoNombre"));
            }
            if (jobject.has("puestoId")) {
                setPuestoId(jobject.getString("puestoId"));
            }
            if (jobject.has("puestoNombre")) {
                setPuestoNombre(jobject.getString("puestoNombre"));
            }
            if (jobject.has("personas")) {
                setPersonas(jobject.getInt("personas"));
            }
            if (jobject.has("vacantes")) {
                setVacantes(jobject.getInt("vacantes"));
            }
            if (jobject.has("estatus")) {
                setEstatus(jobject.getString("estatus"));
            }
        } catch (Exception ex) {
            System.err.println("Depto:" + ex.toString());
        }
    }

    public void dispose() {
    }


    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
    }
    

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    
    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }
    
    
    public String getDeptoId() {
        return deptoId;
    }

    public void setDeptoId(String deptoId) {
        this.deptoId = deptoId;
    }
    
    public String getDeptoNombre() {
        return deptoNombre;
    }

    public void setDeptoNombre(String deptoNombre) {
        this.deptoNombre = deptoNombre;
    }

    public String getPuestoId() {
        return puestoId;
    }

    public void setPuestoId(String puestoId) {
        this.puestoId = puestoId;
    }
    
    public String getPuestoNombre() {
        return puestoNombre;
    }

    public void setPuestoNombre(String puestoNombre) {
        this.puestoNombre = puestoNombre;
    }

    public int getPersonas() {
        return personas;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }
    
    public int getVacantes() {
        return vacantes;
    }

    public void setVacantes(int vacantes) {
        this.vacantes = vacantes;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    
    public void update() {
        jobject.put("cardLevel", cardLevel);
        jobject.put("cardId", cardId);
        jobject.put("secuencia", secuencia);
        jobject.put("deptoId", deptoId);
        jobject.put("deptoNombre", deptoNombre);
        jobject.put("puestoId", puestoId);
        jobject.put("puestoNombre", puestoNombre);
        jobject.put("personas", personas);
        jobject.put("vacantes", vacantes);
        jobject.put("estatus", estatus);
    }

    public JSONObject json() {
        return jobject;
    }
}
