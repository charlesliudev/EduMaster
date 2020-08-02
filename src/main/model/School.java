package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import persistence.Saveable;

// represents a school with a list of students that attend, a list of teachers that teach, list of course offerings
public class School implements Saveable {
    public String schoolName = "";
    public ArrayList<Student> students = new ArrayList<>();
    public ArrayList<Teacher> teachers = new ArrayList<>();
    public ArrayList<Course> courses = new ArrayList<>();
    public int accumulatedAnnualTuition = 0;
    public int accumulatedAnnualSalary = 0;
    public ArrayList<String> transactionRecordSummary = new ArrayList<>();
    public int lastStudentIDGenerated = 0;
    public int lastTeacherIDGenerated = 0;

    // EFFECTS: constructs a new school object
    public School(String schoolName) {
        this.schoolName = schoolName;
    }

    // Start new year (enacts the annual transactions with students and teachers based on courses taken / taught)
    // MODIFIES: this
    // EFFECTS: For all students at school > adds to their outstanding tuition costs by the total annual cost of the
    //          courses they are enrolled in. For all teachers in school > adds to their outstanding salaries to be paid
    //          by the total annual salaries of the courses they teach. If the student has already been charged for a
    //          course, or the teacher has already been paid for a course, then do nothing with those courses.
    public void enactNewOutstandingTransactions() {
        // increment student tuition due
        for (Student student : this.students) {
            int amountToIncrement = 0;
            for (String courseName : student.coursesEnrolled) {
                Course course = getCourseByName(courseName);
                if (! (student.coursesPaidFor.contains(course.courseName))) {
                    amountToIncrement += course.courseCost;
                    student.coursesPaidFor.add(courseName);
                }
            }
            student.outstandingTuition += amountToIncrement;
        }
        // increment teacher salaries due
        for (Teacher teacher : this.teachers) {
            int amountToIncrement = 0;
            for (String courseName : teacher.coursesTaught) {
                Course course = getCourseByName(courseName);
                if (! (teacher.coursesPaidFor.contains(course.courseName))) {
                    amountToIncrement += course.courseSalary;
                    teacher.coursesPaidFor.add(course.courseName);
                }
            }
            teacher.outstandingSalary += amountToIncrement;
        }
    }

    // MODIFIES: this, student
    // EFFECTS: takes a Student and adds it to the schools array of students. Sets student's schoolAttended to this
    public void addStudent(Student student) {
        students.add(student);
        student.schoolAttended = this.schoolName;
    }

    // REQUIRES: student that is in this.students array
    // MODIFIES: this, student
    // EFFECTS: removes student from school
    public void removeStudent(Student student) {
        this.students.remove(student);
        student.schoolAttended = "";
    }

    // MODIFIES: this, teacher
    // EFFECTS: takes a Teacher and adds it to the schools array of teachers. Sets teacher's schoolAttended to this
    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
        teacher.schoolAttended = this.schoolName;
    }

    // REQUIRES: teacher that is in this.teachers array
    // MODIFIES: this, student
    // EFFECTS: removes teacher from school
    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
        teacher.schoolAttended = "";
    }

    // MODIFIES: this
    // EFFECTS: takes a course and adds it to the schools offering of courses
    public void addCourse(Course course) {
        courses.add(course);
    }

    // REQUIRES: course that is in this.courses array
    // MODIFIES: this
    // EFFECTS: removes course from school offerings
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    // EFFECTS: returns the total outstanding tuition from all students in the school
    public int getAllOutstandingTuition() {
        int totalOutstanding = 0;
        for (Student student : students) {
            totalOutstanding += student.outstandingTuition;
        }
        return totalOutstanding;
    }

    // EFFECTS: returns the total outstanding salary to be paid to all teachers in the school
    public int getAllOutstandingSalaries() {
        int totalOutstanding = 0;
        for (Teacher teacher : teachers) {
            totalOutstanding += teacher.outstandingSalary;
        }
        return totalOutstanding;
    }

    // MODIFIES: this
    // EFFECTS: resets AccumulatedAnnualTuition and AccumulatedAnnualSalary to 0
    public void startNewYear() {
        this.accumulatedAnnualSalary = 0;
        this.accumulatedAnnualTuition = 0;
    }

    // REQUIRES: course name must belong to a course offered by the school
    // EFFECTS: takes a course name and returns the course if found in courses offered by School
    public Course getCourseByName(String courseName) {
        for (Course course : this.courses) {
            if (course.courseName.equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public void saveAll() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter writer = new FileWriter("./data/school.json");
            gson.toJson(this, writer);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}