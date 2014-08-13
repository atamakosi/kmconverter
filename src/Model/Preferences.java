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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class to hold the user preferences for exporting of data from csv to moodle.  
 * 
 * This class will only hold data that is used by the Parser to modify
 * the data input from the external file.  No modifications should take place 
 * in this class.  
 * 
 * 
 * @author mcnabba
 */
public class Preferences {
    
    private String domain = "";
    private final boolean removeEmptyColumns = false;
    private boolean hasDomain = false;
    private final HashMap<String, String> headers;
    private String[] enrolStrings;
    private final boolean convertHeaders = false;
    private final boolean addEmail = false;
    
    public Preferences()    {
        this.headers = new HashMap<>();
    }
    
    public String getDomain()   {
        return this.domain;
    }
    
    public void setDomain(String domain)    {
        this.domain = domain;
        hasDomain = true;
    }
    
    public boolean removeEmptyColumns()   {
        return this.removeEmptyColumns;
    }
    
    public boolean hasDomain()  {
        return this.hasDomain;
    }
    
    public void setUserHeaders(TableModel t) {
        String[][] tempTable = new String[t.getRowCount()][t.getColumnCount()];
        for ( int x = 0; x < t.getRowCount(); x++)  {
            for ( int y = 0; y < t.getColumnCount(); y++)  {
                tempTable[x][y] = (String) t.getValueAt(x, y);
            }
        }
        for ( int x = 0; x < t.getRowCount(); x++ )  {
            headers.put(tempTable[x][1], tempTable[x][0]);
        }
    }
    
    public void setCourseHeaders(TableModel t)   {
        String[][] tempTable = new String[t.getRowCount()][t.getColumnCount()];
        int courseCount = 1, groupCount = 1, cohortCount = 1;
        for ( int x = 0; x < t.getRowCount(); x++)  {
            for ( int y = 0; y < t.getColumnCount(); y++)  {
                tempTable[x][y] = (String) t.getValueAt(x, y);
            }
        }
        //iterate the headers so that they are moodle compatible.  e.g. Option-subject1 becomes
        //course1, Option-subject2 becomes course2 by appending the count to the end of the category
        for ( int x = 0; x < t.getRowCount(); x++ )  {
            if ( tempTable[x][1].equalsIgnoreCase(EnrolHeaders.COURSE.toString()))    {
                System.out.println(tempTable[x][0] + " " + tempTable[x][1]);
                headers.put(EnrolHeaders.COURSE.toString() + courseCount, tempTable[x][0]);
                courseCount++;
            }   else if ( tempTable[x][1].equalsIgnoreCase(EnrolHeaders.GROUP.toString()))    {
                headers.put(EnrolHeaders.GROUP.toString() + groupCount, tempTable[x][0]);
                groupCount++;
            }   else if ( tempTable[x][1].equalsIgnoreCase(EnrolHeaders.COHORT.toString()))   {
                headers.put(EnrolHeaders.COHORT.toString() + cohortCount, tempTable[x][0]);
                cohortCount++;
            }
        }
    }
    
    
    public void writePreferences() throws URISyntaxException    {
        try {
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //root element
            Element root = doc.createElement("Preferences");
            doc.appendChild(root);
            
            for (String str : headers.keySet())  {
                Element node = doc.createElement(str);
                node.appendChild(doc.createTextNode(headers.get(str)));
                if (hasDomain && str.equalsIgnoreCase(UserHeaders.USERNAME.toString())) {
                    Attr domainAttr = doc.createAttribute("domain");
                    domainAttr.setValue(domain);
                    node.setAttributeNode(domainAttr);
                }
                root.appendChild(node);
            }
            
                       
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
    
            try {
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                File prefsFile = new File("Preferences.xml");
                try {
                    prefsFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                StreamResult result = new StreamResult(prefsFile);
                transformer.transform(source, result);
            } catch (TransformerException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
