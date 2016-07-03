package mx.logipax.shared.objects.card.cat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

public class CardSuc {

    private JSONObject jobject;
    private int cardLevel;
    private String cardId;
    private String cardNombre;
    private String responsableId;
    private String responsableNombre;
    private String visorId;
    private String visorNombre;
    private String email;
    private String formatoId;
    private String mismaId;
    private Date apertura;
    private String estatus;
    private CardSucPerfil perfil;
    private CardSucPersona personal[];
    private CardSucEquipo equipos[];
    private String formatoFecha = "dd/MM/yyyy";
    private SimpleDateFormat df;

    public CardSuc(JSONObject jobject) {
        df = new SimpleDateFormat(formatoFecha, new Locale("ES", "MX"));
        cardLevel = 0;
        cardId = "";
        cardNombre = "";
        responsableId = "";
        responsableNombre = "";
        visorId = "";
        visorNombre = "";
        email = "";
        formatoId = "";
        mismaId = "";
        apertura = new java.util.Date();
        estatus = "";
        perfil = new CardSucPerfil(new JSONObject());
        personal = new CardSucPersona[0];
        equipos = new CardSucEquipo[0];
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("cardLevel")) {
                setCardLevel(jobject.getInt("cardLevel"));
            }
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
            if (jobject.has("email")) {
                setEMail(jobject.getString("email"));
            }
            if (jobject.has("formatoId")) {
                setFormatoId(jobject.getString("formatoId"));
            }
            if (jobject.has("mismaId")) {
                setMismaId(jobject.getString("mismaId"));
            }
            if (jobject.has("apertura")) {
                setFecha(jobject.getString("apertura"));
            }
            if (jobject.has("estatus")) {
                setEstatus(jobject.getString("estatus"));
            }
            perfil = new CardSucPerfil(new JSONObject());
            if (jobject.has("perfil")) {
                JSONObject jreport = jobject.getJSONObject("perfil");
                perfil = new CardSucPerfil(jreport);
            }
            personal = new CardSucPersona[0];
            if (jobject.has("personal")) {
                JSONArray jpersonal = jobject.getJSONArray("personal");
                personal = new CardSucPersona[jpersonal.length()];
                for (int i = 0; i < personal.length; i++) {
                    JSONObject jcard = jpersonal.getJSONObject(i);
                    if (jcard == null) {
                        personal[i] = new CardSucPersona(new JSONObject());
                        continue;
                    }
                    personal[i] = new CardSucPersona(jcard);
                }
            }
            equipos = new CardSucEquipo[0];
            if (jobject.has("equipos")) {
                JSONArray jequipos = jobject.getJSONArray("equipos");
                equipos = new CardSucEquipo[jequipos.length()];
                for (int i = 0; i < equipos.length; i++) {
                    JSONObject jcard = jequipos.getJSONObject(i);
                    if (jcard == null) {
                        equipos[i] = new CardSucEquipo(new JSONObject());
                        continue;
                    }
                    equipos[i] = new CardSucEquipo(jcard);
                }
            }
        } catch (Exception ex) {
            System.err.println("Card:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public int getCardLevel() {
        return cardLevel;
    }

    public void setCardLevel(int cardLevel) {
        this.cardLevel = cardLevel;
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
    
    public String getEMail() {
        return email;
    }

    public void setEMail(String email) {
        this.email = email;
    }

    public String getFormatoId() {
        return formatoId;
    }

    public void setFormatoId(String formatoId) {
        this.formatoId = formatoId;
    }

    public String getMismaId() {
        return mismaId;
    }

    public void setMismaId(String mismaId) {
        this.mismaId = mismaId;
    }
    
    public java.util.Date getFecha() {
        return apertura;
    }
    public String getFechaString() {
        return df.format(apertura);
    }

    public void setFecha(String apertura) {
        try {
            this.apertura = df.parse(apertura);
        } catch (Exception ex) {
        }
    }

    public void setFecha(java.util.Date apertura) {
        this.apertura = apertura;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public CardSucPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(CardSucPerfil perfil) {
        this.perfil = perfil;
    }
    
    public CardSucPersona[] getPersonal() {
        return personal;
    }

    public void setPersonal(CardSucPersona[] personal) {
        this.personal = personal;
    }

    public CardSucEquipo[] getEquipos() {
        return equipos;
    }

    public void setEquipos(CardSucEquipo[] equipos) {
        this.equipos = equipos;
    }
    
    public void update() {
        jobject.put("cardLevel", cardLevel);
        jobject.put("cardId", cardId);
        jobject.put("cardNombre", cardNombre);
        jobject.put("responsableId", responsableId);
        jobject.put("responsableNombre", responsableNombre);
        jobject.put("visorId", visorId);
        jobject.put("visorNombre", visorNombre);
        jobject.put("email", email);
        jobject.put("formatoId", formatoId);
        jobject.put("mismaId", mismaId);
        jobject.put("apertura", df.format(apertura));
        jobject.put("estatus", estatus);
        perfil.update();
        jobject.put("perfil", perfil.json());
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
