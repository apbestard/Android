package backend.card.def.min;

import backend.BEServletInterfase;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;
import mx.logipax.shared.DBDefaults;


public class DefDBMinAcuerdo extends DefDBBase{
        
    private static String tableModelName2 = "SIE_MIN_ACUERDO";
    private static String tableTagName2 = "SIE_MIN_ACUERDO";
    private static String tableDescName2 = "Acuerdo";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "MINUTA_ID", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Minuta", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "SECUENCIA", DBDefaults.NUMBERTYPE + " NOT NULL", "Secuencia", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "ACUERDO_ID", DBDefaults.STRINGTYPE+"(32) NOT NULL", "Acuerdo", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "SOLICITA_ID", DBDefaults.STRINGTYPE+"(32) NOT NULL", "Solicita", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "RESPONSABLE_ID", DBDefaults.STRINGTYPE+"(32) NOT NULL", "Responsable", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "OBJETIVO", DBDefaults.STRINGTYPE + "(256) NOT NULL", "Objetivo", "", ""),
        new DefDBField(DBDefaults.DATECLASS, "FECHA", DBDefaults.DATETYPE + " NOT NULL", "Fecha", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "TEXTO", DBDefaults.STRINGTYPE + "(2048)", "Texto", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "ESTATUSTRN", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Estatus", "", ""),
    };
    
        public DefDBMinAcuerdo(BEServletInterfase servlet) {
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
        return(error);
    }
}
