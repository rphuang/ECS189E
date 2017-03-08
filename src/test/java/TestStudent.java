import api.IAdmin;
import api.core.impl.Admin;
import api.IInstructor;
import api.core.impl.Instructor;
import api.IStudent;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by USER on 3/5/2017.
 */
public class TestStudent {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setupA() {
        this.admin = new Admin();
    }

    @Before
    public void setupI() { this.instructor = new Instructor();}

    @Before
    public void setupS() { this.student = new Student();}

    // Try to register after creating a class
    @Test
    public void testRegisterClass1() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Student1","Test",2017);
        assertTrue(this.student.isRegisteredFor("Student1", "Test", 2017));
    }
    // Next year
    @Test
    public void testRegisterClass1a() {
        this.admin.createClass("Test", 2018, "Instructor", 15);
        this.student.registerForClass("Student1","Test",2018);
        assertTrue(this.student.isRegisteredFor("Student1", "Test", 2018));
    }
    // Previous year (should not be able to)
    @Test
    public void testRegisterClass1b() {
        this.admin.createClass("Test", 2015, "Instructor", 15);
        this.student.registerForClass("Student1","Test",2015);
        assertFalse(this.student.isRegisteredFor("Student1", "Test", 2015));
    }
    // Try to register without creating a class
    @Test
    public void testRegisterClass2() {
        this.student.registerForClass("Student1","Test",2017);
        assertFalse(this.student.isRegisteredFor("Student1", "Test", 2017));
    }

    // Try to register a second student to a class
    @Test
    public void testRegisterClass3() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Student1","Test",2017);
        this.student.registerForClass("Student2","Test",2017);
        assertTrue(this.student.isRegisteredFor("Student2", "Test", 2017));
    }

    // Try to register a third student to a class after max enrollment capacity
    @Test
    public void testRegisterClass4() {
        this.admin.createClass("Test", 2017, "Instructor", 2);
        this.student.registerForClass("Student1","Test",2017);
        this.student.registerForClass("Student2","Test",2017);
        this.student.registerForClass("Student3","Test",2017);
        assertFalse(this.student.isRegisteredFor("Student3", "Test", 2017));
    }

    // Try to drop a registered class that has not ended (or started)
    @Test
    public void testDropClass1() {
        this.admin.createClass("Test", 2018, "Instructor", 15);
        this.student.registerForClass("Student1","Test",2018);
        this.student.dropClass("Student1", "Test", 2018);
        assertFalse(this.student.isRegisteredFor("Student1", "Test", 2018));
    }

    // Try to drop a registered class that is current (and assuming not ended)
    @Test
    public void testDropClass2() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Student1","Test",2017);
        this.student.dropClass("Student1", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("Student1", "Test", 2017));
    }
    // Try to drop a registered class in the past year, assuming the class was created in the past and has now ended.
    @Test
    public void testDropClass3() {
        this.admin.createClass("Test", 2015, "Instructor", 15);
        this.student.registerForClass("Student1","Test",2015);
        this.student.dropClass("Student1", "Test", 2015);
        assertTrue(this.student.isRegisteredFor("Student1", "Test", 2015));
    }

    // Submit homework after creating class, creating homework, and registering class in current year
    @Test
    public void testSubmitHomework1() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 1", "This is the first assignment");
        this.student.registerForClass("Student1","Test",2017);
        this.student.submitHomework("Student1","Assignment 1", "Some answer",
                "Test", 2017);
        assertTrue(this.student.hasSubmitted("Student1", "Assignment 1","Test",
                2017));
    }

    // Submit homework after creating class, creating homework, and registering class in another year
    @Test
    public void testSubmitHomework2() {
        this.admin.createClass("Test", 2018, "Instructor", 15);
        this.instructor.addHomework("Instructor","Test", 2018,
                "Assignment 1", "This is the first assignment");
        this.student.registerForClass("Student1","Test",2018);
        this.student.submitHomework("Student1","Assignment 1", "Some answer",
                "Test", 2018);
        assertFalse(this.student.hasSubmitted("Student1", "Assignment 1","Test",
                2018));
    }

    // Submit homework after creating class, registering class but not creating homework.
    @Test
    public void testSubmitHomework3() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Student1","Test",2017);
        this.student.submitHomework("Student1","Assignment 1", "Some answer",
                "Test", 2017);
        assertFalse(this.student.hasSubmitted("Student1", "Assignment 1","Test",
                2017));
    }

    // Submit homework after creating class, creating 2 homework, and registering class in current year
    @Test
    public void testSubmitHomework4() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 1", "This is the first assignment");
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 2", "This is the second assignment");
        this.student.registerForClass("Student1","Test",2017);
        this.student.submitHomework("Student1","Assignment 2", "Some answer",
                "Test", 2017);
        assertTrue(this.student.hasSubmitted("Student1", "Assignment 2","Test",
                2017));
    }
}
