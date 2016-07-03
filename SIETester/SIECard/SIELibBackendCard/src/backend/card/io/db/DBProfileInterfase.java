/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package backend.card.io.db;

import backend.card.def.vwr.DefDBVPerfil;
import backend.BEServletInterfase;
import backend.catalogs2.db.DB2CatList;
import backend.catalogs2.db.DB2CatUpd;
import backend.card.def.vwr.DefDBVEstructura;
import backend.card.def.vwr.DefDBVReporte;
import backend.card.io.ViewRecords;
import backend.card.def.vwr.DefDBVModulo;
import mx.logipax.shared.DBDefaults;
import mx.logipax.utility.Arrays2;
import mx.logipax.utility.Dates;

public class DBProfileInterfase {

    BEServletInterfase servlet;
    int corporate;
    DefDBVPerfil db_perfil;
    DB2CatUpd db_cat;

    public class PerfilReporte {

        public String moduleId;
        public String moduleName;
        public String id;
        public String idimage;
        public String pool;
        public String pretab0;
        public String pretab1;
        public String name;
        public String desc;
        public int columnFixed;
        public String attrs;
        public String tree1Name;
        public String tree2Name;
        public int treeHSize;
        public int level;
        public String node;
    }

    public DBProfileInterfase(BEServletInterfase servlet, int corporate) {
        this.servlet = servlet;
        this.corporate = corporate;
        db_perfil = new DefDBVPerfil(servlet);
        db_cat = new DB2CatUpd(servlet.db(), DBDefaults.DBVWRPOOL, corporate, db_perfil);
    }

    public boolean appendPerfil(String perfilId,
            String name, String profile, String email, String phone,
            String password) {
        String perfil = perfilId;
        String activateKey = "0000000000";
//        if (profile.equals(DBDefaults.PROFILEUSER)) {
//            activateKey = Long.toString((long) (Math.random() * 100000000));
//            if (activateKey.length() > 6) {
//                activateKey = activateKey.substring(0, 6);
//            }
//            while (activateKey.length() < 6) {
//                activateKey = "0" + activateKey;
//            }
//            String result = SMTPClient.sendEmail(perfilId, "Premio Obras Cemex", "\nClave de activaci�n: " + activateKey);
//            if (result != null) {
//            }
//        }

//        if (!AppendSecurity.appendAccount(servlet, corporate,
//                perfil,
//                name,
//                password,
//                profile,
//                email,
//                phone,
//                activateKey,
//                db_perfil)) {
//            return false;
//        }
        return true;
    }

    public boolean getPerfil(String perfilId) {
        db_cat.init();
        if (db_cat.get(new Object[]{perfilId})) {
            return (true);
        }
        if (db_cat.get(new Object[]{perfilId + "." + perfilId.toLowerCase()})) {
            return (true);
        }
        return false;
    }

    public String[] getPerfilFields() {
        String perfilId = (String) db_cat.keys[0];
        int inx = perfilId.indexOf(".");
        String customer = perfilId;
        if (inx > 0) {
            customer = perfilId.substring(inx + 1);
            perfilId = perfilId.substring(0, inx);
        }
        String name = (String) db_cat.fields[0];
        String pswd2 = (String) db_cat.fields[1];
        String profile = (String) db_cat.fields[4];
        String email = (String) db_cat.fields[5];
        String phone = (String) db_cat.fields[6];
        String status = db_cat.getStatus();
        return new String[]{perfilId, name, profile, email, phone, status};
    }

    private boolean updatePerfil(String names[], Object values[], String status) {
        String[] fnames = db_perfil.getFieldsName();
        Object[] fvalues = db_cat.getFields();
        if (fvalues == null || fnames == null) {
            return false;
        }
        for (int i = 0; i < names.length; i++) {
            for (int f = 0; f < fnames.length; f++) {
                if (fnames[f].equals(names[i])) {
                    fvalues[f] = values[i];
                    break;
                }
            }
        }
        db_cat.init(db_cat.getKeys(), fvalues, status, db_cat.getUser(), Dates.todayTimestamp());
        boolean result = db_cat.update();
        return result;
    }

    private boolean blockPerfil() {
        String[] fnames = db_perfil.getFieldsName();
        Object[] fvalues = db_cat.getFields();
        db_cat.init(db_cat.getKeys(), fvalues, "C", db_cat.getUser(), Dates.todayTimestamp());
        boolean result = db_cat.update();
        return result;
    }

    public boolean updatePerfil(String perfilId,
            String name, String profile, String email, String phone, String status) {
        if (!getPerfil(perfilId)) {
            return false;
        }
        String[] udata = getPerfilFields();
        int inx = 0;
        String perfilId0 = udata[inx++];
        String name0 = udata[inx++];
        String profile0 = udata[inx++];
        String email0 = udata[inx++];
        String phone0 = udata[inx++];
        String terminal0 = udata[inx++];
        String names[] = new String[0];
        Object values[] = new Object[0];
        if (!name0.equals(name)) {
            names = Arrays2.append(names, "perfil_name");
            values = Arrays2.append(values, name);
        }
        if (!email0.equals(email)) {
            names = Arrays2.append(names, "perfil_email");
            values = Arrays2.append(values, email);
        }
        if (!phone0.equals(phone)) {
            names = Arrays2.append(names, "perfil_phone");
            values = Arrays2.append(values, phone);
        }
        if (names.length > 0) {
            updatePerfil(names, values, status);
        }
        return true;
    }

    public boolean deletePerfil(String perfilId) {
        if (!getPerfil(perfilId)) {
            return false;
        }
//        if (!AppendSecurity.deleteAccount(servlet, corporate,
//                perfilId.toLowerCase() + "." + perfilId.toLowerCase(), db_perfil)) {
//            return blockPerfil();
//        }
        return true;
    }

    public PerfilReporte[] getPerfiles(String profileId, String role, String level, String value) {
        PerfilReporte[] viewer = getViewerPerfiles(profileId);
        PerfilReporte[] calc = getCalcPerfiles(profileId, role);
        PerfilReporte[] profile = new PerfilReporte[viewer.length + calc.length];
        System.arraycopy(viewer, 0, profile, 0, viewer.length);
        System.arraycopy(calc, 0, profile, viewer.length, calc.length);
        return profile;
    }

    public PerfilReporte[] getViewerPerfiles(String perfilId) {
        DB2CatList db_list2 = new DB2CatList(servlet.db(), DBDefaults.DBVWRPOOL,
                new String[]{"r.modulo_id", "m.nombre", "p.reporte_id", "r.reporte2_id", 
                     "r.pool", "r.pretabl0", "r.pretabl1", 
                    "r.nombre", "r.descripcion", "r.colfija", "r.atributos",
            "e1.nombre", "e2.nombre", "e1.hventana", "e2.hventana",
            "p.nivel", "p.nodo"},
                new Class[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, Integer.class, String.class,
            String.class, String.class, Integer.class, Integer.class,
            Integer.class, String.class},
                DefDBVPerfil.tableTagName2 + " p"
                + " join " + DefDBVReporte.tableTagName2 + " r on r.reporte_id = p.reporte_id and r.estatus = 'A'"
                + " join " + DefDBVModulo.tableTagName2 + " m on m.modulo_id = r.modulo_id"
                + " left join " + DefDBVEstructura.tableTagName2 + " e1 on e1.estructura_id = r.estructura1_id"
                + " left join " + DefDBVEstructura.tableTagName2 + " e2 on e2.estructura_id = r.estructura2_id",
                "perfil_id = \'" + perfilId + "\'", "r.indice");
        java.util.Vector data2 = db_list2.getPage();
        int size2 = data2.size();
        PerfilReporte[] perfiles = new PerfilReporte[size2];
        int inx = 0;
        for (int i = 0; i < size2; i++) {
            int rinx = 0;
            Object row[] = (Object[]) data2.elementAt(i);
            perfiles[inx] = new PerfilReporte();
            perfiles[inx].moduleId = (String) row[rinx++];
            perfiles[inx].moduleName = (String) row[rinx++];
            perfiles[inx].id = (String) row[rinx++];
            perfiles[inx].idimage = (String) row[rinx++];
            perfiles[inx].pool = (String) row[rinx++];
            perfiles[inx].pretab0 = (String) row[rinx++];
            perfiles[inx].pretab1 = (String) row[rinx++];
            perfiles[inx].name = (String) row[rinx++];
            perfiles[inx].desc = (String) row[rinx++];
            perfiles[inx].columnFixed = ((Integer) row[rinx++]).intValue();
            perfiles[inx].attrs = (String) row[rinx++];
            perfiles[inx].tree1Name = (String) row[rinx++];
            perfiles[inx].tree2Name = (String) row[rinx++];
            int tree1size = ((Integer) row[rinx++]).intValue();
            int tree2size = ((Integer) row[rinx++]).intValue();
            perfiles[inx].treeHSize = Math.max(tree1size, tree2size);
            perfiles[inx].level = ((Integer) row[rinx++]).intValue();
            perfiles[inx].node = (String) row[rinx++];
            ++inx;
        }
        return perfiles;
    }    
    public static ViewRecords.SReport REPORTS[] = new ViewRecords.SReport[]{
        
    };

    public PerfilReporte[] getCalcPerfiles(String profileId, String role) {
        PerfilReporte[] perfiles = new PerfilReporte[REPORTS.length];
        for (int i = 0; i < REPORTS.length; i++) {
            int inx = i;
            perfiles[inx] = new PerfilReporte();
            perfiles[inx].moduleId = REPORTS[i].moduleId;
            perfiles[inx].moduleName = "Calculadora";
            perfiles[inx].id = REPORTS[i].id;
            perfiles[inx].idimage = REPORTS[i].idimage;
            perfiles[inx].pool = REPORTS[i].pool;
            perfiles[inx].pretab0 = REPORTS[i].pretab0;
            perfiles[inx].pretab1 = REPORTS[i].pretab1;
            perfiles[inx].name = REPORTS[i].name;
            perfiles[inx].desc = REPORTS[i].desc;
            perfiles[inx].columnFixed = REPORTS[i].columnFixed;
            perfiles[inx].attrs = REPORTS[i].attrs;
            perfiles[inx].tree1Name = "";
            perfiles[inx].tree2Name = "";
            perfiles[inx].treeHSize = REPORTS[i].treeHSize;
            perfiles[inx].level = 0;
            perfiles[inx].node = "";
        }
//        int inx = 0;
//        perfiles[inx] = new PerfilReporte();
//        perfiles[inx].moduleId = "CALC";
//        perfiles[inx].moduleName = "Calculadora";
//        perfiles[inx].id = "XNOR";
//        perfiles[inx].name = "Normalidad";
//        perfiles[inx].desc = "Calculadora de Normalidad";
//        perfiles[inx].columnFixed = 0;
//        perfiles[inx].attrs = "";
//        perfiles[inx].tree1Name = "";
//        perfiles[inx].tree2Name = "";
//        perfiles[inx].treeHSize = 200;
//        perfiles[inx].level = 0;
//        perfiles[inx].node = "";
//        ++inx;
//        perfiles[inx] = new PerfilReporte();
//        perfiles[inx].moduleId = "CALC";
//        perfiles[inx].moduleName = "Calculadora";
//        perfiles[inx].id = "XCOB";
//        perfiles[inx].name = "Cobranza";
//        perfiles[inx].desc = "Calculadora de Cobranza";
//        perfiles[inx].columnFixed = 0;
//        perfiles[inx].attrs = "";
//        perfiles[inx].tree1Name = "";
//        perfiles[inx].tree2Name = "";
//        perfiles[inx].treeHSize = 200;
//        perfiles[inx].level = 0;
//        perfiles[inx].node = "";
        return perfiles;
    }
}
