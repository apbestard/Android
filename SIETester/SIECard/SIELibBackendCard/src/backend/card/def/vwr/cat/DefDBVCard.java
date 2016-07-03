/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.def.vwr.cat;

import backend.BEServletInterfase;
import mx.logipax.shared.DBDefaults;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;

public class DefDBVCard extends DefDBBase {

    private static String tableModelName2 = DBDefaults.OWNER+"sie_vwr2_ppto";
    public static String tableTagName2 = DBDefaults.OWNER+"sie_vwr2_ppto";
    private static String tableDescName2 = "Presupuesto";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "nivel_id", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Id", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "nivel", DBDefaults.NUMBER4TYPE + " NOT NULL", "Nivel", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "nodo", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Nodo", "", ""),
        new DefDBField(DBDefaults.DATECLASS, "fecha", DBDefaults.DATETYPE + " NOT NULL", "Fecha", "", ""),
        };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "nodo_padre", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Nodo", "", ""),
         new DefDBField(DBDefaults.DATECLASS, "fecha_apertura", DBDefaults.DATETYPE + " NOT NULL", "fecha apertura", "", ""),
       new DefDBField(DBDefaults.NUMBERCLASS, "vacantes", DBDefaults.NUMBER8TYPE, "", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "asignadas", DBDefaults.NUMBER8TYPE, "", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "plazas", DBDefaults.NUMBER8TYPE, "", "", ""),
    };

    public DefDBVCard(BEServletInterfase servlet) {
        super(servlet,
                "", //DefDBBase.NOEDIT,
                tableModelName2,
                tableTagName2,
                tableDescName2,
                keys2,
                fields2,
                false,
                false, false, false);
        super.addIndex(new DefDBIndex(true, keys2.length));
    }
    public String create(int corporate) {
        String error = super.create(corporate);
        if (error.indexOf("ok") >= 0) {
            //AppendDefaults.appendDatos(servlet, this);
        }
        return(error);
    }
}
