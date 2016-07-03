/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.data;

import backend.card.io.ViewRecords.STableRow;

public class Rows {

    public static STableRow[] rows() {
        return new STableRow[]{
            new STableRow("r1", "Vigente", "Cartera", "1", ""),
            new STableRow("r2", "Vencido", "Cartera", "S", ""),
            new STableRow("r3", "Por Liquidar", "Cartera", "S", ""),
            new STableRow("r4", "Total-Por Liq", "Cartera", "M", ""),
            new STableRow("r4", "Total", "Cartera", "M", ""),
            new STableRow("r1", "Normalidad", "Cartera", "1", ""),
            new STableRow("r2", "Atrasado", "Cartera", "S", ""),
            new STableRow("r2", "Muy Atrasado", "Cartera", "S", ""),
            new STableRow("r2", "Irregular", "Cartera", "S", ""),
            new STableRow("r2", "Vencido", "Cartera", "S", ""),
            new STableRow("r4", "Total", "Cartera", "M", ""),
            new STableRow("r1", "01 Semana", "Cartera", "1", ""),
            new STableRow("r2", "01 Mes", "Cartera", "S", ""),
            new STableRow("r2", "02 Mes", "Cartera", "S", ""),
            new STableRow("r2", "03 Mes", "Cartera", "S", ""),
            new STableRow("r2", "04 Mes", "Cartera", "S", ""),
            new STableRow("r2", "05 Mes", "Cartera", "S", ""),
            new STableRow("r2", "06 Mes", "Cartera", "S", ""),
            new STableRow("r2", "07 Mes", "Cartera", "S", ""),
            new STableRow("r2", "08 Mes", "Cartera", "S", ""),
            new STableRow("r2", "09 Mes", "Cartera", "S", ""),
            new STableRow("r2", "10 Mes", "Cartera", "S", ""),
            new STableRow("r1", "+01 Semana", "Cartera", "1", ""),
            new STableRow("r2", "+01 Mes", "Cartera", "S", ""),
            new STableRow("r2", "+02 Mes", "Cartera", "S", ""),
            new STableRow("r2", "+03 Mes", "Cartera", "S", ""),
            new STableRow("r2", "+04 Mes", "Cartera", "S", ""),
            new STableRow("r2", "+05 Mes", "Cartera", "S", ""),
            new STableRow("r2", "+06 Mes", "Cartera", "S", ""),
            new STableRow("r2", "+07 Mes", "Cartera", "S", ""),
            new STableRow("r2", "+08 Mes", "Cartera", "S", ""),
            new STableRow("r2", "+09 Mes", "Cartera", "S", ""),
            new STableRow("r2", "+10 Mes", "Cartera", "S", ""),
            new STableRow("r1", "-7 a 0 Días", "Cartera", "1", ""),
            new STableRow("r2", "1 a 7 Días", "Cartera", "S", ""),
            new STableRow("r2", "8 a 103 Días", "Cartera", "S", ""),
            new STableRow("r2", "Más de 104 Días", "Cartera", "S", ""),
            new STableRow("r2", "Total", "Cartera", "S", ""),};
    }
}
