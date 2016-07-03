package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;
/**
 *
 * @author admin
 */
public class Sucursal {
    private JSONObject jobject;
    private String sucursalId;
    private String nombre;
    private String regionId;
    private String zonaId;
    private String direccion;
    private Double latitud;
    private Double longitud;
    
    public Sucursal(JSONObject jobject){        
        sucursalId = "";
        nombre = "";
        regionId = "";
        zonaId = "";
        direccion = "";
        latitud = 0.0;
        longitud = 0.0;
        reload(jobject);
    }
    
    public void reload(JSONObject jobject){
        this.jobject = jobject;
        try {
            if (jobject.has("nombre")){
                nombre = jobject.getString("nombre");
            }
            if (jobject.has("sucursalId")){
                sucursalId = jobject.getString("sucursalId");
            }
            if (jobject.has("regionId")){
                regionId = jobject.getString("regionId");
            }
            if (jobject.has("zonaId")){
               zonaId = jobject.getString("zonaId");
            }
            if (jobject.has("direccion")){
               direccion = jobject.getString("direccion"); 
            }
            if (jobject.has("latitud")){
               latitud = jobject.getDouble("latitud");
            }
            if (jobject.has("longitud")){
               longitud = jobject.getDouble("longitud");
            }
        } catch (Exception ex) {
            System.err.println("Sucursal:" + ex.toString());
        }
    }
    
    public void dispose()
    {  
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
    
    public String getZonaId() {
        return zonaId;
    }

    public void setZonaId(String zonaId) {
        this.zonaId = zonaId;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    public void update(){
        jobject.put("sucursalId", sucursalId);
        jobject.put("nombre", nombre);
        jobject.put("regionId", regionId);
        jobject.put("zonaId", zonaId);
        jobject.put("direccion", direccion);
        jobject.put("latitud", latitud);
        jobject.put("longitud", longitud);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
