package model;

import java.util.ArrayList;

// represents a course with a course name, list of teachers that teach it, and list of students enrolled in it
public class Course {
    public String courseName;
    public ArrayList<Teacher> teachers = new ArrayList<>();
    public ArrayList<Student> students = new ArrayList<>();
    public int courseCost;
    public int courseSalary;
    public int maxStudents;

    //REQUIRES: courseName has non-zero length and is in form of "CPSC-210"
    //EFFECTS: constructs a new course object with course name, annual cost of the course for students, and the annual
    //         salary paid to teachers for teaching it
    public Course(String courseName, int courseCost, int courseSalary, int maxStudents) {
        this.courseName = courseName;
        this.courseCost = courseCost;
        this.courseSalary = courseSalary;
        this.maxStudents = maxStudents;
    }

    // GETTERS: ------------------------

    public ArrayList<Teacher> getTeachers() {
        return this.teachers;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public String getCourseName() {
        return this.courseName;
    }
}
