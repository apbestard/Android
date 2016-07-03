package backend.card.def.min;

import backend.BEServletInterfase;
import backend.card.AppendDefaults;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;
import mx.logipax.shared.DBDefaults;


public class DefDBMinParticipanteEx extends DefDBBase{
        
    private static String tableModelName2 = "SIE_MIN_PARTICIPANTEEX";
    private static String tableTagName2 = "SIE_MIN_PARTICIPANTEEX";
    private static String tableDescName2 = "Participante Externos";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "MINUTA_ID", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Grupo Trabajo", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "SECUENCIA", DBDefaults.NUMBERTYPE + " NOT NULL", "Secuencia", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "NOMBRE", DBDefaults.STRINGTYPE + "(100) NOT NULL", "Nombre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "EMAIL", DBDefaults.STRINGTYPE+"(100) NOT NULL", "EMail", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "ESTATUSTRN", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Estatus", "", ""),
    };
    
        public DefDBMinParticipanteEx(BEServletInterfase servlet) {
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
