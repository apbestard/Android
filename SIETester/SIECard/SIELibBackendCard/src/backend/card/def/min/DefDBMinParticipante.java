package backend.card.def.min;

import backend.BEServletInterfase;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;
import mx.logipax.shared.DBDefaults;


public class DefDBMinParticipante extends DefDBBase{
        
    private static String tableModelName2 = "SIE_MIN_PARTICIPANTE";
    private static String tableTagName2 = "SIE_MIN_PARTICIPANTE";
    private static String tableDescName2 = "Participante";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "MINUTA_ID", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Minuta", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "SECUENCIA", DBDefaults.NUMBERTYPE + " NOT NULL", "Secuencia", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "EJECUTIVO_ID", DBDefaults.STRINGTYPE+"(32) NOT NULL", "Ejecutivo", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "ESTATUSTRN", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Estatus", "", ""),
    };
    
        public DefDBMinParticipante(BEServletInterfase servlet) {
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
