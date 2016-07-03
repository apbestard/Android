package backend.card.def.min;

import backend.BEServletInterfase;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;
import mx.logipax.shared.DBDefaults;


public class DefDBMinuta extends DefDBBase{
        
    private static String tableModelName2 = "SIE_MIN_MINUTA";
    private static String tableTagName2 = "SIE_MIN_MINUTA";
    private static String tableDescName2 = "Minuta";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "MINUTA_ID", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Grupo Trabajo", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "NOMBRE", DBDefaults.STRINGTYPE + "(100) NOT NULL", "Nombre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "GPOTRABAJO_ID", DBDefaults.STRINGTYPE + "(32)", "Grupo", "", ""),
        new DefDBField(DBDefaults.DATECLASS, "FECHA", DBDefaults.DATETYPE + " NOT NULL", "Fecha", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "TEXTO", DBDefaults.STRINGTYPE + "(2048)", "Texto", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "ESTATUSTRN", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Estatus", "", ""),
    };
    
        public DefDBMinuta(BEServletInterfase servlet) {
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
