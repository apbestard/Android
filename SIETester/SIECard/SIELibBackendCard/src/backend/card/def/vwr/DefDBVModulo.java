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

public class DefDBVModulo extends DefDBBase {
    private static String tableModelName2 = DBDefaults.OWNER+"sie_vwr2x0_modulo";
    public static String tableTagName2 = DBDefaults.OWNER+"sie_vwr2x0_modulo";
    private static String tableDescName2 = "Módulo";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "modulo_id", DBDefaults.STRINGTYPE+"(4) NOT NULL", "Módulo", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.NUMBERCLASS, "indice", DBDefaults.NUMBER4TYPE + " NOT NULL", "Indice", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "nombre", DBDefaults.STRINGTYPE+"(64) NOT NULL", "Nombre", "", ""),
    };

    public DefDBVModulo(BEServletInterfase servlet) {
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
        super.addIndex(new DefDBIndex(true, new int[]{1,0}));
    }
}
