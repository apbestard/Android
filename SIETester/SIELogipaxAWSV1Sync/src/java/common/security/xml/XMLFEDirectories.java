package common.security.xml;

import beutility.XML;
import beutility.XMLModel;
import beutility.XMLModelInterfase;
import beutility.Arrays2;

public class XMLFEDirectories implements XMLModelInterfase {
    static final String TAGOK = "ok";
    static final String TAGERROR = "error";    
    
    static final String TAGDIRECTORIES = "directories";
    static final String TAGDIRECTORY = "directory";

    
    final XMLModel model;
    String servlet = null;
    String ok = null;
    String error = null;
    String directories[] = null;
    
    public XMLFEDirectories() {
        this.model = new XMLModel(this);
    }
    
    public boolean parse(char xml[]) {
        servlet = null;
        directories = new String[0];
        ok = null;
        error = null;

//System.out.println("in="+String.valueOf(xml));        
        return(model.parse(xml));
    }


   public void attrib(String element[], String attrib, String value) {
    }
    
    public void next(String element[], String chars, String chars2) {
         if ((chars != null) && (element.length > 1)) {
            if (element[1].equals(TAGOK)) {
                ok = XML.getFromXML(chars);
            } else 
            if (element[1].equals(TAGERROR)) {
                error = XML.getFromXML(chars);
            } else if (element[1].equals(TAGDIRECTORIES)) {
                if ((element.length == 3) && element[2].equals(TAGDIRECTORY)) {
                    directories = Arrays2.append(directories, chars);
                } else {
                    XML.noprocess(element, chars);
                }
            } else {
                XML.noprocess(element, chars);
            }
        }
    }

    public String getServlet() {
        return(servlet);
    }
    public String getOk() {
        return(ok);
    }
    public String getError() {
        return(error);
    }
    
    public String[] getAccounts() {
        return(directories);
    }


    public static String getXMLDirectories(String directories[]) {
        String xml = "<xml>";
        xml += "<" + TAGDIRECTORIES + ">";
        for (int i=0;i<directories.length;i++) {
            xml += "<" + TAGDIRECTORY + ">" + XML.getToXML(directories[i]) + "</" + TAGDIRECTORY + ">";
        }
        xml += "</" + TAGDIRECTORIES + ">";
        xml += "</xml>";
        return (xml);
    }

}
