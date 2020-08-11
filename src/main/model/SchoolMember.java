package model;

import java.util.ArrayList;

// abstract class that represents an individual at a school (student or teacher)
public abstract class SchoolMember {
    public String firstName;
    public String lastName;
    public int id;
    public ArrayList<String> courses = new ArrayList<>();
    public ArrayList<String> coursesPaidFor = new ArrayList<>();
    public int outstandingTransaction = 0;
    public ArrayList<Transaction> transactionRecord = new ArrayList<>();
    public String schoolAttended = null;

    public SchoolMember(String firstName, String lastName, int id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    // assigns course to individual
    public abstract boolean assignCourse(Course course);

    // pays outstanding transaction for individual
    public abstract void payOutstandingTransaction(int amount, School school);


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public ArrayList<String> getCoursesPaidFor() {
        return coursesPaidFor;
    }

    public int getOutstandingTransaction() {
        return outstandingTransaction;
    }

    public ArrayList<Transaction> getTransactionRecord() {
        return transactionRecord;
    }

    public String getSchoolAttended() {
        return schoolAttended;
    }
}
