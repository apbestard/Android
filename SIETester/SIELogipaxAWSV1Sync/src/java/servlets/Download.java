package servlets;

import beutility.Strings;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Download extends HttpServlet {
    ServletContext servletContext = null;
    String home = null;
    String bkhome = null;
    public void init(ServletConfig config) throws ServletException {
        super.init(config);                  
        servletContext = config.getServletContext();
        try {
            home = //"/Volumes/DATA"+
                    config.getInitParameter("home");
            bkhome = //"/Volumes/DATA"+
                    config.getInitParameter("home")+"backup/";
        } catch(Exception ex) {
            home = servletContext.getRealPath("")+"/";
            bkhome = servletContext.getRealPath("")+"/backup/";
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String ticket = request.getParameter("ticket");
        FileServices files = FileServices.get(home, bkhome, ticket);
        String restart = request.getParameter("restart");
        if (restart != null && restart.equals("true")) {
            files.restart();
        }
        String id = request.getParameter("id");
        String directories[] = new String[0];
        if (ticket != null && ticket.equals("logipax")) {
            directories = new String[]{"*"};
        } else {
            directories = Service.getDirectories(ticket);
        }
        boolean valid = false;
        if (id != null) {
            for (int i=0;i<directories.length;i++) {
                int all = directories[i].indexOf("*");
                String starts = directories[i];
                if (all > 0) {
                    starts = directories[i].substring(0, all);
                } else
                if (all == 0) {
                    valid = true;
                    break;
                }
                if (id.startsWith(starts)) {
                    valid = true;
                    break;
                }
            }
        }
        if (!valid) {
            try {
                PrintWriter out = response.getWriter();
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Download Error!</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Invalid password</h1>");
                out.println("</body>");
                out.println("</html>");
                out.close();
            } catch (Exception ex) {}
            return;
        }
        String get = request.getParameter("get"); 
        if (get != null && get.equalsIgnoreCase("true")){
            response.setContentType("image/x");
            boolean result = files.sendFile(id, response.getOutputStream());
            return;
        }
        String from = request.getParameter("from");
        long fromNo = 0;
        if (from != null) {
            fromNo = Strings.getLong(from);
        }
        String ssdir = request.getParameter("subdir");
        boolean sdir = ssdir != null && ssdir.equals("true");
        String sodir = request.getParameter("onlydirs");
        boolean odir = sodir != null && sodir.equals("true");
        String result = "";
        result = files.getFilesFrom("", id, sdir, fromNo, odir);
        try {
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.print(result);
            out.close();
        } catch (Exception ex) {}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    public String getServletInfo() {
        return "Download";
    }
}
