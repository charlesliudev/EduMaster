package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SchoolTest {
    School mySchool;
    Course cpsc210;
    Course cpsc110;
    Course cpsc121;
    Course cpsc221;
    Student george;
    Student elizabeth;
    Teacher vivian;
    Teacher samuel;

    @BeforeEach
    public void setup() {
        mySchool = new School("UBC");
        cpsc210 = new Course("CPSC-210", 600, 5000, 30);
        cpsc110 = new Course("CPSC-110", 500, 4000, 30);
        cpsc121 = new Course("CPSC-121", 500, 4000, 30);
        mySchool.addCourse(cpsc210);
        mySchool.addCourse(cpsc110);
        mySchool.addCourse(cpsc121);
        george = new Student("George", "Becky", 100000);
        elizabeth = new Student("Elizabeth", "Parkinson", 100001);
        vivian = new Teacher("Vivian", "Lin", 100004);
        samuel = new Teacher("Samuel", "Williams", 100009);
    }

    @Test
    public void testAddRemoveStudent() {
        assertFalse(mySchool.students.contains(george));
        mySchool.addStudent(george);
        assertTrue(mySchool.students.contains(george));
        assertEquals(george.schoolAttended, mySchool.schoolName);
        mySchool.removeStudent(george);
        assertFalse(mySchool.students.contains(george));
    }

    @Test
    public void testAddRemoveTeacher() {
        assertFalse(mySchool.teachers.contains(vivian));
        mySchool.addTeacher(vivian);
        assertTrue(mySchool.teachers.contains(vivian));
        assertEquals(vivian.schoolAttended, mySchool.schoolName);
        mySchool.removeTeacher(vivian);
        assertFalse(mySchool.teachers.contains(vivian));
    }

    @Test
    public void testAddRemoveCourse() {
        cpsc221 = new Course("CPSC-221", 600, 5000, 30);
        assertFalse(mySchool.courses.contains(cpsc221));
        mySchool.addCourse(cpsc221);
        assertTrue(mySchool.courses.contains(cpsc221));
        mySchool.removeCourse(cpsc221);
        assertFalse(mySchool.courses.contains(cpsc221));
    }

    @Test
    public void testEnactNewOutstandingTransactions() {
        mySchool.addStudent(george);
        mySchool.addTeacher(vivian);

        george.enroll(cpsc210);
        george.enroll(cpsc110);
        assertEquals(george.outstandingTuition, 0);

        vivian.assignCourse(cpsc110);
        vivian.assignCourse(cpsc121);
        assertEquals(vivian.outstandingSalary, 0);

        mySchool.enactNewOutstandingTransactions();

        assertEquals(george.outstandingTuition, 1100);
        assertEquals(vivian.outstandingSalary, 8000);
    }

    @Test
    public void testEnactTuitionTwiceForOneCourse() {
        mySchool.addStudent(george);
        george.enroll(cpsc210);
        assertEquals(george.getOutstandingTuition(), 0);
        mySchool.enactNewOutstandingTransactions();
        assertEquals(george.getOutstandingTuition(), 600);
        mySchool.enactNewOutstandingTransactions();
        assertEquals(george.getOutstandingTuition(), 600);
    }

    @Test
    public void testEnactSalaryTwiceForOneCourse() {
        mySchool.addTeacher(vivian);
        vivian.assignCourse(cpsc210);
        assertEquals(vivian.getOutstandingSalary(), 0);
        mySchool.enactNewOutstandingTransactions();
        assertEquals(vivian.getOutstandingSalary(), 5000);
        mySchool.enactNewOutstandingTransactions();
        assertEquals(vivian.getOutstandingSalary(), 5000);
    }

    @Test
    public void testGetAllOutstandingTuition() {
        mySchool.addStudent(george);
        mySchool.addStudent(elizabeth);
        george.enroll(cpsc210);
        elizabeth.enroll(cpsc110);

        mySchool.enactNewOutstandingTransactions();

        assertEquals(mySchool.getAllOutstandingTuition(), 1100);
    }

    @Test
    public void testGetAllOutstandingSalaries() {
        mySchool.addTeacher(vivian);
        mySchool.addTeacher(samuel);
        vivian.assignCourse(cpsc121);
        samuel.assignCourse(cpsc210);

        mySchool.enactNewOutstandingTransactions();

        assertEquals(mySchool.getAllOutstandingSalaries(), 9000);
    }

    @Test
    public void testStartNewYear() {
        mySchool.addStudent(george);
        mySchool.addTeacher(vivian);
        assertEquals(mySchool.accumulatedAnnualTuition, 0);
        assertEquals(mySchool.accumulatedAnnualSalary, 0);
        george.payTuition(1000, mySchool);
        vivian.collectSalary(2000, mySchool);
        assertEquals(mySchool.accumulatedAnnualTuition, 1000);
        assertEquals(mySchool.accumulatedAnnualSalary, 2000);

        mySchool.startNewYear();

        assertEquals(mySchool.accumulatedAnnualTuition, 0);
        assertEquals(mySchool.accumulatedAnnualSalary, 0);
    }

    @Test
    public void testGetCourseByName() {
        assertTrue(mySchool.courses.contains(cpsc210));
        assertEquals(mySchool.getCourseByName("CPSC-210"), cpsc210);
        assertFalse(mySchool.courses.contains(cpsc221));
        assertEquals(mySchool.getCourseByName("CPSC-221"), null);
    }

    @Test
    public void testSaveAll() {
        // TODO
    }
}
