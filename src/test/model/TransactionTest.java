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
        mySchool = new School();
        george = new Student("George", "Becky");
        elizabeth = new Student("Elizabeth", "Parkinson");
        vivian = new Teacher("Vivian", "Lin");
        samuel = new Teacher("Samuel", "Williams");
    }

    @Test
    public void testStudentConstructor() {
        george.payTuition(1000, mySchool);
        george.tuitionRecord.get(0).amount = 1000;
    }

    @Test
    public void testTeacherConstructor() {
        vivian.collectSalary(1000, mySchool);
        vivian.salaryRecord.get(0).amount = 1000;
    }
}
