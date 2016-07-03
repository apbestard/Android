package mx.logipax.shared.objects.card.cat2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

public class Ejecutivo {
    private JSONObject jobject;
    private String ejecutivoId;
    private String nombre;
    private String puestoId;
    private String sucursalId;
    private Double sueldo;
    private Date fechaIngreso;    

    private String formatoFecha = "dd/MM/yyyy";
    private SimpleDateFormat df;
    
    public Ejecutivo(JSONObject jobject){
        df = new SimpleDateFormat(formatoFecha, new Locale("ES","MX"));
        ejecutivoId = "";
        nombre = "";
                
        puestoId = "";
        sucursalId = "";
        sueldo = 0.0;
        fechaIngreso = new Date();
        reload(jobject);
    }
    
    public void reload(JSONObject jobject){
        this.jobject = jobject;
        try {
            if (jobject.has("ejecutivoId")){
                ejecutivoId = jobject.getString("ejecutivoId");
            }
            if (jobject.has("nombre")){
                nombre = jobject.getString("nombre");
            }
            if (jobject.has("puestoId")){
                puestoId = jobject.getString("puestoId");
            }
            if (jobject.has("sucursalId")){
                sucursalId = jobject.getString("sucursalId");
            }
            if (jobject.has("sueldo")){
                sueldo = jobject.getDouble("sueldo");
            }
            if (jobject.has("fechaIngreso")){
                fechaIngreso = df.parse(jobject.getString("fechaIngreso"));
            }
        } catch (Exception ex) {
            System.err.println("Ejecutivo:" + ex.toString());
        }
    }
    
    public void dispose()
    {
    }
    
    public String getEjecutivoId() {
        return ejecutivoId;
    }
    
    public void setEjecutivoId(String ejecutivoId) {
        this.ejecutivoId = ejecutivoId;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getPuestoId() {
        return puestoId;
    }
    
    public void setPuestoId(String puestoId) {
        this.puestoId = puestoId;
    }
    
    public String getSucursalId() {
        return sucursalId;
    }
    
    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public String getFechaIngresoString() {
            return df.format(fechaIngreso);
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    
    public void setFechaIngreso(String fecha) {
        try {
                this.fechaIngreso = df.parse(fecha);
        } catch (ParseException e) {
                e.printStackTrace();
        }
    }
    
    public void update() {
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("nombre", nombre);
        jobject.put("puestoId", puestoId);
        jobject.put("sucursalId", sucursalId);
        jobject.put("sueldo", sueldo);
        jobject.put("fechaIngreso", getFechaIngresoString());
    }
    
    public JSONObject json() {
        return jobject;
    }
}
