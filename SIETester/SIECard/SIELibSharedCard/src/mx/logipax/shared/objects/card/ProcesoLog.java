package mx.logipax.shared.objects.card;


import java.text.ParseException;
import java.util.Date;

import org.json.JSONObject;

public class ProcesoLog {
    private JSONObject jobject;
    
    private String		procesoId;
    private Date		fecha;
    private String		mensaje;
    
    
    public ProcesoLog(JSONObject jobject) {
        procesoId   = "";
        fecha       = null;
        mensaje     = "";
        
        reload(jobject);
    }
    
    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if(jobject.has("procesoId")) {
                procesoId = jobject.getString("procesoId");
            }
            if(jobject.has("fecha")) {
                fecha = (jobject.getLong("fecha")>0)?new Date(jobject.getLong("fecha")):null;
            }
            if(jobject.has("mensaje")) {
                mensaje = jobject.getString("mensaje");
            }
            
        } catch (Exception e) {
            System.err.println("ProcesoLog:"+e.toString());
        }
    }
    
    public void dispose() {
    }
    
    
    public void update() {
        try {
            jobject.put("procesoId", procesoId);
            jobject.put("fecha", (fecha!=null)?fecha.getTime():0 );
            jobject.put("mensaje", mensaje);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    public JSONObject getJobject() {
        return jobject;
    }
    
    public void setJobject(JSONObject jobject) {
        this.jobject = jobject;
    }
    
    /**
     * @return the procesoId
     */
    public String getProcesoId() {
        return procesoId;
    }
    
    /**
     * @param procesoId the procesoId to set
     */
    public void setProcesoId(String procesoId) {
        this.procesoId = procesoId;
    }
    
    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }
    
    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public JSONObject json() {
        update();
        return jobject;
    }
    
}
