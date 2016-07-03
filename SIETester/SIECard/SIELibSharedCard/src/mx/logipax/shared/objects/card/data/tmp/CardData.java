/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package mx.logipax.shared.objects.card.data.tmp;

import mx.logipax.shared.objects.card.data.*;
import mx.logipax.utility.Strings;
import org.json.JSONArray;
import org.json.JSONObject;

public class CardData {

    private JSONObject jobject;
    public int index = 0;
    public int dateinx = 0;
    public int rows = 0;
    public int cols = 0;
    public double values[][];

    public CardData(JSONObject jobject) {

        this.jobject = jobject;
        try {
            if (jobject.has("index")) {
                index = jobject.getInt("index");
            }
            if (jobject.has("dateinx")) {
                dateinx = jobject.getInt("dateinx");
            }
            if (jobject.has("rows")) {
                rows = jobject.getInt("rows");
            }
            if (jobject.has("cols")) {
                cols = jobject.getInt("cols");
            }
            values = new double[rows][cols];
            if (jobject.has("values")) {
                JSONArray jnodes = jobject.getJSONArray("values");
                int size = jnodes.length();
                values = new double[rows][cols];
                for (int i = 0; i < size; i++) {
                    Object objectcell = jnodes.get(i);
                    if (objectcell == null) {
                        continue;
                    }
                    int r = i / cols;
                    int c = i % cols;
                    if (objectcell instanceof Number) {
                        values[r][c] = ((Number) objectcell).doubleValue();
                    } else {
                        values[r][c] = Strings.getDouble(objectcell.toString());
                    }
                }
//            } else {
//                System.out.println("zero values");
            }
        } catch (Exception ex) {
            System.err.println("BudgetData:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public void update(JSONArray jvalues) {
        jobject.put("index", index);
        jobject.put("dateinx", dateinx);
        jobject.put("rows", rows);
        jobject.put("cols", cols);
        jobject.put("values", jvalues);
    }

    public void update() {
        jobject.put("index", index);
        jobject.put("dateinx", dateinx);
        jobject.put("rows", rows);
        jobject.put("cols", cols);
    }

    public JSONObject json() {
        return jobject;
    }
}
