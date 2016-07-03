package mx.logipax.shared.objects.card.piz;

import org.json.JSONObject;

public class MCEjecutivoIn {

    private JSONObject jobject;
    public String ticket;
    public String function;
    public String filter1;
    public String filter2;
    public MCEjecutivo ejecutivo;
    public MCRuta ruta;
    public MCRutaCredito rutacredito;

    public MCEjecutivoIn(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("ticket")) {
                ticket = jobject.getString("ticket");
            }
            if (jobject.has("function")) {
                function = jobject.getString("function");
            }
            if (jobject.has("filter1")) {
                filter1 = jobject.getString("filter1");
            }
            if (jobject.has("filter2")) {
                filter2 = jobject.getString("filter2");
            }
            ejecutivo = new MCEjecutivo(new JSONObject());
            if (jobject.has("ejecutivo")) {
                JSONObject jejecutivo = jobject.getJSONObject("ejecutivo");
                ejecutivo = new MCEjecutivo(jejecutivo);
            }
            ruta = new MCRuta(new JSONObject());
            if (jobject.has("ruta")) {
                JSONObject jruta = jobject.getJSONObject("ruta");
                ruta = new MCRuta(jruta);
            }
            rutacredito = new MCRutaCredito(new JSONObject());
            if (jobject.has("rutacredito")) {
                JSONObject jrutacredito = jobject.getJSONObject("rutacredito");
                rutacredito = new MCRutaCredito(jrutacredito);
            }
        } catch (Exception ex) {
            System.err.println("Module:" + ex.toString());
        }
    }

    public void dispose() {
    }
    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFilter1() {
        return filter1;
    }

    public void setFilter1(String filter1) {
        this.filter1 = filter1;
    }

    public String getFilter2() {
        return filter2;
    }

    public void setFilter2(String filter2) {
        this.filter2 = filter2;
    }

    public MCEjecutivo getEjecutivo() {
        return ejecutivo;
    }

    public void setEjecutivo(MCEjecutivo ejecutivo) {
        this.ejecutivo = ejecutivo;
    }

    public MCRuta getRuta() {
        return ruta;
    }

    public void setRuta(MCRuta ruta) {
        this.ruta = ruta;
    }

    public MCRutaCredito getRutacredito() {
        return rutacredito;
    }

    public void setRutacredito(MCRutaCredito rutacredito) {
        this.rutacredito = rutacredito;
    }


    public void update() {
        jobject.put("ticket", ticket);
        jobject.put("function", function);
        jobject.put("filter1", filter1);
        jobject.put("filter2", filter2);
        jobject.put("ejecutivo", ejecutivo.json());
        jobject.put("ruta", ruta.json());
        jobject.put("rutarutacredito", rutacredito.json());
    }

    public JSONObject json() {
        return jobject;
    }
}
