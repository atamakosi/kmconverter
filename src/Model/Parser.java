/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
    private ArrayList<String[]> allStudents = new ArrayList();
    private boolean hasHeaders = true;
    private String[] headers;
    private Preferences prefs = new Preferences();
    private String[] tableModel;
    
    /**
     * reads .csv file and returns a LinkedList of each students info
     * @param f
     * @return 
     */
    public void read(File f)    {
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
    
    public ArrayList<String[]> getData() {
        return this.allStudents;
    }
    
    public String[] getHeaders()    {
        if ( hasHeaders )   {
            this.headers = allStudents.get(0);
            allStudents.remove(0);
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
   
}
