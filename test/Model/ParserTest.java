/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.io.File;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mcnabba
 */
public class ParserTest {
    
    public ParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of read method, of class Parser.
     */
    @Test
    public void testRead() {
        System.out.println("read");
        File f = null;
        Parser instance = new Parser();
        instance.read(f);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getData method, of class Parser.
     */
    @Test
    public void testGetData() {
        System.out.println("getData");
        Parser instance = new Parser();
        ArrayList expResult = null;
        ArrayList result = instance.getData();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getHeaders method, of class Parser.
     */
    @Test
    public void testGetHeaders() {
        System.out.println("getHeaders");
        Parser instance = new Parser();
        String[] expResult = null;
        String[] result = instance.getHeaders();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of clear method, of class Parser.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Parser instance = new Parser();
        instance.clear();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrefs method, of class Parser.
     */
    @Test
    public void testGetPrefs() {
        System.out.println("getPrefs");
        Parser instance = new Parser();
        Preferences expResult = null;
        Preferences result = instance.getPrefs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
