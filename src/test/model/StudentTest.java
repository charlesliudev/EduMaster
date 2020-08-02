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
        george.outstandingTuition = 1000;
        assertEquals(george.outstandingTuition, 1000);
        george.payTuition(300, mySchool);
        assertEquals(george.outstandingTuition, 700);
        assertEquals(mySchool.accumulatedAnnualTuition, 300);
        assertEquals(mySchool.transactionRecordSummary.size(), 1);
        assertEquals(george.tuitionRecord.size(), 1);
    }

    @Test
    public void testEnroll() {
        assertFalse(george.coursesEnrolled.contains(cpsc210));
        assertFalse(cpsc210.students.contains(george));
        george.enroll(cpsc210);
        assertTrue(george.coursesEnrolled.contains(cpsc210));
        assertTrue(cpsc210.students.contains(george));
        assertFalse(george.coursesEnrolled.contains(cpsc110));
        assertFalse(cpsc110.students.contains(george));
        george.enroll(cpsc110);
        assertTrue(george.coursesEnrolled.contains(cpsc110));
        assertTrue(cpsc110.students.contains(george));
    }

    @Test
    public void testEnrollSameCourseTwice() {
        assertEquals(george.coursesEnrolled.size(), 0);
        assertEquals(cpsc210.students.size(), 0);
        george.enroll(cpsc210);
        assertEquals(cpsc210.students.size(), 1);
        assertEquals(george.coursesEnrolled.size(), 1);
        george.enroll(cpsc210);
        assertEquals(cpsc210.students.size(), 1);
        assertEquals(george.coursesEnrolled.size(), 1);
    }

    @Test
    public void testEnrollInFullCourse() {
        rebecca.enroll(cpsc210);
        jessie.enroll(cpsc210);
        brian.enroll(cpsc210);
        assertEquals(cpsc210.students.size(), 3);
        assertFalse(cpsc210.students.contains(george));
        george.enroll(cpsc210);
        assertFalse(george.coursesEnrolled.contains(cpsc210));
        assertFalse(cpsc210.students.contains(george));
    }

    @Test
    public void testGenerateStudentID() {
        assertTrue((0 < george.studentID) && (george.studentID < 1000000));
        assertTrue((0 < rebecca.studentID) && (rebecca.studentID < 1000000));
        assertTrue((0 < jessie.studentID) && (jessie.studentID < 1000000));
        assertTrue((0 < brian.studentID) && (brian.studentID < 1000000));
    }

    @Test
    public void testGetterMethods() {
        assertEquals(george.getStudentID(), george.studentID);
        assertEquals(george.getCoursesEnrolled(), george.coursesEnrolled);
        assertEquals(george.getCoursesPaidFor(), george.coursesPaidFor);
        assertEquals(george.getTuitionRecord(), george.tuitionRecord);
    }
}