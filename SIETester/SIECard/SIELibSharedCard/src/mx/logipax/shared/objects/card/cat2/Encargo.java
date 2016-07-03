package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;
/**
 *
 * @author admin
 */
public class Encargo {
    private JSONObject jobject;
    private String encargoId;
    private String sucursalId;
    private String regionId;
    private String nombre;
    private String estatusEncargo;
    
    public Encargo(JSONObject jobject){
        encargoId = "";
        sucursalId = "";
        regionId = "";
        nombre = "";
        estatusEncargo = "";
        reload(jobject);
    }
    
    public void reload(JSONObject jobject){
        this.jobject = jobject;
        try {
            if (jobject.has("encargoId")){
                setEncargoId(jobject.getString("encargoId"));
            }
            if (jobject.has("sucursalId")){
                setSucursalId(jobject.getString("sucursalId"));
            }
            if (jobject.has("regionId")){
                setRegionId(jobject.getString("regionId"));
            }
            if (jobject.has("nombre")){
                setNombre(jobject.getString("nombre"));
            }
            if (jobject.has("estatusEncargo")){
                setEstatusEncargo(jobject.getString("estatusEncargo"));
            }
        } catch (Exception ex) {
            System.err.println("Encargo:" + ex.toString());
        }
    }
    
    public void dispose()
    {  
    }
   
    public String getEncargoId() {
        return encargoId;
    }

    public void setEncargoId(String encargoId) {
        this.encargoId = encargoId;
    }

    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getEstatusEncargo() {
        return estatusEncargo;
    }

    public void setEstatusEncargo(String estatusEncargo) {
        this.estatusEncargo = estatusEncargo;
    }

    public void update() {
        jobject.put("encargoId", encargoId);
        jobject.put("sucursalId", sucursalId);
        jobject.put("regionId", regionId);
        jobject.put("nombre", nombre);
        jobject.put("estatusEncargo", estatusEncargo);
    }
    
    public JSONObject json() {
        return jobject;
    }




}
