package mx.logipax.shared.objects.card.cat;

import org.json.JSONObject;

public class CardSucPerfil {

    private JSONObject jobject;
    private String cardId;
    private String parentId;
    private boolean supervisor;
    private boolean editable;
    private String options;
    private String estatus;

    public CardSucPerfil(JSONObject jobject) {
        cardId = "";
        parentId = "";
        supervisor = false;
        editable = false;
        options = "";
        estatus = "";
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("cardId")) {
                setCardId(jobject.getString("cardId"));
            }
            if (jobject.has("parentId")) {
                setParentId(jobject.getString("parentId"));
            }
            if (jobject.has("supervisor")) {
                setSupervisor(jobject.getBoolean("supervisor"));
            }
            if (jobject.has("editable")) {
                setEditable(jobject.getBoolean("editable"));
            }
            if (jobject.has("options")) {
                setOptions(jobject.getString("options"));
            }
            if (jobject.has("estatus")) {
                setEstatus(jobject.getString("estatus"));
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    public boolean getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(boolean supervisor) {
        this.supervisor = supervisor;
    }

    public boolean getEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    
    public void update() {
        jobject.put("cardId", cardId);
        jobject.put("parentId", parentId);
        jobject.put("supervisor", supervisor);
        jobject.put("editable", editable);
        jobject.put("options", options);
        jobject.put("estatus", estatus);
    }

    public JSONObject json() {
        return jobject;
    }
}
