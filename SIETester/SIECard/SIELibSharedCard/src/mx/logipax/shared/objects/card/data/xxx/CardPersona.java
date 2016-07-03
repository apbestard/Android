package mx.logipax.shared.objects.card.data.xxx;

import mx.logipax.shared.objects.card.data.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import mx.logipax.shared.objects.viewer.Report;
import org.json.JSONArray;
import org.json.JSONObject;

public class CardPersona {

    private JSONObject jobject;
    private String deptoId;
    private String deptoNombre;
    private String puestoId;
    private String puestoNombre;
    private int personas;
    private int vacantes;
    private String estatus;

    public CardPersona(JSONObject jobject) {
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
            if (jobject.has("deptoId")) {
                setDeptoId(jobject.getString("deptoId"));
            }
            if (jobject.has("deptoNombre")) {
                setDeptoNombre(jobject.getString("deptoNombre"));
            }
            if (jobject.has("puestoId")) {
                setResponsableId(jobject.getString("puestoId"));
            }
            if (jobject.has("puestoNombre")) {
                setResponsableNombre(jobject.getString("puestoNombre"));
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

    public String getResponsableId() {
        return puestoId;
    }

    public void setResponsableId(String puestoId) {
        this.puestoId = puestoId;
    }
    
    public String getResponsableNombre() {
        return puestoNombre;
    }

    public void setResponsableNombre(String puestoNombre) {
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
