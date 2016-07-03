package mx.logipax.shared.objects.card.cat2;

import org.json.JSONArray;
import org.json.JSONObject;

public class Sucursales {

    private JSONObject jobject;
    public String region;
    public Sucursal sucursal;
    public Sucursal sucursales[];

    
    public Sucursales(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("region")) {
                region = jobject.getString("region");
            }
            sucursal = new Sucursal(new JSONObject());
            if (jobject.has("sucursal")) {
                JSONObject jsucursal = jobject.getJSONObject("sucursal");
                sucursal = new Sucursal(jsucursal);
            }
            sucursales = new Sucursal[0];
            if (jobject.has("sucursales")) {
                JSONArray jsucursales = jobject.getJSONArray("sucursales");
                sucursales = new Sucursal[jsucursales.length()];
                for (int i = 0; i < sucursales.length; i++) {
                    JSONObject jsucursal = jsucursales.getJSONObject(i);
                    if (jsucursal == null) {
                        sucursales[i] = new Sucursal(new JSONObject());
                        continue;
                    }
                    sucursales[i] = new Sucursal(jsucursal);
                }
            }
        } catch (Exception ex) {
            System.err.println("Sucursales:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < sucursales.length; i++) {
            sucursales[i].dispose();
        }
    }

    public void update() {
        jobject.put("region", region);
        jobject.put("sucursal", sucursal.json());
    }
    public void update(JSONArray osucursales) {
        jobject.put("region", region);
        jobject.put("sucursal", sucursal.json());
        jobject.put("sucursales", osucursales);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
