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

public class DefDBVNivel extends DefDBBase {
    private static String tableModelName2 = DBDefaults.OWNER+"sie_vwr2_nivel";
    public static String tableTagName20 = DBDefaults.OWNER+"sie_vwr2_nivel";
    private static String tableDescName2 = "Nivel";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "nivel_id", DBDefaults.STRINGTYPE+"(32) NOT NULL", "Nivel ID", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "nivel", DBDefaults.NUMBER4TYPE + " NOT NULL", "Nivel", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "nodo", DBDefaults.STRINGTYPE+"(32) NOT NULL", "Nodo", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "nodo_padre", DBDefaults.STRINGTYPE+"(32)", "Nodo Padre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "nombre", DBDefaults.STRINGTYPE+"(100) NOT NULL", "Nombre", "", ""),
    };

    public DefDBVNivel(BEServletInterfase servlet) {
        super(servlet,
              "", //DefDBBase.NOEDIT,
              tableModelName2,
              tableTagName20,
              tableDescName2,
              keys2,
              fields2,
              false,
              true, true, true);
        super.addIndex(new DefDBIndex(true, keys2.length));
        //super.addIndex(new DefDBIndex(true, new int[]{0, 3, 1, 2}));
    }
    public String create(int corporate) {
        String error = super.create(corporate);
//        if (error.indexOf("ok") >= 0) {
//            AppendDefaults.appendNivel(servlet, this);
//        }
        return(error);
    }
    
}
