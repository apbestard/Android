package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;
/**
 *
 * @author admin
 */
public class Zona {
    private JSONObject jobject;
    private String zonaId;
    private String nombre;
    private String residenciaId;
    private String jefezonaId;

    public Zona(JSONObject jobject){
        zonaId = "";
        nombre = "";
        residenciaId = "";
        jefezonaId = "";
        reload(jobject);
    }
    
    public void reload(JSONObject jobject){
        this.jobject = jobject;
        try {
            if (jobject.has("zonaId")){
               zonaId = jobject.getString("zonaId");
            }
            if (jobject.has("nombre")){
               nombre = jobject.getString("nombre");
            }
            if (jobject.has("residenciaId")){
               residenciaId = jobject.getString("residenciaId");
            }
            if (jobject.has("jefezonaId")){
               jefezonaId = jobject.getString("jefezonaId");
            }
        } catch (Exception ex) {
            System.err.println("Zona:" + ex.toString());
        }
    }
    
    public void dispose()
    {  
    }
    
    public String getZonaId() {
        return zonaId;
    }

    public void setZonaId(String zonaId) {
        this.zonaId = zonaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResidenciaId() {
        return residenciaId;
    }

    public void setResidenciaId(String residenciaId) {
        this.residenciaId = residenciaId;
    }

    public String getJefezonaId() {
        return jefezonaId;
    }

    public void setJefezonaId(String jefezonaId) {
        this.jefezonaId = jefezonaId;
    }
    
    public void update() {
        jobject.put("zonaId", zonaId);
        jobject.put("nombre", nombre);
        jobject.put("residenciaId", residenciaId);
        jobject.put("jefezonaId", jefezonaId);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
