/*
 Derechos Reservados (c)
 Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
 2009, 2010, 2011, 2012, 2013, 2014
 logipax Marca Registrada (R)  2003, 2014
 */
package backend.card.io.db;

import backend.BEServletInterfase;
import mx.logipax.shared.objects.card.cat.CardSuc;
import mx.logipax.shared.objects.card.cat.CardSucEquipo;
import mx.logipax.shared.objects.card.cat.CardSucPerfil;
import mx.logipax.shared.objects.card.cat.CardSucPersona;
import org.json.JSONObject;

public class DBDataInterfase {

    BEServletInterfase servlet;
    int corporate;

    private java.util.Map<Integer, Double> diasFeriados;
    private java.util.Map<Integer, Double[]> factorDiario;

    public DBDataInterfase(BEServletInterfase servlet, int corporate) {
        this.servlet = servlet;
        this.corporate = corporate;
//        diasFeriados = DBDataUtility.getDiasFeriados(servlet, "local");
//        factorDiario = DBDataUtility.getFactorDiario(servlet, "local");
    }
//
//    public DB2CatList db_list(String pool, int id, String tabExt,
//            String niveltable, String pptotable,
//            String dataId, String nivel0Id, int nivel0No, String nodo0Id, String nivel1Id, int nivel1No, String nodo1Id, String periodoId, int columnFixed, TableColumn[] columns) {
////        String nvalues[] = Strings.tokenizer(periodoId, "=");
////        int vindex = 0;
////        int vseg = 0;
////        if (nvalues.length == 4) {
////            vindex = Strings.getInt(nvalues[0]);
////            vseg = Strings.getInt(nvalues[1]);
////        } else if (nvalues.length == 3) {
////            vindex = Strings.getInt(nvalues[0]);
////        }
////        if (nvalues.length < 2) {
////            nvalues = Arrays2.append(nvalues, "0");
////        }
////        String var = nvalues[nvalues.length-2];
////        String vrange = nvalues[nvalues.length-1];
//
////        String inxwhere = "";
////        String key0 = "";
////        String key1 = "";
////       String table = pptotable + " a";
////        if (vindex == 0) {
////            table += " left join "+/*OWNER.*/"sie_vwr2_catmedico m on m.marca = a.marca and m.medico = a.medico";
////            key0 = "concat(to_char(a.marca, '009'),concat('-',to_char(a.medico, '00000009')))";
////            key1 = "nvl(m.nombre, 'NA')";
////        } else if (vindex == 1) {
////            table += " left join "+/*OWNER.*/"sie_vwr2_catruta m on m.marca = a.marca and m.ruta = a.ruta";
////            key0 = "concat(to_char(a.marca, '009'),concat('-',to_char(a.ruta, '009')))";
////            key1 = "nvl(m.representante_nombre, 'NA')";
////           inxwhere = "a.ruta > 0";
////        } else if (vindex == 2) {
////           table += " left join "+/*OWNER.*/"sie_vwr2_catpromocion m on m.marca = a.marca and m.promocion = a.promocion";
////            key0 = "concat(to_char(a.marca, '009'),concat('-',to_char(a.promocion, '000009')))";
////            key1 = "nvl(m.nombre, 'NA')";
////           inxwhere = "a.promocion > 0";
////        } else if (vindex == 3) {
////            table += " left join "+/*OWNER.*/"sie_vwr2_catperfil m on m.perfil = a.perfil";
////            key0 = "to_char(a.perfil, '000009')";
////            key1 = "nvl(m.nombre, 'NA')";
////           inxwhere = "a.perfil > 0";
////        } else if (vindex == 4) {
////           table += " left join "+/*OWNER.*/"sie_vwr2_catexamen m on m.examen = a.examen";
////            key0 = "to_char(a.examen, '000009')";
////            key1 = "nvl(m.nombre, 'NA')";
////           inxwhere = "a.perfil = 0";
////        } else if (vindex == 5) {
////           table += " left join "+/*OWNER.*/"sie_vwr2_catcliente m on m.marca = a.marca and m.cliente = a.cliente";
////            key0 = "concat(to_char(a.marca, '009'),concat('-',to_char(a.cliente, '000009')))";
////            key1 = "nvl(m.nombre, 'NA')";
////        }
////        String order = "vta1 desc";
////        if (var != null && var.length() > 0) {
////            if (var.startsWith("+")) {
////                order = var.substring(1) + " desc";
////            } else if (var.startsWith("-")) {
////                order = var.substring(1);
////            } else {
////                order = var;
////            }
////        }
////        String range[] = Strings.tokenizer(vrange, "_");
////        String dates[][] = new String[range.length][];
////        String[] wdates = new String[range.length];
////        for (int i = 0; i < range.length; i++) {
////            dates[i] = Strings.tokenizer(range[i], "-");
////            if (range[i] != null && range[i].length() > 0) {
////                if (dates[i].length > 0 && dates[i][0] != null && dates[i][0].length() > 0) {
////                    String date = Dates.getDateString(Dates.toLong(Strings.getInt(dates[i][0])), "dd-MMM-yyyy");
////                    if (dates[i].length > 1 && dates[i][1] != null && dates[i][1].length() > 0 && !dates[i][0].equals(dates[i][1])) {
////                        String date2 = Dates.getDateString(Dates.toLong(Strings.getInt(dates[i][1])), "dd-MMM-yyyy");
////                        wdates[i] = "(a.fecha >= '" + date + "' and a.fecha <= '" + date2 + "')";
////                    } else {
////                        wdates[i] = "(a.fecha = '" + date + "')";
////                    }
////                }
////            }
////        }
////        String where = "";
////        if (wdates.length > 0 && wdates[0].length() > 0) {
////            if (wdates.length > 1 && wdates[1].length() > 0) {
////                where += "(" + wdates[0] + " or " + wdates[1] + ")";
////            } else {
////                where += wdates[0];
////            }
////        } else {
////            where += "a.fecha >= " + Dates.getDateString(Dates.toLong(0), "dd-MMM-yyyy");
////        }
////        if (where.length() > 0 && inxwhere.length() > 0) {
////            where += " and ";
////        }
////        where += inxwhere;
////        if (vseg == 0) {
////            where += "a.tipo = '00'";
////        } else if (vseg == 1) {
////            where += "(a.segmento >= 100 and a.segmento < 110)";
////        } else if (vseg == 2) {
////            where += "(a.segmento >= 200 and a.segmento < 210)";
////        } else {
////            where += "a.segmento = " + Integer.toString(segmentoNoStr[vseg]);
////        }
//        String table = pptotable + " a";
//        table += " left join " + niveltable + " n04 on n04.nivel_id = '" + nivel0Id + "' and n04.nivel = 4 and n04.nodo = to_char(a.sucursal)";
//        table += " left join " + niveltable + " n03 on n03.nivel_id = '" + nivel0Id + "' and n03.nivel = 3 and n03.nodo = n04.nodo_padre";
//        table += " left join " + niveltable + " n02 on n02.nivel_id = '" + nivel0Id + "' and n02.nivel = 2 and n02.nodo = n03.nodo_padre";
//        table += " left join " + niveltable + " n01 on n01.nivel_id = '" + nivel0Id + "' and n01.nivel = 1 and n01.nodo = n02.nodo_padre";
//        table += " left join " + niveltable + " n00 on n00.nivel_id = '" + nivel0Id + "' and n00.nivel = 0 and n00.nodo = n01.nodo_padre";
//        String where = "";
//        if (where.length() > 0) {
//            where += " and ";
//        }
//        where += "n0" + Integer.toString(nivel0No) + ".nivel = " + nivel0No + " and n0" + Integer.toString(nivel0No) + ".nodo = '" + nodo0Id + "'";
//
//        DB2CatList db_list2 = new DB2CatList(servlet.db(), pool,
//                new String[]{"n.nivel", "n.nodo_padre", "n.nodo", "n.nombre",
//                    "b.especialidad", "b.segmento",
//                    "b.fecha", "sum(b.monto)", "sum(b.volumen)", "sum(b.volcapacidad)", "sum(b.volparo)",},
//                new Class[]{Integer.class, String.class, String.class, String.class,
//                    Integer.class, Integer.class,
//                    java.util.Date.class, Double.class, Double.class, Double.class, Double.class,},
//                table,
//                where,
//                new String[]{"n.nivel", "n.nodo_padre", "n.nodo", "n.nombre",
//                    "b.especialidad", "b.segmento",
//                    "b.fecha"},
//                "n.nivel, n.nodo_padre, n.nodo, n.nombre, b.especialidad, b.segmento, b.fecha",
//                "n.nivel, n.nodo_padre, n.orden, n.nombre, n.nodo");
//        return db_list2;
//    }

    public CardSuc getCardSuc(String pool, String nivel0Id, int nivel0No, String xnodo0Id, String xnodo0PadreId) {
//        String table = pptotable + " a";
//        table += " left join " + niveltable + " n04 on n04.nivel_id = '" + nivel0Id + "' and n04.nivel = 4 and n04.nodo = to_char(a.sucursal)";
//        table += " left join " + niveltable + " n03 on n03.nivel_id = '" + nivel0Id + "' and n03.nivel = 3 and n03.nodo = n04.nodo_padre";
//        table += " left join " + niveltable + " n02 on n02.nivel_id = '" + nivel0Id + "' and n02.nivel = 2 and n02.nodo = n03.nodo_padre";
//        table += " left join " + niveltable + " n01 on n01.nivel_id = '" + nivel0Id + "' and n01.nivel = 1 and n01.nodo = n02.nodo_padre";
//        table += " left join " + niveltable + " n00 on n00.nivel_id = '" + nivel0Id + "' and n00.nivel = 0 and n00.nodo = n01.nodo_padre";
//        String where = "";
//        if (where.length() > 0) {
//            where += " and ";
//        }
//        String nx = "n0" + Integer.toString(nivel0No);
//        if (xnodo0PadreId != null) {
//            where += nx + ".nivel = " + Integer.toString(nivel0No) + " and " + nx + ".nodo_padre = '" + xnodo0PadreId + "'";
//        } else {
//            where += nx + ".nivel = " + Integer.toString(nivel0No) + " and " + nx + ".nodo = '" + xnodo0Id + "'";
//        }
//        DB2CatList db_list2 = new DB2CatList(servlet.db(), pool,
//                new String[]{nx + ".nivel", nx + ".nodo_padre", nx + ".nodo", nx + ".nombre",
//                    "a.especialidad", "a.segmento",
//                    "a.fecha", "sum(a.monto)", "sum(a.volumen)", "sum(a.volcapacidad)", "sum(a.volparo)",},
//                new Class[]{Integer.class, String.class, String.class, String.class,
//                    Integer.class, Integer.class,
//                    java.util.Date.class, Double.class, Double.class, Double.class, Double.class,},
//                table,
//                where,
//                new String[]{nx + ".nivel", nx + ".nodo_padre", nx + ".nodo", nx + ".nombre",
//                    "a.especialidad", "a.segmento",
//                    "a.fecha"},
//                nx + ".nivel, " + nx + ".nodo_padre, " + nx + ".nodo, " + nx + ".nombre, a.especialidad, a.segmento, a.fecha",
//                nx + ".nivel, " + nx + ".nodo_padre, " + nx + ".nodo");
//        java.util.Vector data2 = db_list2.getPage();
//        int size2 = data2.size();
        CardSuc card = new CardSuc(new JSONObject());
        card.setCardId("cardId");
    card.setCardNombre("cardNombre");
    card.setResponsableId("responsableId");
    card.setResponsableNombre("responsableNombre");
    card.setVisorId("visorId");
    card.setVisorNombre("visorNombre");
    card.setFecha("01/01/2016");
    //card.setFecha(java.util.Date fecha");
    card.setEMail("email");
    card.setEstatus("estatus");
    CardSucPerfil profile = new CardSucPerfil(new JSONObject());
    profile.setCardId("cardId");
    profile.setParentId("parentId");
    profile.setSupervisor(true);
    profile.setEditable(true);
    profile.setOptions("options");
    profile.setEstatus("estatus");
    profile.update();
    card.setPerfil(profile);
    
    CardSucPersona personal = new CardSucPersona(new JSONObject());

    personal.setDeptoId("deptoId");
    personal.setDeptoNombre("deptoNombre");
    personal.setPuestoId("puestoId");
    personal.setPuestoNombre("puestoNombre");
    personal.setPersonas(1);
    personal.setVacantes(2);
    personal.setEstatus("estatus");
    
    card.setPersonal(new CardSucPersona[]{personal});
    CardSucEquipo equipo = new CardSucEquipo(new JSONObject()); 
    equipo.setDeptoId("deptoId");
    equipo.setDeptoNombre("deptoNombre");
    equipo.setEquipoId("equipoId");
    equipo.setEquipoNombre("equipoNombre");
    equipo.setEquipos(1);
    equipo.setVacantes(2);
    equipo.setEstatus("estatus");
    card.setEquipos(new CardSucEquipo[]{equipo});
    card.update();
        
//        if (data2.size() > 0) {
//            int rinx = 0;
//            Object row[] = (Object[]) data2.elementAt(0);
//            Integer nnivel = (Integer) row[rinx++];
//            String padre = (String) row[rinx++];
//            level.level = nnivel.intValue();
//            level.id = padre;
//            level.datas = get(data2);
//        }
        return card;
    }

//1	2	AUDIOLOGIA
//2	1	BIOLOGIA MOLECULAR
//3	1	BIOQUIMICA
//4	2	CHEQUEOS
//5	1	CITOLOGIA
//6	2	COLPOSCOPIA
//7	2	DOMICILIOS
//8	2	CARDIOLOGIA
//9	1	HEMATOLOGIA
//10	1	HISTOPATOLOGIA
//11	1	INMUNOLOGIA
//12	2	MASTOGRAFIA
//13	2	MEDICINA NUCLEAR
//14	1	MICROBIOLOGIA
//16	1	PARASITOLOGIA Y URIANALISIS
//18	2	RADIOLOGIA
//19	2	RESONANCIA MAGNETICA
//20	2	TOMADORES
//21	2	TOMOGRAFIA
//22	1	TOXICOLOGIA
//23	2	ULTRASONIDO
//24	2	DENSITOMETRIA
//25	2	ELECTROENCEFALOGRAFIA
//30	2	HEMODIALISIS
//31	1	CITOGENETICA
//32	2	PET
//34	2	OPTOMETRIA
    private static int especialidadMax = 27;
    private static int[] especialidades = new int[]{-1, 2, 1, 1, 2, 1, 2, 2, 2, 1, 1, 1, 2, 2, 1, -1, 1, -1, 2, 2, 2, 2, 1, 2, 2, 2, -1, -1, -1, -1, 2, 1, 2, 2};
    private static int[] especialidadesInx = new int[]{-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, -1, 14, -1, 15, 16, 17, 18, 19, 20, 21, 22, -1, -1, -1, -1, 23, 24, 25, 26};
    private static int segmentoMax = 6;
    private static int[] segmentosInx = new int[]{0, 1, 2, 3, 4, 5};
//
//    private CardLevelData[] get(java.util.Vector data0) {
//        if (data0.size() == 0) {
//            return new CardLevelData[0];
//        }
//        int size = data0.size();
//        String ids[] = new String[0];
//        for (int n = 0; n < size; n++) {
//            int rinx = 0;
//            Object row[] = (Object[]) data0.elementAt(n);
//            Integer nivel = (Integer) row[rinx++];
//            String padre = (String) row[rinx++];
//            String nodo = (String) row[rinx++];
//            if (ids.length == 0 || !ids[ids.length - 1].equals(nodo)) {
//                ids = Arrays.append(ids, nodo);
//            }
//        }
//        CardLevelData[] datas = new CardLevelData[ids.length];
//        CardLevelData data = null;
//        int inx = -1;
//        for (int n = 0; n < size; n++) {
//            int rinx = 0;
//            Object row[] = (Object[]) data0.elementAt(n);
//            Integer nivel = (Integer) row[rinx++];
//            String padre = (String) row[rinx++];
//            String nodo = (String) row[rinx++];
//            String nombre = (String) row[rinx++];
//            if (data == null || !data.sucursal.equals(nodo)) {
//                if (data != null) {
//                    data.update();
//                }
//                data = new CardLevelData(new JSONObject());
//                data.sucursal = nodo;
//                data.rows = especialidadMax;
//                data.cols = segmentoMax;
//                data.dates = 12;
//                data.montos = new double[especialidadMax][segmentoMax][12];
//                data.volumenes = new double[especialidadMax][segmentoMax][12];
//                data.volcapacidades = new double[especialidadMax][segmentoMax][12];
//                data.volparos = new double[especialidadMax][segmentoMax][12];
//                for (int i = 0; i < ids.length; i++) {
//                    if (nodo.equals(ids[i])) {
//                        inx = i;
//                        datas[inx] = data;
//                        break;
//                    }
//                }
//            }
////            
////                int inx = 0;
////                for (int r = 0; r < data.rows; r++) {
////                    for (int c = 0; c < data.cols; c++) {
////                        for (int d = 0; d < data.dates; d++) {
////                            data.montos[r][c][d] = inx++;
////                            data.volumenes[r][c][d] = inx++;
////                            data.volcapacidades[r][c][d] = inx++;
////                            data.volparos[r][c][d] = inx++;
////                        }
////                    }
////                }
//            Integer especialidad = (Integer) row[rinx++];
//            Integer segmento = (Integer) row[rinx++];
//            java.util.Date fecha = (java.util.Date) row[rinx++];
//            Double monto = (Double) row[rinx++];
//            Double volumen = (Double) row[rinx++];
//            Double volcapacidad = (Double) row[rinx++];
//            Double volparo = (Double) row[rinx++];
//            int especialidadInx = 0;
//            if (especialidad.intValue() >= 0 && especialidad.intValue() < especialidadesInx.length) {
//                especialidadInx = especialidadesInx[especialidad.intValue()];
//            }
//            int segmentoInx = 0;
//            if (segmento.intValue() >= 0 && segmento.intValue() < segmentosInx.length) {
//                segmentoInx = segmentosInx[segmento.intValue()];
//            }
//            int mes = Dates.getMonth(fecha.getTime());
//            if (especialidadInx < 0 || segmentoInx < 0 || mes < 0 || mes >= 12) {
//                continue;
//            }
//            data.montos[especialidadInx][segmentoInx][mes] = monto.doubleValue();
//            data.volumenes[especialidadInx][segmentoInx][mes] = volumen.doubleValue();
//            data.volcapacidades[especialidadInx][segmentoInx][mes] = volcapacidad.doubleValue();
//            data.volparos[especialidadInx][segmentoInx][mes] = volparo.doubleValue();
//        }
//        if (data != null) {
//            data.update();
//        }
//        return datas;
//    }

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
}
