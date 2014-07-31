/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author mcnabba
 */
public enum UserHeaders {
    
    USERNAME("username"), FIRSTNAME("firstname"), LASTNAME("lastname"), 
    PASSWORD("password");
    
    private String name;
    
    private UserHeaders(String str)
    {
        this.name = str;
    }
    
    @Override
    public String toString()    {
        return this.name;
    }
}
