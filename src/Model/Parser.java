/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that will convert the .csv file from SMS to Moodle compatible upload.
 * 
 * Uses the Preferences class to modify a generic file.
 * 
 * @author mcnabba
 */
public class Parser {
    
    private BufferedReader br = null;
    private String line = "";
    private String separator = ",";
    private static ArrayList<String[]> allStudents = new ArrayList();
    private boolean hasHeaders = true;
    private String[] headers;
    private Preferences prefs = new Preferences();
    private String[] tableModel;
    private String fileURL = "";
    
    /**
     * reads .csv file and returns a LinkedList of each students info
     * @param f 
     */
    public void read(File f)    {
        fileURL = f.getAbsolutePath();
        try {
            br = new BufferedReader(new FileReader(f));
            
            while ((line = br.readLine()) != null) {
                String[] studentData = line.split(separator);
                allStudents.add(studentData);
            }
        }   catch (FileNotFoundException e) {
        }   catch (IOException e)   {
        }   finally {
            if ( br != null)    {
                try {
                    br.close();
                } catch (IOException ex) {
                }
            }
        }
    }
    
    /**
     * writes the reformatted file back to .csv for uploading.
     * @param fileUrl
     */
    public void write(String fileUrl) {
        FileWriter writer;
        try {
            writer = new FileWriter(fileUrl);
            for (String[] strArray : allStudents )  {
                for (int i = 0; i < strArray.length; i++)   {
                    if ( i < strArray.length - 1)  {
                        //adds comma after every entry
                        writer.append(strArray[i] + ',');
                    }   else    {
                        //breaks last line without adding a comma
                        writer.append(strArray[i] + '\n');
                    }
                    
                }
            }
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public ArrayList<String[]> getData() {
        return Parser.allStudents;
    }
    
    public String[] getHeaders()    {
        if ( hasHeaders )   {
            this.headers = allStudents.remove(0);
            hasHeaders = false;
        }
        return headers;
    }
    
    private boolean addHeaders(String[] headers)    {
        if ( !hasHeaders )    {
            this.headers = headers;
            allStudents.add(0, headers);
            hasHeaders = true;
            return true;
        }
        return false;
    }
    
    public void clear()  {
        headers = null;
        hasHeaders = true;
        allStudents.clear();
        br = null;
        line = "";
    }
    
    public Preferences getPrefs()   {
        return this.prefs;
    }
   
    public void beginConversion()   {
        Converter c = new Converter();
        c.init();
//        read(new File(fileURL));
        addHeaders(c.convertHeaders(headers));
//        allStudents = c.addEmailColumn(allStudents);
        allStudents = c.convertEmail(allStudents);
        write(fileURL);
    }
}
