package model;

import java.lang.Math;
import java.util.ArrayList;

// Represents a teacher in the school with a first name, last name, and a unique teacher ID identifier
public class Teacher {
    public String firstName;
    public String lastName;
    public int teacherID;
    public ArrayList<String> coursesTaught = new ArrayList<>();
    public ArrayList<String> coursesPaidFor = new ArrayList<>();
    public int outstandingSalary = 0;
    public ArrayList<Transaction> salaryRecord = new ArrayList<>();
    public String schoolAttended = null;

    //Constructor
    //REQUIRES: firstName and lastName have non-zero length
    //EFFECTS: constructs a teacher with firstName and lastName, then generates a random int teacher ID
    public Teacher(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.teacherID = id;
    }

    // REQUIRES: course must be in the teacher's school's course offerings
    // MODIFIES: this
    // EFFECTS: takes a Course, and assigns a teacher to teach it by adding it to coursesTaught, adds teacher to the
    //          course's list of teachers. If teacher already teaching the course, do nothing.
    public void assignCourse(Course course) {
        if (!(this.coursesTaught.contains(course.courseName))) {
            coursesTaught.add(course.courseName);
            course.teachers.add("" + this.teacherID);
        }
    }

    // collect salary
    // REQUIRES: a non-zero positive amount, the school that teacher teaches at
    // MODIFIES: this, school
    // EFFECTS: takes an amount and the school that teacher attends, creates a Transaction object, adds transaction to
    //          teacher's salary records, deprecates teacher's outstanding salary, adds to school's accumulated
    //          salary, adds transaction summary to school's transaction records
    public void collectSalary(int amount, School school) {
        this.outstandingSalary -= amount;
        Transaction thisTransaction = new Transaction(this, school, amount);
        this.salaryRecord.add(thisTransaction);
        school.accumulatedAnnualSalary += amount;
        school.transactionRecordSummary.add(thisTransaction.transactionSummary);
    }

    // GETTERS:

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getTeacherID() {
        return this.teacherID;
    }

    public ArrayList<String> getCoursesTaught() {
        return this.coursesTaught;
    }

    public ArrayList<String> getCoursesPaidFor() {
        return this.coursesPaidFor;
    }

    public ArrayList<Transaction> getSalaryRecord() {
        return this.salaryRecord;
    }

    public int getOutstandingSalary() {
        return this.outstandingSalary;
    }

}
