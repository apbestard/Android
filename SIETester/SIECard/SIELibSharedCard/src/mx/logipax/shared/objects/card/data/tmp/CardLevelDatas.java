/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package mx.logipax.shared.objects.card.data.tmp;

import mx.logipax.shared.objects.card.data.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class CardLevelDatas {

    private final JSONObject jobject;
    public String id = "";
    public CardLevelData levels[] = new CardLevelData[0];

    public CardLevelDatas(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("id")) {
                id = jobject.getString("id");
            }
            if (jobject.has("levels")) {
                JSONArray jlevels = jobject.getJSONArray("levels");
                levels = new CardLevelData[jlevels.length()];
                for (int i = 0; i < levels.length; i++) {
                    JSONObject jnode = jlevels.getJSONObject(i);
                    if (jnode == null) {
                        levels[i] = new CardLevelData(new JSONObject());
                        continue;
                    }
                    levels[i] = new CardLevelData(jnode);
                }
            }
        } catch (Exception ex) {
            System.err.println("Level:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < levels.length; i++) {
            levels[i].dispose();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CardLevelData[] getLevels() {
        return levels;
    }

    public void setLevel(CardLevelData[] levels) {
        this.levels = levels;
    }

    public void update(JSONArray jlevels) {
        jobject.put("id", id);
        jobject.put("levels", jlevels);
    }

    public JSONObject json() {
        return jobject;
    }
}
