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

public class CardLevel {

    private final JSONObject jobject;
    public int level = 0;
    public String id = "";
    public String name = "";
    public String desc = "";
    public int check = -1;
    public CardLevelData datas[] = new CardLevelData[0];

    public CardLevel(JSONObject jobject) {
        this.jobject = jobject;
        try {
            if (jobject.has("id")) {
                id = jobject.getString("id");
            }
            if (jobject.has("level")) {
                level = jobject.getInt("level");
            }
            if (jobject.has("name")) {
                name = jobject.getString("name");
            }
            if (jobject.has("desc")) {
                desc = jobject.getString("desc");
            }
            if (jobject.has("check")) {
                check = jobject.getInt("check");
            }
            if (jobject.has("datas")) {
                JSONArray jdatas = jobject.getJSONArray("datas");
                datas = new CardLevelData[jdatas.length()];
                for (int i = 0; i < datas.length; i++) {
                    JSONObject jnode = jdatas.getJSONObject(i);
                    if (jnode == null) {
                        datas[i] = new CardLevelData(new JSONObject());
                        continue;
                    }
                    datas[i] = new CardLevelData(jnode);
                }
            }
        } catch (Exception ex) {
            System.err.println("Level:" + ex.toString());
        }
    }

    public void dispose() {
        for (int i = 0; i < datas.length; i++) {
            datas[i].dispose();
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }

    public CardLevelData[] getDatas() {
        return datas;
    }

    public void setDatas(CardLevelData[] datas) {
        this.datas = datas;
    }

    public void update() {
        jobject.put("level", level);
        jobject.put("id", id);
        jobject.put("name", name);
        jobject.put("desc", desc);
        jobject.put("check", check);
        JSONArray jdatas = new JSONArray();
        for (int i=0;i<datas.length;i++) {
            jdatas.put(datas[i].json());
        }
        jobject.put("datas", jdatas);
    }

    public JSONObject json() {
        return jobject;
    }
}
