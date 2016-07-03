package mx.logipax.shared.objects.card.cat2;

import org.json.JSONArray;
import org.json.JSONObject;

public class Semanas {

    private JSONObject jobject;
    public String region;
    public Semana semana;
    public Semana semanas[];

    
    public Semanas(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("region")) {
                region = jobject.getString("region");
            }
            semana = new Semana(new JSONObject());
            if (jobject.has("semana")) {
                JSONObject jsemana = jobject.getJSONObject("semana");
                semana = new Semana(jsemana);
            }
            semanas = new Semana[0];
            if (jobject.has("semanas")) {
                JSONArray jsemanas = jobject.getJSONArray("semanas");
                semanas = new Semana[jsemanas.length()];
                for (int i = 0; i < semanas.length; i++) {
                    JSONObject jsemana = jsemanas.getJSONObject(i);
                    if (jsemana == null) {
                        semanas[i] = new Semana(new JSONObject());
                        continue;
                    }
                    semanas[i] = new Semana(jsemana);
                }
            }
        } catch (Exception ex) {
            System.err.println("Semanas:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < semanas.length; i++) {
            semanas[i].dispose();
        }
    }

    public void update() {
        jobject.put("region", region);
        jobject.put("semana", semana.json());
    }
    public void update(JSONArray osemanas) {
        jobject.put("region", region);
        jobject.put("semana", semana.json());
        jobject.put("semanas", osemanas);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
