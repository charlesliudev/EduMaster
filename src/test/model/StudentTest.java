package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    Student george;
    Student rebecca;
    Student jessie;
    Student brian;
    School mySchool;
    Course cpsc210;
    Course cpsc110;
    Course cpsc121;

    @BeforeEach
    public void setup() {
        george = new Student("George", "Becky", 1000000);
        rebecca = new Student("Rebecca", "Ku", 100001);
        jessie = new Student("Jessie", "Lee", 100002);
        brian = new Student("Brian", "Qi", 100003);
        mySchool = new School("UBC");
        cpsc210 = new Course("CPSC-210", 600, 5000, 3);
        cpsc110 = new Course("CPSC-110", 500, 4000, 30);
        cpsc121 = new Course("CPSC-121", 500, 4000, 30);
    }

    @Test
    public void testConstructor() {
        assertEquals(george.getFirstName(), "George");
        assertEquals(george.getLastName(), "Becky");
    }

    @Test
    public void testPayTuition() {
        george.outstandingTransaction = 1000;
        assertEquals(george.getOutstandingTransaction(), 1000);
        george.payOutstandingTransaction(300, mySchool);
        assertEquals(george.outstandingTransaction, 700);
        assertEquals(mySchool.accumulatedAnnualTuition, 300);
        assertEquals(mySchool.transactionRecordSummary.size(), 1);
        assertEquals(george.transactionRecord.size(), 1);
    }

    @Test
    public void testEnroll() {
        assertFalse(george.courses.contains(cpsc210.courseName));
        assertFalse(cpsc210.getStudents().contains(george));
        george.assignCourse(cpsc210);
        assertTrue(george.courses.contains(cpsc210.courseName));
        assertTrue(cpsc210.getStudents().contains("" + george.id));
        assertFalse(george.courses.contains(cpsc110.courseName));
        assertFalse(cpsc110.getStudents().contains("" + george.id));
        george.assignCourse(cpsc110);
        assertTrue(george.courses.contains(cpsc110.courseName));
        assertTrue(cpsc110.getStudents().contains("" + george.id));
    }

    @Test
    public void testEnrollSameCourseTwice() {
        assertEquals(george.courses.size(), 0);
        assertEquals(cpsc210.students.size(), 0);
        george.assignCourse(cpsc210);
        assertEquals(cpsc210.students.size(), 1);
        assertEquals(george.courses.size(), 1);
        george.assignCourse(cpsc210);
        assertEquals(cpsc210.students.size(), 1);
        assertEquals(george.courses.size(), 1);
    }

    @Test
    public void testEnrollInFullCourse() {
        rebecca.assignCourse(cpsc210);
        jessie.assignCourse(cpsc210);
        brian.assignCourse(cpsc210);
        assertEquals(cpsc210.getStudents().size(), 3);
        assertFalse(cpsc210.getStudents().contains(george));
        george.assignCourse(cpsc210);
        assertFalse(george.getCourses().contains(cpsc210));
        assertFalse(cpsc210.getStudents().contains(george));
    }

    @Test
    public void testGetterMethods() {
        assertEquals(george.getId(), george.id);
        assertEquals(george.getCourses(), george.courses);
        assertEquals(george.getCoursesPaidFor(), george.coursesPaidFor);
        assertEquals(george.getTransactionRecord(), george.transactionRecord);
    }
}
