package mx.logipax.shared.objects.card.cat;

import org.json.JSONObject;

public class CardSucEquipo {

    private JSONObject jobject;
    private int cardLevel;
    private String cardId;
    private int secuencia;
    private String deptoId;
    private String deptoNombre;
    private String equipoId;
    private String equipoNombre;
    private int equipos;
    private int vacantes;
    private String estatus;

    public CardSucEquipo(JSONObject jobject) {
        cardLevel = 0;
        cardId = "";
        secuencia = 0;
        deptoId = "";
        deptoNombre = "";
        equipoId = "";
        equipoNombre = "";
        equipos = 0;
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
            if (jobject.has("equipoId")) {
                setEquipoId(jobject.getString("equipoId"));
            }
            if (jobject.has("equipoNombre")) {
                setEquipoNombre(jobject.getString("equipoNombre"));
            }
            if (jobject.has("equipos")) {
                setEquipos(jobject.getInt("equipos"));
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

    public String getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(String equipoId) {
        this.equipoId = equipoId;
    }
    
    public String getEquipoNombre() {
        return equipoNombre;
    }

    public void setEquipoNombre(String equipoNombre) {
        this.equipoNombre = equipoNombre;
    }

    public int getEquipos() {
        return equipos;
    }

    public void setEquipos(int equipos) {
        this.equipos = equipos;
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
        jobject.put("equipoId", equipoId);
        jobject.put("equipoNombre", equipoNombre);
        jobject.put("equipos", equipos);
        jobject.put("vacantes", vacantes);
        jobject.put("estatus", estatus);
    }

    public JSONObject json() {
        return jobject;
    }
}
