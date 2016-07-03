package mx.logipax.shared.objects.card.min;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONObject;

public class MinAcuerdo {

    private JSONObject jobject;
    private String minutaId;
    private int secuencia;
    private String acuerdoId;
    private String solicitaId;
    private String responsableId;
    private String objetivo;
    private Date fecha;
    private String texto;
    private String estatus;
    private String formatoFecha = "dd/MM/yyyy";
    private SimpleDateFormat df;
   
    public MinAcuerdo(JSONObject jobject) {
        df = new SimpleDateFormat(formatoFecha, new Locale("ES", "MX"));
        minutaId = "";
        secuencia = 1;
        acuerdoId = "";
        solicitaId = "";
        responsableId = "";
        objetivo = "";
        fecha = new java.util.Date();
        texto = "";
        estatus = "";
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("minutaId")) {
                minutaId = jobject.getString("minutaId");
            }
            if (jobject.has("secuencia")) {
                secuencia = jobject.getInt("secuencia");
            }
            if (jobject.has("acuerdoId")) {
                acuerdoId = jobject.getString("acuerdoId");
            }
            if (jobject.has("solicitaId")) {
                solicitaId = jobject.getString("solicitaId");
            }
            if (jobject.has("responsableId")) {
                responsableId = jobject.getString("responsableId");
            }
            if (jobject.has("objetivo")) {
                objetivo = jobject.getString("objetivo");
            }
            if (jobject.has("fecha")) {
                setFecha(jobject.getString("fecha"));
            }
            if (jobject.has("texto")) {
                setTexto(jobject.getString("texto"));
            }
            if (jobject.has("estatus")) {
                estatus = jobject.getString("estatus");
            }
        } catch (Exception ex) {
            System.err.println("GpoEjecutivo:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public String getMinutaId() {
        return minutaId;
    }

    public void setMinutaId(String minutaId) {
        this.minutaId = minutaId;
    }

    public int getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(int secuencia) {
        this.secuencia = secuencia;
    }

    public String getAcuerdoId() {
        return acuerdoId;
    }

    public void setAcuerdoId(String acuerdoId) {
        this.acuerdoId = acuerdoId;
    }
    
    public String getSolicitaId() {
        return solicitaId;
    }

    public void setSolicitaId(String solicitaId) {
        this.solicitaId = solicitaId;
    }
    
    public String getResponsableId() {
        return responsableId;
    }
    
    public void setResponsableId(String responsableId) {
        this.responsableId = responsableId;
    }
    
    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }


    public java.util.Date getFecha() {
        return fecha;
    }
    public String getFechaString() {
        return df.format(fecha);
    }

    public void setFecha(String fecha) {
        try {
            this.fecha = df.parse(fecha);
        } catch (Exception ex) {
        }
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public void update() {
        jobject.put("minutaId", minutaId);
        jobject.put("secuencia", secuencia);
        jobject.put("acuerdoId", acuerdoId);
        jobject.put("solicitaId", solicitaId);
        jobject.put("responsableId", responsableId);
        jobject.put("objetivo", objetivo);
        jobject.put("fecha", df.format(fecha));
        jobject.put("texto", texto);
        jobject.put("estatus", estatus);
    }

    public JSONObject json() {
        return jobject;
    }
}
