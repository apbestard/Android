package backend.card.def;

import backend.BEServletInterfase;
import backend.database.def.DefDBBase;
import backend.database.def.DefDBField;
import backend.database.def.DefDBIndex;
import mx.logipax.shared.DBDefaults;

public class DefDBCardSuc extends DefDBBase {

    private static String tableModelName2 = DBDefaults.OWNER+"SIE_CRD_CARDSUC";
    private static String tableTagName2 = DBDefaults.OWNER+"SIE_CRD_CARDSUC";
    private static String tableDescName2 = "Cédula de Sucursal";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.NUMBERCLASS, "CARD_NIVEL", DBDefaults.NUMBERTYPE + " NOT NULL", "Nivel", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "CARD_ID", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Id", "", "")
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "CARD_NOMBRE", DBDefaults.STRINGTYPE + "(64) NOT NULL", "Nombre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "RESPONSABLE_ID", DBDefaults.STRINGTYPE + "(32)", "Responsable", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "RESPONSABLE_NOMBRE", DBDefaults.STRINGTYPE + "(64)", "Responsable Nombre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "USERVISOR_ID", DBDefaults.STRINGTYPE + "(32)", "Visor", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "USERVISOR_NOMBRE", DBDefaults.STRINGTYPE + "(64)", "Visor Nombre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "EMAIL", DBDefaults.STRINGTYPE+"(64)", "EMail", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "FORMATO_ID", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Formato", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "MISMAS_ID", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Mismas", "", ""),
        new DefDBField(DBDefaults.DATECLASS, "FECHA_APERTURA", DBDefaults.DATETYPE + "  NOT NULL", "Fecha Apertura", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "ESTATUSTRN", DBDefaults.STRINGTYPE + "(32) NOT NULL", "Estatus", "", ""),
    };
    
    public DefDBCardSuc(BEServletInterfase servlet) {
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
