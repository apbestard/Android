/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.def.vwr;

import backend.BEServletInterfase;
import mx.logipax.shared.DBDefaults;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;
import mx.logipax.shared.CatConstants;

public class DefDBVPerfil extends DefDBBase {
    private static String tableModelName2 = DBDefaults.OWNER+"sie_vwr2x0_perfil";
    public static String tableTagName2 = DBDefaults.OWNER+"sie_vwr2x0_perfil";
    private static String tableDescName2 = "Perfíl";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "perfil_id", DBDefaults.STRINGTYPE+"(32) NOT NULL", "Perfíl", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "reporte_id", DBDefaults.STRINGTYPE+"(12) NOT NULL", "Reporte", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.NUMBERCLASS, "nivel", DBDefaults.NUMBER4TYPE, "Nivel", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "nodo", DBDefaults.STRINGTYPE+"(20)", "Nodo", "", ""),
    };

    public DefDBVPerfil(BEServletInterfase servlet) {
        super(servlet,
              "", //DefDBBase.NOEDIT,
              tableModelName2,
              tableTagName2,
              tableDescName2,
              keys2,
              fields2,
              false,
              true, true, true);
        super.addIndex(new DefDBIndex(true, keys2.length));
    }
}
