package mx.logipax.shared.objects.card.cat;

import org.json.JSONArray;
import org.json.JSONObject;

public class CardSucs {

    private JSONObject jobject;
    public CardSuc record;
    public CardSuc records[];
    
    public CardSucs(JSONObject jobject) {
        reload(jobject);
    }

    public void reload(JSONObject jobject) {
        this.jobject = jobject;
        try {
            record = new CardSuc(new JSONObject());
            if (jobject.has("record")) {
                JSONObject jrecord = jobject.getJSONObject("record");
                record = new CardSuc(jrecord);
            }
            
            records = new CardSuc[0];
            if (jobject.has("records")) {
                JSONArray jrecords = jobject.getJSONArray("records");
                records = new CardSuc[jrecords.length()];
                for (int i = 0; i < records.length; i++) {
                    JSONObject jrecord = jrecords.getJSONObject(i);
                    if (jrecord == null) {
                        records[i] = new CardSuc(new JSONObject());
                        continue;
                    }
                    records[i] = new CardSuc(jrecord);
                }
            }
        } catch (Exception ex) {
            System.err.println("CardSuc:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < records.length; i++) {
            records[i].dispose();
        }
    }

    public void update() {
        jobject.put("record", record.json());
    }
    
    public void update(JSONArray orecords) {
        jobject.put("record", record.json());
        jobject.put("records", orecords);
    }
    
    public JSONObject json() {
        return jobject;
    }
}
