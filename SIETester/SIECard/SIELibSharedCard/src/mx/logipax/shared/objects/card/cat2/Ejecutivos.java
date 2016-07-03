package mx.logipax.shared.objects.card.cat2;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ejecutivos {

    private JSONObject jobject;
    public String region;
    public Ejecutivo ejecutivo;
    public Ejecutivo ejecutivos[];
    
    public Ejecutivos(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("region")) {
                region = jobject.getString("region");
            }
            ejecutivo = new Ejecutivo(new JSONObject());
            if (jobject.has("ejecutivo")) {
                JSONObject jejecutivo = jobject.getJSONObject("ejecutivo");
                ejecutivo = new Ejecutivo(jejecutivo);
            }
            ejecutivos = new Ejecutivo[0];
            if (jobject.has("ejecutivos")) {
                JSONArray jejecutivos = jobject.getJSONArray("ejecutivos");
                ejecutivos = new Ejecutivo[jejecutivos.length()];
                for (int i = 0; i < ejecutivos.length; i++) {
                    JSONObject jejecutivo = jejecutivos.getJSONObject(i);
                    if (jejecutivo == null) {
                        ejecutivos[i] = new Ejecutivo(new JSONObject());
                        continue;
                    }
                    ejecutivos[i] = new Ejecutivo(jejecutivo);
                }
            }
        } catch (Exception ex) {
            System.err.println("Ejecutivos:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < ejecutivos.length; i++) {
            ejecutivos[i].dispose();
        }
    }

    public void update() {
        jobject.put("region", region);
        jobject.put("ejecutivo", ejecutivo.json());
    }
    public void update(JSONArray oejecutivos) {
        jobject.put("region", region);
        jobject.put("ejecutivo", ejecutivo.json());
        jobject.put("ejecutivos", oejecutivos);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
