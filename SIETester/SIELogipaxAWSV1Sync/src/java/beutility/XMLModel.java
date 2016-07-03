package beutility;

import java.io.FileInputStream;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;


public class XMLModel extends DefaultHandler {
    final XMLModelInterfase interfase;
    String element[] = new String[0];
    String chars = null;
    
    public XMLModel(XMLModelInterfase interfase) {
        if (interfase == null)
            this.interfase = new ModelInterfase();
        else
            this.interfase = interfase;
    }
    
    public java.util.Hashtable getData() {
        if (interfase instanceof ModelInterfase) {
            return(((ModelInterfase)interfase).dvalue);
        }
        return(null);
    } 
    
    public boolean parse(String name) {
        try {
            java.io.InputStream baos = new java.io.DataInputStream( new FileInputStream(name));
            javax.xml.parsers.SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(false); // No validation required
            //spf.setNamespaceAware(true);
            javax.xml.parsers.SAXParser sp = spf.newSAXParser();
            org.xml.sax.InputSource input = new InputSource(baos);
            input.setSystemId("xml");
            sp.parse(input, this);
        } catch (ParserConfigurationException ex) {
            System.err.println(ex);
            return(false);
        } catch (SAXException ex) {
            System.err.println(ex);
            return(false);
        } catch (Exception ex) {
            System.err.println(ex);
            return(false);
        } finally {
        }
        return(true);
    }
    
    public boolean parse(char xml[]) {
        try {
            java.io.CharArrayReader baos = new java.io.CharArrayReader(xml);
            javax.xml.parsers.SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(false); // No validation required
            //spf.setNamespaceAware(true);
            javax.xml.parsers.SAXParser sp = spf.newSAXParser();
            org.xml.sax.InputSource input = new InputSource(baos);
            input.setSystemId("xml");
            sp.parse(input, this);
        } catch (ParserConfigurationException ex) {
            System.err.println(ex);
            return(false);
        } catch (SAXException ex) {
            System.err.println(ex);
            return(false);
        } catch (Exception ex) {
            System.err.println(ex);
            return(false);
        } finally {
        }
        return(true);
    }
       
    public void setDocumentLocator(Locator l) {
    }

    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
    }

    String characters[] = new String[10];
    String characterselements[][] = new String[10][];
    
    public void startElement(String namespaceURI,
                             String lName, // local scenarioName
                             String qName, // qualified scenarioName
                             Attributes attrs) throws SAXException {
        String eName = lName; // element scenarioName
        if ("".equals(eName)) eName = qName; // namespaceAware = false
        String element2[] = new String[element.length+1];
        System.arraycopy(element, 0, element2, 0, element.length);
        element2[element.length] = eName;
        element = element2;
        chars = null;
        if (attrs != null) {
            interfase.next(element, null, null);
            for (int i = 0; i < attrs.getLength(); i++) {
                String aName = attrs.getLocalName(i); // Attr title 
                if ("".equals(aName)) aName = attrs.getQName(i);
                String value = attrs.getValue(i).trim();
                interfase.attrib(element, aName, value);
            }
        }
        characters[element.length] = null;
        characterselements[element.length] = null;
    }

    public void endElement(String namespaceURI,
                           String sName, // simple scenarioName
                           String qName  // qualified scenarioName
                          ) throws SAXException {
        if (element.length <= 0) return;
        for (int inx=1;inx<=element.length;inx++) {
            if ((characters[inx] != null) && (characters[inx].length() > 0)) {
                interfase.next(characterselements[inx], characters[inx].trim(), characters[inx]);
                characters[inx] = null;
                characterselements[inx] = null;
            }
        }
        String element2[] = new String[element.length-1];
        System.arraycopy(element, 0, element2, 0, element.length-1);
        element = element2;
    }

    public void characters(char buf[], int offset, int len) throws SAXException {
        String chars2 = new String(buf, offset, len);
        if (characters[element.length] == null) {
            characters[element.length] = "";
            characterselements[element.length] = element;
        }
        if (chars2.indexOf(">") >= 0) {
            characters[element.length] += chars2;
        } else {
            characters[element.length] += chars2;
        }
    }

    class ModelInterfase implements XMLModelInterfase {
        java.util.Hashtable dvalue; 
        ModelInterfase() {
            dvalue = new java.util.Hashtable();
        }
        
        private String getKey(String element[]) {
            if (element.length == 0) return(null);
            String value = element[0];
            for (int i=1;i<element.length;i++) {
                value += "."+element[i];
            }
            return(value);
        }
        
        private void setData(String key, Object[] data) {
            dvalue.put(key, data);
        }
        
        private Object[] getData(String key) {
            Object data[] = (Object[])dvalue.get(key);
            if (data == null || data.length != 2) {
                data = new Object[]{new java.util.Hashtable(), ""};
                setData(key, data);
            }
            if (data[0] instanceof java.util.Hashtable && data[1] instanceof String) {
            } else {
                data = new Object[]{new java.util.Hashtable(), ""};
                setData(key, data);
            }
            return(data);
        }
        
        public void attrib(String element[], String attrib, String value) {
            String key = getKey(element);
            Object[] tdata = getData(key);
            java.util.Hashtable data = (java.util.Hashtable)tdata[0];
            data.put(attrib, value);
            setData(key, tdata);
        }
        public void next(String element[], String chars, String chars2) {
            String key = getKey(element);
            Object[] tdata = getData(key);
            tdata[1] = chars;
        }
    }
    
}
