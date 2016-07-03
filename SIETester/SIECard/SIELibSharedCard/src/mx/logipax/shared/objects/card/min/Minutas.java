package mx.logipax.shared.objects.card.min;

import org.json.JSONArray;
import org.json.JSONObject;

public class Minutas {

    private JSONObject jobject;
    public Minuta minuta;
    public Minuta minutas[];
    
    public Minutas(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            minuta = new Minuta(new JSONObject());
            if (jobject.has("minuta")) {
                JSONObject jminuta = jobject.getJSONObject("minuta");
                minuta = new Minuta(jminuta);
            }
            
            minutas = new Minuta[0];
            if (jobject.has("minutas")) {
                JSONArray jminutas = jobject.getJSONArray("minutas");
                minutas = new Minuta[jminutas.length()];
                for (int i = 0; i < minutas.length; i++) {
                    JSONObject jminuta = jminutas.getJSONObject(i);
                    if (jminuta == null) {
                        minutas[i] = new Minuta(new JSONObject());
                        continue;
                    }
                    minutas[i] = new Minuta(jminuta);
                }
            }
        } catch (Exception ex) {
            System.err.println("Minutas:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < minutas.length; i++) {
            minutas[i].dispose();
        }
    }

    public void update() {
        jobject.put("minuta", minuta.json());
    }
    
    public void update(JSONArray ominutas) {
        jobject.put("minuta", minuta.json());
        jobject.put("minutas", ominutas);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
