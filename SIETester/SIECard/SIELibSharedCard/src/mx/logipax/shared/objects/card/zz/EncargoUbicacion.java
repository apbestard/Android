package mx.logipax.shared.objects.card.zz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

public class EncargoUbicacion {
    private JSONObject jobject;
    private String encargoId;
    private String terminalId;
    private Date fecha;
    private Double latitud;
    private Double longitud;
    private String direccion;
    private String formatoFecha = "yyyyMMddHHmmss";
    private SimpleDateFormat df;
    
    public EncargoUbicacion(JSONObject jobject) {
        df = new SimpleDateFormat(formatoFecha, new Locale("ES","MX"));
        encargoId = "";
        terminalId = "";
        fecha = new Date();
        latitud = 0.0;
        longitud = 0.0;
        direccion = "";
        reload(jobject);
    }
    
    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if(jobject.has("encargoId")) {
                encargoId = jobject.getString("encargoId");
            }
            if(jobject.has("terminalId")) {
                terminalId = jobject.getString("terminalId");
            }
            if(jobject.has("fecha")) {
                    fecha = (jobject.getString("fecha")!=null && !"".equals(jobject.getString("fecha")))?df.parse(jobject.getString("fecha")):null;
            }
            if(jobject.has("latitud")) {
                latitud = jobject.getDouble("latitud");
            }
            if(jobject.has("longitud")) {
                longitud = jobject.getDouble("longitud");
            }
            if(jobject.has("direccion")) {
                direccion = jobject.getString("direccion");
            }
        } catch (Exception e) {
            System.err.println("EncargoUbicacion: "+e.toString());
        }
    }
    
    public void dispose() {
    }
    
    public void update() {
        try {
            jobject.put("encargoId", encargoId);
            jobject.put("terminalId", terminalId);
//            jobject.put("fecha", fecha);
            jobject.put("fecha", (fecha!=null)?df.format(fecha):"" );
            jobject.put("latitud", latitud);
            jobject.put("longitud", longitud);
            jobject.put("direccion", direccion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEncargoId() {
        return encargoId;
    }

    public void setEncargoId(String encargoId) {
        this.encargoId = encargoId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer Double) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public JSONObject json() {
    	update();
        return jobject;
    }
}
