/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.data;

import backend.card.io.ViewRecords.SModule;
import backend.card.io.ViewRecords.SProfile;
import backend.card.io.ViewRecords.SUser;

public class Profile {

    public static String COMERCIALID = "01";
    public static String FINANZASID = "01";

    public static SModule moduleComercial() {
        return new SModule(new String[]{"Administrador"}, COMERCIALID, "COMERCIAL", "");
    }

    public static SModule moduleFinanzas() {
        return new SModule(new String[]{"Administrador"}, FINANZASID, "FINANZAS", "");
    }

    public static SProfile profileAdministrador() {
        return new SProfile("Administrador");
    }

    public static SUser userAdministrador() {
        return new SUser("admin", "Administrador", "Administrador");
    }
}
