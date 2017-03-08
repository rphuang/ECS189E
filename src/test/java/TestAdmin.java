import api.IAdmin;
import api.core.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by USER on 3/5/2017.
 */
public class TestAdmin {
    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    // Test that calendar year is greater than or equal to current year (2017)
    @Test
    public void testMakeClass1() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }
    // Test when year has past (2016), it should not create
    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    // Test next year (2018)
    @Test
    public void testMakeClass3() {
        this.admin.createClass("Test", 2018, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2018));
    }

    // Test that capacity of class is > 0
    @Test
    public void testMakeClass4() {
        this.admin.createClass("Test", 2017, "Instructor", -1);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeClass5() {
        this.admin.createClass("Test", 2017, "Instructor", 0);
        assertFalse(this.admin.classExists("Test", 2017));
    }
    @Test
    public void testMakeClass6() {
        this.admin.createClass("Test", 2017, "Instructor", 1);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    // Test that className/year pair is unique, A second class with same name/year should not overwrite
    @Test
    public void testMakeClass7() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2017, "Instructor", 10);
        assertEquals(15,this.admin.getClassCapacity("Test", 2017));
    }
    // Test unique class/year pair
    @Test
    public void testMakeClass7a() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2017, "Instructor2", 10);
        assertFalse(this.admin.getClassInstructor("Test", 2017).equals("Instructor2"));
    }


    // Test that no instructor can be assigned to more than two courses in a year
    // Create two classes for an instructor in one year
    @Test
    public void testMakeClass8() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        assertTrue(this.admin.classExists("Test2", 2017));
    }
    // Create two classes for an instructor in different years
    @Test
    public void testMakeClass8a() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2019, "Instructor", 10);
        assertTrue(this.admin.classExists("Test", 2019));
    }
    // Create three classes for an instructor in one year
    @Test
    public void testMakeClass9() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 10);
        this.admin.createClass("Test3", 2017, "Instructor", 12);
        assertFalse(this.admin.classExists("Test3", 2017));
    }

    // Test capacity changes
    // No change to capacity
    @Test
    public void testChangeClass1() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test",2017,15);
        assertEquals(15,this.admin.getClassCapacity("Test", 2017));
    }
    // Test changing to a lower capacity
    @Test
    public void testChangeClass2() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test",2017,10);
        Integer num = this.admin.getClassCapacity("Test", 2017);
        assertFalse(num.equals(10));
    }

    // Test changing capacity to 0
    @Test
    public void testChangeClass2a() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test",2017,0);
        Integer num = this.admin.getClassCapacity("Test", 2017);
        assertFalse(num.equals(0));
    }

    @Test
    public void testChangeClass3() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.changeCapacity("Test",2017,20);
        assertEquals(20,this.admin.getClassCapacity("Test", 2017));
    }
}
