package model;

import java.util.ArrayList;

// represents a school with a list of students that attend, a list of teachers that teach, list of course offerings
public class School {
    public ArrayList<Student> students = new ArrayList<>();
    public ArrayList<Teacher> teachers = new ArrayList<>();
    public ArrayList<Course> courses = new ArrayList<>();
    public int accumulatedAnnualTuition = 0;
    public int accumulatedAnnualSalary = 0;
    public ArrayList<String> transactionRecordSummary = new ArrayList<>();

    // EFFECTS: constructs a new school object
    public School() {}

    // Start new year (enacts the annual transactions with students and teachers based on courses taken / taught)
    // MODIFIES: this
    // EFFECTS: For all students at school > adds to their outstanding tuition costs by the total annual cost of the
    //          courses they are enrolled in. For all teachers in school > adds to their outstanding salaries to be paid
    //          by the total annual salaries of the courses they teach.
    public void enactNewOutstandingTransactions() {
        // increment student tuition due
        for (Student student : this.students) {
            int amountToIncrement = 0;
            for (Course course : student.coursesEnrolled) {
                amountToIncrement += course.courseCost;
            }
            student.outstandingTuition += amountToIncrement;
        }
        // increment teacher salaries due
        for (Teacher teacher : this.teachers) {
            int amountToIncrement = 0;
            for (Course course : teacher.coursesTaught) {
                amountToIncrement += course.courseSalary;
            }
            teacher.outstandingSalary += amountToIncrement;
        }
    }

    // REQUIRES: a student
    // MODIFIES: this, student
    // EFFECTS: takes a Student and adds it to the schools array of students. Sets student's schoolAttended to this
    public void addStudent(Student student) {
        students.add(student);
        student.schoolAttended = this;
    }

    // REQUIRES: a teacher
    // MODIFIES: this, teacher
    // EFFECTS: takes a Teacher and adds it to the schools array of teachers. Sets teacher's schoolAttended to this
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        teacher.schoolAttended = this;
    }

    // REQUIRES: a course
    // MODIFIES: this
    // EFFECTS: takes a course and adds it to the schools offering of courses
    public void addCourse(Course course) {
        courses.add(course);
    }

    public int getAllOutstandingTuition() {
        int totalOutstanding = 0;
        for (Student student : students) {
            totalOutstanding += student.outstandingTuition;
        }
        return totalOutstanding;
    }

    public int getAllOutstandingSalaries() {
        int totalOutstanding = 0;
        for (Teacher teacher : teachers) {
            totalOutstanding += teacher.outstandingSalary;
        }
        return totalOutstanding;
    }
}
