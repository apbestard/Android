package backend.card;

import backend.BEServletInterfase;
import backend.catalogs2.db.DB2CatUpd;
import backend.database.def.DefDBBase;
import mx.logipax.utility.Dates;


public class AppendDefaults {
    
    public static void appendDepartamento(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"SUCURSAL", "Sucursal", new Integer(1), new Integer(0)},
{"RADIOLOGÍA", "Radiología", new Integer(2), new Integer(1)},
{"ULTRASONIDO", "Utrasonido", new Integer(2), new Integer(1)},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1], data[i][2], data[i][3]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
    
    public static void appendPuesto(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"DIRECCIÓN GENERAL", "Dirección General", new Integer(0)},
{"DIRECCIÓN", "Dirección", new Integer(1)},
{"SUBDIRECCION", "Subdirección", new Integer(-1)},
{"GERENCIA", "Gerencia", new Integer(-1)},
{"GERENCIA REGIONAL", "Gerencia Regional", new Integer(2)},
{"GERENCIA SUCURSAL", "Gerencia Sucursal", new Integer(3)},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1], data[i][2]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
    public static void appendEquipo(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"RADIOLOGIA 01", "Radiología", new Integer(1)},
{"RADIOLOGÍA 02", "Radiología", new Integer(2)},
{"ULTRASONIDO 03", "Utrasonido", new Integer(2)},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1], data[i][2]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }

    
    public static void appendFormato(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"CADEM", "CADEM"},
{"GRANDE B", "Grande B"},
{"GRANDE", "Grande"},
{"MEDIANA", "Mediana"},
{"CHICA", "Chica"},
{"MÓVIL", "Móvil"},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
 
    public static void appendMismas(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"NUEVA", "Nueva"},
{"EN MADURACIÓN", "En Maduración"},
{"MISMAS", "Grande"},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
    public static void appendResponsable(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"R01", "Responsable 01", new Integer(0)},
{"R02", "Responsable 01", new Integer(1)},
{"R03", "Responsable 01", new Integer(2)},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1], data[i][2]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }

    public static void appendUserVisor(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"U01", "Usuario 01", new Integer(0)},
{"U02", "Usuario 01", new Integer(1)},
{"U03", "Usuario 01", new Integer(2)},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1], data[i][2]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
    
    public static void appendSucursal(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"CORPORATIVO", "Corporativo", "CORPORATIVO"},
{"CORPORATIVO OCCIDENTE", "Corporativo Occidente", "CORPORATIVO"},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1], data[i][2]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
    public static void appendPosicion(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"DIRECCIÓN GENERAL", "Dirección General", "DIRECCIÓN GENERAL", "DIRECCIÓN GENERAL", "CORPORATIVO", "", "carlos.septien", "ASIGNADO"},
{"DIRECCIÓN OPERACIONES CENTRO", "Dirección Operaciones Centro", "OPERACIONES", "DIRECCIÓN", "CORPORATIVO", "DIRECCIÓN GENERAL", "cesar.barragan", "ASIGNADO"},
{"DIRECCIÓN OPERACIONES OCCIDENTE", "Dirección Operaciones Occidente", "OPERACIONES", "DIRECCIÓN", "CORPORATIVO OCCIDENTE", "DIRECCIÓN GENERAL", "carlos.mosqueira", "ASIGNADO"},
{"DIRECCIÓN COMERCIAL", "Dirección Comercial", "COMERCIAL", "DIRECCIÓN", "CORPORATIVO", "DIRECCIÓN GENERAL", "manuel.zapata", "ASIGNADO"},
{"DIRECCIÓN CAPITAL HUMANO", "Dirección Capital Humano", "COMERCIAL", "DIRECCIÓN", "CORPORATIVO", "DIRECCIÓN GENERAL", "juancarlos.toganice", "ASIGNADO"},
{"DIRECCIÓN SISTEMAS", "Dirección Sistemas", "SISTEMAS", "DIRECCIÓN", "CORPORATIVO", "DIRECCIÓN GENERAL", "luis.trejo", "ASIGNADO"},
{"OPERACIONES METRO REGIÓN 1", "Operaciones Región Metro 1", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "", "ASIGNADO"},
{"OPERACIONES METRO REGIÓN 2", "Operaciones Región Metro 2", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "", "ASIGNADO"},
{"OPERACIONES METRO REGIÓN 3", "Operaciones Región Metro 3", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "", "ASIGNADO"},
{"OPERACIONES METRO REGIÓN 4", "Operaciones Región Metro 4", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "", "ASIGNADO"},
{"OPERACIONES METRO REGIÓN 5", "Operaciones Región Metro 5", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "", "ASIGNADO"},
{"OPERACIONES METRO GRUPOS", "Operaciones Queretaro", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "", "ASIGNADO"},
{"OPERACIONES TOLUCA", "Operaciones Toluca", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "", "ASIGNADO"},
{"OPERACIONES PACHUCA", "Operaciones Pachuca", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "", "ASIGNADO"},
{"OPERACIONES QUERETARO", "Operaciones Queretaro", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "", "ASIGNADO"},
{"OPERACIONES CARE", "Operaciones CARE", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO OCCIDENTE", "DIRECCIÓN OPERACIONES OCCIDENTE", "", "ASIGNADO"},
{"OPERACIONES GUADALAJARA", "Operaciones Guadalajara", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO OCCIDENTE", "DIRECCIÓN OPERACIONES OCCIDENTE", "", "ASIGNADO"},
{"OPERACIONES GUANAJUATO", "Operaciones Guanajuato", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO OCCIDENTE", "DIRECCIÓN OPERACIONES OCCIDENTE", "", "ASIGNADO"},
{"OPERACIONES AGUASCALIENTES", "Operaciones Aguascalientes", "OPERACIONES", "GERENTE REGIONAL", "CORPORATIVO OCCIDENTE", "DIRECCIÓN OPERACIONES OCCIDENTE", "", "ASIGNADO"},
            
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1], data[i][2], data[i][3], data[i][4], data[i][5], data[i][6], data[i][7]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
    public static void appendPosEjecutivo(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"DIRECCIÓN GENERAL", "carlos.septien", Dates.getDateFormat("01/07/2015", "dd/MM/yyyy"), "ACTIVO"},
            
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0], data[i][1]}, new Object[]{data[i][2], data[i][3]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
    public static void appendEjecutivo(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"carlos.septien", "Carlos Septien Michel", "carlos.septien@proa.com.mx", "DIRECCIÓN GENERAL", "DIRECCIÓN GENERAL", "CORPORATIVO", "DIRECCIÓN GENERAL", "", Dates.getDateFormat("01/07/2015", "dd/MM/yyyy"), "ACTIVO"},
{"cesar.barragan", "Cesar Barragán", "cesar.barragan@proa.com.mx", "OPERACIONES", "DIRECCIÓN", "CORPORATIVO", "DIRECCIÓN OPERACIONES CENTRO", "DIRECCIÓN GENERAL", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
{"carlos.mosqueira", "Carlos Mosqueira", "carlos.mosqueira@proa.com.mx", "OPERACIONES", "DIRECCIÓN", "CORPORATIVO", "DIRECCIÓN OPERACIONES OCCIDENTE", "DIRECCIÓN GENERAL", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
{"manuel.zapata", "Manuel Zapata", "manuel.zapata@proa.com.mx", "COMERCIAL", "DIRECCIÓN", "CORPORATIVO", "DIRECCIÓN COMERCIAL", "DIRECCIÓN GENERAL", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
{"juancarlos.toganice", "Juan Carlos Toganice", "juancarlos.toganice@proa.com.mx", "CAPITAL HUMANO", "DIRECCIÓN", "CORPORATIVO", "DIRECCIÓN CAPITAL HUMANO", "DIRECCIÓN GENERAL", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
{"luis.trejo", "Luís Trejo", "luis.trejo@proa.com.mx", "SISTEMAS", "DIRECCIÓN", "CORPORATIVO", "DIRECCIÓN SISTEMAS", "DIRECCIÓN GENERAL", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
            
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1], data[i][2], data[i][3], data[i][4], data[i][5], data[i][6], data[i][7], data[i][8], data[i][9]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
    public static void appendGpoTrabajo(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"ADMINISTRACIÓN & CONTROL", "Comité de Administración y Control", "COMITÉ", "ACTIVO"},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1], data[i][2], data[i][3]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
    public static void appendGpoEjecutivo(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"ADMINISTRACIÓN & CONTROL", "carlos.septien", "PRESIDENTE", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
{"ADMINISTRACIÓN & CONTROL", "cesar.barragan", "INTEGRANTE", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
{"ADMINISTRACIÓN & CONTROL", "manuel.zapata", "INTEGRANTE", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
{"ADMINISTRACIÓN & CONTROL", "juancarlos.toganice", "INTEGRANTE", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
{"ADMINISTRACIÓN & CONTROL", "luis.trejo", "INTEGRANTE", Dates.getDateFormat("06/07/2015", "dd/MM/yyyy"), "ACTIVO"},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0], data[i][1]}, new Object[]{data[i][2], data[i][3], data[i][4]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
     
    public static void appendTipoArea(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"CORPORATIVO", "Corporativo"},
{"SUCURSAL", "Sucursal"},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    public static void appendTipoGrupo(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"COMITÉ", "Comité"},
{"REUNIÓN", "Reunión"},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    public static void appendTipoPuesto(BEServletInterfase servlet, DefDBBase defBase) {
        Object data[][] = {
{"PRESIDENTE", "Presidente"},
{"SECRETARIO", "Secretario"},
{"INTEGRANTE", "Integrante"},
        };
        DB2CatUpd db_cat = new DB2CatUpd(servlet.db(), "local", 0, defBase);
        for (int i = 0; i < data.length; i++) {
            db_cat.init(new Object[]{data[i][0]}, new Object[]{data[i][1]}, "A", "system", Dates.todayTimestamp());
            db_cat.insert();
        }
    }
    
}
