package servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Uploader extends HttpServlet {

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

    protected void processRequest(String message, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>BELibrary</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Message:" + message + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest("ERROR", request, response);
    }
    public final static byte ZERO = (byte) '0';
    public final static int LENGTH_MAX_DIGITS = 9;

    public static int readLength(byte[] buffer) {
        if (buffer == null || buffer.length != LENGTH_MAX_DIGITS) {
            return -1;
        }
        int value = 0;
        for (int i = 0; i < LENGTH_MAX_DIGITS; ++i) {
            if (buffer[i] < '0' || buffer[i] > '9') {
                return -1;
            }
            value *= 10;
            value += buffer[i] - ZERO;
        }
        return value;
    }

    private static void readFully(InputStream in, byte[] buffer) throws IOException {
        int bytesRead = 0;
        while (bytesRead < buffer.length) {
            int count = in.read(buffer, bytesRead, buffer.length - bytesRead);
            if (count == -1) {
                throw new IOException("Input stream closed");
            }
            bytesRead += count;
        }
    }

    private static void readFully(InputStream in, OutputStream os, int length) throws IOException {
        int bytesRead = 0;
        byte[] buffer = new byte[1024 * 64];
        while (bytesRead < length) {
            int count = in.read(buffer);
            if (count == -1) {
                throw new IOException("Input stream closed");
            }
            os.write(buffer, 0, count);
            bytesRead += count;
        }
        os.flush();
    }

    private void responseMessage(HttpServletResponse response, String message) {
        PrintWriter out = null;
        try {
            response.setContentType("text/plain");
            out = response.getWriter();
            out.println(message);
        } catch(Exception ex) {
        } finally {
            if (out != null)
                out.close();
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream in = null;
        OutputStream os = null;
        String message = "ERROR: ";
        try {
            in = request.getInputStream();

            byte[] lengthTicketBuf = new byte[LENGTH_MAX_DIGITS];
            readFully(in, lengthTicketBuf);
            int ticketlength = readLength(lengthTicketBuf);
            if (ticketlength < 0) {
                message += "size";
                responseMessage(response, message);
                return;
            }
            byte[] ticketBuf = new byte[ticketlength];
            readFully(in, ticketBuf);
            byte[] lengthIdBuf = new byte[LENGTH_MAX_DIGITS];
            readFully(in, lengthIdBuf);
            int idlength = readLength(lengthIdBuf);
            if (idlength < 0) {
                message += "size";
                responseMessage(response, message);
                return;
            }
            byte[] idBuf = new byte[idlength];
            readFully(in, idBuf);
            String idStr = new String(idBuf);
            String ticket = new String(ticketBuf);
            FileServices files = FileServices.get(home, bkhome, ticket);

            String directories[] = new String[0];
            if (ticket != null && ticket.equals("logipax")) {
                directories = new String[]{"*"};
            } else {
                directories = Service.getDirectories(ticket);
            }
            boolean valid = false;
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
                if (idStr.startsWith(starts)) {
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                message += "ticket";
            } else {
                byte[] lengthDataBuf = new byte[LENGTH_MAX_DIGITS];
                readFully(in, lengthDataBuf);
                int datalength = readLength(lengthDataBuf);
                if (datalength < 0) {
                    message += "size";
                    responseMessage(response, message);
                    return;
                }
                if (datalength == 0) {
                    message = files.remove(new String(idBuf, "UTF-8"));
                } else {
                    message = files.create(new String(idBuf, "UTF-8"), datalength, in);
                }
            }
        } catch (Exception ex) {
            message += ex.toString();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ex) {
                }
            }
        }
        responseMessage(response, message);
    }

    public String getServletInfo() {
        return "Uploader";
    }
}
