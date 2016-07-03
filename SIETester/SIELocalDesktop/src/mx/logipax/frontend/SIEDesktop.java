/*
Derechos Reservados (c)
Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
2009, 2010, 2011, 2012, 2013, 2014
logipax Marca Registrada (R)  2003, 2014
*/
package mx.logipax.frontend;

//VMOptions -Xdock:name=SIEDesktop
import backend.BEModule;
import backend.card.BECardDispatcher;
import backend.prfl.BEProfileDispatcher;
import backend.viewer.BEViewerDispatcher;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.swing.UIManager;
import mx.logipax.db.interfase.FEMain;
import mx.logipax.frontend.prfl.SIEProfileModule;
import mx.logipax.ide.profile.Profile;
import mx.logipax.frontend.security.FEAlive;
import mx.logipax.frontend.security.FELogin;
import mx.logipax.frontend.security.FELogout;
import mx.logipax.frontend.security.FEPassword;
import mx.logipax.frontend.security.SecModule;
import mx.logipax.frontend.security.SecProfile;
import mx.logipax.frontend.system.SysModule;
import mx.logipax.frontend.card.SIECardModule;
import mx.logipax.frontend.viewer.SIEViewerModule;
import mx.logipax.ide.main.BasicApplication;
import mx.logipax.ide.main.SIEMain;
import mx.logipax.shared.DBDefaults;
import mx.logipax.utility.Arrays2;
import mx.logipax.utility.Resources;
import mx.logipax.utility.Strings;
import mx.logipax.shared.backend.BEDispatcher;

public class SIEDesktop extends SIEMain {

    static String VERSION = "Ver 1.00";
    static String SSTUBVERSION = "0.00";
    static String SHOME = "_logipax/";
    static String SENV = "";
    static String SURL = "http://localhost:8080";
    static String SMURL = "http://localhost:8080";
    static String SNAME = "Sistema de Información Ejecutiva";
    static String SDISPATCHER = "/SIEDW0ViewerService/WS";
    static String SMAILER = "/SIEV12Mailer/Mailer";
    static String SSYNC = "/SIEV1Sync";
    static String SEXE = "SIEDesktop.jar";
    static String SOFFLINE = "false";
    static String STUBARGS[] = new String[]{"noversion=true"};
    static String TITLE = SNAME + " " + VERSION;
    static String HTTPSERVLET = SURL + SDISPATCHER;
    static String HTTPMAILER = SMURL + SMAILER;
    static String HTTPSYNC = SURL + SSYNC;
    private String cachepath;

    private static HashMap<String, String> getArgs(String args[]) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        for (int i = 0; i < args.length; ++i) {
            int inx = args[i].indexOf("=");
            String key = Integer.toString(i);
            String value = args[i];
            if (inx > 0) {
                key = args[i].substring(0, inx);
                value = args[i].substring(inx + 1);
            }
            parameters.put(key, value);
        }
        return parameters;

    }

    public static void load(final String args[]) {
        //final boolean local = args.length > 0 && args[0].equals("local");
        try {
            boolean found = false;
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    found = true;
//                    break;
//                }
//            }
            if (!found) {
                //UIManager.setLookAndFeel("mx.logipax.theme.SIELookAndFeel");
                //UIManager.setLookAndFeel("mx.logipax.theme.SIELookAndFeel");
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            Locale.setDefault(Locale.US);
        } catch (Exception ex) {
            System.err.println("error=" + ex.toString());
        }

        HashMap<String, String> parameters = getArgs(args);
        Set<Map.Entry<String, String>> rfset = parameters.entrySet();
        for (Map.Entry<String, String> item : rfset) {
            String name = (String) item.getKey();
            String value = (String) item.getValue();
            if (name.equals("stub")) {
                SSTUBVERSION = value;
            } else if (name.equals("url")) {
                SURL = value;
                STUBARGS = Arrays2.append(STUBARGS, name + "=" + value);
            } else if (name.equals("program")) {
                SPATH = value;
            } else if (name.equals("home")) {
                SHOME = value;
            } else if (name.equals("env")) {
                SENV = value;
                SDATA = SENV + SDATA;
                SPATH = SENV + SPATH;
                STUBARGS = Arrays2.append(STUBARGS, name + "=" + value);
            } else if (name.equals("name")) {
                //SNAME = value;
            } else if (name.equals("dispatcher")) {
                SDISPATCHER = value;
            } else if (name.equals("sync")) {
                SSYNC = value;
            } else if (name.equals("offline")) {
                SOFFLINE = value;
            }
        }
        TITLE = SNAME + " " + VERSION;
        HTTPSERVLET = SURL + SDISPATCHER;
        HTTPMAILER = SMURL + SMAILER;
        HTTPSYNC = SURL + SSYNC;
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new BasicApplication(new SIEMain()).startup();
////                SIEWnd frame = new SIEWnd();
////                //new mx.logipax.theme2.UpdateTheme().update(mx.logipax.theme2.Theme.LOW_VISION, frame);
////                frame.setVisible(true);
//            }
//        });
    }

    public static void main(final String args[]) {
        load(args);
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Locale.setDefault(new Locale("es","MX"));
                byte[] dproperties = Resources.loadResource(this.getClass(), "/db_tester.properties");
                java.util.Properties properties = Strings.loadProperties(dproperties);
                String drivers = properties.getProperty("drivers");
                if (drivers.indexOf("mysql") >= 0) {
                    DBDefaults.MySQL();
                } else if (drivers.indexOf("oracle") >= 0) {
                    DBDefaults.Oracle();
                } else {
                    System.exit(-100);
                }
                String home = System.getProperty("user.home");
                if (!home.endsWith("/") && !home.endsWith("\\")) {
                    home += "/";
                }
                home += "_cache/";
                backend.BEServlet dispatcher = new backend.BEServlet("SIE", "SIELogipax", home, properties);
                BEViewerDispatcher viewer = new BEViewerDispatcher(dispatcher);
                BECardDispatcher card = new BECardDispatcher(dispatcher);
                BEProfileDispatcher profile = new BEProfileDispatcher(dispatcher);
                dispatcher.appendModules(new BEModule[]{viewer, card, profile}); //agreement, cloud, 
                new BasicApplication(new SIEDesktop(TITLE, SHOME, null, null, null, SSTUBVERSION, STUBARGS, dispatcher)).startup();
//                new BasicApplication(new SIEDesktop(TITLE, SHOME, HTTPSERVLET, HTTPSYNC, HTTPMAILER, SSTUBVERSION, STUBARGS, null)).startup();
            }
        });
    }
    protected FELogin login;
    private FELogout logout;
    private FEAlive alive;

    public SIEDesktop(String TITLE, String SHOME, String HTTPSERVLET, String HTTPSYNC, String HTTPMAILER, String SSTUBVERSION, String[] STUBARGS, BEDispatcher dispatcher) {
        super(TITLE, SHOME, HTTPSERVLET, HTTPSYNC, HTTPMAILER, SSTUBVERSION, STUBARGS, dispatcher);
    }

    public Profile profile(FEMain main) {
        SecProfile profile = new SecProfile(main);
        login = new FELogin(main, "admin", "admin", profile, "Control de Acceso");
        logout = new FELogout(main, profile, login);
        alive = new FEAlive(main, profile, login);
        profile.module(new SysModule(main, profile, logout));
        profile.module(new SecModule(main, profile));
        profile.module(new SIEViewerModule(main));
        profile.module(new SIECardModule(main));
        profile.module(new SIEProfileModule(main));
        return profile;

    }

    @Override
    public boolean isLogin(javax.swing.JPanel panel) {
        return (panel == login);
    }

    public void loginStart() {
        login.start();
    }

    public void loginNoStart() {
        login.nostart();;
    }

    public void aliveLogged() {
        alive.logged();
    }

    public void aliveLogout() {
        alive.logout();
    }

    public void aliveEventRequest(String event) {
        alive.eventRequest(event);
    }

    public void password(FEMain main, String name) {
        FEPassword.create(main, name);
    }

    public void logoutStart() {
        logout.start();
    }

    public void logoutDispose() {
        logout.dispose();
    }
///////////////////    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainpn = new java.awt.Panel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SIE");

        org.jdesktop.layout.GroupLayout mainpnLayout = new org.jdesktop.layout.GroupLayout(mainpn);
        mainpn.setLayout(mainpnLayout);
        mainpnLayout.setHorizontalGroup(
            mainpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        mainpnLayout.setVerticalGroup(
            mainpnLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 341, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(0, 0, 0)
                    .add(mainpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 341, Short.MAX_VALUE)
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(0, 0, 0)
                    .add(mainpn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(0, 0, 0)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Panel mainpn;
    // End of variables declaration//GEN-END:variables
}


