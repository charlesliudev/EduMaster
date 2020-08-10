package ui;

import model.Course;
import model.School;
import model.Student;
import model.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JPanel {
    public static void main(String[] args) {
        // making a new school
        School mySchool = new School("UBC");
        Student charles = new Student("Charles", "Liu", 342393);
        Student brian = new Student("Rex", "Liu", 124124);
        Student alan = new Student("Curina", "Liu", 542523);
        Course cpsc210 = new Course("CPSC-210", 500, 1000, 30);
        Course cpsc121 = new Course("CPSC-121", 500, 1000, 30);

        mySchool.addStudent(charles);
        mySchool.addStudent(brian);
        mySchool.addStudent(alan);
        mySchool.addCourse(cpsc210);
        mySchool.addCourse(cpsc121);

        Teacher vivian = new Teacher("Vivian", "Lin", 123123);
        Teacher joshua = new Teacher("Joshua", "Chen", 987987);
        Teacher george = new Teacher("George", "Washington", 345345);

        mySchool.addTeacher(vivian);
        mySchool.addTeacher(joshua);
        mySchool.addTeacher(george);

        vivian.assignCourse(cpsc210);
        charles.enroll(cpsc210);
        mySchool.enactNewOutstandingTransactions();
        charles.payTuition(500, mySchool);
        vivian.collectSalary(1000, mySchool);

        new HomeApp();

    }
}


