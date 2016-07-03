package mx.logipax.shared.objects.card.cat2;

import org.json.JSONArray;
import org.json.JSONObject;

public class Incentivos {

    private JSONObject jobject;
    public String region;
    public Incentivo incentivo;
    public Incentivo incentivos[];

    
    public Incentivos(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("region")) {
                region = jobject.getString("region");
            }
            incentivo = new Incentivo(new JSONObject());
            if (jobject.has("incentivo")) {
                JSONObject jincentivo = jobject.getJSONObject("incentivo");
                incentivo = new Incentivo(jincentivo);
            }
            incentivos = new Incentivo[0];
            if (jobject.has("incentivos")) {
                JSONArray jincentivos = jobject.getJSONArray("incentivos");
                incentivos = new Incentivo[jincentivos.length()];
                for (int i = 0; i < incentivos.length; i++) {
                    JSONObject jincentivo = jincentivos.getJSONObject(i);
                    if (jincentivo == null) {
                        incentivos[i] = new Incentivo(new JSONObject());
                        continue;
                    }
                    incentivos[i] = new Incentivo(jincentivo);
                }
            }
        } catch (Exception ex) {
            System.err.println("Incentivos:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < incentivos.length; i++) {
            incentivos[i].dispose();
        }
    }

    public void update() {
        jobject.put("region", region);
        jobject.put("incentivo", incentivo.json());
    }
    public void update(JSONArray oincentivos) {
        jobject.put("region", region);
        jobject.put("incentivo", incentivo.json());
        jobject.put("incentivos", oincentivos);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
