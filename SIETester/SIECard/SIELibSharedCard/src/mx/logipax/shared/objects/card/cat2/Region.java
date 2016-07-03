package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;

/**
 *
 * @author admin
 */
public class Region {
    private JSONObject jobject;
    private String regionId;
    private String nombre;
    private String divisionId;
    
    public Region(JSONObject jobject){
        regionId = "";
        nombre = "";
        divisionId = "";
        reload(jobject);
    }
    
    public void reload(JSONObject jobject){
        this.jobject = jobject;
        try {
            if (jobject.has("regionId")){
                regionId = jobject.getString("regionId");
            }
            if (jobject.has("nombre")){
                nombre = jobject.getString("nombre");
            }
            if (jobject.has("divisionId")){
                divisionId = jobject.getString("divisionId");
            }
        } catch (Exception ex) {
            System.err.println("Regiones:" + ex.toString());
        }
    }
    
    public void dispose()
    {
    }
    
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }
    
    public void update() {
        jobject.put("regionId", regionId);
        jobject.put("nombre", nombre);
        jobject.put("divisionId", divisionId);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
