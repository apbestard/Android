package common.security.xml;

import beutility.XML;
import beutility.XMLModel;
import beutility.XMLModelInterfase;


public class XMLBEDirectories implements XMLModelInterfase {
    public static final String SERVLETNAME = "login";
    static final String TAGDIRECTORIES = "directories";
    static final String TAGTICKET = "ticket";
    final XMLModel model;
    boolean fdirectories = false;
    public String ticket = null;

    public XMLBEDirectories() {
        this.model = new XMLModel(this);
    }

    public boolean parse(char[] xml) {
        fdirectories = false;
        ticket = "";
        return (model.parse(xml));
    }

    public void attrib(String element[], String attrib, String value) {
    }

    public void next(String element[], String chars, String chars2) {
        if ((chars != null) && (element.length > 1)) {
            if (element[1].equals(TAGDIRECTORIES)) {
                if ((element.length == 3) && element[2].equals(TAGTICKET)) {
                    fdirectories = true;
                    ticket = XML.getFromXML(chars);
                } else {
                    XML.noprocess(element, chars);
                }
            } else {
                XML.noprocess(element, chars);
            }
        }
    }

    public boolean isAccounts() {
        return (fdirectories);
    }

    public static String getServlet(String url) {
        return (SERVLETNAME);
    }

    public static String getXMLDirectories(String ticket) {
        String xml = "<xml>";
        xml += "<" + TAGDIRECTORIES + ">";
        xml += "<" + TAGTICKET + ">" + XML.getToXML(ticket) + "</" + TAGTICKET + ">";
        xml += "</" + TAGDIRECTORIES + ">";
        xml += "</xml>";
        return (xml);
    }
}
