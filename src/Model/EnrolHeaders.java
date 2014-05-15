/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author mcnabba
 */
public enum EnrolHeaders {
    
    COURSE("course"), COHORT("cohort"), GROUP("group"), NA("N/A");
    
    private String name;
    
    private EnrolHeaders(String n)  {
        this.name = n;
    }
    
    @Override
    public String toString()  {
        return this.name;
    }
}
