package mx.logipax.shared.objects.card.zz;

import org.json.JSONArray;
import org.json.JSONObject;

public class LevelEncargo {

    private final JSONObject jobject;
    private int level = 0;
    private String id = "";
    private String name = "";
    private String desc = "";
    private int check = -1;
    public LevelEncargo levels[] = new LevelEncargo[0];

    public LevelEncargo(JSONObject jobject) {
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
            if (jobject.has("levels")) {
                JSONArray jlevels = jobject.getJSONArray("levels");
                levels = new LevelEncargo[jlevels.length()];
                for (int i = 0; i < levels.length; i++) {
                    JSONObject jnode = jlevels.getJSONObject(i);
                    if (jnode == null) {
                        levels[i] = new LevelEncargo(new JSONObject());
                        continue;
                    }
                    levels[i] = new LevelEncargo(jnode);
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

    public LevelEncargo[] getLevels() {
        return levels;
    }

    public void setLevels(LevelEncargo[] levels) {
        this.levels = levels;
    }
    
    public void update(JSONArray jlevels) {
        jobject.put("level", level);
        jobject.put("id", id);
        jobject.put("name", name);
        jobject.put("desc", desc);
        jobject.put("check", check);
        jobject.put("levels", jlevels);
    }

    public JSONObject json() {
        return jobject;
    }

    public int find(LevelEncargo row) {
        for (int i = 0; i < levels.length; i++) {
            if (row.id.equals(levels[i].id)) {
                return i;
            }
        }
        return -1;
    }
}
