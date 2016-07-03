package mx.logipax.shared.objects.card;

import mx.logipax.shared.objects.viewer.Report;
import org.json.JSONObject;

public class Cards {

    private JSONObject jobject;
    public Report report;
    public JSONObject card;
    
    public Cards(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("report")) {
                report = new Report(new JSONObject());
                JSONObject jbudget = jobject.getJSONObject("report");
                report = new Report(jbudget);
            }
            card = new JSONObject();
            if (jobject.has("card")) {
                card = jobject.getJSONObject("card");
            }
        } catch (Exception ex) {
            System.err.println("Cards:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public void update() {
        jobject.put("report", report.json());
        jobject.put("card", card);
    }
    public void updateNoData() {
        jobject.put("report", report.json());
        if (jobject.has("card")) {
            jobject.remove("card");
        }        
    }
    public void updateNoReport() {
        if (jobject.has("report")) {
            jobject.remove("report");
        }        
        jobject.put("card", card);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
