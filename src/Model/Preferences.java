/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class to hold the user preferences for exporting of data from csv to moodle.  
 * 
 * This class will only hold static data that is used by the Parser to modify
 * the data input from the external file.  No modificatinos should take place 
 * in this class.  
 * 
 * 
 * @author mcnabba
 */
public class Preferences {
    
    private String domain = "";
    private boolean removeEmptyColumns = false;
    private boolean hasDomain = false;
    private HashMap<String, String> userHeaders, enrolHeaders;
    private String[] enrolStrings;
    private boolean convertHeaders = false;
    private boolean addEmail = false;
    
    public Preferences()    {
        this.userHeaders = new HashMap();
        this.enrolHeaders = new HashMap();
    }
    
    public String getDomain()   {
        return this.domain;
    }
    
    public void setDomain(String domain)    {
        this.domain = domain;
    }
    
    public boolean removeEmptyColumns()   {
        return this.removeEmptyColumns;
    }
    
    public boolean hasDomain()  {
        return this.hasDomain;
    }
    
    public HashMap<String, String> getUserHeaders()   {
        return this.userHeaders;
    }
    
    public void setUserHeaders(TableModel t) {
        String[][] tempTable = new String[t.getRowCount()][t.getColumnCount()];
        for ( int x = 0; x < t.getRowCount(); x++)  {
            for ( int y = 0; y < t.getColumnCount(); y++)  {
                tempTable[x][y] = (String) t.getValueAt(x, y);
            }
        }
        for ( int x = 0; x < t.getRowCount(); x++ )  {
            userHeaders.put(tempTable[x][0], tempTable[x][1]);
        }
        if ( addEmail ) {
            String temp = userHeaders.remove(UserHeaders.EMAIL.toString());
            temp += domain;
            userHeaders.put(UserHeaders.EMAIL.toString(), temp);
        }
    }
    
    public void setEnrolHeaders(TableModel t)   {
        String[][] tempTable = new String[t.getRowCount()][t.getColumnCount()];
        int courseCount = 1, groupCount = 1, cohortCount = 1;
        for ( int x = 0; x < t.getRowCount(); x++)  {
            for ( int y = 0; y < t.getColumnCount(); y++)  {
                tempTable[x][y] = (String) t.getValueAt(x, y);
            }
        }
        //itterate the headers so that they are moodle compatible
        for ( int x = 0; x < t.getRowCount(); x++ )  {
            if ( tempTable[x][1].equalsIgnoreCase(EnrolHeaders.COURSE.toString()))    {
                enrolHeaders.put(tempTable[x][0], tempTable[x][1] + courseCount);
                courseCount++;
            }   else if ( tempTable[x][1].equalsIgnoreCase(EnrolHeaders.GROUP.toString()))    {
                enrolHeaders.put(tempTable[x][0], tempTable[x][1] + groupCount);
                groupCount++;
            }   else if ( tempTable[x][1].equalsIgnoreCase(EnrolHeaders.COHORT.toString()))   {
                enrolHeaders.put(tempTable[x][0], tempTable[x][1] + cohortCount);
                cohortCount++;
            }
        }
    }
    
    public void writePreferences() throws URISyntaxException  {
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //root element
            Element root = doc.createElement("Preferences");
            doc.appendChild(root);
            //header elements
            Element username = doc.createElement("username");
            username.appendChild(doc.createTextNode(userHeaders.get(UserHeaders.USERNAME.toString())));
            root.appendChild(username);
            
            Element password = doc.createElement("password");
            password.appendChild(doc.createTextNode(userHeaders.get(UserHeaders.PASSWORD.toString())));
            root.appendChild(password);
            
            Element firstname = doc.createElement("firstname");
            firstname.appendChild(doc.createTextNode(userHeaders.get(UserHeaders.FIRSTNAME.toString())));
            root.appendChild(firstname);
            
            Element lastname = doc.createElement("lastname");
            lastname.appendChild(doc.createTextNode(userHeaders.get(UserHeaders.LASTNAME.toString())));
            root.appendChild(lastname);
            
            Element email = doc.createElement("email");
            email.appendChild(doc.createTextNode(userHeaders.get(UserHeaders.EMAIL.toString())));
            root.appendChild(email);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
    
            Transformer transformer;
            try {
                transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(getClass().getResource("/xml/prefs.xml").toURI()));
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void openPreferences()   {
        
        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(getClass().getResourceAsStream("/xml/prefs.xml"));
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            
            //read the nodes from the document and build the preferences tree
            for (int i = 0; i < nodeList.getLength(); i++ ) {
                
            }
            
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
