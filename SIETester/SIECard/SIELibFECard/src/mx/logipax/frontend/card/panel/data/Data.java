/*
 Derechos Reservados (c)
 Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
 2009, 2010, 2011, 2012, 2013, 2014
 logipax Marca Registrada (R)  2003, 2014
 */
package mx.logipax.frontend.card.panel.data;

import mx.logipax.shared.objects.RepoLevel;
import mx.logipax.shared.objects.viewer.ObjectClass;
import mx.logipax.shared.objects.viewer.Report;
import mx.logipax.shared.objects.viewer.ReportList;
import mx.logipax.shared.objects.viewer.ReportListNode;
import mx.logipax.shared.objects.viewer.ReportListRow;
import mx.logipax.shared.objects.viewer.TableColumn;
import mx.logipax.shared.objects.viewer.TableRow;
import mx.logipax.utility.Arrays2;
import mx.logipax.utility.Comparator;
import mx.logipax.utility.Dates;
import mx.logipax.utility.Sort;
import mx.logipax.utility.Strings;
import mx.logipax.utility.Utility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Data {

    public static class DataPeriodRow {

        public Object[] columns;
    }

    public static class DataPeriod {

        public int periodInx;
        public int dateinx;
        public DataPeriodRow[] rows;
    }

    public static class DataProduct {

        public String product;
        public int periodMinInx;
        public int periodMaxInx;
        public java.util.Hashtable<Integer, DataPeriod> hperiods;
    }

    public static class DataReport {

        public String node;
        public String period;
        public int rankRows;
        public String ids[];
        public String names[];
        public String parents[];
        public java.util.Hashtable<String, DataProduct> hproducts;
    }

    private static int especialidadMax = 27;
    private static int[] especialidades = new int[]{2, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 2, 1, 1, 2, 2, 2, 2, 1, 2, 2, 2, 2, 1, 2, 2};
    private static int[] especialidadesInx = new int[]{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, -1, 14, -1, 15, 16, 17, 18, 19, 20, 21, 22, -1, -1, -1, -1, 23, 24, 25, 26};
    private static int segmentoMax = 6;
    private static int[] segmentosInx = new int[]{0, 1, 2, 3, 4, 5};

    private Report report;
    private JSONObject card;

    public Data(Report report, JSONObject card) {
        this.report = report;
        this.card = card;
    }

    public ReportList getDataReportList(String extId, 
            String level01No, String level01Id) {
        if (extId == null) {
            extId = "";
        }
        if (extId.equals("4")) {
            extId = "0";
        }
        String nodes0No[] = Strings.tokenizer(level01No, "|");
        String nodes0Id[] = Strings.tokenizer(level01Id, "|");
        if (nodes0Id == null || nodes0Id.length == 0) {
            nodes0Id = new String[]{"0"};
        }
        if (nodes0No.length != nodes0Id.length) {
            return null;
        }
        int[] nnode0No = new int[nodes0No.length];
        for (int i0 = 0; i0 < nodes0No.length; i0++) {
            nnode0No[i0] = Strings.getInt(nodes0No[i0]);
        }
        DataReport rdata = getData(extId, nnode0No, nodes0Id, 
                                report.tabrows, report.columnFixed, report.tabcolumns, report.attrs);
        ReportList data = getDataList(extId, nodes0Id[0], rdata, report);
        return new ReportList(data.json());
    }

    private RepoLevel getLevels(String extId, int[] nivel0No, String[] nodo0Id) {
        if (extId.equals("1")) {
            return report.levels[0];
        } else if (extId.equals("2")) {
            return report.levels[1];
        } else if (extId.equals("3")) {
            return report.levels[2];
        }
        return null;
    }

    public DataReport getData(String extId, int[] nivel0No, String[] nodo0Id, TableRow[] rows, int columnFixed, TableColumn[] columns, String attrs) {
        String[] names = new String[0];
        int periodMinInx = 0;
        int periodMaxInx = 12;
        double[][] datas = new double[0][columns.length - columnFixed];
        RepoLevel level = getLevels(extId, nivel0No, nodo0Id);
        if (level != null) {
            names = new String[level.levels.length];
            for (int i = 0; i < names.length; i++) {
                names[i] = level.levels[i].name;
            }
        }
        if (extId.equals("1")) {
//            datas = new double[names.length][columns.length - columnFixed];
//            for (int i0 = 0; i0 < card.datas.length; i0++) {
//                for (int i1 = 0; i1 < card.datas[i0].montos.length; i1++) {
//                    for (int i2 = 0; i2 < card.datas[i0].montos[i1].length; i2++) {
//                        for (int i3 = 0; i3 < card.datas[i0].montos[i1][i2].length; i3++) {
//                            datas[i0][i3] += card.datas[i0].montos[i1][i2][i3];
//                        }
//                    }
//                }
//            }
        } else if (extId.equals("2")) {
        } else if (extId.equals("3")) {
        } else {
//            names = new String[]{"MONTO", "VOLUMEN"};
//            datas = new double[names.length][columns.length - columnFixed];
//            for (int i0 = 0; i0 < card.datas.length; i0++) {
//                for (int i1 = 0; i1 < card.datas[i0].montos.length; i1++) {
//                    for (int i2 = 0; i2 < card.datas[i0].montos[i1].length; i2++) {
//                        for (int i3 = 0; i3 < card.datas[i0].montos[i1][i2].length; i3++) {
//                            datas[0][i3] = card.datas[i0].montos[i1][i2][i3];
//                        }
//                    }
//                }
//                for (int i1 = 0; i1 < card.datas[i0].volumenes.length; i1++) {
//                    for (int i2 = 0; i2 < card.datas[i0].volumenes[i1].length; i2++) {
//                        for (int i3 = 0; i3 < card.datas[i0].volumenes[i1][i2].length; i3++) {
//                            datas[1][i3] = card.datas[i0].volumenes[i1][i2][i3];
//                        }
//                    }
//                }
//            }
        }
        return getData(names, periodMinInx, periodMaxInx, datas);
    }

    public DataReport getData(String[] names, int periodMinInx, int periodMaxInx, double[][] datas) {
        DataReport data = new DataReport();
        data.node = "";
        data.period = "";
        data.hproducts = new java.util.Hashtable();
        data.rankRows = names.length;
        data.ids = new String[names.length];
        data.names = new String[names.length];
        data.parents = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            data.ids[i] = names[i];
            data.names[i] = names[i];
            data.parents[i] = "";
            DataProduct dprod = new DataProduct();
            dprod.product = "";
            dprod.periodMinInx = periodMinInx;
            dprod.periodMaxInx = periodMaxInx;
            dprod.hperiods = new java.util.Hashtable();
            for (int ii = dprod.periodMinInx; ii < dprod.periodMaxInx; ii++) {
                DataPeriod period = new DataPeriod();
                period.dateinx = ii;
                period.periodInx = ii;
                period.rows = new DataPeriodRow[datas.length];
                for (int r = 0; r < datas.length; r++) {
                    period.rows[r] = new DataPeriodRow();
                    period.rows[r].columns = new Object[datas[r].length];
                    for (int c = 0; c < datas[r].length; c++) {
                        period.rows[r].columns[c] = new Double(datas[r][c]);
                    }
                }
                dprod.hperiods.put(new Integer(ii), period);
            }
            data.hproducts.put(data.ids[i], dprod);
        }
        return data;
    }

    public void dispose() {
    }
    
    private ReportList getDataList(String extId, String levelId, DataReport rdata, Report sreport) {
        ReportList data = new ReportList(new JSONObject());
        data.id = sreport.id;
        data.extId = extId;
        data.level = levelId;
        data.period = "";
        data.from = 1;
        data.size = 12;
        JSONArray otabnames = new JSONArray();
        if (rdata.names != null) {
            for (int n = 0; n < rdata.names.length; n++) {
                otabnames.put(rdata.ids[n] + "|" + rdata.names[n] + "|" + rdata.parents[n]);
            }
        }
        java.util.Set set01 = rdata.hproducts.entrySet();
        java.util.Iterator it01 = set01.iterator();
        JSONArray otabnode = new JSONArray();
        while (it01.hasNext()) {
            java.util.Map.Entry entry01 = (java.util.Map.Entry) it01.next();
            //System.out.println(entry01.getKey() + " : " + entry01.getValue());
            DataProduct node01 = (DataProduct) entry01.getValue();
            ReportListNode rnode01 = new ReportListNode(new JSONObject());
            rnode01.id = node01.product;
            rnode01.from = node01.periodMinInx;
            rnode01.size = (node01.periodMaxInx - node01.periodMinInx) + 1;
            JSONArray otabrow = new JSONArray();
            java.util.Set set02 = node01.hperiods.entrySet();

            DataPeriod[] datas = new DataPeriod[node01.hperiods.size()];
            java.util.Iterator it02x = set02.iterator();
            int inx = 0;
            while (it02x.hasNext()) {
                java.util.Map.Entry entry02 = (java.util.Map.Entry) it02x.next();
                //System.out.println(entry02.getKey() + " : " + entry02.getValue());
                datas[inx++] = (DataPeriod) entry02.getValue();
            }
            sortvalues(datas);
            for (int ii = 0; ii < datas.length; ii++) {
//            java.util.Iterator it02 = set02.iterator();
//            while (it02.hasNext()) {
//                java.util.Map.Entry entry02 = (java.util.Map.Entry) it02.next();
//                //System.out.println(entry02.getKey() + " : " + entry02.getValue());
//                DataPeriod node02 = (DataPeriod) entry02.getValue();
                DataPeriod node02 = datas[ii];
                ReportListRow rrow01 = new ReportListRow(new JSONObject());
                rrow01.index = node02.periodInx;
                rrow01.dateinx = node02.dateinx;
                rrow01.rows = node02.rows.length;
                rrow01.cols = 0;
                if (rrow01.rows > 0) {
                    rrow01.cols = node02.rows[0].columns.length;
                }
                boolean zero = true;
                JSONArray otabvalues = new JSONArray();
                for (int r = 0; r < node02.rows.length; r++) {
                    for (int c = 0; c < node02.rows[r].columns.length; c++) {
                        otabvalues.put(node02.rows[r].columns[c]);
                        if (node02.rows[r].columns[c] != null && node02.rows[r].columns[c] instanceof Double
                                && ((Double) node02.rows[r].columns[c]).doubleValue() != 0) {
                            zero = false;
                        }
                    }
                }
                if (zero) {
                    rrow01.update();
                } else {
                    rrow01.update(otabvalues);
                }
                otabrow.put(rrow01.json());
            }
            rnode01.update(otabrow);
            otabnode.put(rnode01.json());
        }
        data.update(otabnames, otabnode);
        return data;
    }

    private void sortvalues(DataPeriod[] values) {
        final int sortOrder = -1;
        if ((values.length >= 0)) {
            Sort.sort(values, 0, values.length, new Comparator() {
                public int compare(Object o1, Object o2) {
                    DataPeriod v1 = (DataPeriod) o1;
                    DataPeriod v2 = (DataPeriod) o2;
                    Object obj1 = v1.periodInx;
                    Object obj2 = v2.periodInx;
                    if (obj1 == null) {
                        return sortOrder * 1;
                    }
                    if (obj2 == null) {
                        return sortOrder * -1;
                    }
                    if ((obj1 instanceof String) && (obj2 instanceof String)) {
                        return sortOrder * (((String) obj1).compareTo((String) obj2));
                    } else if ((obj1 instanceof Integer) || (obj2 instanceof Integer)) {
                        if ((obj1 instanceof Integer) && (obj2 instanceof Integer)) {
                            return sortOrder * (((Integer) obj1).intValue() - ((Integer) obj2).intValue());
                        }
                        if (obj1 instanceof Integer) {
                            return sortOrder * -1;
                        } else {
                            return sortOrder * 1;
                        }
                    } else if ((obj1 instanceof Double) || (obj2 instanceof Double)) {
                        double dif = 0;
                        int ndif = 0;
                        if ((obj1 instanceof Double) && (obj2 instanceof Double)) {
                            dif = ((Double) obj1).doubleValue() - ((Double) obj2).doubleValue();
                            if (dif > 0.00001) {
                                ndif = 1;
                            } else if (dif < -0.00001) {
                                ndif = -1;
                            } else {
                                ndif = 0;
                            }
                        } else if (obj1 instanceof Double) {
                            dif = -1;
                        } else {
                            dif = 1;
                        }
                        int result = sortOrder * ndif;
                        //System.out.println("compare 1:" + obj1.toString() + " 2:" + obj2.toString() + " " + result);
                        return result;
                    }
                    return 0;
                }
            });
        }

    }

    private String listId(String[] list) {
        String id = "";
        for (int i = 0; i < list.length; i++) {
            if (id.length() > 0) {
                id += "|";
            }
            id += list[i];
        }
        return id;
    }

    private static String list(String names[]) {
        String str = "";
        if (names == null) {
            return str;
        }
        for (int i = 0; i < names.length; i++) {
            if (str.length() > 0) {
                str += "|";
            }
            str += names[i];
        }
        return str;
    }

    private byte[] getAllData(int n0, int n1, int n2, byte[][][][] datas) {
        if (n0 == 1 && n1 == 1 && n2 == 1) {
            return datas[0][0][0];
        }
        int namessize = 0;
        ReportList data = null;
        for (int i0 = 0; i0 < n0; i0++) {
            for (int i1 = 0; i1 < n1; i1++) {
                for (int i2 = 0; i2 < n2; i2++) {
                    String jsresult = Utility.toString(datas[i0][i1][i2]);
                    JSONObject jdata = new JSONObject(new JSONTokener(jsresult));
                    ReportList data2 = new ReportList(jdata);
                    if (data == null) {
                        data = data2;
                        namessize = data.names.length;
                    } else {
                        data = joinReportList(data, data2);
                    }
                }
            }
        }
        if (namessize != data.names.length) {
            int num = 0;
            String snum = null;
            for (int n = 0; n < data.names.length; n++) {
                String[] name = Strings.tokenizer(data.names[n], "|");
                if (name.length >= 3) {
                    if (snum == null || !snum.equals(name[2])) {
                        snum = name[2];
                        ++num;
                    }
                    name[1] += "/" + Integer.toString(num);
                }
                data.names[n] = list(name);
            }
        }
        JSONArray otabnames = new JSONArray();
        for (int n = 0; n < data.names.length; n++) {
            otabnames.put(data.names[n]);
        }
        JSONArray otabnode = new JSONArray();
        for (int i0 = 0; i0 < data.values.length; i0++) {
            ReportListNode rnode01 = data.values[i0];
            JSONArray otabrow = new JSONArray();
            for (int i1 = 0; i1 < data.values[i0].values.length; i1++) {
                ReportListRow row = data.values[i0].values[i1];
                JSONArray otabvalues = new JSONArray();
                boolean zero = true;
                for (int r = 0; r < data.values[i0].values[i1].values.length; r++) {
                    for (int c = 0; c < data.values[i0].values[i1].values[r].length; c++) {
                        if (data.values[i0].values[i1].values.length != data.values[i0].values[0].rows) {
                            System.err.println("--rows");
                        }
                        if (data.values[i0].values[i1].values[0].length != data.values[i0].values[0].cols) {
                            System.err.println("--cols");
                        }
                        otabvalues.put(data.values[i0].values[i1].values[r][c]);

                        if (data.values[i0].values[i1].values[r][c] != 0) {
                            zero = false;
                        }
                    }
                }
                if (zero) {
                    row.update();
                } else {
                    row.update(otabvalues);
                }
                otabrow.put(row.json());
            }
            rnode01.update(otabrow);
            otabnode.put(rnode01.json());
        }
        data.update(otabnames, otabnode);
        String result = data.json().toString();
        byte[] zresult = Utility.toBytes(result);
        return zresult;
    }

    private ReportList joinReportList(ReportList data, ReportList data2) {
        boolean changed = false;
        int[] namesmap = new int[data.names.length];
        String dataNameIds[] = new String[data.names.length];
        for (int i = 0; i < namesmap.length; i++) {
            namesmap[i] = -1;
            String[] names = Strings.tokenizer(data.names[i], "|");
            if (names.length > 1) {
                dataNameIds[i] = names[1];
            } else {
                dataNameIds[i] = "";
            }
        }
        for (int i = 0; i < data2.names.length; i++) {
            String data2NameId = "";
            String[] names = Strings.tokenizer(data2.names[i], "|");
            if (names.length > 1) {
                data2NameId = names[1];
            } else {
                data2NameId = "";
            }
            boolean found = false;
            for (int ii = 0; ii < dataNameIds.length; ii++) {
                if (data2NameId.equals(dataNameIds[ii])) {
                    namesmap[ii] = i;
                    found = true;
                    break;
                }
            }
            if (!found) {
                changed = true;
                namesmap = Arrays2.append(namesmap, i);
                data.names = Arrays2.append(data.names, data2.names[i]);
                String[] name2s = Strings.tokenizer(data2.names[i], "|");
                if (name2s.length == 0) {
                    name2s = new String[]{""};
                }
                dataNameIds = Arrays2.append(dataNameIds, data2NameId);
                for (int i0 = 0; i0 < data.values.length; i0++) {
                    for (int i1 = 0; i1 < data.values[i0].values.length; i1++) {
                        double[][] values = new double[data.values[i0].values[i1].rows + 1][data.values[i0].values[i1].cols];
                        for (int in = 0; in < data.values[i0].values[i1].values.length; in++) {
                            System.arraycopy(data.values[i0].values[i1].values[in], 0, values[in], 0, data.values[i0].values[i1].values[in].length);
                        }
                        ++data.values[i0].values[i1].rows;
                        data.values[i0].values[i1].values = values;
                    }
                }
            }
        }
        //validate(data);
        if (data.values.length != data.values.length) {
            System.err.println("not equal 1");
            return data;
        }
        if (data.values.length > 0) {
            if (data.values[0].values.length != data.values[0].values.length) {
                System.err.println("not equal 2");
                return data;
            }
        }
        for (int i0 = 0; i0 < data.values.length; i0++) {
            int dateinx = data.values[i0].values[0].dateinx;
            //int ndateinx = dateinx - data.values[i0].values.length;
            int ndateinx = data.values[i0].values[data.values[i0].values.length - 1].dateinx;
            int date2inx = data2.values[i0].values[0].dateinx;
            //int ndate2inx = date2inx - data2.values[i0].values.length;
            int ndate2inx = data2.values[i0].values[data2.values[i0].values.length - 1].dateinx;
            int first = 0;
            int firstofs = 0;
            int last = 0;
            if (dateinx < date2inx) {
                first = date2inx - dateinx;
                if (data.period.equals(ObjectClass.MENSUAL0)) {
                    String dateStr = Dates.getDateString(Dates.toLong(dateinx));
                    int year = Dates.getYear(Dates.toLong(dateinx));
                    int month = Dates.getMonth(Dates.toLong(dateinx));
                    String date2Str = Dates.getDateString(Dates.toLong(date2inx));
                    int year2 = Dates.getYear(Dates.toLong(date2inx));
                    int month2 = Dates.getMonth(Dates.toLong(date2inx));
                    first = (year2 - year) * 12 + month2 - month;
                }
                firstofs = 0;
            } else {
                firstofs = dateinx - date2inx;
                if (data.period.equals(ObjectClass.MENSUAL0)) {
                    int year = Dates.getYear(Dates.toLong(dateinx));
                    int month = Dates.getMonth(Dates.toLong(dateinx));
                    int year2 = Dates.getYear(Dates.toLong(date2inx));
                    int month2 = Dates.getMonth(Dates.toLong(date2inx));
                    firstofs = (year - year2) * 12 + month - month2;
                }
            }
            if (ndateinx > ndate2inx) {
                last = ndateinx - ndate2inx;
                if (data.period.equals(ObjectClass.MENSUAL0)) {
                    int year = Dates.getYear(Dates.toLong(ndateinx));
                    int month = Dates.getMonth(Dates.toLong(ndateinx));
                    int year2 = Dates.getYear(Dates.toLong(ndate2inx));
                    int month2 = Dates.getMonth(Dates.toLong(ndate2inx));
                    last = (year - year2) * 12 + month - month2;
                }
            }
            ReportListRow values[] = new ReportListRow[first + data.values[i0].values.length + last];
            for (int i = 0; i < first; i++) {
                values[i] = new ReportListRow(new JSONObject());
                if (i >= data2.values[i0].values.length) {
                    values[i].index = values[i - 1].index - 1;
                    values[i].dateinx = values[i - 1].dateinx - 1;
                    if (data.period.equals(ObjectClass.MENSUAL0)) {
                        values[i].dateinx = values[i - 1].dateinx - Dates.getDaysOfMonth(Dates.toLong(values[i].dateinx));
                        values[i].index = values[i].dateinx;
                    }
//                    System.out.println(Dates.getDateString(Dates.toLong(values[i-1].dateinx))+
//                            " "+
//                            Dates.getDateString(Dates.toLong(values[i].dateinx)));
                } else {
                    values[i].index = data2.values[i0].values[i].index;
                    values[i].dateinx = data2.values[i0].values[i].dateinx;
                }
                values[i].rows = data.values[i0].values[0].rows;
                values[i].cols = data.values[i0].values[0].cols;
                values[i].values = new double[values[i].rows][values[i].cols];
            }
            System.arraycopy(data.values[i0].values, 0, values, first, data.values[i0].values.length);
            for (int ii = 0; ii < last; ii++) {
                int i = values.length - ii - 1;
                int i2 = data2.values[i0].values.length - ii - 1;
                values[i] = new ReportListRow(new JSONObject());
                values[i].index = data2.values[i0].values[i2].index;
                values[i].dateinx = data2.values[i0].values[i2].dateinx;
                values[i].rows = data.values[i0].values[0].rows;
                values[i].cols = data.values[i0].values[0].cols;
                values[i].values = new double[values[i].rows][values[i].cols];
            }

//            for (int i = 0; i < values.length; i++) {
//                if (values[i] == null) {
//                    values[i] = new ReportListRow(data.values[i0].values[0].json());
//                    for (int n0 = 0; n0 < values[i].values.length; n0++) {
//                        for (int n1 = 0; n1 < values[i].values[n0].length; n1++) {
//                            values[i].values[n0][n1] = 0.0;
//                        }
//                    }
//                }
//            }
            data.values[i0].size = values.length;
            data.values[i0].values = values;
            //validate(data);
            for (int i1 = data2.values[i0].values.length - 1; i1 >= 0; i1--) {
                int i1x = i1 + firstofs;
                if (i1x >= data.values[i0].values.length) {
                    continue;
                }
//                if (data.values[i0].values[i1x].dateinx != data2.values[i0].values[i1].dateinx) {
//                    System.out.println(Dates.getDateString(Dates.toLong(data.values[i0].values[i1x].dateinx))
//                            + " "
//                            + Dates.getDateString(Dates.toLong(data2.values[i0].values[i1].dateinx)));
//                }
                for (int i2 = 0; i2 < data.values[i0].values[i1x].values.length; i2++) {
                    int i2x = namesmap[i2];
                    if (i2x < 0) {
                        continue;
                    }
                    for (int i3 = 0; i3 < data.values[i0].values[i1x].values[i2].length; i3++) {
                        data.values[i0].values[i1x].values[i2][i3] += data2.values[i0].values[i1].values[i2x][i3];
                    }
                }
//                JSONArray otabvalues = new JSONArray();
//                for (int r = 0; r < data.values[i0].values[i1].values.length; r++) {
//                    for (int c = 0; c < data.values[i0].values[i1].values[r].length; c++) {
//                        otabvalues.put(data.values[i0].values[i1].values[r][c]);
//                    }
//                }
//                data.values[i0].values[i1].update(otabvalues);
            }
        }
        //validate(data);

//        for (int i = data.values.length - 1; i >= 0; i--) {
//            for (int ii = data.values[i].values.length - 1; ii >= 0; ii--) {
//                for (int iii = 0;iii<data.values[i].values[ii].values.length; iii++) {
//                    System.out.println("bk1 vars=" + ii + " " + iii + " " + Dates.getDateString(Dates.toLong(data.values[i].values[ii].dateinx)) + " " + data.values[i].values[ii].values[iii][0]);
//                }
//            }
//        }
        return data;
    }

}
