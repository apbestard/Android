package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;
/**
 *
 * @author admin
 */
public class Incentivo {
    private JSONObject jobject;
    private String incentivoId;
    private String descripcion;
    private Double monto;
    private Double excedente;
    private String fechaCreacion;
    private String usuarioCreoId;
    private String fehaCambio;
    private String usuarioActualisoId;
    
    public Incentivo(JSONObject jobject){
        incentivoId = "";
        descripcion = "";
        monto = 0.0;
        excedente = 0.0;
        fechaCreacion = "";
        usuarioCreoId = "";
        fehaCambio = "";
        usuarioActualisoId = "";
        reload(jobject);
    }
    
    public void reload(JSONObject jobject){
        this.jobject = jobject;
        try {
            if (jobject.has("incentivoId")){
                incentivoId = jobject.getString("incentivoId");
            }
            if (jobject.has("descripcion")){
                descripcion = jobject.getString("descripcion"); 
            }
            if (jobject.has("monto")){
                monto = jobject.getDouble("monto");
            }
            if (jobject.has("excedente")){
                excedente = jobject.getDouble("excedente");
            }
            if (jobject.has("fechaCreacion")){
                fechaCreacion = jobject.getString("fechaCreacion");
            }
            if (jobject.has("usuarioCreoId")){
                usuarioCreoId = jobject.getString("usuarioCreoId");
            }
            if (jobject.has("fehaCambio")){
                fehaCambio = jobject.getString("fehaCambio");
            }
            if (jobject.has("usuarioActualisoId")){
                usuarioActualisoId = jobject.getString("usuarioActualisoId");
            }
        } catch (Exception ex) {
            System.err.println("Incentivo:" + ex.toString());
        }
    }
    
    public void dispose()
    {  
    }
    
    public String getIncentivoId() {
        return incentivoId;
    }

    public void setIncentivoId(String incentivoId) {
        this.incentivoId = incentivoId;
    }

    public String getdescripcion() {
        return descripcion;
    }

    public void setdescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getExcedente() {
        return excedente;
    }

    public void setExcedente(double excedente) {
        this.excedente = excedente;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreoId() {
        return usuarioCreoId;
    }

    public void setUsuarioCreoId(String usuarioCreoId) {
        this.usuarioCreoId = usuarioCreoId;
    }

    public String getFehaCambio() {
        return fehaCambio;
    }

    public void setFehaCambio(String fehaCambio) {
        this.fehaCambio = fehaCambio;
    }

    public String getUsuarioActualisoId() {
        return usuarioActualisoId;
    }

    public void setUsuarioActualisoId(String usuarioActualisoId) {
        this.usuarioActualisoId = usuarioActualisoId;
    }
    
    public void update(){
        jobject.put("incentivoId", incentivoId);
        jobject.put("descripcion", descripcion);
        jobject.put("monto", monto);
        jobject.put("excedente", excedente);
        jobject.put("fechaCreacion", fechaCreacion);
        jobject.put("usuarioCreoId", usuarioCreoId);
        jobject.put("fehaCambio", fehaCambio);
        jobject.put("usuarioActualisoId", usuarioActualisoId);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
