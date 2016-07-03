package mx.logipax.shared.objects.card.cat2;

import org.json.JSONObject;

public class Notificacion {
    private JSONObject jobject;
    private String notificacionId;
    private String usuarioNotifica;
    private String tipoNotificacion;
    private String titulo;
    private String contenido;
    private String rutaId;
    private String semanaId;
    private String encargoId;
    private Integer secuencia;
    private String estatusNotifica;
    
    public Notificacion(JSONObject jobject){
        notificacionId = "";
        usuarioNotifica = "";
        tipoNotificacion = "";
        titulo = "";
        contenido = "";
        rutaId = "";
        semanaId = "";
        encargoId = "";
        secuencia = 0;
        estatusNotifica = "";
        reload(jobject);
    }

    public enum StatusNotificacion {
        NUEVA		("1"),
        RECIBIDA	("2"),
        MOSTRADA	("3");
        
        public String value;
        
        StatusNotificacion(String value){
            this.value = value;
        }
    }
    
    public enum TipoNotificacion {
        MENSAJE			("1"),
        NUEVA_VISITA		("2"),
        CANCELA_VISITA		("3"),
        FRECUENCIA_UBICACION	("4");
        
        public String value;
        
        TipoNotificacion(String value){
            this.value = value;
        }
    }
    
    public void reload(JSONObject jobject){
        this.jobject = jobject;
        try {
            if (jobject.has("notificacionId")){
                setNotificacionId(jobject.getString("notificacionId"));
            }
            if (jobject.has("usuarioNotifica")){
                setUsuarioNotifica(jobject.getString("usuarioNotifica"));
            }
            if (jobject.has("tipoNotificacion")){
                setTipoNotificacion(jobject.getString("tipoNotificacion"));
            }
            if (jobject.has("titulo")){
                setTitulo(jobject.getString("titulo"));
            }
            if (jobject.has("contenido")){
                setContenido(jobject.getString("contenido"));
            }
            if (jobject.has("rutaId")){
                setRutaId(jobject.getString("rutaId"));
            }
            if (jobject.has("semanaId")){
                setSemanaId(jobject.getString("semanaId"));
            }
            if (jobject.has("encargoId")){
                setEncargoId(jobject.getString("encargoId"));
            }
            if (jobject.has("secuencia")){
                setSecuencia(jobject.getInt("secuencia"));
            }
            if (jobject.has("estatusNotifica")){
                setEstatusNotifica(jobject.getString("estatusNotifica"));
            }
        } catch (Exception ex) {
            System.err.println("Notificacion:" + ex.toString());
        }
    }

    public void dispose()
    {  
    }
    
    public void update() {
    	try {    	
            jobject.put("notificacionId", notificacionId);
            jobject.put("usuarioNotifica", usuarioNotifica);
            jobject.put("tipoNotificacion", tipoNotificacion);
            jobject.put("titulo", titulo);
            jobject.put("contenido", contenido);
            jobject.put("rutaId", rutaId);
            jobject.put("semanaId", semanaId);
            jobject.put("encargoId", encargoId);
            jobject.put("secuencia", secuencia);
            jobject.put("estatusNotifica", estatusNotifica);
            
        }catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public String getNotificacionId() {
        return notificacionId;
    }

    public void setNotificacionId(String notificacionId) {
        this.notificacionId = notificacionId;
    }
    
    public String getUsuarioNotifica() {
        return usuarioNotifica;
    }

    public void setUsuarioNotifica(String usuarioNotifica) {
        this.usuarioNotifica = usuarioNotifica;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getRutaId() {
        return rutaId;
    }

    public void setRutaId(String rutaId) {
        this.rutaId = rutaId;
    }

    public String getSemanaId() {
        return semanaId;
    }

    public void setSemanaId(String semanaId) {
        this.semanaId = semanaId;
    }

    public String getEncargoId() {
        return encargoId;
    }

    public void setEncargoId(String encargoId) {
        this.encargoId = encargoId;
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }

    public String getEstatusNotifica() {
        return estatusNotifica;
    }

    public void setEstatusNotifica(String estatusNotifica) {
        this.estatusNotifica = estatusNotifica;
    }
    
    public JSONObject json() {
		update();
        return jobject;
    }
}
