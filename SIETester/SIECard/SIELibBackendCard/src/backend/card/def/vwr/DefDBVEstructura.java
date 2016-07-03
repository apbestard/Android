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

public class DefDBVEstructura extends DefDBBase {
    private static String tableModelName2 = DBDefaults.OWNER+"sie_vwr2x0_estructura";
    public static String tableTagName2 = DBDefaults.OWNER+"sie_vwr2x0_estructura";
    private static String tableDescName2 = "Estructura";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "estructura_id", DBDefaults.STRINGTYPE+"(20) NOT NULL", "Estructura Id", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "nombre", DBDefaults.STRINGTYPE+"(64) NOT NULL", "Nombre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "nivel_id", DBDefaults.STRINGTYPE+"(20) NOT NULL", "Nivel ID", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "maxnivel", DBDefaults.NUMBER4TYPE, "Max Nivel", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "nodo", DBDefaults.STRINGTYPE+"(20)", "Nodo", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "nivel", DBDefaults.NUMBER4TYPE, "Nivel", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "hventana", DBDefaults.NUMBER4TYPE, "Ventana Horz", "", ""),
    };

    public DefDBVEstructura(BEServletInterfase servlet) {
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
