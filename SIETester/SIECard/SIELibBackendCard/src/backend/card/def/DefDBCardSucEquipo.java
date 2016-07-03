package backend.card.def;

import backend.BEServletInterfase;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;
import mx.logipax.shared.DBDefaults;

public class DefDBCardSucEquipo extends DefDBBase {

    private static String tableModelName2 = DBDefaults.OWNER+"SIE_CRD_EQUIPOS";
    private static String tableTagName2 = DBDefaults.OWNER+"SIE_CRD_EQUIPOS";
    private static String tableDescName2 = "Equipos";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.NUMBERCLASS, "CARD_NIVEL", DBDefaults.NUMBERTYPE + " NOT NULL", "Nivel", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "CARD_ID", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Id", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "SECUENCIA", DBDefaults.NUMBERTYPE + " NOT NULL", "Secuencia", "", "")
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "DEPARTAMENTO_ID", DBDefaults.STRINGTYPE + "(32)", "Departamento", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "DEPARTAMENTO_NOMBRE", DBDefaults.STRINGTYPE + "(64)", "Departamento Nombre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "EQUIPO_ID", DBDefaults.STRINGTYPE + "(32)", "Equipo", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "EQUIPO_NOMBRE", DBDefaults.STRINGTYPE + "(64)", "Equipo Nombre", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "EQUIPOS", DBDefaults.NUMBERTYPE + "", "Equipos", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "VACANTES", DBDefaults.NUMBERTYPE + "", "Vacantes", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "ESTATUSTRN", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Estatus", "", ""),
    };

    public DefDBCardSucEquipo(BEServletInterfase servlet) {
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
