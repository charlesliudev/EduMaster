package model;

import java.util.ArrayList;

// Represents a teacher in the school with a first name, last name, and a unique teacher ID identifier
public class Teacher extends SchoolMember {

    //Constructor
    //REQUIRES: firstName and lastName have non-zero length
    //EFFECTS: constructs a teacher with firstName and lastName, then generates an int teacher ID
    public Teacher(String firstName, String lastName, int id) {
        super(firstName, lastName, id);
    }

    // REQUIRES: course must be in the teacher's school's course offerings
    // MODIFIES: this
    // EFFECTS: takes a Course, and assigns a teacher to teach it by adding it to coursesTaught, adds teacher to the
    //          course's list of teachers. If teacher already teaching the course, do nothing.
    @Override
    public boolean assignCourse(Course course) {
        if (!(this.courses.contains(course.courseName))) {
            courses.add(course.courseName);
            course.teachers.add("" + this.id);
            return true;
        }
        return false;
    }

    // collect salary
    // REQUIRES: a non-zero positive amount, the school that teacher teaches at
    // MODIFIES: this, school
    // EFFECTS: takes an amount and the school that teacher attends, creates a Transaction object, adds transaction to
    //          teacher's salary records, deprecates teacher's outstanding salary, adds to school's accumulated
    //          salary, adds transaction summary to school's transaction records
    @Override
    public void payOutstandingTransaction(int amount, School school) {
        this.outstandingTransaction -= amount;
        Transaction thisTransaction = new Transaction(this, school, amount);
        this.transactionRecord.add(thisTransaction);
        school.accumulatedAnnualSalary += amount;
        school.transactionRecordSummary.add(thisTransaction.transactionSummary);
    }
}
