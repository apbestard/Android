package backend.card.def;

import backend.BEServletInterfase;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;
import mx.logipax.shared.DBDefaults;

public class DefDBCardSucPersona extends DefDBBase {

    private static String tableModelName2 = DBDefaults.OWNER+"SIE_CRD_PERSONAL";
    private static String tableTagName2 = DBDefaults.OWNER+"SIE_CRD_PERSONAL";
    private static String tableDescName2 = "Personal";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.NUMBERCLASS, "CARD_NIVEL", DBDefaults.NUMBERTYPE + " NOT NULL", "Nivel", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "CARD_ID", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Id", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "SECUENCIA", DBDefaults.NUMBERTYPE + " NOT NULL", "Secuencia", "", "")
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "DEPARTAMENTO_ID", DBDefaults.STRINGTYPE + "(32)", "Departamento", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "DEPARTAMENTO_NOMBRE", DBDefaults.STRINGTYPE + "(64)", "Departamento Nombre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "PUESTO_ID", DBDefaults.STRINGTYPE + "(32)", "Puesto", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "PUESTO_NOMBRE", DBDefaults.STRINGTYPE + "(64)", "Puesto Nombre", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "PERSONAS", DBDefaults.NUMBERTYPE + "", "Personas", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "VACANTES", DBDefaults.NUMBERTYPE + "", "Vacantes", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "ESTATUSTRN", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Estatus", "", ""),
    };

    public DefDBCardSucPersona(BEServletInterfase servlet) {
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
