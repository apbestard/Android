package mx.logipax.shared.objects.card.zz;

import org.json.JSONArray;
import org.json.JSONObject;

public class Asignaciones {

    private JSONObject jobject;
    private String sucursalId;
    private String ejecutivoId;
    public Asignacion credito;
    public Asignacion creditos[];
    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("sucursalId")) {
                sucursalId = jobject.getString("sucursalId");
            }
            if (jobject.has("ejecutivoId")) {
                ejecutivoId = jobject.getString("ejecutivoId");
            }
            credito = new Asignacion(new JSONObject());
            if (jobject.has("credito")) {
                JSONObject jcredito = jobject.getJSONObject("credito");
                credito = new Asignacion(jcredito);
            }
            creditos = new Asignacion[0];
            if (jobject.has("creditos")) {
                JSONArray jcreditos = jobject.getJSONArray("creditos");
                creditos = new Asignacion[jcreditos.length()];
                for (int i = 0; i < creditos.length; i++) {
                    JSONObject jcredito = jcreditos.getJSONObject(i);
                    if (jcredito == null) {
                        creditos[i] = new Asignacion(new JSONObject());
                        continue;
                    }
                    creditos[i] = new Asignacion(jcredito);
                }
            }
        } catch (Exception ex) {
            System.err.println("Module:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < creditos.length; i++) {
            creditos[i].dispose();
        }
    }

    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getEjecutivoId() {
        return ejecutivoId;
    }

    public void setEjecutivoId(String ejecutivoId) {
        this.ejecutivoId = ejecutivoId;
    }

    public Asignacion getCredito() {
        return credito;
    }

    public void setCredito(Asignacion credito) {
        this.credito = credito;
    }

    public Asignacion[] getCreditos() {
        return creditos;
    }

    public void setCreditos(Asignacion[] creditos) {
        this.creditos = creditos;
    }
    
    public Asignaciones(JSONObject jobject) {
        reload(jobject);
    }


    public void update() {
        jobject.put("sucursalId", sucursalId);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("credito", credito.json());
    }
    public void update(JSONArray ocreditos) {
        jobject.put("sucursalId", sucursalId);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("credito", credito.json());
        jobject.put("creditos", ocreditos);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
