package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;
/**
 *
 * @author admin
 */
public class Puesto {
    private JSONObject jobject;
    private String puestoId;
    private String nombre;
    
    public Puesto(JSONObject jobject){
        puestoId = "";
        nombre = "";
        reload(jobject);
    }
    
    public void reload(JSONObject jobject){
        this.jobject = jobject;
        try {
            if (jobject.has("puestoId")){
                puestoId = jobject.getString("puestoId");
            }
            if (jobject.has("nombre")){
                nombre = jobject.getString("nombre"); 
                System.out.print(nombre);
            }

        } catch (Exception ex) {
            System.err.println("Puesto:" + ex.toString());
        }
    }
    
    public void dispose()
    {  
    }
    
    public String getPuestoId() {
        return puestoId;
    }

    public void setPuestoId(String puestoId) {
        this.puestoId = puestoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void update(){
        jobject.put("puestoId", puestoId);
        jobject.put("nombre", nombre);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
