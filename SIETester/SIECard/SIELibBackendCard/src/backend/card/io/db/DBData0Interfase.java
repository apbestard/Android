/*
 Derechos Reservados (c)
 Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
 2009, 2010, 2011, 2012, 2013, 2014
 logipax Marca Registrada (R)  2003, 2014
 */
package backend.card.io.db;

import backend.BEServletInterfase;
import mx.logipax.shared.objects.viewer.TableColumn;
import mx.logipax.shared.objects.viewer.TableRow;

public class DBData0Interfase {

    private final DBOPERMEDDataInterfase OPERMED;

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

    private java.util.Map<Integer, Double> diasFeriados;
    private java.util.Map<Integer, Double[]> factorDiario;
 
    public DBData0Interfase(BEServletInterfase servlet, int corporate) {
        OPERMED = new DBOPERMEDDataInterfase(servlet, corporate);
//        diasFeriados = DBDataUtility.getDiasFeriados(servlet, "local");
//        factorDiario = DBDataUtility.getFactorDiario(servlet, "local");
    }

    public java.util.Map<Integer, Double> diasFeriados() {
        return diasFeriados;
    }
    public java.util.Map<Integer, Double[]> factorDiario() {
        return factorDiario;
    }

    public static Double prc(Double ref, Double totl) {
        if (totl.doubleValue() == 0) {
            return new Double(0);
        }
        return new Double(ref.doubleValue() * 100.0 / totl.doubleValue());

    }

    public static Double avg(Double mnto, Integer num) {
        if (num.intValue() == 0) {
            return mnto;
        }
        return new Double(1.0 * mnto.doubleValue() / num.intValue());

    }

    public DataReport getData(String pool, String pretabl0, String pretabl1, String tables[], String dataId, String extId, String nivel0Id, int nivel0No, String nodo0Id, String nivel1Id, int nivel1No, String nodo1Id, String periodoId, TableRow[] rows, int columnFixed, TableColumn[] columns, String attrs) {
        DataReport data = null;
        if (data == null) {
             data = OPERMED.getData(pool, pretabl0, pretabl1, tables, dataId, extId, nivel0Id, nivel0No, nodo0Id, nivel1Id, nivel1No, nodo1Id, periodoId, rows, columnFixed, columns);
        }
        return data;
    }
}
