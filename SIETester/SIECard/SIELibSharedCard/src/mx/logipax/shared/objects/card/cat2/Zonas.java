package mx.logipax.shared.objects.card.cat2;

import org.json.JSONArray;
import org.json.JSONObject;

public class Zonas {

    private JSONObject jobject;
    public String region;
    public Zona zona;
    public Zona zonas[];

    
    public Zonas(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("region")) {
                region = jobject.getString("region");
            }
            zona = new Zona(new JSONObject());
            if (jobject.has("zona")) {
                JSONObject jzona = jobject.getJSONObject("zona");
                zona = new Zona(jzona);
            }
            zonas = new Zona[0];
            if (jobject.has("zonas")) {
                JSONArray jzonas = jobject.getJSONArray("zonas");
                zonas = new Zona[jzonas.length()];
                for (int i = 0; i < zonas.length; i++) {
                    JSONObject jzona = jzonas.getJSONObject(i);
                    if (jzona == null) {
                        zonas[i] = new Zona(new JSONObject());
                        continue;
                    }
                    zonas[i] = new Zona(jzona);
                }
            }
        } catch (Exception ex) {
            System.err.println("Zonas:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < zonas.length; i++) {
            zonas[i].dispose();
        }
    }

    public void update() {
        jobject.put("region", region);
        jobject.put("zona", zona.json());
    }
    public void update(JSONArray ozonas) {
        jobject.put("region", region);
        jobject.put("zona", zona.json());
        jobject.put("zonas", ozonas);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
