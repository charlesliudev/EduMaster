package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// represents a monetary transaction between either a School and Student, or a School and Teacher
public class Transaction {
    public int amount;
    public String timestamp;
    public int transactionID;
    public String transactionSummary;

    // REQUIRES: amount is a positive non-zero number
    // EFFECTS: constructs a new student-school transaction object, with the Student paying the School the amount,
    //          generates a transaction ID to identify it, generates a transaction timestamp
    // NOTE*: this constructor is for a student-school transaction
    public Transaction(Student student, School school, int amount) {
        this.amount = amount;
        this.transactionID = generateTransactionID(school);
        this.timestamp = formatDate(LocalDateTime.now());
        this.transactionSummary = "Student " + student.id + " paid in $" + amount + " on " + this.timestamp;
    }

    // REQUIRES: amount is a positive non-zero number
    // EFFECTS: constructs a new teacher-school transaction object, with the School paying the Teacher the amount,
    //          generates a transaction ID to identify it, generates a transaction timestamp
    // NOTE*: this constructor is for a student-school transaction
    public Transaction(Teacher teacher, School school, int amount) {
        this.amount = amount;
        this.transactionID = generateTransactionID(school);
        this.timestamp = formatDate(LocalDateTime.now());
        this.transactionSummary = "Teacher " + teacher.id + " was paid $" + amount + " on " + this.timestamp;
    }

    //EFFECTS: returns a transaction ID that increments the last transaction ID by 1. If it is the school's first
    // transaction, then return 300000. 3 represents that it is a transaction ID.
    public int generateTransactionID(School school) {
        if (!(school.lastTransactionIDGenerated == 0)) {
            int newId = school.lastTransactionIDGenerated + 1;
            school.lastTransactionIDGenerated += 1;
            return newId;
        } else {
            int newId = 300000;
            school.lastTransactionIDGenerated = 300000;
            return newId;
        }
    }

    // MODIFIES: LocalDateTime input
    // EFFECTS: takes a LocalDateTime object, reformat it into "dd-MM-yyyy HH:mm:ss", then return as String
    public String formatDate(LocalDateTime date) {
        DateTimeFormatter myDateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return date.format(myDateTimeFormat);
    }
}
