/*
 Derechos Reservados (c)
 Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
 2009, 2010, 2011, 2012, 2013, 2014
 logipax Marca Registrada (R)  2003, 2014
 */
package backend.card.io.db;

import backend.BEServletInterfase;
import backend.card.BECardDispatcher;
import backend.catalogs2.db.DB2CatList;
import backend.card.io.ViewRecords.SReport;
import backend.card.def.vwr.cat.DefDBVNivel;
import backend.card.io.BECardSuc;
import backend.card.io.ViewRecords;
import backend.card.io.ViewRecords.SRepoLevel;
import backend.card.io.ViewRecords.SRepoLevelData;
import backend.card.io.ViewRecords.STableRow;
import java.util.Hashtable;
import mx.logipax.shared.DBDefaults;
import mx.logipax.shared.objects.RepoLevel;
import mx.logipax.shared.objects.RepoLevelData;
import mx.logipax.shared.objects.RepoLevelDatas;
import mx.logipax.shared.objects.card.Cards;
import mx.logipax.shared.objects.viewer.ObjectClass;
import mx.logipax.shared.objects.viewer.RepoPeriod;
import mx.logipax.shared.objects.viewer.RepoView;
import mx.logipax.shared.objects.viewer.Report;
import mx.logipax.shared.objects.viewer.TableColumn;
import mx.logipax.shared.objects.viewer.TableRow;
import mx.logipax.utility.Dates;
import mx.logipax.utility.Utility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DBCardInterfase {

    BEServletInterfase servlet;
    int corporate;
    public static String CORPORATENAME = null;
    boolean savedef = false;
    final BECardDispatcher viewer;

    public DBCardInterfase(BEServletInterfase servlet, int corporate, BECardDispatcher viewer) {
        this.servlet = servlet;
        this.corporate = corporate;
        this.viewer = viewer;
    }

    public Object[] getReporteDef(Object[] key, String names[], Class clases[]) {
        String keyId = key[0].toString();
        for (int i = 1; i < key.length; i++) {
            keyId += "_" + key[i];
        }
        byte[] bresult = servlet.getCache("cards", keyId);
        if (bresult != null) {
            return new Object[]{bresult};
        }
        if (!savedef) {
            return null;
        }
        return null;
    }

    public boolean updateReporteDef(Object[] key, String names[], Class clases[], Object values[]) {
        servlet.setCache("cards", getReportFileName(key), (byte[]) values[0]);
        if (!savedef) {
            return true;
        }
        return true;
    }

    public String getReportFileName(Object[] key) {
        String keyId = key[0].toString();
        for (int i = 1; i < key.length; i++) {
            keyId += "_" + key[i];
        }
        return keyId;
    }

    public boolean insertReporteDef(Object[] key, String names[], Class clases[], Object values[]) {
        servlet.setCache("cards", getReportFileName(key), (byte[]) values[0]);
        if (!savedef) {
            return true;
        }
        return true;
    }

    public SReport getReporte(String pool, String level, String levelvalue) {
        SReport reporte = new SReport();
        reporte.moduleId = "module";
        reporte.id = "card";
        reporte.idimage = "report";
        reporte.pool = "";
        reporte.pretab0 = "";
        reporte.pretab1 = "";

        reporte.name = "Cédula de Información";
        reporte.desc = "Cédula de Información";
        String level1Id = "UNIDAD_RES_VTA";
        String level1Name = "ORGANIZACIÓN";
        int level1Max = 6;
        int start1Level = 0;
        String start1Node = "0";
        int level1HSize = 200;
        reporte.columnFixed = 1;
        reporte.attrs = Report.ATTRRANK;
        reporte.datosId = "CARD";
        reporte.edit = true;
        reporte.filter = "";
        if (reporte.pool != null && reporte.pool.length() > 0) {
            pool = reporte.pool;
        }
        String niveltable = DefDBVNivel.tableTagName20;
        if (reporte.pretab0 != null && reporte.pretab0.length() > 0) {
            niveltable = reporte.pretab0;
        }
        SRepoLevel level01 = getLevelAll(pool, niveltable, level1Id, 0, null, reporte.ranking() ? level1Max - 2 : level1Max - 1, levelDataTable(levelData(pool, level1Id)));
        level01 = getLevel(start1Level, start1Node, level01);
        reporte.levelIds = new String[]{level1Id};
        reporte.levelNames = new String[]{level1Name};
        reporte.levels = new SRepoLevel[]{level01};
        reporte.treeHSize = level1HSize;
        reporte.periods = new ViewRecords.SPeriod[]{
            new ViewRecords.SPeriod(ObjectClass.ADHOC, "", ObjectClass.ADHOC),};
        reporte.tabcolumns = new ViewRecords.STableColumn[1];
        reporte.tabcolumns[0] = new ViewRecords.STableColumn("cnp", "Concepto", "", ObjectClass.STRING, "Concepto", 200);
        reporte.tabrows = new STableRow[]{new ViewRecords.STableRow("row", "Rank", "", "1", "")};
        reporte.tabHSize = 90;
        int[] cols = new int[]{0};
        reporte.views = new ViewRecords.SRepoView[]{
            new ViewRecords.SRepoView("vw", "Vista", "Desc", 1,
            cols,
            new int[]{0},
            0, new String[]{}, new int[]{}, new int[]{},
            new int[]{}, new int[]{}, new int[]{}, new int[]{},
            new String[]{}, new int[]{})};
        return reporte;
    }

    public static final String VARIABLES[] = new String[]{
        "2014 Real",
        "2015 Real",
        "2016 Presupuesto"
    };
    public static final int VARSIZES[] = new int[]{
        100,
        100,
        100
    };
    public static final String MESES[] = new String[]{
        "Total",
        "Enero",
        "Febrero",
        "Marzo",
        "Abril",
        "Mayo",
        "Junio",
        "Julio",
        "Agosto",
        "Septiembre",
        "Octubre",
        "Noviembre",
        "Diciembre",};

    public static SRepoLevel levelSegmento() {
        return new SRepoLevel(0, "0", "SEGMENTOS", "desc", new SRepoLevelData(),
                new SRepoLevel[]{new SRepoLevel(1, "1", "PÚBLICO", "Segmento de Venta Público", new SRepoLevelData(),
                            new SRepoLevel[]{}),
                    new SRepoLevel(1, "2", "MÉDICO", "Segmento de Venta Médicos", new SRepoLevelData(),
                            new SRepoLevel[]{}),
                    new SRepoLevel(1, "5", "BANCOS & ASEGURADORAS", "Segmento de Bancos y Aseguradoras", new SRepoLevelData(),
                            new SRepoLevel[]{}),
                    new SRepoLevel(1, "3", "EMPRESAS", "Segmento de Empresas", new SRepoLevelData(),
                            new SRepoLevel[]{}),
                    new SRepoLevel(1, "4", "GOBIERNO", "Segmento de Gobierno", new SRepoLevelData(),
                            new SRepoLevel[]{}),});
    }

    public static SRepoLevel levelEspecialidad() {
        return new SRepoLevel(0, "0", "ESPECIALIDADES", "desc", new SRepoLevelData(),
                new SRepoLevel[]{new SRepoLevel(1, "1", "LABORATORIO", "Laboratorio", new SRepoLevelData(),
                            new SRepoLevel[]{
                                new SRepoLevel(2, "2", "BIOLOGIA MOLECULAR", "BIOLOGIA MOLECULAR", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "3", "BIOQUIMICA", "BIOQUIMICA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "31", "CITOGENETICA", "CITOGENETICA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "5", "CITOLOGIA", "CITOLOGIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "9", "HEMATOLOGIA", "HEMATOLOGIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "10", "HISTOPATOLOGIA", "HISTOPATOLOGIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "11", "INMUNOLOGIA", "INMUNOLOGIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "14", "MICROBIOLOGIA", "MICROBIOLOGIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "16", "PARASITOLOGIA Y URIANALISIS", "PARASITOLOGIA Y URIANALISIS", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "22", "TOXICOLOGIA", "TOXICOLOGIA", new SRepoLevelData(), new SRepoLevel[]{}),}),
                    new SRepoLevel(1, "2", "GABINETE", "Gabinete", new SRepoLevelData(),
                            new SRepoLevel[]{
                                new SRepoLevel(2, "1", "AUDIOLOGIA", "AUDIOLOGIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "8", "CARDIOLOGIA", "CARDIOLOGIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "4", "CHEQUEOS", "CHEQUEOS", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "6", "COLPOSCOPIA", "COLPOSCOPIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "24", "DENSITOMETRIA", "DENSITOMETRIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "7", "DOMICILIOS", "DOMICILIOS", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "25", "ELECTROENCEFALOGRAFIA", "ELECTROENCEFALOGRAFIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "30", "HEMODIALISIS", "HEMODIALISIS", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "12", "MASTOGRAFIA", "MASTOGRAFIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "13", "MEDICINA NUCLEAR", "MEDICINA NUCLEAR", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "34", "OPTOMETRIA", "OPTOMETRIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "32", "PET", "PET", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "18", "RADIOLOGIA", "RADIOLOGIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "19", "RESONANCIA MAGNETICA", "RESONANCIA MAGNETICA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "20", "TOMADORES", "TOMADORES", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "21", "TOMOGRAFIA", "TOMOGRAFIA", new SRepoLevelData(), new SRepoLevel[]{}),
                                new SRepoLevel(2, "23", "ULTRASONIDO", "ULTRASONIDO", new SRepoLevelData(), new SRepoLevel[]{}),})});
    }
//    public static SRepoLevel levelSegmentoBk() {
//        return new SRepoLevel("s0", "SEGMENTOS", "desc", new SRepoLevelData(),
//                new SRepoLevel[]{new SRepoLevel("s1", "PÚBLICO", "Segmento de Venta Público", new SRepoLevelData(),
//                            new SRepoLevel[]{new SRepoLevel("s11", "PÚBLICO", "Segmento de Venta Público", new SRepoLevelData(),
//                                        new SRepoLevel[]{}),}),
//                    new SRepoLevel("s2", "REPR MÉDICO", "Segmento de Venta Médicos", new SRepoLevelData(),
//                            new SRepoLevel[]{new SRepoLevel("s2", "REPR MÉDICO", "Segmento de Venta Médicos", new SRepoLevelData(),
//                                        new SRepoLevel[]{})}),
//                    new SRepoLevel("s3", "EMPRESAS", "Segmento de Venta Empresas", new SRepoLevelData(),
//                            new SRepoLevel[]{
//                                new SRepoLevel("s31", "INDUSTRIAS", "Segmento de Venta Empresas", new SRepoLevelData(),
//                                        new SRepoLevel[]{}),
//                                new SRepoLevel("s31", "BANCOS Y ASEGURADORAS", "Segmento de Venta Bancos y Aseguradoras", new SRepoLevelData(),
//                                        new SRepoLevel[]{}),
//                                new SRepoLevel("s32", "GOBERNO", "Segmento de Venta Gobierno", new SRepoLevelData(),
//                                        new SRepoLevel[]{}),
//                                new SRepoLevel("s33", "TARJETEROS", "Segmento de Venta Cuponeras", new SRepoLevelData(),
//                                        new SRepoLevel[]{}),
//                                new SRepoLevel("s34", "ALPABI", "Segmento de Venta ALPABI", new SRepoLevelData(),
//                                        new SRepoLevel[]{}),})});
//    }

    private SRepoLevel getLevel(int startLevel, String startNode, SRepoLevel levels) {
        if (startLevel == 0 || levels == null) {
            return levels;
        }
        boolean ok = false;
        for (int i = startLevel - 1; i < levels.levels.length; i++) {
            if (levels.levels[i] == null) {
                continue;
            }
            if (levels.levels[i].id.equals(startNode)) {
                levels.id = levels.levels[i].id;
                levels.name = levels.levels[i].name;
                levels.desc = levels.levels[i].desc;
                levels.level = levels.levels[i].level;
                levels.data = levels.levels[i].data;
                levels.levels = levels.levels[i].levels;
                ok = true;
                break;
            }
        }
        return levels;
    }

    public SReport getCalcReporte(String reporteId, String level, String levelvalue) {
        for (int i = 0; i < backend.card.io.db.DBProfileInterfase.REPORTS.length; i++) {
            if (reporteId.equals(backend.card.io.db.DBProfileInterfase.REPORTS[i].id)) {
                return backend.card.io.db.DBProfileInterfase.REPORTS[i];
            }
        }
        return null;
    }

    private Hashtable<String, RepoLevelData> levelDataTable(RepoLevelDatas datas) {
        Hashtable<String, RepoLevelData> data = new Hashtable<String, RepoLevelData>();
        for (int i = 0; i < datas.levels.length; i++) {
            data.put(Integer.toString(datas.levels[i].level) + "-" + datas.levels[i].id, datas.levels[i]);
        }
        return data;
    }

    private RepoLevelDatas levelData(String pool, String level_id) {
//        Object[] key = new Object[]{"LDAT", level_id};
//        String keyId = getReportFileName(key);
//        byte[] bresult = servlet.getCache("cardreports", keyId);
//        if (bresult != null) {
//            String jsontxt = Utility.toString(bresult);
//            JSONObject jobj = new JSONObject(new JSONTokener(jsontxt));
//            return new RepoLevelDatas(jobj);
//        }
        RepoLevelDatas data = new RepoLevelDatas(new JSONObject());
//        JSONArray jlevels = new JSONArray();
        data.id = level_id;
//        if (level_id == null || level_id.length() == 0) {
//            data.update(jlevels);
//            return data;
//        }
        data.levels = new RepoLevelData[0];
//        String where = "n.nivel_id = \'" + level_id + "\'  and n.nivel = 2";
//        String tabla = DefDBVCard.tableTagName2 + " n";// left join " + DefDBVSucursal.tableTagName2 + " s on s.sucursal_id = n.nodo";
//        DB2CatList db_list2 = new DB2CatList(servlet.db(), pool,
//                new String[]{"n.nivel", "n.nodo", "n.fecha_apertura", "n.asignadas", "n.vacantes"},
//                new Class[]{Integer.class, String.class, java.util.Date.class, Integer.class, Integer.class},
//                tabla, where, "n.nivel, n.nodo");
//        java.util.Vector data2 = db_list2.getPage();
//        int size2 = data2.size();
//        data.levels = new RepoLevelData[size2];
//        for (int n = 0; n < size2; n++) {
//            int rinx = 0;
//            Object row[] = (Object[]) data2.elementAt(n);
//            Integer nnivel = (Integer) row[rinx++];
//            String nodo = (String) row[rinx++];
//            java.util.Date apertura = (java.util.Date) row[rinx++];
//            if (apertura == null) {
//                apertura = new java.util.Date();
//            }
//            long dif = (System.currentTimeMillis() - apertura.getTime()) / 1000;
//            String sapertura = "";
//            if (dif / (24 * 3600) >= 10) {
//                sapertura = Dates.getDateString(apertura, "MMM-yy");
//            }
//            Integer asignadas = (Integer) row[rinx++];
//            Integer vacantes = (Integer) row[rinx++];
//            RepoLevelData level = new RepoLevelData(new JSONObject());
//            level.level = nnivel.intValue();
//            level.id = nodo;
//            level.apertura = sapertura;
//            level.asignadas = asignadas.intValue();
//            level.vacantes = vacantes.intValue();
//            level.update();
//            data.levels[n] = level;
//            jlevels.put(level.json());
//        }
//        data.update(jlevels);
//        //000 servlet.setCache("cardreports", keyId, Utility.toBytes(data.json().toString()));
        return data;
    }

    private SRepoLevel[] getLevelsAll(java.util.Vector data2, String id, int nivel, String nodo, int max_nivel, Hashtable<String, RepoLevelData> data) {
        if (nivel > max_nivel) {
            return new SRepoLevel[0];
        }
        int size2 = data2.size();
        SRepoLevel levels[] = new SRepoLevel[0];
        int inx = 0;
        for (int i = 0; i < size2; i++) {
            int rinx = 0;
            Object row[] = (Object[]) data2.elementAt(i);
            Integer nnivel = (Integer) row[rinx++];
            String padre = (String) row[rinx++];
            if (!padre.equals(nodo) || nnivel != nivel) {
                continue;
            }
            SRepoLevel levels2[] = new SRepoLevel[levels.length + 1];
            System.arraycopy(levels, 0, levels2, 0, levels.length);
            levels = levels2;
            levels[inx] = new SRepoLevel();
            levels[inx].level = nnivel.intValue();
            levels[inx].id = (String) row[rinx++];
            levels[inx].name = (String) row[rinx++];
            levels[inx].desc = levels[inx].name;
            RepoLevelData data0 = data.get(Integer.toString(levels[inx].level) + "-" + levels[inx].id);
            if (data0 == null) {
                data0 = new RepoLevelData(new JSONObject());
            }
            levels[inx].data = new SRepoLevelData(levels[inx].level, levels[inx].id, data0.apertura, data0.asignadas, data0.vacantes);
            levels[inx].levels = getLevelsAll(data2, id, nivel + 1, levels[inx].id, max_nivel, data);
            ++inx;
        }
        return levels;
    }

    private SRepoLevel getLevelAll(String pool, String niveltable, String id, int nivel, String nodo, int max_nivel, Hashtable<String, RepoLevelData> data) {
        String where = "n.nivel_id = \'" + id + "\' and n.usada > 0";
        DB2CatList db_list2 = new DB2CatList(servlet.db(), pool,
                new String[]{"n.nivel", "n.nodo_padre", "n.nodo", "n.nombre"},
                new Class[]{Integer.class, String.class, String.class, String.class},
                niveltable + " n", where, "n.nivel, n.nodo_padre, n.orden, n.nombre, n.nodo");
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        for (int n = 0; n < size2; n++) {
            int rinx = 0;
            Object row[] = (Object[]) data2.elementAt(n);
            SRepoLevel level = new SRepoLevel();
            Integer nnivel = (Integer) row[rinx++];
            String padre = (String) row[rinx++];
            level.level = nnivel.intValue();
            level.id = (String) row[rinx++];
            RepoLevelData data0 = data.get(Integer.toString(level.level) + "-" + level.id);
            if (data0 == null) {
                data0 = new RepoLevelData(new JSONObject());
            }
            level.data = new SRepoLevelData(level.level, level.id, data0.apertura, data0.asignadas, data0.vacantes);
            if (nnivel == nivel) {
                if ((nodo == null && padre.length() == 0) || (nodo != null && level.id.equals(nodo))) {
                    level.name = (String) row[rinx++];
                    level.desc = level.name;
                    level.levels = getLevelsAll(data2, id, nivel + 1, level.id, max_nivel, data);
                    return level;
                }
            }
        }
        if (size2 == 0) {
            return getLevelAllCorp(pool, niveltable, id, nivel, nodo, max_nivel, data);
        }

        return null;
    }

    private SRepoLevel getLevelAllCorp(String pool, String niveltable, String id, int nivel, String nodo, int max_nivel, Hashtable<String, RepoLevelData> data) {
        String where = "n.nivel_id = \'" + id + "\'";
        DB2CatList db_list2 = new DB2CatList(servlet.db(), pool,
                new String[]{"n.nivel", "n.nodo_padre", "n.nodo", "n.nombre"},
                new Class[]{Integer.class, String.class, String.class, String.class},
                niveltable + " n", where, "n.nivel, n.nodo_padre, n.nodo");
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        for (int n = 0; n < size2; n++) {
            int rinx = 0;
            Object row[] = (Object[]) data2.elementAt(n);
            SRepoLevel level = new SRepoLevel();
            Integer nnivel = (Integer) row[rinx++];
            String padre = (String) row[rinx++];
            level.level = nnivel.intValue();
            level.id = (String) row[rinx++];
            RepoLevelData data0 = data.get(Integer.toString(level.level) + "-" + level.id);
            if (data0 == null) {
                data0 = new RepoLevelData(new JSONObject());
            }
            level.data = new SRepoLevelData(level.level, level.id, data0.apertura, data0.asignadas, data0.vacantes);
            if (nnivel == nivel) {
                if ((nodo == null && padre.length() == 0) || (nodo != null && level.id.equals(nodo))) {
                    level.name = (String) row[rinx++];
                    level.desc = level.name;
                    level.levels = getLevelsAll(data2, id, nivel + 1, level.id, max_nivel, data);
                    return level;
                }
            }
        }
        return null;
    }

    public static Report report(SReport sreport) {
        Report report = new Report(new JSONObject());
        report.id = sreport.id;
        report.idimage = sreport.idimage;
        report.pool = sreport.pool;
        report.pretab0 = sreport.pretab0;
        report.pretab1 = sreport.pretab1;

        report.name = sreport.name;
        report.desc = sreport.desc;
        report.columnFixed = sreport.columnFixed;
        report.attrs = sreport.attrs;
        report.edit = sreport.edit;
        report.filter = sreport.filter;
        report.tabHSize = sreport.tabHSize;
        report.treeHSize = sreport.treeHSize;
        report.dataId = sreport.datosId;
        int nsize = 0;
        for (int i = 0; i < sreport.levelIds.length; i++) {
            if (sreport.levelIds[i] == null || sreport.levelIds[i].length() == 0) {
                break;
            }
            nsize = i + 1;
        }
        report.levelNames = new String[nsize];
        report.levelIds = new String[nsize];
        report.levels = new RepoLevel[nsize];
        for (int i = 0; i < report.levelIds.length; i++) {
            report.levels[i] = new RepoLevel(new JSONObject());
            level(report.levels[i], sreport.levels[i]);
            report.levelNames[i] = sreport.levelNames[i];
            report.levelIds[i] = sreport.levelIds[i];
        }
        RepoView views[] = new RepoView[sreport.views.length];
        JSONArray oviews = new JSONArray();
        for (int i = 0; i < views.length; i++) {
            views[i] = new RepoView(new JSONObject());
            views[i].id = sreport.views[i].id;
            views[i].name = sreport.views[i].name;
            views[i].desc = sreport.views[i].desc;
            views[i].attrs = sreport.views[i].attrs;
            views[i].divide = sreport.views[i].divide;
            JSONArray ovcolumns = new JSONArray();
            views[i].tabcolumns = sreport.views[i].tabcolumns;
            for (int i0 = 0; i0 < views[i].tabcolumns.length; i0++) {
                ovcolumns.put(views[i].tabcolumns[i0]);
            }
            JSONArray ovrows = new JSONArray();
            views[i].tabrows = sreport.views[i].tabrows;
            for (int i0 = 0; i0 < views[i].tabrows.length; i0++) {
                ovrows.put(views[i].tabrows[i0]);
            }
            views[i].grppointsize = sreport.views[i].grppointsize;
            JSONArray ovgrppntnames = new JSONArray();
            views[i].grppntnames = sreport.views[i].grppntnames;
            for (int i0 = 0; i0 < views[i].grppntnames.length; i0++) {
                ovgrppntnames.put(views[i].grppntnames[i0]);
            }
            JSONArray ovgrppntrows = new JSONArray();
            views[i].grppntrows = sreport.views[i].grppntrows;
            for (int i0 = 0; i0 < views[i].grppntrows.length; i0++) {
                ovgrppntrows.put(views[i].grppntrows[i0]);
            }
            JSONArray ovgrppntcolumns = new JSONArray();
            views[i].grppntcolumns = sreport.views[i].grppntcolumns;
            for (int i0 = 0; i0 < views[i].grppntcolumns.length; i0++) {
                ovgrppntcolumns.put(views[i].grppntcolumns[i0]);
            }
            JSONArray ovgrppntrefs = new JSONArray();
            views[i].grppntrefs = sreport.views[i].grppntrefs;
            for (int i0 = 0; i0 < views[i].grppntrefs.length; i0++) {
                ovgrppntrefs.put(views[i].grppntrefs[i0]);
            }
            JSONArray ovgrppntoffsets = new JSONArray();
            views[i].grppntoffsets = sreport.views[i].grppntoffsets;
            for (int i0 = 0; i0 < views[i].grppntoffsets.length; i0++) {
                ovgrppntoffsets.put(views[i].grppntoffsets[i0]);
            }
            JSONArray ovgrppntescales = new JSONArray();
            views[i].grppntescales = sreport.views[i].grppntescales;
            for (int i0 = 0; i0 < views[i].grppntescales.length; i0++) {
                ovgrppntescales.put(views[i].grppntescales[i0]);
            }
            JSONArray ovgrppntcolors = new JSONArray();
            views[i].grppntcolors = sreport.views[i].grppntcolors;
            for (int i0 = 0; i0 < views[i].grppntcolors.length; i0++) {
                ovgrppntcolors.put(views[i].grppntcolors[i0]);
            }
            JSONArray ovgrpescales = new JSONArray();
            views[i].grpescales = sreport.views[i].grpescales;
            for (int i0 = 0; i0 < views[i].grpescales.length; i0++) {
                ovgrpescales.put(views[i].grpescales[i0]);
            }
            JSONArray ovgrpescalecolors = new JSONArray();
            views[i].grpescalecolors = sreport.views[i].grpescalecolors;
            for (int i0 = 0; i0 < views[i].grpescalecolors.length; i0++) {
                ovgrpescalecolors.put(views[i].grpescalecolors[i0]);
            }
            views[i].update(ovcolumns, ovrows,
                    ovgrppntnames, ovgrppntrows, ovgrppntcolumns,
                    ovgrppntrefs, ovgrppntoffsets, ovgrppntescales, ovgrppntcolors,
                    ovgrpescales, ovgrpescalecolors);
            oviews.put(views[i].json());
        }
        RepoPeriod periods[] = new RepoPeriod[sreport.periods.length];
        JSONArray operiods = new JSONArray();
        for (int i = 0; i < periods.length; i++) {
            periods[i] = new RepoPeriod(new JSONObject());
            periods[i].id = sreport.periods[i].id;
            periods[i].name = sreport.periods[i].name;
            periods[i].type = sreport.periods[i].type;
            periods[i].update();
            operiods.put(periods[i].json());
        }
        TableColumn tabcolumns[] = new TableColumn[sreport.tabcolumns.length];
        JSONArray otabcolumns = new JSONArray();
        for (int i = 0; i < tabcolumns.length; i++) {
            tabcolumns[i] = new TableColumn(new JSONObject());
            tabcolumns[i].id = sreport.tabcolumns[i].id;
            tabcolumns[i].name = sreport.tabcolumns[i].name;
            tabcolumns[i].subname = sreport.tabcolumns[i].subname;
            tabcolumns[i].subsubname = sreport.tabcolumns[i].subsubname;
            tabcolumns[i].tabWSize = sreport.tabcolumns[i].tabWSize;
            tabcolumns[i].bkcolor = sreport.tabcolumns[i].bkcolor;
            tabcolumns[i].color = sreport.tabcolumns[i].color;
            tabcolumns[i].attrs = sreport.tabcolumns[i].attrs;
            tabcolumns[i].type = sreport.tabcolumns[i].type;
            tabcolumns[i].desc = sreport.tabcolumns[i].desc;
            tabcolumns[i].update();
            otabcolumns.put(tabcolumns[i].json());
        }
        TableRow tabrows[] = new TableRow[sreport.tabrows.length];
        JSONArray otabrows = new JSONArray();
        for (int i = 0; i < tabrows.length; i++) {
            tabrows[i] = new TableRow(new JSONObject());
            tabrows[i].id = sreport.tabrows[i].id;
            tabrows[i].name = sreport.tabrows[i].name;
            tabrows[i].subname = sreport.tabrows[i].subname;
            tabrows[i].subsubname = sreport.tabrows[i].subsubname;
            tabrows[i].tabHSize = sreport.tabrows[i].tabHSize;
            tabrows[i].bkcolor = sreport.tabrows[i].bkcolor;
            tabrows[i].color = sreport.tabrows[i].color;
            tabrows[i].attrs = sreport.tabrows[i].attrs;
            tabrows[i].type = sreport.tabrows[i].type;
            tabrows[i].desc = sreport.tabrows[i].desc;
            tabrows[i].update();
            otabrows.put(tabrows[i].json());
        }

        JSONArray levelIds = new JSONArray();
        JSONArray levelNames = new JSONArray();
        JSONArray levels = new JSONArray();
        for (int i = 0; i < report.levels.length; i++) {
            levelNames.put(report.levelNames[i]);
            levelIds.put(report.levelIds[i]);
            levels.put(report.levels[i].json());
        }
        report.update(levelIds, levelNames, levels, oviews, operiods, otabcolumns, otabrows);
        return report;
    }

    private static void level(RepoLevel levels, SRepoLevel slevel) {
        if (slevel == null) {
            return;
        }
        levels.level = slevel.level;
        levels.id = slevel.id;
        levels.name = slevel.name;
        levels.desc = slevel.desc;
        levels.data = new RepoLevelData(new JSONObject());
        levels.data.level = slevel.level;
        levels.data.id = slevel.id;
        levels.data.apertura = slevel.data.apertura;
        levels.data.asignadas = slevel.data.asignadas;
        levels.data.vacantes = slevel.data.vacantes;
        levels.data.update();
        levels.levels = new RepoLevel[slevel.levels.length];
        JSONArray olevelslevels = new JSONArray();
        levels.update(olevelslevels);
        for (int i = 0; i < slevel.levels.length; i++) {
            levels.levels[i] = new RepoLevel(new JSONObject());
            level(levels.levels[i], slevel.levels[i]);
            olevelslevels.put(levels.levels[i].json());
        }
        levels.update(olevelslevels);
    }

    public Cards getReport(String pool, String profile, String level, String levelvalue) {
        String[] result = get(pool, profile, level, levelvalue);
        if (result == null) {
            return null;
        }
        JSONObject response = new JSONObject(new JSONTokener(result[0]));
        Cards report = new Cards(response);
        return report;
    }

    private RepoLevel found(Report reporte, RepoLevel level, String levelvalue, boolean last, boolean equal) {
        for (int i = 0; i < level.levels.length; i++) {
            if (level.levels[i] == null) {
                continue;
            }
            if ((level.levels[i].levels.length > 0 && !last) || (level.levels[i].levels.length == 0 && last)) {
//System.out.println(level.levels[i].id+" "+level.levels[i].name+" x "+levelvalue+" "+equal);
                if (equal) {
                    if (level.levels[i].name.equals(levelvalue) || level.levels[i].id.equals(levelvalue)) {
                        return level.levels[i];
                    }
                } else {
                    if (level.levels[i].name.indexOf(levelvalue) >= 0 || level.levels[i].id.endsWith(levelvalue)) {
                        return level.levels[i];
                    }
                }
            }
            RepoLevel ilevel = found(reporte, level.levels[i], levelvalue, last, equal);
            if (ilevel != null) {
                return ilevel;
            }
        }
        return null;
    }

    private Report updateReport(String jobject, String profile, String level, String levelvalue) {
        Report reporte = new Report(new JSONObject(new JSONTokener(jobject)));
        if (!BECardSuc.isNull(level)) {
            boolean ok = false;
            RepoLevel nlevel = found(reporte, reporte.levels[0], levelvalue, level.equals("SUCURSAL"), true);
            if (nlevel == null) {
                nlevel = found(reporte, reporte.levels[0], levelvalue, level.equals("SUCURSAL"), false);
                if (nlevel != null && level.equals("SUCURSAL")) {
                    if (nlevel.levels.length > 0) {
                        nlevel = null;
                    }
                }
            }

            if (nlevel != null) {
                reporte.levels[0] = nlevel;
                reporte.updateLevels();
                ok = true;
            }
            if (!ok) {
                reporte.levels[0] = new RepoLevel(new JSONObject());
                level(reporte.levels[0], new SRepoLevel(0, "NA", "NA", "NA", new SRepoLevelData(), new SRepoLevel[0]));
                reporte.updateLevels();
            }
        }
        return reporte;
    }

    public String[] get(String pool, String profile, String level, String levelvalue) {
        String reportId = "card";
        Report report = null;
        Object[] xreport = getReporteDef(new Object[]{reportId, "-", "-"}, new String[]{"properties"}, new Class[]{byte.class});
        if (xreport != null && xreport.length > 0 && xreport[0] != null) {
            byte[] bgresult = (byte[]) xreport[0];
            if (bgresult != null && bgresult.length > 4) {
                String result = Utility.toString(bgresult);
                JSONObject jobj = new JSONObject(new JSONTokener(result));
                report = new Report(jobj);
            }
        }
        if (report == null) {
            SReport sreport = getReporte(pool, level, levelvalue);
            if (sreport == null) {
                return null;
            }
            report = report(sreport);
            String rresult = report.json().toString();
            byte[] rbresult = Utility.toBytes(rresult);
            if (xreport == null) {
                insertReporteDef(new Object[]{reportId, "-", "-"}, new String[]{"properties"}, new Class[]{byte.class}, new Object[]{rbresult});
            } else {
                updateReporteDef(new Object[]{reportId, "-", "-"}, new String[]{"properties"}, new Class[]{byte.class}, new Object[]{rbresult});
            }
        }
        report = updateReport(report.json().toString(), profile, level, levelvalue);
        Cards cards = new Cards(new JSONObject());
        cards.report = report;
        cards.updateNoData();
        String result = cards.json().toString();
        byte[] bresult = Utility.toBytes(result);
        return new String[]{result, null, null};
    }
}
