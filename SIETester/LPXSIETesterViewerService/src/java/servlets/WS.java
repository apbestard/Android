/*
 Derechos Reservados (c)
 Ing. Jorge Guzmán Camarena / Consultoria Integral en Sistemas y Finanzas, S.A. de C.V.
 2009, 2010, 2011, 2012, 2013, 2014
 logipax Marca Registrada (R)  2003, 2014
 */
package servlets;

import backend.BEModule;
import backend.card.BECardDispatcher;
import backend.prfl.BEProfileDispatcher;
import backend.viewer.BEViewerDispatcher;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.logipax.shared.DBDefaults;
import mx.logipax.utility.Arrays2;
import mx.logipax.utility.Utility;
import org.json.JSONObject;
import org.json.JSONTokener;

@WebServlet(name = "WS", urlPatterns = {"/WS"})
public class WS extends HttpServlet implements Runnable {

    ServletContext servletContext = null;
    DBProperties start = null;
    public static backend.BEServlet dispatcher = null;
    volatile Thread dispatcherBackground = null;
    java.util.Date today = null;
    String path2 = "NONE";
//    String starterror = "";

    public String propertiesPath(String dbproperties) {
        String path = servletContext.getRealPath(dbproperties);
        if (path == null) {
            return (null);
        }
        char adata[] = path.toCharArray();
        int separator[] = new int[0];
        for (int i = 0; i < adata.length; i++) {
            if (adata[i] == '\\' || adata[i] == '/') {
                separator = Arrays2.append(separator, i);
            }
        }
        if (separator.length < 2) {
            return (null);
        }
        String path2 = path.substring(0, separator[separator.length - 2]);
        path2 += path.substring(separator[separator.length - 1]);
        return (path2);
    }

    private void reload() {
        String path = servletContext.getRealPath("/db.properties");
        DBProperties.load(start.getProperties(), path);
    }

    String[][] values = new String[][]{
        {"id", "SIE"},
        {"name", "SIE Visor"},
        {"corporates.number", "1"},
        {"corporate.0.id", "1"},
        {"corporate.0.name", "SIE"},
        {"drivers", "oracle.jdbc.driver.OracleDriver"},
        {"logfile", "dblog.txt"},
        {"logpath", "./logs/"},
        {"cachepath", "./"},
        {"local.url", "jdbc:oracle:thin:@172.16.210.30:1534:DWHPROA1"},
        {"local.user", "USER_SIE"},
        {"local.password", "5e395147524f341"},
        {"local.maxconn", "250"},
        {"dw.url", "jdbc:oracle:thin:@172.16.210.30:1534:DWHPROA1"},
        {"dw.user", "USER_SIE"},
        {"dw.password", "5e395147524f341"},
        {"dw.maxconn", "250"},
        {"dw0.url", "jdbc:oracle:thin:@172.16.210.30:1534:DWHPROA1"},
        {"dw0.user", "USER_SIE"},
        {"dw0.password", "5e395147524f341"},
        {"dw0.maxconn", "250"},};

    public java.util.Properties load(java.util.Properties dbProps, String path) {
        java.io.InputStream is = null;
        //starterror += "l.0(" + path + ")-";
        //System.out.println("load db.properties="+path);
        try {
            //starterror += "l.1-";
            is = new java.io.FileInputStream(new java.io.File(path));
            java.util.Properties props = new java.util.Properties();
            //starterror += "l.2-";
            props.load(is);
            //System.out.println("load db.properties");
            for (java.util.Enumeration e = props.keys(); e.hasMoreElements();) {
                //starterror += "l.3-";
                String key = (String) e.nextElement();
                String value = props.getProperty(key);
                //System.out.println("key="+key+" value="+value);
                //System.setProperty(key, value);
                dbProps.setProperty(key, value);
            }
            //starterror += "l.4-";
        } catch (Exception ex) {
            //starterror += "l.5(" + ex.getMessage() + ")-";
            System.err.println("Can't read the properties file. "
                    + "Make sure .properties is in the CLASSPATH" + path);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ex) {
                }
            }
        }
        //System.out.println("logipax.properties loaded");
        return (dbProps);
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //starterror = "1-";
        servletContext = config.getServletContext();
        try {
            String path = servletContext.getRealPath("/db.properties");
            if (path != null) {
                //starterror += "1.1(" + path + ")-";

                java.util.Properties dbProps = new java.util.Properties();
                java.util.Properties properties = load(dbProps, path);
                //starterror += "1.3-";

                start = new DBProperties(path);
                //starterror += "1.2-";
                String path2 = null;// propertiesPath("db.properties");
                //starterror += "1.3-";
                if (path2 != null) {
                    this.path2 = path2;
                    if (new java.io.File(path2).exists()) {
                        this.path2 += " existe!";
                        if (new java.io.File(path2).canRead()) {
                            this.path2 += " canread!";
                        }
                        if (new java.io.File(path2).canWrite()) {
                            this.path2 += " canwrite!";
                        }
                    }
                    start = new DBProperties(start.properties, path2);
                }
            } else {
                //starterror += "1.9-";
                start = new DBProperties(values);
            }
            String drivers = start.properties.getProperty("drivers");
            if (drivers.indexOf("mysql") > 0) {
                DBDefaults.MySQL();
            } else {
                DBDefaults.Oracle();
            }
            //starterror += "2.0-";
            java.text.DateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
            String date = format.format(new java.util.Date().getTime() + 900000);
            //starterror += "2.1-";
            String logpath = start.getProperties().getProperty("logpath", "");
            //starterror += "2.2("+logpath+")-";
            String lpath = servletContext.getRealPath(logpath);
            //starterror += "2.3("+lpath+")-";
            if (lpath == null) {
                lpath = servletContext.getRealPath("logs/");
            }
            //starterror += "2.4("+lpath+")-";
            if (lpath == null) {
                lpath = servletContext.getRealPath("");
            }
            //starterror += "2.5("+lpath+")-";
            //start.getProperties().setProperty("logpath", lpath);
            //starterror += "2.9-";
            dispatcher = new backend.BEServlet(start.getId(), start.getName(), servletContext.getRealPath("/"), start.getProperties());
            dispatcher.clearCache("reports");
            dispatcher.clearCache("datas");
            //starterror += "3-";
            BEProfileDispatcher profile = new BEProfileDispatcher(dispatcher);
            BEViewerDispatcher viewer = new BEViewerDispatcher(dispatcher);
            BECardDispatcher card = new BECardDispatcher(dispatcher);
            dispatcher.appendModules(new BEModule[]{viewer, card, profile});
            //starterror += "4-";
            dispatcherBackground = new Thread(this);
            dispatcherBackground.setPriority(Thread.MIN_PRIORITY);  // be a good citizen
            dispatcherBackground.start();
            //starterror += "5-";
            dispatcher.dblog("SIE", "Started!");
        } catch (Exception ex) {
            //starterror += ex.toString();
            System.err.println(ex.toString());
        }
    }

    private void restartServer() {
        dispatcher.dblog("EndOfDay", "Stop Dispatcher");
        dispatcher.dispose();
        dispatcher = null;
        dispatcher = new backend.BEServlet(start.getId(), start.getName(), servletContext.getRealPath("/"), start.getProperties());
        dispatcher.dblog("EndOfDay", "Start Dispatcher");
        dispatcher.clearCache("reports");
        dispatcher.clearCache("datas");
        BEProfileDispatcher profile = new BEProfileDispatcher(dispatcher);
        BEViewerDispatcher viewer = new BEViewerDispatcher(dispatcher);
        BECardDispatcher card = new BECardDispatcher(dispatcher);
        dispatcher.appendModules(new BEModule[]{viewer, card, profile});
        dispatcher.dblog("SIE", "Started!");
    }

    public void run() {
        Thread thisThread = Thread.currentThread();
        //dispatcher.timer(true);
        while (dispatcherBackground == thisThread) {
            try {
                thisThread.sleep(60000);
                if (dispatcher != null) {
                    String error = dispatcher.timer(false);
                    if (error == null) { //ADMIN
                        restartServer();
                    }
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void destroy() {
        Thread thisThread = dispatcherBackground;
        dispatcherBackground = null;
        thisThread.interrupt();
        if (dispatcher != null) {
            dispatcher.dispose();
            dispatcher = null;
        }
    }

    private static void message(String title, String message, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("application/xml;charset=UTF-8");
            out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + title + "</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + message + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return;
    }

    private static void error(String error, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            response.setContentType("application/xml;charset=UTF-8");
            out = response.getWriter();
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ERROR</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Error: " + error + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("command");
        if (command != null && command.equals("restartserver")) {
            restartServer();
            BEModule[] modules = dispatcher.modules();
            String sm = "Modulos: ";
            for (int i = 0; i < modules.length; i++) {
                if (i > 0) {
                    sm += ", ";
                }
                sm += modules[i].getName();
            }
            message("RESTART", sm, response);
            return;
        }
        String certificate = request.getParameter("certificate");
        String encryption = request.getParameter("encryption");
        String zip = request.getParameter("zip");
        String b61 = request.getParameter("b64");
        String input = request.getParameter("input");
        if (command == null) {
            String reload = request.getParameter("reload");
            if (reload != null && reload.length() > 0) {
                reload();
                error("reload", response);
                return;
            }
            error("parámetros invalidos", response);
            return;
        }
        String[] results = results = new String[]{null, null, null};
        String mime = "";
        byte[] bresults = null;
        try {
            String inputtxt = "";
            if (input != null) {
                inputtxt = Utility.jsonDecode((zip != null && zip.equals("true")), encryption, certificate, input);
            }
            JSONObject jsoninput = new JSONObject();
            if (inputtxt.length() > 0) {
                jsoninput = new JSONObject(new JSONTokener(inputtxt));
                jsoninput.put("ip", request.getRemoteAddr());
            }
            if (dispatcher == null) {
                results = new String[]{null, null, "No esta cargado el despachador"};// + starterror};

            } else {
                int index[] = dispatcher.getIndex(command);
                mime = dispatcher.getMIME(index);
                if (mime == null) {
                    results = new String[]{null, null, "Comando invalido"};
                }
                if (mime.indexOf("text") >= 0) {
                    results = dispatcher.processText(index, command, jsoninput);
                } else if (mime.indexOf("image/") >= 0) {
                    bresults = dispatcher.processBytes(index, command, jsoninput);
                    if (bresults == null) {
                        results = new String[]{null, null, "Error en procesamiento"};
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error Interno!");
            System.out.println(ex.toString());
            ex.printStackTrace(System.out);
            dispatcher.trace(ex.getStackTrace());
            System.err.println(ex.getMessage());
            System.out.println(ex.getMessage());
            results = new String[]{null, null, "Error interno: " + ex.getMessage()};
        } finally {
            String result = results[0];
            String ok = results[1];
            String error = results[2];
            if ((mime.indexOf("zip") >= 0)) {
                response.setContentType("application/octet-stream");
            } else {
                if (bresults != null) {
                    response.setContentType("image/x");
                } else {
                    response.setContentType("application/json;charset=UTF-8");
                }
            }
            OutputStream os = response.getOutputStream();
            if (bresults != null) {
                os.write(bresults);
            } else if (error != null) {
                os.write(Utility.jsonEncodeResponse((mime.indexOf("zip") >= 0), null, null, "{\"error\":\"" + error + "\"}}"));
            } else if (ok != null) {
                os.write(Utility.jsonEncodeResponse((mime.indexOf("zip") >= 0), null, null, "{\"ok\":\"" + ok + "\"}}"));
            } else if (result != null) {
                os.write(Utility.jsonEncodeResponse((mime.indexOf("zip") >= 0), null, null, result));
            } else {
                os.write(Utility.toBytes("null"));
            }
            os.flush();
            os.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "WS LSF";
    }
}
