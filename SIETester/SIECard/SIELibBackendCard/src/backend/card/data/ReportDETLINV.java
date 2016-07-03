/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.data;

import java.awt.Color;
import backend.card.io.ViewRecords;
import mx.logipax.shared.objects.viewer.ObjectClass;
import mx.logipax.shared.objects.viewer.Report;

public class ReportDETLINV {

    public static ViewRecords.SReport report() {
        return new ViewRecords.SReport("", "DETLINV", "", "", "", 
                "DETLINV",
                "Detalle", "Flujo de Captación",
                "DETLINV",
                true, Report.ATTRRANK, "",
                new String[]{"id"},
                new String[]{"name"},
                new ViewRecords.SRepoLevel[]{
            new ViewRecords.SRepoLevel(0, "l0", "ORGANIZACIÓN", "desc",
            new ViewRecords.SRepoLevelData(),
            new ViewRecords.SRepoLevel[0])},
                200, 2,
                new ViewRecords.SRepoView[]{
            new ViewRecords.SRepoView("vw", "Vista", "Desc", 1, new int[]{0, 6, 3, 1, 2}, new int[]{0},
            0, new String[]{}, new int[]{}, new int[]{},
            new int[]{}, new int[]{}, new int[]{}, new int[]{},
            new String[]{}, new int[]{})
        },
                new ViewRecords.SPeriod[]{
            new ViewRecords.SPeriod(ObjectClass.DIAS01, "Diaria", ObjectClass.DIAS01),},
                new ViewRecords.STableColumn[]{
            new ViewRecords.STableColumn("cte", "Cliente", "", ObjectClass.STRING, "Cliente", 100),
            new ViewRecords.STableColumn("suc", "Sucursal/Movimiento", "", ObjectClass.STRING, "Sucursal", 298),
            new ViewRecords.STableColumn("net", "Total", "Neto", ObjectClass.REAL, "Inicio", 120),
            new ViewRecords.STableColumn("iabn", "Inversión", "Inicio", ObjectClass.REAL, "Inicio", 120),
            new ViewRecords.STableColumn("icar", "Inversión", "Vencimiento", ObjectClass.REAL, "Vencimiento", 120),
            new ViewRecords.STableColumn("inet", "Inversión", "Neto", ObjectClass.REAL, "Neto", 120),
            new ViewRecords.STableColumn("aabn", "Ahorro", "Abono", ObjectClass.REAL, "Abono", 120),
            new ViewRecords.STableColumn("acar", "Ahorro", "Cargo", ObjectClass.REAL, "Cargo", 120),
            new ViewRecords.STableColumn("anet", "Ahorro", "Neto", ObjectClass.REAL, "Neto", 120),},
                new ViewRecords.STableRow[]{
            new ViewRecords.STableRow("row", "Rank", "", "1", ""),});
    }
}
