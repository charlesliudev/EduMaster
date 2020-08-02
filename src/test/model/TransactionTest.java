package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    School mySchool;
    Student george;
    Student elizabeth;
    Teacher vivian;
    Teacher samuel;

    @BeforeEach
    public void setup() {
        mySchool = new School("UBC");
        george = new Student("George", "Becky", 100000);
        elizabeth = new Student("Elizabeth", "Parkinson", 100001);
        vivian = new Teacher("Vivian", "Lin", 100002);
        samuel = new Teacher("Samuel", "Williams", 100003);
    }

    @Test
    public void testStudentConstructor() {
        george.payTuition(1000, mySchool);
        assertEquals(george.tuitionRecord.get(0).amount, 1000);
    }

    @Test
    public void testTeacherConstructor() {
        vivian.collectSalary(1000, mySchool);
        assertEquals(vivian.salaryRecord.get(0).amount, 1000);
    }
}
