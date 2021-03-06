package mx.logipax.shared.objects.card.piz;

import org.json.JSONArray;
import org.json.JSONObject;

public class MCRuta {
    private JSONObject jobject;
    private String creditoId;
    private String ejecutivoId;
    private String semanaId;
    private String sucursalId;
    private String segmento;
    private double capVencido;
    private double intVencido;
    private double intMoratorio;
    private String direccion;
    private double latitud;
    private double longitud;
    public MCRutaCredito rutacredito;
    public MCRutaCredito rutacreditos[];

    public MCRuta(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("creditoId")) {
                creditoId = jobject.getString("creditoId");
            }
            if (jobject.has("ejecutivoId")) {
                ejecutivoId = jobject.getString("ejecutivoId");
            }
            if (jobject.has("semanaId")) {
                semanaId = jobject.getString("semanaId");
            }
            if (jobject.has("sucursalId")) {
                sucursalId = jobject.getString("sucursalId");
            }
            if (jobject.has("segmento")) {
                segmento = jobject.getString("segmento");
            }
            if (jobject.has("capVencido")) {
                capVencido = jobject.getDouble("capVencido");
            }
            if (jobject.has("intVencido")) {
                intVencido = jobject.getDouble("intVencido");
            }
            if (jobject.has("intMoratorio")) {
                intMoratorio = jobject.getDouble("intMoratorio");
            }
            if (jobject.has("direccion")) {
                direccion = jobject.getString("direccion");
            }
            if (jobject.has("latitud")) {
                latitud = jobject.getDouble("latitud");
            }
            if (jobject.has("longitud")) {
                longitud = jobject.getDouble("longitud");
            }
            rutacredito = new MCRutaCredito(new JSONObject());
            if (jobject.has("rutacredito")) {
                JSONObject jrutacredito = jobject.getJSONObject("rutacredito");
                rutacredito = new MCRutaCredito(jrutacredito);
            }
            rutacreditos = new MCRutaCredito[0];
            if (jobject.has("rutacreditos")) {
                JSONArray jrutacreditos = jobject.getJSONArray("rutacreditos");
                rutacreditos = new MCRutaCredito[jrutacreditos.length()];
                for (int i = 0; i < rutacreditos.length; i++) {
                    JSONObject jrutacredito = jrutacreditos.getJSONObject(i);
                    if (jrutacredito == null) {
                        rutacreditos[i] = new MCRutaCredito(new JSONObject());
                        continue;
                    }
                    rutacreditos[i] = new MCRutaCredito(jrutacredito);
                }
            }
         } catch (Exception ex) {
            System.err.println("Collector:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public String getCreditoId() {
        return creditoId;
    }

    public void setCreditoId(String creditoId) {
        this.creditoId = creditoId;
    }

    public String getEjecutivoId() {
        return ejecutivoId;
    }

    public void setEjecutivoId(String ejecutivoId) {
        this.ejecutivoId = ejecutivoId;
    }

    public String getSemanaId() {
        return semanaId;
    }

    public void setSemanaId(String semanaId) {
        this.semanaId = semanaId;
    }

    public String getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(String sucursalId) {
        this.sucursalId = sucursalId;
    }
    

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public double getCapVencido() {
        return capVencido;
    }

    public void setCapVencido(double capVencido) {
        this.capVencido = capVencido;
    }

    public double getIntVencido() {
        return intVencido;
    }

    public void setIntVencido(double intVencido) {
        this.intVencido = intVencido;
    }

    public double getIntMoratorio() {
        return intMoratorio;
    }

    public void setIntMoratorio(double intMoratorio) {
        this.intMoratorio = intMoratorio;
    }
    
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    public void update(JSONArray orutacreditos) {
        jobject.put("semanaId", semanaId);
        jobject.put("creditoId", creditoId);
        jobject.put("ejecutivoId", ejecutivoId);
        jobject.put("sucursalId", sucursalId);
        jobject.put("segmento", segmento);
        jobject.put("capVencido", capVencido);
        jobject.put("intVencido", intVencido);
        jobject.put("intMoratorio", intMoratorio);
        jobject.put("direccion", direccion);
        jobject.put("latitud", latitud);
        jobject.put("longitud", longitud);
        jobject.put("rutacredito", rutacredito.json());
        jobject.put("rutacreditos", orutacreditos);
    }

    public JSONObject json() {
        return jobject;
    }
}
