/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.io.db;

import backend.card.io.db.NivelName;
import backend.BEServletInterfase;
import backend.card.def.vwr.cat.DefDBVNivel;
import backend.catalogs2.db.DB2CatList;
import backend.card.io.db.DBData0Interfase;
import backend.card.io.db.DBData0Interfase.DataPeriod;
import backend.card.io.db.DBData0Interfase.DataProduct;
import backend.card.io.db.DBData0Interfase.DataReport;
import java.sql.Connection;
import java.util.Map;
import mx.logipax.shared.DBDefaults;
import mx.logipax.shared.objects.viewer.TableColumn;
import mx.logipax.shared.objects.viewer.TableRow;
import mx.logipax.utility.Arrays2;
import mx.logipax.utility.Dates;
import mx.logipax.utility.Strings;

public class DBOPERMEDDataInterfase  {

    BEServletInterfase servlet;
    int corporate;
    String niveltable = DefDBVNivel.tableTagName20;     String xtables[];

    public DBOPERMEDDataInterfase(BEServletInterfase servlet, int corporate) {
        this.servlet = servlet;
        this.corporate = corporate;
    }

    public String[] getCardList() {
        return new String[]{""};
    }
    public boolean prevCardReading() {
        return true;
    }
    public void setPrevCardReading(Object[] row, int rinx) {
    }
    public Double[] getCardValues(Object[] row, int rinx) {
        return new Double[]{};
    }
    
    public DataPeriod updateCardData(int id, DataPeriod period, Integer periodo_inx, int rowIndex,
            Double[] values,
            Map<Integer, Double[]> presupuesto) {
        int inx = 0;
        Double invabono = values[inx++];
        Double invcargo = values[inx++];
        Double ahoabono = values[inx++];
        Double ahocargo = values[inx++];
        Double[] data = new Double[]{
            new Double(invabono - invcargo + ahoabono - ahocargo),
            invabono,
            invcargo, new Double(invabono - invcargo),
            ahoabono,
            ahocargo, new Double(ahoabono - ahocargo)};
        boolean[] reset = new boolean[data.length];
        int ninx = 0;
        if (rowIndex >= period.rows.length) {
            System.err.println("data size rows");
            rowIndex = period.rows.length - 1;
        }
        if (rowIndex < 0) {
            System.err.println("data no rows");
            return period;
        }
        for (int i = 0; i < data.length; i++) {
            if (reset[i] || period.rows[rowIndex].columns[i] == null) {
                period.rows[rowIndex].columns[i] = data[ninx++];
            } else if (period.rows[rowIndex].columns[i] instanceof Double) {
                period.rows[rowIndex].columns[i] = new Double((Double) period.rows[rowIndex].columns[i] + data[ninx++]);
            }
        }
        return period;
    }

//    public DataPeriod updateCardData(int id, DataPeriod period, Object[][] row, DataProduct product, Integer periodo_inx, int rowIndex, Map<Integer, Double[]> presupuesto) {
//        return null;
//    }
//
//    public DataPeriod updateCardData(int id, DataPeriod period, Object[] row, DataProduct product, Integer periodo_inx, int rowIndex, Map<Integer, Double[]> presupuesto) {
//        Integer nodo_level = interfase.getNodoLevel(id, row);
//        String nodo_id = interfase.getNodoId(id, row);
//        String nodo_name = interfase.getNodoName(id, row);
//        java.util.Date fecha = interfase.getFecha(id, row);
//        periodo_inx = new Integer(Dates.toInt(fecha.getTime()));
//        periodo_inx = new Integer(Dates.toInt(fecha.getTime()));
//        if (product.periodMaxInx < 0) {
//            product.periodMaxInx = periodo_inx;
//            product.periodMinInx = periodo_inx;
//        }
//        if (product.periodMaxInx < periodo_inx) {
//            product.periodMaxInx = periodo_inx;
//        }
//        if (product.periodMinInx > periodo_inx) {
//            product.periodMinInx = periodo_inx;
//        }
//        Double invabono = new Double(0);
//        Double invcargo = new Double(0);
//        Double ahoabono = new Double(0);
//        Double ahocargo = new Double(0);
//
//        int rinx = 6;
//        if (id == 0) {
//            invabono = (Double) row[rinx++];
//            invcargo = (Double) row[rinx++];
//        } else {
//            ahoabono = (Double) row[rinx++];
//            ahocargo = (Double) row[rinx++];
//        }
//        return updateCardData(id, period, periodo_inx, rowIndex,
//                new Double[]{
//            invabono,
//            invcargo,
//            ahoabono,
//            ahocargo,},
//                presupuesto);
//    }
//
//    public java.util.Map<String, NivelName> getRankNivelNames(String pool, int id, String nivel0Id, int nivel0No, String nodo0Padre, String nivel1Id, int nivel1No, String nodo1Padre) {
//        java.util.Hashtable<String, NivelName> names = new java.util.Hashtable();
////        Integer nodo_level = 0;
////        String nodo = "0000000000";
////        String nombre = "????";
////        names.put(nodo, new NivelName(nombre, 0, nodo0Padre));
//        return names;
//    }
//    
//    public DBDataUtility getCardData(String pool, int id, String dataId, String nivel0Id, int nivel0No, String nodo0Id, String nivel1Id, int nivel1No, String nodo1Id) {
//        return new DBDataUtility(servlet, corporate);
//    }
//
//    public DB2CatList db_list(String pool, int id, String tabExt, String dataId, String nivel0Id, int nivel0No, String nodo0Id, String nivel1Id, int nivel1No, String nodo1Id, String periodoId, int columnFixed, TableColumn[] columns) {
//        if (id == 1) {
//            String table = xtables[0]+" m";
//            String where = "m.fecha_valor = '" + periodoId + "'";// and (m.monto_cargo >= 100000 or m.monto_abono >= 100000)";
//            if (nivel0No == 1) {
//                where += " and s.region_id = '" + nodo0Id + "'";
//            } else if (nivel0No == 2) {
//                where += " and s.sucursal_id  = '" + nodo0Id + "'";
//            }
//            table += " left join "+xtables[1]+" a on a.fecha_valor = m.fecha_valor and a.cuenta_id = m.cuenta_id ";
//            table += " left join "+xtables[3]+" s on s.sucursal_id = m.sucursal_id";
//            table += " left join "+xtables[3]+" s2 on s2.sucursal_id = m.sucursal_mov_id";
//            String cliente = "case when abs(m.monto_abono-m.monto_cargo) > 100000 then a.cliente_id else concat('OTROS ', m.sucursal_id) end";
//
//            DB2CatList db_list2 = new DB2CatList(servlet.db(), pool,
//                    new String[]{cliente, "m.sucursal_id", "s.nombre", "m.sucursal_mov_id", "s2.nombre", "m.fecha_valor",
//                "sum(m.monto_abono)",
//                "sum(m.monto_cargo)",},
//                    new Class[]{String.class, String.class, String.class, String.class, String.class, java.util.Date.class,
//                Double.class,
//                Double.class,},
//                    table, where,
//                    new String[]{cliente, "m.sucursal_id", "s.nombre", "m.sucursal_mov_id", "s2.nombre", "m.fecha_valor"},
//                    cliente + ", m.sucursal_id, s.nombre, m.sucursal_mov_id, s2.nombre, m.fecha_valor", "m.sucursal_id");
//            return db_list2;
//        } else if (id == 0) {
//            String table = xtables[2]+" m";
//            String where = "m.fecha = '" + periodoId + "'";// and (m.abn_cap >= 100000 or m.car_cap >= 100000)";
//            if (nivel0No == 1) {
//                where += " and s.region_id = '" + nodo0Id + "'";
//            } else if (nivel0No == 2) {
//                where += " and s.sucursal_id  = '" + nodo0Id + "'";
//            }
//            table += " left join "+xtables[4]+" i on i.cuenta_id =  m.cuenta_id and i.secuencia = 1";
//            table += " left join "+xtables[3]+" s on s.sucursal_id = m.sucursal";
//            String cliente = "case when m.abn_cap > 100000 or m.car_cap > 100000 then i.cliente_id else concat('OTROS ', m.sucursal) end";
//            DB2CatList db_list2 = new DB2CatList(servlet.db(), pool,
//                    new String[]{cliente, "m.sucursal", "s.nombre", "m.sucursal", "s.nombre", "m.fecha",
//                "sum(m.abn_cap)",
//                "sum(car_cap)",},
//                    new Class[]{String.class, String.class, String.class, String.class, String.class, java.util.Date.class,
//                Double.class,
//                Double.class,},
//                    table, where,
//                    new String[]{cliente, "m.sucursal", "s.nombre", "m.fecha"},
//                    cliente + ", m.sucursal, s.nombre, m.fecha", "m.sucursal");
//            return db_list2;
//        }
//        return null;
//    }
//
//    public int fields(int id) {
//        return 8;
//    }
//
//    public Object[] empty(int id) {
//        return new Object[]{"NA", "NA", "NA", "NA", "NA", new java.util.Date()};
//    }
//
//    public Integer getNodoLevel(int id, Object[] row) {
//        return new Integer(0);
//    }
//
//    public String getNodoId(int id, Object[] row) {
//        return (String) row[0];
//    }
//
//    public String getNodoName(int id, Object[] row) {
//        if (((String) row[1]).equals((String) row[3])) {
//            return (String) row[1] + " " + (String) row[2];
//        } else {
//            return (String) row[1] + " " + (String) row[2] + "/" + (String) row[3] + " " + (String) row[4];
//        }
//    }
//
//    public java.util.Date getFecha(int id, Object row) {
//        return (java.util.Date) row;
//    }
//
//    public Integer getPeriodo(int id, Object row) {
//        return Dates.toInt(((java.util.Date)row).getTime());
//    }
//
//    public java.util.Date getFecha(int id, Object[] row) {
//        return (java.util.Date) row[5];
//    }
//
//    public Integer getPeriodo(int id, Object[] row) {
//        return Dates.toInt(((java.util.Date)row[3]).getTime());
//    }
//    
//     public Double[] cardArray(Double[] values, String[] segmentos, Double[] ppto) {
//       return ppto;
//    }
//   
//   public DataReport getCardData(String pool, int[] ids, String tabExt, String dataId, String nivel0Id, int nivel0No, String nodo0Id, String nivel1Id, int nivel1No, String nodo1Id, String periodoId, int columnFixed, TableColumn[] columns) {
//        DataReport data = new DataReport();
//        data.node = nodo0Id;
//        data.period = periodoId;
//        data.hproducts = new java.util.Hashtable();
//        data.rankRows = 0;
//        data.ids = new String[0];
//        data.names = new String[0];
//        data.parents = new String[0];
//        DBDataUtility presupuesto = interfase.getCardData(pool != null && pool.length() > 0?pool:DBDefaults.DBVWRDATAPOOL, ids[0], dataId, nivel0Id, nivel0No, nodo0Id, nivel1Id, nivel1No, nodo1Id);
//        Integer min = Dates.toInt(Dates.getDateFormat(periodoId, "yyyy-MM-dd").getTime());
//        Integer rmax = min;
//        Integer max = min;
//        DataProduct product = new DataProduct();
//        product.hperiods = new java.util.Hashtable<Integer, DataPeriod>();
//        for (int inx = 0; inx < ids.length; inx++) {
//            int id = ids[inx];
//            if (inx == 0) {
//                java.util.Map<String, NivelName> vnames = interfase.getRankNivelNames(pool, id, nivel0Id, nivel0No, nodo0Id, nivel1Id, nivel1No, nodo1Id);
//                data.rankRows = vnames.size();
//                data.ids = new String[data.rankRows];
//                data.names = new String[data.rankRows];
//                data.parents = new String[data.rankRows];
//                java.util.Set set02 = vnames.entrySet();
//                java.util.Iterator it02 = set02.iterator();
//                while (it02.hasNext()) {
//                    java.util.Map.Entry entry02 = (java.util.Map.Entry) it02.next();
//                    String key = (String) entry02.getKey();
//                    NivelName value = (NivelName) entry02.getValue();
//                    data.ids[value.index] = key;
//                    data.names[value.index] = value.nombre;
//                    data.parents[value.index] = value.padre;
//                }
//            }
//            for (int i = 0; i < data.ids.length; i++) {
//                int index = -1;
//                for (int n = 0; n < presupuesto.list.length; n++) {
//                    if (data.ids[i].equals(presupuesto.list[n])) {
//                        index = n;
//                        break;
//                    }
//                }
//                if (index < 0) {
//                    presupuesto.append(data.ids[i], data.names[i], false);
//                }
//            }
//            DB2CatList db_list2 = interfase.db_list(pool, id, tabExt, dataId, nivel0Id, nivel0No, nodo0Id, nivel1Id, nivel1No, nodo1Id, periodoId, columnFixed, columns);
//            Connection conn = null;
//            java.util.Vector data2 = new java.util.Vector();
//            try {
//                conn = servlet.newDirectConnection(pool, new String[][]{{"socketTimeout", Integer.toString(60000 * 30)}});
//                conn.setAutoCommit(false);
//                data2 = db_list2.getPage(conn);
//            } catch (Exception ex) {
//                System.err.println(ex.toString());
//            } finally {
//                if (conn != null) {
//                    servlet.freeDirectConnection(pool, conn);
//                }
//            }
//            int size2 = data2.size();
//            if (size2 == 0 && data.ids.length == 1) {
//                Object row[] = interfase.empty(id);
//                data2.add(DBInterfase.initialize(row, db_list2));
//                size2 = data2.size();
//            }
//            product.product = nodo0Id;
//            product.periodMaxInx = rmax;
//            product.periodMinInx = min;
//            if (data.hproducts.get(nodo0Id) == null) {
//                data.hproducts.put(nodo0Id, product);
//            }
//            for (int i = 0; i < size2; i++) {
//                Object row[] = (Object[]) data2.elementAt(i);
//                Integer nodo_level = interfase.getNodoLevel(id, row);
//                String nodo_id = interfase.getNodoId(id, row);
//                String nodo_name = interfase.getNodoName(id, row);
//                java.util.Date fecha = interfase.getFecha(id, row);
//                Integer periodo_inx = new Integer(Dates.toInt(fecha.getTime()));
//                if (min > periodo_inx) {
//                    min = periodo_inx;
//                }
//                if (rmax < periodo_inx) {
//                    rmax = periodo_inx;
//                }
//                int index = -1;
//                for (int n = 0; n < presupuesto.list.length; n++) {
//                    if (nodo_id.equals(presupuesto.list[n])) {
//                        index = n;
//                        break;
//                    }
//                }
//                if (index < 0) {
//                    data.ids = Arrays2.append(data.ids, nodo_id);
//                    data.names = Arrays2.append(data.names, nodo_name);
//                    data.parents = Arrays2.append(data.parents, "--");
//                    data.rankRows = data.ids.length;
//                    index = data.rankRows - 1;
//                    presupuesto.append(nodo_id, nodo_name, false);
//                }
//            }
//            for (int i = 0; i < size2; i++) {
//                Object row[] = (Object[]) data2.elementAt(i);
//                Integer nodo_level = interfase.getNodoLevel(id, row);
//                String nodo_id = interfase.getNodoId(id, row);
//                String nodo_name = interfase.getNodoName(id, row);
//                java.util.Date fecha = interfase.getFecha(id, row);
//                Integer periodo_inx = new Integer(Dates.toInt(fecha.getTime()));
//                int index = -1;
//                for (int n = 0; n < presupuesto.list.length; n++) {
//                    if (nodo_id.equals(presupuesto.list[n])) {
//                        index = n;
//                        break;
//                    }
//                }
//                if (index < 0) {
//                    continue;
//                }
//                DataPeriod period = product.hperiods.get(periodo_inx);
//                if (period == null) {
//                    period = new DataPeriod();
//                    period.periodInx = periodo_inx;
//                    period.dateinx = Dates.toInt(fecha.getTime());
//                    period.rows = new DBData0Interfase.DataPeriodRow[data.rankRows];
//                    for (int r = 0; r < period.rows.length; ++r) {
//                        period.rows[r] = new DBData0Interfase.DataPeriodRow();
//                        period.rows[r].columns = new Object[columns.length - columnFixed];
//                        for (int c = 0; c < columns.length - columnFixed; ++c) {
//                            period.rows[r].columns[c] = new Double(0);
//                        }
//                    }
//                } else if (period.rows.length != data.rankRows) {
//                    DBData0Interfase.DataPeriodRow rows2[] = new DBData0Interfase.DataPeriodRow[data.rankRows];
//                    System.arraycopy(period.rows, 0, rows2, 0, period.rows.length);
//                    period.rows = rows2;
//                    for (int r = 0; r < period.rows.length; ++r) {
//                        if (period.rows[r] != null) {
//                            continue;
//                        }
//                        period.rows[r] = new DBData0Interfase.DataPeriodRow();
//                        period.rows[r].columns = new Object[columns.length - columnFixed];
//                        for (int c = 0; c < columns.length - columnFixed; ++c) {
//                            period.rows[r].columns[c] = new Double(0);
//                        }
//                    }
//
//                }
//                interfase.updateCardData(id, period, row, product, periodo_inx, index, presupuesto.hlist[index]);
//                product.hperiods.put(periodo_inx, period);
//            }
//        }
//        return data;
//    }

    public DataReport getData(String pool, String pretabl0, String pretabl1, String tables[], String dataId, String extId, String nivel0Id, int nivel0No, String nodo0Id, String nivel1Id, int nivel1No, String nodo1Id, String periodoId, TableRow[] rows, int columnFixed, TableColumn[] columns) {
        if (dataId.equals("CEX")) {
            if (tables == null || tables.length < 5) {
                xtables = new String[]{"sie_trn2_ahr_mov", "sie_trn2_ahr_sdos", "sie_trn2_inv_mov", "sie_cat_sucursal", "sie_cat_inversion"};
            } else {
                xtables = new String[]{tables[0], tables[1], tables[2], tables[3], tables[4]};
            }
            String date = Dates.getDateString(Dates.toLong(Strings.getInt(periodoId)), "yyyy-MM-dd");
            int[] ids = new int[]{0, 1};
            return null;//getCardData(pool != null && pool.length() > 0?pool:DBDefaults.DBVWRDATA0POOL, ids, "", dataId, nivel0Id, nivel0No, nodo0Id, nivel1Id, nivel1No, nodo1Id, date, columnFixed, columns);
        }
        return null;
    }
}
