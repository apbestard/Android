/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.data;

import backend.card.io.ViewRecords.STableColumn;
import mx.logipax.shared.objects.viewer.ObjectClass;


public class Columns {
    
    public static STableColumn[] columns() {
        return new STableColumn[]{
            new STableColumn("c1", "Concepto", "", ObjectClass.STRING, "Concepto", 100),
            new STableColumn("n2", "Créditos", "Cartera", ObjectClass.INTEGER, "Número de créditos", 100),
            new STableColumn("%3", "% Créditos", "Total", ObjectClass.REAL, "Porcentaje de créditos", 100),
            new STableColumn("m3", "Saldo", "Cartera", ObjectClass.REAL, "Saldos de créditos", 100),
            new STableColumn("%3", "% Saldo", "Total", ObjectClass.REAL, "Porcentaje de saldo", 100),
            new STableColumn("p3", "Saldo", "Promedio", ObjectClass.REAL, "Saldo promedio otorgado", 100),
            new STableColumn("%3", "% Otorgado", "Saldo", ObjectClass.REAL, "Saldo promedio otorgado", 100),};
    }
}
