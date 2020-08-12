package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {
    Teacher vivian;
    School mySchool;
    Course cpsc210;
    Course cpsc110;
    Course cpsc121;

    @BeforeEach
    public void setup() {
        vivian = new Teacher("Vivian", "Lin", 129394);
        mySchool = new School("UBC");
        cpsc210 = new Course("CPSC-210", 600, 5000, 30);
        cpsc110 = new Course("CPSC-110", 500, 4000, 30);
        cpsc121 = new Course("CPSC-121", 500, 4000, 30);
    }

    @Test
    public void testConstructor() {
        assertEquals(vivian.firstName, "Vivian");
        assertEquals(vivian.lastName, "Lin");
    }

    @Test
    public void testCollectSalary() {
        vivian.outstandingTransaction = 2000;
        assertEquals(vivian.getOutstandingTransaction(), 2000);
        vivian.payOutstandingTransaction(1000, mySchool);
        assertEquals(vivian.getOutstandingTransaction(), 1000);
        assertEquals(mySchool.accumulatedAnnualSalary, 1000);
        assertEquals(mySchool.transactionRecordSummary.size(), 1);
        assertEquals(vivian.transactionRecord.size(), 1);
    }

    @Test
    public void testAssignCourse() {
        assertFalse(vivian.courses.contains(cpsc210));
        assertFalse(cpsc210.teachers.contains(vivian));
        vivian.assignCourse(cpsc210);
        assertTrue(vivian.courses.contains(cpsc210));
        assertTrue(cpsc210.teachers.contains(vivian));
        assertFalse(vivian.courses.contains(cpsc110));
        assertFalse(cpsc110.teachers.contains(vivian));
        vivian.assignCourse(cpsc110);
        assertTrue(vivian.courses.contains(cpsc110));
        assertTrue(cpsc110.teachers.contains(vivian));
    }

    @Test
    public void testAssignSameCourseTwice() {
        assertEquals(vivian.courses.size(), 0);
        assertEquals(cpsc210.teachers.size(), 0);
        vivian.assignCourse(cpsc210);
        assertEquals(vivian.courses.size(), 1);
        assertEquals(cpsc210.teachers.size(), 1);
        vivian.assignCourse(cpsc210);
        assertEquals(vivian.courses.size(), 1);
        assertEquals(cpsc210.teachers.size(), 1);
    }

    @Test
    public void testGetterMethods() {
        assertEquals(vivian.getFirstName(), vivian.firstName);
        assertEquals(vivian.getLastName(), vivian.lastName);
        assertEquals(vivian.getId(), vivian.id);
        assertEquals(vivian.getCourses(), vivian.courses);
        assertEquals(vivian.getCoursesPaidFor(), vivian.coursesPaidFor);
        assertEquals(vivian.getTransactionRecord(), vivian.transactionRecord);
    }
}

