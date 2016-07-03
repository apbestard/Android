package mx.logipax.shared.objects.card.cat2;

import org.json.JSONArray;
import org.json.JSONObject;

public class Puestos {

    private JSONObject jobject;
    public String region;
    public Puesto puesto;
    public Puesto puestos[];
    
    public Puestos(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("region")) {
                region = jobject.getString("region");
            }
            puesto = new Puesto(new JSONObject());
            if (jobject.has("puesto")) {
                JSONObject jpuesto = jobject.getJSONObject("puesto");
                puesto = new Puesto(jpuesto);
            }
            puestos = new Puesto[0];
            if (jobject.has("puestos")) {
                JSONArray jpuestos = jobject.getJSONArray("puestos");
                puestos = new Puesto[jpuestos.length()];
                for (int i = 0; i < puestos.length; i++) {
                    JSONObject jpuesto = jpuestos.getJSONObject(i);
                    if (jpuesto == null) {
                        puestos[i] = new Puesto(new JSONObject());
                        continue;
                    }
                    puestos[i] = new Puesto(jpuesto);
                }
            }
        } catch (Exception ex) {
            System.err.println("Puestos:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public void update() {
        jobject.put("region", region);
        jobject.put("puesto", puesto.json());
    }
    public void update(JSONArray opuestos) {
        jobject.put("region", region);
        jobject.put("puesto", puesto.json());
        jobject.put("puestos", opuestos);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
