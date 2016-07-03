package mx.logipax.shared.objects.card.cat2;

import org.json.JSONArray;
import org.json.JSONObject;

public class Regiones {

    private JSONObject jobject;
    public Region region;
    public Region regiones[];

    public Regiones(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            region = new Region(new JSONObject());
            if (jobject.has("region")) {
                JSONObject jregion = jobject.getJSONObject("region");
                region = new Region(jregion);
            }
            regiones = new Region[0];
            if (jobject.has("regiones")) {
                JSONArray jregiones = jobject.getJSONArray("regiones");
                regiones = new Region[jregiones.length()];
                for (int i = 0; i < regiones.length; i++) {
                    JSONObject jregion = jregiones.getJSONObject(i);
                    if (jregion == null) {
                        regiones[i] = new Region(new JSONObject());
                        continue;
                    }
                    regiones[i] = new Region(jregion);
                }
            }
        } catch (Exception ex) {
            System.err.println("Regiones:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < regiones.length; i++) {
            regiones[i].dispose();
        }
    }

    public void update() {
        jobject.put("region", region.json());
    }
    public void update(JSONArray oregiones) {
        jobject.put("region", region.json());
        jobject.put("regiones", oregiones);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
