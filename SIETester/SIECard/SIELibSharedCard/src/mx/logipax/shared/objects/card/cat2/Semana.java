package mx.logipax.shared.objects.card.cat2;
 
import org.json.JSONObject;
/**
 *
 * @author admin
 */
public class Semana {
    private JSONObject jobject;
    private String semanaId;
    private String fechaInicial;
    private String fechaFinal;
    
    public Semana(JSONObject jobject){
        semanaId = "";
        fechaInicial = "";
        fechaFinal = "";
        reload(jobject);
    }
    
    public void reload(JSONObject jobject){
        this.jobject = jobject;
        try {
            if (jobject.has("semanaId")){
                semanaId = jobject.getString("semanaId");
            }
            if (jobject.has("fechaInicial")){
               fechaInicial = jobject.getString("fechaInicial");
            }
            if (jobject.has("fechaFinal")){
               fechaFinal = jobject.getString("fechaFinal");
            }
        } catch (Exception ex) {
            System.err.println("Semana:" + ex.toString());
        }
    }
    
    public void dispose()
    {  
    }
    
    public String getSemanaId() {
        return semanaId;
    }

    public void setSemanaId(String semanaId) {
        this.semanaId = semanaId;
    }

    public String getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(String fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinalal) {
        this.fechaFinal = fechaFinalal;
    }
    
    public void update(){
        jobject.put("semanaId", semanaId);
        jobject.put("fechaInicial", fechaInicial);
        jobject.put("fechaFinal", fechaFinal);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
