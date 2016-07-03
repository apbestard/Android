package mx.logipax.shared.objects.card.cat2;

import org.json.JSONArray;
import org.json.JSONObject;

public class Encargos {

    private JSONObject jobject;
    public String sucursalId;
    public Encargo encargo;
    public Encargo encargos[];

    
    public Encargos(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            sucursalId = "";
            if (jobject.has("sucursalId")) {
                sucursalId = jobject.getString("sucursalId");
            }
            
            encargo = new Encargo(new JSONObject());
            if (jobject.has("encargo")) {
                JSONObject jencargo = jobject.getJSONObject("encargo");
                encargo = new Encargo(jencargo);
            }
            
            encargos = new Encargo[0];
            if (jobject.has("encargos")) {
                JSONArray jencargos = jobject.getJSONArray("encargos");
                encargos = new Encargo[jencargos.length()];
                for (int i = 0; i < encargos.length; i++) {
                    JSONObject jencargo = jencargos.getJSONObject(i);
                    if (jencargo == null) {
                        encargos[i] = new Encargo(new JSONObject());
                        continue;
                    }
                    encargos[i] = new Encargo(jencargo);
                }
            }
        } catch (Exception ex) {
            System.err.println("Encargos:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < encargos.length; i++) {
            encargos[i].dispose();
        }
    }

    public void update() {
        jobject.put("sucursalId", sucursalId);
        jobject.put("encargo", encargo.json());
    }
    
    public void update(JSONArray oencargos) {
        jobject.put("encargo", encargo.json());
        jobject.put("sucursalId", sucursalId);
        jobject.put("encargo", encargo.json());
        jobject.put("encargos", oencargos);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
