package mx.logipax.shared.objects.card.piz;

import mx.logipax.shared.objects.cat.Level;
import org.json.JSONArray;
import org.json.JSONObject;

public class MCEjecutivos {

    private JSONObject jobject;
    public String sucursalId;
    public String ejecutivoId;
    public MCEjecutivo ejecutivo;
    public MCEjecutivo ejecutivos[];
    public Level level0;

    
    public MCEjecutivos(JSONObject jobject) {
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
            ejecutivo = new MCEjecutivo(new JSONObject());
            if (jobject.has("ejecutivo")) {
                JSONObject jejecutivo = jobject.getJSONObject("ejecutivo");
                ejecutivo = new MCEjecutivo(jejecutivo);
            }
            ejecutivos = new MCEjecutivo[0];
            if (jobject.has("ejecutivos")) {
                JSONArray jejecutivos = jobject.getJSONArray("ejecutivos");
                ejecutivos = new MCEjecutivo[jejecutivos.length()];
                for (int i = 0; i < ejecutivos.length; i++) {
                    JSONObject jejecutivo = jejecutivos.getJSONObject(i);
                    if (jejecutivo == null) {
                        ejecutivos[i] = new MCEjecutivo(new JSONObject());
                        continue;
                    }
                    ejecutivos[i] = new MCEjecutivo(jejecutivo);
                }
            }
            level0 = new Level(new JSONObject());
            if (jobject.has("level0")) {
                JSONObject jlevel0 = jobject.getJSONObject("level0");
                level0 = new Level(jlevel0);
            }
        } catch (Exception ex) {
            System.err.println("Module:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < ejecutivos.length; i++) {
            ejecutivos[i].dispose();
        }
    }
    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }

    public String getEjecutivoId() {
        return ejecutivoId;
    }

    public void setEjecutivoId(String ejecutivoId) {
        this.ejecutivoId = ejecutivoId;
    }

    public MCEjecutivo getEjecutivo() {
        return ejecutivo;
    }

    public void setEjecutivo(MCEjecutivo ejecutivo) {
        this.ejecutivo = ejecutivo;
    }

    public MCEjecutivo[] getEjecutivos() {
        return ejecutivos;
    }

    public void setEjecutivos(MCEjecutivo[] ejecutivos) {
        this.ejecutivos = ejecutivos;
    }

    public Level getLevel0() {
        return level0;
    }

    public void setLevel0(Level level0) {
        this.level0 = level0;
    }


    public void update() {
        jobject.put("sucursalId", sucursalId);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("ejecutivo", ejecutivo.json());
        jobject.put("level0", level0.json());
    }
    public void update(JSONArray oejecutivos) {
        jobject.put("sucursalId", sucursalId);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("ejecutivo", ejecutivo.json());
        jobject.put("ejecutivos", oejecutivos);
        jobject.put("level0", level0.json());
    }
    
    public JSONObject json() {
        return jobject;
    }
}
