/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
public class PreferencesTest {
    
    public PreferencesTest() {
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
     * Test of setUserHeaders method, of class Preferences.
     */
    @Test
    public void testSetUserHeaders() {
        TableModel t = new DefaultTableModel(2, 2);
        t.setValueAt("testValue", 0, 0);
        t.setValueAt("username", 0, 1);
        t.setValueAt("anotherTestValue", 1, 0);
        t.setValueAt("password", 1, 1);
        Preferences instance = new Preferences();
        instance.setUserHeaders(t);
//        assertEquals(t.getValueAt(0, 0), instance.getUserHeaders().get("username"));
//        assertEquals(t.getValueAt(1, 0), instance.getUserHeaders().get("password"));
    }

    /**
     * Test of setCourseHeaders method, of class Preferences.
     */
    @Test
    public void testSetCourseHeaders() {
        TableModel t = new DefaultTableModel(5, 2);
        t.setValueAt("courseMath", 0, 0);
        t.setValueAt(EnrolHeaders.COURSE.toString(), 0, 1);
        t.setValueAt("courseEnglish", 1, 0);
        t.setValueAt(EnrolHeaders.COURSE.toString(), 1, 1);
        t.setValueAt("oneMoreCourse", 2, 0);
        t.setValueAt(EnrolHeaders.COURSE.toString(), 2, 1);
        t.setValueAt("cohortYear", 3, 0);
        t.setValueAt(EnrolHeaders.COHORT.toString(), 3, 1);
        t.setValueAt("groupJuniors", 4, 0);
        t.setValueAt(EnrolHeaders.GROUP.toString(), 4, 1);
        Preferences instance = new Preferences();
        instance.setCourseHeaders(t);
//        assertEquals(t.getValueAt(0, 0), instance.getCourseHeaders().get("course1"));
//        assertEquals(t.getValueAt(1, 0), instance.getCourseHeaders().get("course2"));
//        assertEquals(t.getValueAt(2, 0), instance.getCourseHeaders().get("course3"));
//        assertEquals(t.getValueAt(3, 0), instance.getCourseHeaders().get("cohort1"));
//        assertEquals(t.getValueAt(4, 0), instance.getCourseHeaders().get("group1"));
    }

//    /**
//     * Test of writeUserPreferences method, of class Preferences.
//     */
//    @Test
//    public void testWriteUserPreferences() throws Exception {
//        System.out.println("writeUserPreferences");
//        Preferences instance = new Preferences();
//        instance.writeUserPreferences();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of writePreferences method, of class Preferences.
//     */
//    @Test
//    public void testWriteCoursePreferences() throws Exception {
//        System.out.println("writePreferences");
//        Preferences instance = new Preferences();
//        instance.writePreferences();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of openPreferences method, of class Preferences.
//     */
//    @Test
//    public void testOpenPreferences() {
//        System.out.println("openPreferences");
//        Preferences instance = new Preferences();
//        instance.openPreferences();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
