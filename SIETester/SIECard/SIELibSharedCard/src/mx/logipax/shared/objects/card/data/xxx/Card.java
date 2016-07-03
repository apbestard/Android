package mx.logipax.shared.objects.card.data.xxx;

import mx.logipax.shared.objects.card.data.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

public class Card {

    private JSONObject jobject;
    private String cardId;
    private String cardNombre;
    private String responsableId;
    private String responsableNombre;
    private String visorId;
    private String visorNombre;
    private Date fecha;
    private String email;
    private String estatus;
    private CardProfile profile;
    private CardPersona personal[];
    private CardEquipo equipos[];
    private String formatoFecha = "dd/MM/yyyy";
    private SimpleDateFormat df;

    public Card(JSONObject jobject) {
        df = new SimpleDateFormat(formatoFecha, new Locale("ES", "MX"));
        cardId = "";
        cardNombre = "";
        responsableId = "";
        responsableNombre = "";
        visorId = "";
        visorNombre = "";
        fecha = new java.util.Date();
        email = "";
        estatus = "";
        profile = new CardProfile(new JSONObject());
        personal = new CardPersona[0];
        equipos = new CardEquipo[0];
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("cardId")) {
                setCardId(jobject.getString("cardId"));
            }
            if (jobject.has("cardNombre")) {
                setCardNombre(jobject.getString("cardNombre"));
            }
            if (jobject.has("responsableId")) {
                setResponsableId(jobject.getString("responsableId"));
            }
            if (jobject.has("responsableNombre")) {
                setResponsableNombre(jobject.getString("responsableNombre"));
            }
            if (jobject.has("visorId")) {
                setVisorId(jobject.getString("visorId"));
            }
            if (jobject.has("visorNombre")) {
                setVisorNombre(jobject.getString("visorNombre"));
            }
            if (jobject.has("fecha")) {
                setFecha(jobject.getString("fecha"));
            }
            if (jobject.has("email")) {
                setEMail(jobject.getString("email"));
            }
            if (jobject.has("estatus")) {
                setEstatus(jobject.getString("estatus"));
            }
            profile = new CardProfile(new JSONObject());
            if (jobject.has("profile")) {
                JSONObject jreport = jobject.getJSONObject("profile");
                profile = new CardProfile(jreport);
            }
            personal = new CardPersona[0];
            if (jobject.has("personal")) {
                JSONArray jpersonal = jobject.getJSONArray("personal");
                personal = new CardPersona[jpersonal.length()];
                for (int i = 0; i < personal.length; i++) {
                    JSONObject jcard = jpersonal.getJSONObject(i);
                    if (jcard == null) {
                        personal[i] = new CardPersona(new JSONObject());
                        continue;
                    }
                    personal[i] = new CardPersona(jcard);
                }
            }
            equipos = new CardEquipo[0];
            if (jobject.has("equipos")) {
                JSONArray jequipos = jobject.getJSONArray("equipos");
                equipos = new CardEquipo[jequipos.length()];
                for (int i = 0; i < equipos.length; i++) {
                    JSONObject jcard = jequipos.getJSONObject(i);
                    if (jcard == null) {
                        equipos[i] = new CardEquipo(new JSONObject());
                        continue;
                    }
                    equipos[i] = new CardEquipo(jcard);
                }
            }
        } catch (Exception ex) {
            System.err.println("Card:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    
    public String getCardNombre() {
        return cardNombre;
    }

    public void setCardNombre(String cardNombre) {
        this.cardNombre = cardNombre;
    }

    public String getResponsableId() {
        return responsableId;
    }

    public void setResponsableId(String responsableId) {
        this.responsableId = responsableId;
    }
    
    public String getResponsableNombre() {
        return responsableNombre;
    }

    public void setResponsableNombre(String responsableNombre) {
        this.responsableNombre = responsableNombre;
    }

    public String getVisorId() {
        return visorId;
    }

    public void setVisorId(String visorId) {
        this.visorId = visorId;
    }
    
    public String getVisorNombre() {
        return visorNombre;
    }

    public void setVisorNombre(String visorNombre) {
        this.visorNombre = visorNombre;
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

    public String getEMail() {
        return email;
    }

    public void setEMail(String email) {
        this.email = email;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }


    public CardProfile getCardProfile() {
        return profile;
    }

    public void setCardProfile(CardProfile profile) {
        this.profile = profile;
    }
    
    public CardPersona[] getPersonal() {
        return personal;
    }

    public void setPersonal(CardPersona[] personal) {
        this.personal = personal;
    }

    public CardEquipo[] getEquipos() {
        return equipos;
    }

    public void setEquipos(CardEquipo[] equipos) {
        this.equipos = equipos;
    }
    
    public void update() {
        jobject.put("cardId", cardId);
        jobject.put("cardNombre", cardNombre);
        jobject.put("responsableId", responsableId);
        jobject.put("responsableNombre", responsableNombre);
        jobject.put("visorId", visorId);
        jobject.put("visorNombre", visorNombre);
        jobject.put("fecha", df.format(fecha));
        jobject.put("email", email);
        jobject.put("estatus", estatus);
        profile.update();
        jobject.put("profile", profile.json());
        JSONArray opersonal = new JSONArray();
        if (personal != null) {
            for (int i = 0; i < personal.length; i++) {
                personal[i].update();
                opersonal.put(personal[i].json());
            }
        }
        jobject.put("personal", opersonal);
        JSONArray oequipos = new JSONArray();
        if (equipos != null) {
            for (int i = 0; i < equipos.length; i++) {
                equipos[i].update();
                oequipos.put(equipos[i].json());
            }
        }
        jobject.put("equipos", oequipos);
    }

    public JSONObject json() {
        return jobject;
    }

}
