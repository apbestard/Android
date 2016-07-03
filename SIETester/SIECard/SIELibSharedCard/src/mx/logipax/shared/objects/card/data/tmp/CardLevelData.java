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

public class CardLevelData {

    private JSONObject jobject;
    public String sucursal = "";
    public int rows = 0;
    public int cols = 0;
    public int dates = 0;
    public double montos[][][];
    public double volumenes[][][];
    public double volcapacidades[][][];
    public double volparos[][][];
    
    public CardLevelData(JSONObject jobject) {

        this.jobject = jobject;
        try {
            if (jobject.has("sucursal")) {
                sucursal = jobject.getString("sucursal");
            }
            if (jobject.has("rows")) {
                rows = jobject.getInt("rows");
            }
            if (jobject.has("cols")) {
                cols = jobject.getInt("cols");
            }
            if (jobject.has("dates")) {
                dates = jobject.getInt("dates");
            }
            montos = new double[rows][cols][dates];
            if (jobject.has("montos")) {
                JSONArray jnodes = jobject.getJSONArray("montos");
                int size = jnodes.length();
                montos = new double[rows][cols][dates];
                for (int i = 0; i < size; i++) {
                    Object objectcell = jnodes.get(i);
                    if (objectcell == null) {
                        continue;
                    }
                    int r = i / (cols*dates);
                    int c = (i % (cols*dates))/(dates);
                    int d = i % (dates);
                    if (objectcell instanceof Number) {
                        montos[r][c][d] = ((Number) objectcell).doubleValue();
                    } else {
                        montos[r][c][d] = Strings.getDouble(objectcell.toString());
                    }
                }
            }
            volumenes = new double[rows][cols][dates];
            if (jobject.has("volumenes")) {
                JSONArray jnodes = jobject.getJSONArray("volumenes");
                int size = jnodes.length();
                volumenes = new double[rows][cols][dates];
                for (int i = 0; i < size; i++) {
                    Object objectcell = jnodes.get(i);
                    if (objectcell == null) {
                        continue;
                    }
                    int r = i / (cols*dates);
                    int c = (i % (cols*dates))/(dates);
                    int d = i % (dates);
                    if (objectcell instanceof Number) {
                        volumenes[r][c][d] = ((Number) objectcell).doubleValue();
                    } else {
                        volumenes[r][c][d] = Strings.getDouble(objectcell.toString());
                    }
                }
            }
            volcapacidades = new double[rows][cols][dates];
            if (jobject.has("volcapacidades")) {
                JSONArray jnodes = jobject.getJSONArray("volcapacidades");
                int size = jnodes.length();
                volcapacidades = new double[rows][cols][dates];
                for (int i = 0; i < size; i++) {
                    Object objectcell = jnodes.get(i);
                    if (objectcell == null) {
                        continue;
                    }
                    int r = i / (cols*dates);
                    int c = (i % (cols*dates))/(dates);
                    int d = i % (dates);
                    if (objectcell instanceof Number) {
                        volcapacidades[r][c][d] = ((Number) objectcell).doubleValue();
                    } else {
                        volcapacidades[r][c][d] = Strings.getDouble(objectcell.toString());
                    }
                }
            }
            volparos = new double[rows][cols][dates];
            if (jobject.has("volparos")) {
                JSONArray jnodes = jobject.getJSONArray("volparos");
                int size = jnodes.length();
                volparos = new double[rows][cols][dates];
                for (int i = 0; i < size; i++) {
                    Object objectcell = jnodes.get(i);
                    if (objectcell == null) {
                        continue;
                    }
                    int r = i / (cols*dates);
                    int c = (i % (cols*dates))/(dates);
                    int d = i % (dates);
                    if (objectcell instanceof Number) {
                        volparos[r][c][d] = ((Number) objectcell).doubleValue();
                    } else {
                        volparos[r][c][d] = Strings.getDouble(objectcell.toString());
                    }
                }
            }
        } catch (Exception ex) {
            System.err.println("BudgetData:" + ex.toString());
        }
    }

    public void dispose() {
    }

    public void update() {
        jobject.put("sucursal", sucursal);
        jobject.put("dates", dates);
        jobject.put("rows", rows);
        jobject.put("cols", cols);
        JSONArray jmontos = new JSONArray();
        for (int r=0;r<rows;r++) {
            for (int c=0;c<cols;c++) {
                for (int d=0;d<dates;d++) {
                    jmontos.put(montos[r][c][d]);
                }
            }
        }
        jobject.put("montos", jmontos);
        JSONArray jvolumenes = new JSONArray();
        for (int r=0;r<rows;r++) {
            for (int c=0;c<cols;c++) {
                for (int d=0;d<dates;d++) {
                    jvolumenes.put(volumenes[r][c][d]);
                }
            }
        }
        jobject.put("volumenes", jvolumenes);
        JSONArray jvolcapacidades = new JSONArray();
        for (int r=0;r<rows;r++) {
            for (int c=0;c<cols;c++) {
                for (int d=0;d<dates;d++) {
                    jvolcapacidades.put(volcapacidades[r][c][d]);
                }
            }
        }
        jobject.put("volcapacidades", jvolcapacidades);
        JSONArray jvolparos = new JSONArray();
        for (int r=0;r<rows;r++) {
            for (int c=0;c<cols;c++) {
                for (int d=0;d<dates;d++) {
                    jvolparos.put(volparos[r][c][d]);
                }
            }
        }
        jobject.put("volparos", jvolparos);
    }

    public JSONObject json() {
        return jobject;
    }
}
