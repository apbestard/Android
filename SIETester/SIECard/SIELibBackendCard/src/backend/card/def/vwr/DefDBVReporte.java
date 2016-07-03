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

public class DefDBVReporte extends DefDBBase {
    private static String tableModelName2 = DBDefaults.OWNER+"sie_vwr2x0_reporte";
    public static String tableTagName2 = DBDefaults.OWNER+"sie_vwr2x0_reporte";
    private static String tableDescName2 = "Reporte";
    private static DefDBField keys2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "reporte_id", DBDefaults.STRINGTYPE+"(12) NOT NULL", "Reporte", "", ""),
    };
    private static DefDBField fields2[] = {
        new DefDBField(DBDefaults.STRINGCLASS, "reporte2_id", DBDefaults.STRINGTYPE+"(12)", "Reporte Eq", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "indice", DBDefaults.NUMBER4TYPE + " NOT NULL", "Indice", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "modulo_id", DBDefaults.STRINGTYPE+"(4) NOT NULL", "Módulo", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "pool", DBDefaults.STRINGTYPE+"(20)", "Pool", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "pretabl0", DBDefaults.STRINGTYPE+"(20)", "Tables", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "pretabl1", DBDefaults.STRINGTYPE+"(620)", "Tables", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "estructura1_id", DBDefaults.STRINGTYPE+"(20)", "Estructura", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "estructura2_id", DBDefaults.STRINGTYPE+"(20)", "Estructura Producto 1", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "estructura3_id", DBDefaults.STRINGTYPE+"(20)", "Estructura Product 2", "", ""),
        new DefDBField(DBDefaults.NUMBERCLASS, "colfija", DBDefaults.NUMBER4TYPE + " NOT NULL", "Columnas Fijas", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "atributos", DBDefaults.STRINGTYPE + "(1024)", "Atributos", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "data_id", DBDefaults.STRINGTYPE+"(20) NOT NULL", "Datos", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "nombre", DBDefaults.STRINGTYPE+"(64) NOT NULL", "Nombre", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "descripcion", DBDefaults.STRINGTYPE+"(128) NOT NULL", "Descripción", "", ""),
        new DefDBField(DBDefaults.STRINGCLASS, "md5", DBDefaults.STRINGTYPE+"(34)", "md5", "", ""),
        new DefDBField(DBDefaults.PROPERTIESCLASS, "properties", DBDefaults.PROPERTIESTYPE, "Properties", "", ""),
    };

    public DefDBVReporte(BEServletInterfase servlet) {
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
        super.addIndex(new DefDBIndex(true, new int[]{2,0}));
    }
    
}
