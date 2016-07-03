package mx.logipax.shared.objects.card.min;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

public class Minuta {

    private JSONObject jobject;
    private String minutaId;
    private String gpotrabajoId;
    private String nombre;
    private Date fecha;
    private String texto;
    private String estatus;
    private MinParticipante asistentes[];
    private MinParticipanteEx externos[];
    private MinAgenda agendas[];
    private MinAcuerdo acuerdos[];
    private String formatoFecha = "dd/MM/yyyy";
    private SimpleDateFormat df;

    public Minuta(JSONObject jobject) {
        df = new SimpleDateFormat(formatoFecha, new Locale("ES", "MX"));
        minutaId = "";
        gpotrabajoId = "";
        nombre = "";
        fecha = new java.util.Date();
        texto = "";
        estatus = "";
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("minutaId")) {
                setMinutaId(jobject.getString("minutaId"));
            }
            if (jobject.has("gpotrabajoId")) {
                setGpoTrabajoId(jobject.getString("gpotrabajoId"));
            }
            if (jobject.has("nombre")) {
                setNombre(jobject.getString("nombre"));
            }
            if (jobject.has("fecha")) {
                setFecha(jobject.getString("fecha"));
            }
            if (jobject.has("texto")) {
                setTexto(jobject.getString("texto"));
            }
            if (jobject.has("estatus")) {
                setEstatus(jobject.getString("estatus"));
            }

            asistentes = new MinParticipante[0];
            if (jobject.has("asistentes")) {
                JSONArray jasistentes = jobject.getJSONArray("asistentes");
                asistentes = new MinParticipante[jasistentes.length()];
                for (int i = 0; i < asistentes.length; i++) {
                    JSONObject jminuta = jasistentes.getJSONObject(i);
                    if (jminuta == null) {
                        asistentes[i] = new MinParticipante(new JSONObject());
                        continue;
                    }
                    asistentes[i] = new MinParticipante(jminuta);
                }
            }
            externos = new MinParticipanteEx[0];
            if (jobject.has("externos")) {
                JSONArray jexternos = jobject.getJSONArray("externos");
                externos = new MinParticipanteEx[jexternos.length()];
                for (int i = 0; i < externos.length; i++) {
                    JSONObject jminuta = jexternos.getJSONObject(i);
                    if (jminuta == null) {
                        externos[i] = new MinParticipanteEx(new JSONObject());
                        continue;
                    }
                    externos[i] = new MinParticipanteEx(jminuta);
                }
            }
            agendas = new MinAgenda[0];
            if (jobject.has("agendas")) {
                JSONArray jagendas = jobject.getJSONArray("agendas");
                agendas = new MinAgenda[jagendas.length()];
                for (int i = 0; i < agendas.length; i++) {
                    JSONObject jminuta = jagendas.getJSONObject(i);
                    if (jminuta == null) {
                        agendas[i] = new MinAgenda(new JSONObject());
                        continue;
                    }
                    agendas[i] = new MinAgenda(jminuta);
                }
            }
            acuerdos = new MinAcuerdo[0];
            if (jobject.has("acuerdos")) {
                JSONArray jacuerdos = jobject.getJSONArray("acuerdos");
                acuerdos = new MinAcuerdo[jacuerdos.length()];
                for (int i = 0; i < acuerdos.length; i++) {
                    JSONObject jminuta = jacuerdos.getJSONObject(i);
                    if (jminuta == null) {
                        acuerdos[i] = new MinAcuerdo(new JSONObject());
                        continue;
                    }
                    acuerdos[i] = new MinAcuerdo(jminuta);
                }
            }
        } catch (Exception ex) {
            System.err.println("Minuta:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public String getMinutaId() {
        return minutaId;
    }

    public void setMinutaId(String minutaId) {
        this.minutaId = minutaId;
    }

    public String getGpoTrabajoId() {
        return gpotrabajoId;
    }

    public void setGpoTrabajoId(String gpotrabajoId) {
        this.gpotrabajoId = gpotrabajoId;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public java.util.Date getFecha() {
        return fecha;
    }
    public String getFechaString() {
        return df.format(fecha);
    }

    public void setFecha(String fecha) {
        try {
            this.fecha = df.parse(fecha);
        } catch (Exception ex) {
        }
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public MinParticipante[] getParticipantes() {
        return asistentes;
    }

    public void setParticipantes(MinParticipante[] asistentes) {
        this.asistentes = asistentes;
    }
    
    public MinParticipanteEx[] getParticipanteExs() {
        return externos;
    }

    public void setParticipanteExs(MinParticipanteEx[] externos) {
        this.externos = externos;
    }

    public MinAgenda[] getAgendas() {
        return agendas;
    }

    public void setAgendas(MinAgenda[] agendas) {
        this.agendas = agendas;
    }
    
    public MinAcuerdo[] getAcuerdos() {
        return acuerdos;
    }

    public void setAcuerdos(MinAcuerdo[] acuerdos) {
        this.acuerdos = acuerdos;
    }
    
    public void update() {
        jobject.put("minutaId", minutaId);
        jobject.put("gpotrabajoId", gpotrabajoId);
        jobject.put("nombre", nombre);
        jobject.put("fecha", df.format(fecha));
        jobject.put("texto", texto);
        jobject.put("estatus", estatus);
  
        JSONArray oasistentes = new JSONArray();
        if (asistentes != null) {
            for (int i = 0; i < asistentes.length; i++) {
                MinParticipante asistente = new MinParticipante(new JSONObject());
                asistente.setMinutaId(asistentes[i].getMinutaId());
                asistente.setEjecutivoId(asistentes[i].getEjecutivoId());
                asistente.setEstatus(asistentes[i].getEstatus());
                asistente.update();
                oasistentes.put(asistente.json());
            }
        }
        jobject.put("asistentes", oasistentes);
        JSONArray oexternos = new JSONArray();
        if (externos != null) {
            for (int i = 0; i < externos.length; i++) {
                MinParticipanteEx externo = new MinParticipanteEx(new JSONObject());
                externo.setMinutaId(externos[i].getMinutaId());
                externo.setNombre(externos[i].getNombre());
                externo.setEMail(externos[i].getEMail());
                externo.setEstatus(externos[i].getEstatus());
                externo.update();
                oexternos.put(externo.json());
            }
        }
        jobject.put("externos", oexternos);
        JSONArray oagendas = new JSONArray();
        if (agendas != null) {
            for (int i = 0; i < agendas.length; i++) {
                MinAgenda agenda = new MinAgenda(new JSONObject());
                agenda.setMinutaId(agendas[i].getMinutaId());
                //agenda.setEjecutivoId(agendas[i].getEjecutivoId());
                agenda.setEstatus(agendas[i].getEstatus());
                agenda.update();
                oagendas.put(agenda.json());
            }
        }
        jobject.put("agendas", oagendas);
        JSONArray oacuerdos = new JSONArray();
        if (acuerdos != null) {
            for (int i = 0; i < acuerdos.length; i++) {
                MinAcuerdo acuerdo = new MinAcuerdo(new JSONObject());
                acuerdo.setMinutaId(acuerdos[i].getMinutaId());
                //acuerdo.setEjecutivoId(acuerdos[i].getEjecutivoId());
                acuerdo.setEstatus(acuerdos[i].getEstatus());
                acuerdo.update();
                oacuerdos.put(acuerdo.json());
            }
        }
        jobject.put("acuerdos", oacuerdos);
    }

    public JSONObject json() {
        return jobject;
    }

}
