package model;

import java.util.ArrayList;

// Represents a student in the school with a first name, last name, and a unique student ID identifier
public class Student extends SchoolMember {

    //REQUIRES: firstName and lastName have non-zero length
    //EFFECTS: constructs a student with firstName and lastName, then generates an int student ID
    public Student(String firstName, String lastName, int id) {
        super(firstName, lastName, id);
    }

    // REQUIRES: a non-zero positive amount, amount is <= student's outstanding tuition, student attends the school
    // MODIFIES: this, school
    // EFFECTS: takes an amount and the school that student attends, creates a Transaction object, adds transaction to
    //          student's tuition records, deprecates student's outstanding tuition, adds to school's accumulated
    //          tuition, adds transaction summary to school's transaction records
    @Override
    public void payOutstandingTransaction(int amount, School school) {
        this.outstandingTransaction -= amount;
        Transaction thisTransaction = new Transaction(this, school, amount);
        this.transactionRecord.add(thisTransaction);
        school.accumulatedAnnualTuition += amount;
        school.transactionRecordSummary.add(thisTransaction.transactionSummary);
    }

    // Enroll in courses
    //REQUIRES: method must take a valid course that exists School's courses
    //MODIFIES: this
    //EFFECTS: takes a Course, and adds course to coursesEnrolled, adds student to the course's students list. If
    //         student is already enrolled, then do nothing. If the course is full (reached maxStudents), do nothing.
    @Override
    public boolean assignCourse(Course course) {
        if (course.students.size() < course.maxStudents) {
            if (!(this.courses.contains(course.courseName))) {
                this.courses.add(course.courseName);
                course.students.add("" + this.id);
                return true;
            }
        }
        return false;
    }
}
