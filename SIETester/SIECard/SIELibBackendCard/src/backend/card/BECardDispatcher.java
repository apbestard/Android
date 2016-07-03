package backend.card;

import backend.BEModule;
import backend.BEObject;
import backend.BEServletInterfase;
import backend.card.def.DefDBCardSuc;
import backend.card.def.DefDBCardSucEquipo;
import backend.card.def.DefDBCardSucPersona;
import backend.card.def.cat.DefDBCATDepartamento;
import backend.card.def.cat.DefDBCATEquipo;
import backend.card.def.cat.DefDBCATMismas;
import backend.card.def.cat.DefDBCATPuesto;
import backend.card.def.cat.DefDBCATResponsable;
import backend.card.def.cat.DefDBCATFormato;
import backend.card.def.cat.DefDBCATUserVisor;
import backend.database.def.DefDBBase;
import backend.card.io.BECardTabla;
import backend.card.io.BECardSuc;
import backend.card.io.BECardSucData;
import backend.card.io.BECardSucUpdate;
import backend.card.io.cat.BECATCardSuc;
import backend.card.io.cat.BEMinuta;
import backend.card.io.db.DBDataInterfase;
import backend.card.io.db.DBCardInterfase;
import backend.security.SecuritySession;
import backend.security.rec.ProfileSubMenu;
import mx.logipax.shared.DBDefaults;
import mx.logipax.shared.objects.Login;
import org.json.JSONObject;

public class BECardDispatcher implements BEModule {

    final BEServletInterfase servlet;
    final BEObject servlets[];
    final BECardTabla betabla;
    final BECardSuc becard;
    final BECardSucData bedata;
    final BECATCardSuc bdcatcard;
    public final DBCardInterfase dbcard;
    public final DBDataInterfase dbdata;

    public BECardDispatcher(BEServletInterfase servlet) {
        this.servlet = servlet;
        betabla = new BECardTabla(servlet);
        becard = new BECardSuc(servlet, this);
        bedata = new BECardSucData(servlet, this);
        bdcatcard = new BECATCardSuc(servlet, this);
        servlets = new BEObject[4];
        servlets[0] = betabla;
        servlets[1] = becard;
        servlets[2] = bedata;
        servlets[3] = bdcatcard;
        dbcard = new DBCardInterfase(servlet, 0, this);
        dbdata = new DBDataInterfase(servlet, 0);
    }

    public void dispose() {
    }

    public String getName() {
        return "card";
    }

    @Override
    public Login logged(Login login) {
        return login;
    }

    public int getIndex(String command) {
        for (int i = 0; i < servlets.length; i++) {
            if (servlets[i].match(command)) {
                return (i);
            }
        }
        return (-1);
    }

    public String getMIME(int index) {
        if (index < 0 || index >= servlets.length) {
            return null;
        }
        return (servlets[index].getMime());
    }

    public String[] processText(int index, String command, JSONObject jobject) {
        if (index < 0 || index >= servlets.length) {
            return null;
        }
        servlet.lastTimeUsed(new java.util.Date().getTime());
        return (servlets[index].responseText(command, jobject));
    }

    public byte[] processBytes(int index, String command, JSONObject jobject) {
        if (index < 0 || index >= servlets.length) {
            return null;
        }
        servlet.lastTimeUsed(new java.util.Date().getTime());
        return (servlets[index].responseBytes(command, jobject));
    }

    public DefDBBase[] getTables() {
        DefDBBase tables[] = new DefDBBase[10];
        tables[0] = new DefDBCardSuc(servlet);
        tables[1] = new DefDBCardSucPersona(servlet);
        tables[2] = new DefDBCardSucEquipo(servlet);
        
        tables[3] = new DefDBCATResponsable(servlet);
        tables[4] = new DefDBCATUserVisor(servlet);
        tables[5] = new DefDBCATDepartamento(servlet);
        tables[6] = new DefDBCATEquipo(servlet);
        tables[7] = new DefDBCATPuesto(servlet);
        tables[8] = new DefDBCATFormato(servlet);
        tables[9] = new DefDBCATMismas(servlet);
        
        return (tables);
    }

    @Override
    public String createTables(SecuritySession session, String options) {
        DefDBBase tables[] = getTables();
        String responseStr = "";
        for (int i = 0; i < tables.length; i++) {
            if ((options.indexOf(getName() + ".*") >= 0) || (options.indexOf(getName() + "." + tables[i].getTableName() + ";") >= 0)) {
                System.out.println("processing:" + tables[i].getTableName());
                if (options.charAt(0) == '-') {
                    responseStr += tables[i].drop(session.corporate);
                } else {
                    responseStr += tables[i].create( -1 ); //session.corporate);//-1
                }

            }
        }
        if (responseStr.length() == 0) {
            return null;
        }
        return (responseStr);
    }

    @Override
    public ProfileSubMenu[] getProfile(SecuritySession securitySession) {
        if ((securitySession.profile.equals(DBDefaults.PROFILEADMINISTRADOR)
                || securitySession.role.indexOf(";CARD") >= 0)) {
            ProfileSubMenu submenu[] = new ProfileSubMenu[1];
            int smnuinx = submenu.length - 1;
            submenu[smnuinx] = new ProfileSubMenu("NA");
            submenu[smnuinx].setDesc("Cédula de Información");
            submenu[smnuinx].order = 400;
            appendMemory(submenu[smnuinx]);
            return submenu;
        }
        return new ProfileSubMenu[0];
    }

    private void appendMemory(ProfileSubMenu submenu) {
        int iteminx = 0;
        submenu.appendMenuItem("Cédula de Información", "card-trn");
        iteminx = submenu.items.length - 1;
        submenu.items[iteminx].setId("CARD");
        submenu.items[iteminx].setDesc("Módulo de Cédula de Información");
        submenu.items[iteminx].setOptions(";EDITABLE;MEMORY;COLUMNS");
        submenu.items[iteminx].setCatTable("");
        submenu.items[iteminx].setCatDisplay("");
        
        submenu.appendMenuItem("Cédula de Información2", "card2-trn");
        iteminx = submenu.items.length - 1;
        submenu.items[iteminx].setId("CARD 2");
        submenu.items[iteminx].setDesc("Módulo de Cédula de Información 2");
        submenu.items[iteminx].setOptions(";EDITABLE;MEMORY;COLUMNS");
        submenu.items[iteminx].setCatTable("");
        submenu.items[iteminx].setCatDisplay("");
        
//        
//        submenu.appendMenuItem("Minutas", "min-trn");
//        iteminx = submenu.items.length - 1;
//        submenu.items[iteminx].setId("MINUTA");
//        submenu.items[iteminx].setDesc("Módulo de Minutas de Dirección");
//        submenu.items[iteminx].setOptions(";EDITABLE;MEMORY;COLUMNS");
//        submenu.items[iteminx].setCatTable("");
//        submenu.items[iteminx].setCatDisplay("");
        
    }
}
