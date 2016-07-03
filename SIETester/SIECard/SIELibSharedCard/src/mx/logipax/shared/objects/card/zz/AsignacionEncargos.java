package mx.logipax.shared.objects.card.zz;

import org.json.JSONArray;
import org.json.JSONObject;

public class AsignacionEncargos {
    
    private JSONObject jobject;
    private String ejecutivoId;
    private String sucursalId;
    private String nombre;
    public AsignacionEncargo encargo;
    public AsignacionEncargo encargos[];
    
    public AsignacionEncargos(JSONObject jobject) {
        reload(jobject);
    }
    
    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("sucursalId")) {
                sucursalId = jobject.getString("sucursalId");
            }
            if (jobject.has("ejecutivoId")) {
                ejecutivoId = jobject.getString("ejecutivoId");
            }
            if (jobject.has("nombre")) {
                nombre = jobject.getString("nombre");
            }
            encargo = new AsignacionEncargo(new JSONObject());
            if (jobject.has("encargo")) {
                JSONObject jasignacionEncargo = jobject.getJSONObject("encargo");
                encargo = new AsignacionEncargo(jasignacionEncargo);
            }
            encargos = new AsignacionEncargo[0];
            if (jobject.has("encargos")) {
                JSONArray jasignacionEncargos = jobject.getJSONArray("encargos");
                encargos = new AsignacionEncargo[jasignacionEncargos.length()];
                for (int i = 0; i < encargos.length; i++) {
                    JSONObject jasignacionEncargo = jasignacionEncargos.getJSONObject(i);
                    if (jasignacionEncargo == null) {
                        encargos[i] = new AsignacionEncargo(new JSONObject());
                        continue;
                    }
                    encargos[i] = new AsignacionEncargo(jasignacionEncargo);
                }
            }
        } catch (Exception ex) {
            System.err.println("Module:" + ex.toString());
        }
    }
    
    public void dispose() {
        for (int i = 0; i < encargos.length; i++) {
            encargos[i].dispose();
        }
    }

    public String getEjecutivoId() {
        return ejecutivoId;
    }

    public void setEjecutivoId(String ejecutivoId) {
        this.ejecutivoId = ejecutivoId;
    }

    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }

    public AsignacionEncargo getEncargo() {
        return encargo;
    }

    public void setEncargo(AsignacionEncargo encargo) {
        this.encargo = encargo;
    }

    public AsignacionEncargo[] getEncargos() {
        return encargos;
    }

    public void setEncargos(AsignacionEncargo[] encargos) {
        this.encargos = encargos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void update() {
        jobject.put("sucursalId", sucursalId);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("nombre", nombre);
        jobject.put("asignacionEncargo", encargo.json());
    }
    
    public void update(JSONArray oencargos) {
        jobject.put("sucursalId", sucursalId);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("nombre", nombre);
        jobject.put("encargo", encargo.json());
        jobject.put("encargos", oencargos);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
