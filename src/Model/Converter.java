/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author mcnabba
 */
public class Converter {
    
    private String[] convertedHeaders;
    private Map<String, String> headerPrefs;
    private String domain = "";
    
    //load preferences
    public void init()  {
        headerPrefs = new HashMap<>();
        
        File prefsFile = new File("Preferences.xml");
        if (prefsFile.exists()) {
            openPreferences(prefsFile);
        }   else    {
            System.out.println("Can't locate file preferences.xml.  please run the wizard.");
        }
    }
    
    //using preferences, convert csv to moodle format
    private void openPreferences(File file)   {
        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            if (root.hasChildNodes())    {
                readHeadersToMap(root.getChildNodes());
            } else  {
                System.out.println("Failed to read the XML Root node!");
            }
            
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Failed to read XML!");
        }
    }
    
    private void readHeadersToMap(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++)  {
            Node tmp = nodeList.item(i);
            
            if ( tmp.getNodeType() == Node.ELEMENT_NODE)    {
                headerPrefs.put(tmp.getTextContent(),tmp.getNodeName());

                if (tmp.getNodeName().equalsIgnoreCase("username") && 
                        !tmp.getAttributes().getNamedItem("domain").getNodeValue().isEmpty())  {
                    domain = tmp.getAttributes().getNamedItem("domain").getNodeValue();
                }
                if (tmp.hasChildNodes())    {
                    readHeadersToMap(tmp.getChildNodes());
                }
            }
        }
    }
    
    public String[] convertHeaders(String[] headers)    {
        this.convertedHeaders = new String[headers.length + 1];
        System.arraycopy(headers, 0, convertedHeaders, 0, headers.length);
        convertedHeaders[convertedHeaders.length - 1] = "email";
        //for each mapped value in preferences xml, find and match to the value in 
        for (String str : headerPrefs.keySet()) {
            for (int i = 0; i < convertedHeaders.length; i++)    {
                if ( convertedHeaders[i].equalsIgnoreCase(str) ) {
                    convertedHeaders[i] = headerPrefs.get(str);
                }
            }
        }
        return this.convertedHeaders;
    }
    
    public ArrayList<String[]> convertEmail(ArrayList<String[]> allStudents)    {
        ArrayList<String[]> newStudents = new ArrayList<>();
        String[] headers = allStudents.remove(0);
        if (!domain.equalsIgnoreCase(""))   {
            for (String[] strArray : allStudents)   {
                String[] newArray = new String[strArray.length + 1];
                System.arraycopy(strArray, 0, newArray, 0, strArray.length);
                int userCol = 0;
                for ( int i = 0; i < convertedHeaders.length; i++)  {
                    if (convertedHeaders[i].equalsIgnoreCase(UserHeaders.USERNAME.toString()))   {
                        userCol = i;
                        break;
                    }
                }
                newArray[newArray.length - 1] = strArray[userCol] + domain;
                newStudents.add(newArray);
            }
            newStudents.add(0, headers);
            return newStudents;
        }
        allStudents.add(0, headers);
        return allStudents;
    }
}
