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
public class TestInstructor {
    private IAdmin admin;
    private IInstructor instructor;
    private IStudent student;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.instructor = new Instructor();
        this.student = new Student();
    }

    // Try to add homework after creating a class and adding instructor to it.
    @Test
    public void testAddHomework1() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 1", "This is the first assignment");
        assertTrue(this.instructor.homeworkExists("Test", 2017,"Assignment 1"));
    }

    // Try to add homework without creating class
    @Test
    public void testAddHomework2() {
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 1", "This is the first assignment");
        assertFalse(this.instructor.homeworkExists("Test", 2017,"Assignment 1"));
    }

    // Try to add homework after creating a class with a different instructor
    @Test
    public void testAddHomework3() {
        this.admin.createClass("Test", 2017, "Instructor2", 15);
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 1", "This is the first assignment");
        assertFalse(this.instructor.homeworkExists("Test", 2017,"Assignment 1"));
    }

    // Assign grade after creating class, adding homework, and having registered student submit homework
    @Test
    public void testAssignGrade1(){
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 1", "This is the first assignment");
        this.student.registerForClass("Student1","Test",2017);
        this.student.submitHomework("Student1","Assignment 1", "Some answer",
                "Test", 2017);
        this.instructor.assignGrade("Instructor","Test",2017,
                "Assignment 1", "Student1", 90);
        Integer grade = this.instructor.getGrade("Test", 2017, "Assignment 1",
                "Student1");
        assertTrue(grade.equals(90));
    }
    // Test assigning a negative score, should be false because percentage implies lower bound by 0
    @Test
    public void testAssignGrade1a(){
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 1", "This is the first assignment");
        this.student.registerForClass("Student1","Test",2017);
        this.student.submitHomework("Student1","Assignment 1", "Some answer",
                "Test", 2017);
        this.instructor.assignGrade("Instructor","Test",2017,
                "Assignment 1", "Student1", -50);
        Integer grade = this.instructor.getGrade("Test", 2017, "Assignment 1",
                "Student1");
        assertFalse(grade.equals(-50));
    }

    // Assign grade with wrong instructor
    @Test
    public void testAssignGrade2(){
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 1", "This is the first assignment");
        this.student.registerForClass("Student1","Test",2017);
        this.student.submitHomework("Student1","Assignment 1", "Some answer",
                "Test", 2017);
        this.instructor.assignGrade("Instructor2","Test",2017,
                "Assignment 1", "Student1", 90);
        Integer grade = this.instructor.getGrade("Test", 2017, "Assignment 1",
                "Student1");
        assertFalse(grade.equals(90));
    }

    // Assign grade without student submission
    @Test
    public void testAssignGrade3(){
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor","Test", 2017,
                "Assignment 1", "This is the first assignment");
        this.student.registerForClass("Student1","Test",2017);
        this.instructor.assignGrade("Instructor","Test",2017,
                "Assignment 1", "Student1", 90);
        Integer grade = this.instructor.getGrade("Test", 2017, "Assignment 1",
                "Student1");
        assertFalse(grade.equals(90));
    }

    // Assign grade without assigning homework
    @Test
    public void testAssignGrade4(){
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("Student1","Test",2017);
        this.student.submitHomework("Student1","Assignment 1", "Some answer",
                "Test", 2017);
        this.instructor.assignGrade("Instructor","Test",2017,
                "Assignment 1", "Student1", 90);
        Integer grade = this.instructor.getGrade("Test", 2017, "Assignment 1",
                "Student1");
        //assertTrue(grade == null);
        assertNull(grade);
    }
}
