package model;

import java.lang.Math;
import java.util.ArrayList;

// Represents a student in the school with a first name, last name, and a unique student ID identifier
public class Student {
    public String firstName;
    public String lastName;
    public int studentID;
    public ArrayList<Course> coursesEnrolled = new ArrayList<>();
    public ArrayList<Course> coursesPaidFor = new ArrayList<>();
    public int outstandingTuition = 0;
    public ArrayList<Transaction> tuitionRecord = new ArrayList<>();
    public School schoolAttended = null;

    //REQUIRES: firstName and lastName have non-zero length
    //EFFECTS: constructs a student with firstName and lastName, then generates a random int student ID
    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = generateStudentID();
    }

    // REQUIRES: a non-zero positive amount, amount is <= student's outstanding tuition, student attends the school
    // MODIFIES: this, school
    // EFFECTS: takes an amount and the school that student attends, creates a Transaction object, adds transaction to
    //          student's tuition records, deprecates student's outstanding tuition, adds to school's accumulated
    //          tuition, adds transaction summary to school's transaction records
    public void payTuition(int amount, School school) {
        this.outstandingTuition -= amount;
        Transaction thisTransaction = new Transaction(this, school, amount);
        this.tuitionRecord.add(thisTransaction);
        school.accumulatedAnnualTuition += amount;
        school.transactionRecordSummary.add(thisTransaction.transactionSummary);
    }

    // Enroll in courses
    //REQUIRES: method must take a valid course that exists School's courses
    //MODIFIES: this
    //EFFECTS: takes a Course, and adds course to coursesEnrolled, adds student to the course's students list. If
    //         student is already enrolled, then do nothing. If the course is full (reached maxStudents), do nothing.
    public boolean enroll(Course course) {
        if (course.students.size() < course.maxStudents) {
            if (!(this.coursesEnrolled.contains(course))) {
                this.coursesEnrolled.add(course);
                course.students.add(this);
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns a randomly generated 6 digit number
    public int generateStudentID() {
        String id = "";
        for (int i = 1; i < 7; i++) {
            String nextNum = "" + (int)(Math.random() * 10);
            id += nextNum;
        }
        return Integer.valueOf(id);
    }

    // GETTERS:

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public ArrayList<Course> getCoursesEnrolled() {
        return this.coursesEnrolled;
    }

    public ArrayList<Course> getCoursesPaidFor() {
        return this.coursesPaidFor;
    }

    public ArrayList<Transaction> getTuitionRecord() {
        return this.tuitionRecord;
    }

    public int getOutstandingTuition() {
        return this.outstandingTuition;
    }
}
