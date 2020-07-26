package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
    Course cpsc210;

    @BeforeEach
    public void setup() {
        cpsc210 = new Course("CPSC-210", 600, 5000, 20);
    }

    @Test
    public void testConstructor() {
        assertEquals(cpsc210.courseName, "CPSC-210");
        assertEquals(cpsc210.courseCost, 600);
        assertEquals(cpsc210.courseSalary, 5000);
        assertEquals(cpsc210.maxStudents, 20);
        assertEquals(cpsc210.getStudents().size(), 0);
        assertEquals(cpsc210.getTeachers().size(), 0);
    }
}
