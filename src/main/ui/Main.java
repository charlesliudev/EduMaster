package ui;

import model.Course;
import model.School;
import model.Student;

import javax.swing.*;
import java.awt.*;

public class Main extends JPanel {
    public static void main(String[] args) {
        // making a new school
        School mySchool = new School("UBC");
        Student charles = new Student("Charles", "Liu", 342393);
        Student brian = new Student("Rex", "Liu", 124124);
        Student alan = new Student("Curina", "Liu", 542523);
        Course cpsc210 = new Course("CPSC-210", 500, 1000, 30);

        mySchool.addStudent(charles);
        mySchool.addStudent(brian);
        mySchool.addStudent(alan);
        mySchool.addCourse(cpsc210);

        charles.enroll(cpsc210);
        mySchool.enactNewOutstandingTransactions();
        charles.payTuition(500, mySchool);


        //new HomeApp();
        //new SchoolApp();
    }
}
