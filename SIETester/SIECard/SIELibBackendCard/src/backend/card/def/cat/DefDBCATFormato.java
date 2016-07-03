/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.def.cat;

import backend.BEServletInterfase;
import backend.card.AppendDefaults;
import mx.logipax.shared.DBDefaults;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;

public class DefDBCATFormato extends DefDBBase {
    private static String tableModelName2 = DBDefaults.OWNER+"SIE_CRD_CATFORMATO";
    public static String tableTagName2 = DBDefaults.OWNER+"SIE_CRD_CATFORMATO";
    private static String tableDescName2 = "Formato";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "FORMATO_ID", DBDefaults.STRINGTYPE+"(32) NOT NULL", "Formato", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "NOMBRE", DBDefaults.STRINGTYPE+"(64) NOT NULL", "Nombre", "", ""),
    };

    public DefDBCATFormato(BEServletInterfase servlet) {
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
    
    
    public String create(int corporate) {
        String error = super.create(corporate);
        if (error.indexOf("ok") >= 0) {
            AppendDefaults.appendFormato(servlet, this);
        }
        return(error);
    }
    
}
