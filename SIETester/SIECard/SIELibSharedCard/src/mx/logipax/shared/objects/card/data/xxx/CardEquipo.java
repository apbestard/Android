package mx.logipax.shared.objects.card.data.xxx;

import org.json.JSONObject;

public class CardEquipo {

    private JSONObject jobject;
    private String deptoId;
    private String deptoNombre;
    private String equipoId;
    private String equipoNombre;
    private int equipos;
    private int vacantes;
    private String estatus;

    public CardEquipo(JSONObject jobject) {
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
