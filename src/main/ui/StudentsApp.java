package ui;

import model.Course;
import model.School;
import model.Student;
import model.Transaction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;

// builds and runs the students page
public class StudentsApp extends JPanel {
    School mySchool;
    Color myColor = new Color(52, 79, 235);
    JFrame frame;
    JFrame singleStudentFrame;

    // runs student app
    public StudentsApp(School school) {
        this.mySchool = school;
        runStudentsApp();
        //showStudentPage(mySchool.students.get(0));
    }

    // builds the students page
    public void runStudentsApp() {
        frame = new JFrame("Students");
        frame.setFont(new Font("Proxima Nova", Font.PLAIN, 13));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setForeground(myColor);

        Container contentPane = frame.getContentPane();

        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        makeAddStudentSection(contentPane, layout);
        makeRemoveStudentSection(contentPane, layout);
        makeGetStudentSection(contentPane, layout);
        makeStudentTable(contentPane, layout, mySchool.students);

        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // displaysn the add student section
    public void makeAddStudentSection(Container contentPane, SpringLayout layout) {
        JLabel addNewStudentLabel = new JLabel("Add New Student: ", JLabel.CENTER);
        addNewStudentLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        JTextField studentFirstNameTxtField = new JTextField("First Name", 10);
        JTextField studentLastNameTxtField = new JTextField("Last Name", 10);
        JButton submitNewStudentBtn = new JButton("Add Student");

        contentPane.add(addNewStudentLabel);
        contentPane.add(studentFirstNameTxtField);
        contentPane.add(studentLastNameTxtField);
        contentPane.add(submitNewStudentBtn);

        // move add new student label
        layout.putConstraint(SpringLayout.WEST, addNewStudentLabel, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, addNewStudentLabel, 30, SpringLayout.NORTH, contentPane);
        // move first name field
        layout.putConstraint(SpringLayout.WEST, studentFirstNameTxtField, 15,
                SpringLayout.EAST, addNewStudentLabel);
        layout.putConstraint(SpringLayout.NORTH, studentFirstNameTxtField, 25, SpringLayout.NORTH, contentPane);
        // move last name field
        layout.putConstraint(SpringLayout.WEST, studentLastNameTxtField, 13,
                SpringLayout.EAST, studentFirstNameTxtField);
        layout.putConstraint(SpringLayout.NORTH, studentLastNameTxtField, 25, SpringLayout.NORTH, contentPane);
        // move submit button
        layout.putConstraint(SpringLayout.WEST, submitNewStudentBtn, 10,
                SpringLayout.EAST, studentLastNameTxtField);
        layout.putConstraint(SpringLayout.NORTH, submitNewStudentBtn, 25, SpringLayout.NORTH, contentPane);

        // ADD BUTTON LISTENER TO ADD NEW STUDENT:
        submitNewStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unhandledFirstName = studentFirstNameTxtField.getText();
                String firstName = unhandledFirstName.substring(0, 1).toUpperCase()
                        + unhandledFirstName.substring(1).toLowerCase();
                String unhandledLastName = studentLastNameTxtField.getText();
                String lastName = unhandledLastName.substring(0, 1).toUpperCase()
                        + unhandledLastName.substring(1).toLowerCase();

                if (!(firstName.equals("")) && !(lastName.equals(""))) {
                    Student newStudent = new Student(firstName, lastName, generateStudentID());
                    mySchool.addStudent(newStudent);
                    JOptionPane.showMessageDialog(frame, firstName + " " + lastName + " added with an ID of "
                            + newStudent.studentID);
                    new StudentsApp(mySchool);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter student's name.");
                }
            }
        });
    }

    // displays the remove student section
    public void makeRemoveStudentSection(Container contentPane, SpringLayout layout) {
        JLabel removeStudentLabel = new JLabel("Remove a Student: ", JLabel.CENTER);
        removeStudentLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        JTextField studentIdTxtField = new JTextField("Student ID", 22);
        JButton removeStudentBtn = new JButton("Remove Student");

        contentPane.add(removeStudentLabel);
        contentPane.add(studentIdTxtField);
        contentPane.add(removeStudentBtn);

        // move remove student label
        layout.putConstraint(SpringLayout.WEST, removeStudentLabel, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, removeStudentLabel, 65, SpringLayout.NORTH, contentPane);
        // move student id text field
        layout.putConstraint(SpringLayout.WEST, studentIdTxtField, 10, SpringLayout.EAST, removeStudentLabel);
        layout.putConstraint(SpringLayout.NORTH, studentIdTxtField, 60, SpringLayout.NORTH, contentPane);
        // move button
        layout.putConstraint(SpringLayout.WEST, removeStudentBtn, 10, SpringLayout.EAST, studentIdTxtField);
        layout.putConstraint(SpringLayout.NORTH, removeStudentBtn, 60, SpringLayout.NORTH, contentPane);

        // ADD BUTTON LISTENER TO REMOVE STUDENT:
        removeStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentId = Integer.valueOf(studentIdTxtField.getText());
                    if (findStudentByID(studentId) != null) {
                        Student toRemove = findStudentByID(studentId);
                        mySchool.removeStudent(toRemove);
                        toRemove.schoolAttended = null;
                        JOptionPane.showMessageDialog(frame, studentId + " was succesfully removed.");
                        new StudentsApp(mySchool);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter valid ID.");
                    }
                } catch (NumberFormatException f) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid 6-digit ID.");
                }
            }
        });
    }

    // EFFECTS: takes a student ID and searches for student in school. Return student if found, else return null
    private Student findStudentByID(int id) {
        for (Student student : mySchool.students) {
            if (student.studentID == id) {
                return student;
            }
        }
        return null;
    }

    // get a student
    public void makeGetStudentSection(Container contentPane, SpringLayout layout) {
        JLabel getStudentLabel = new JLabel("Get a Student: ", JLabel.CENTER);
        getStudentLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        JTextField studentIdTxtField = new JTextField("Student ID", 22);
        JButton getStudentBtn = new JButton("Get Student");

        contentPane.add(getStudentLabel);
        contentPane.add(studentIdTxtField);
        contentPane.add(getStudentBtn);

        // move get student label
        layout.putConstraint(SpringLayout.WEST, getStudentLabel, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, getStudentLabel, 100, SpringLayout.NORTH, contentPane);
        // move student id text field
        layout.putConstraint(SpringLayout.WEST, studentIdTxtField, 40, SpringLayout.EAST, getStudentLabel);
        layout.putConstraint(SpringLayout.NORTH, studentIdTxtField, 95, SpringLayout.NORTH, contentPane);
        // move button
        layout.putConstraint(SpringLayout.WEST, getStudentBtn, 10, SpringLayout.EAST, studentIdTxtField);
        layout.putConstraint(SpringLayout.NORTH, getStudentBtn, 95, SpringLayout.NORTH, contentPane);

        // ADD BUTTON LISTENER TO GET STUDENT:
        getStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int studentId = Integer.valueOf(studentIdTxtField.getText());
                    if (findStudentByID(studentId) != null) {
                        Student toView = findStudentByID(studentId);
                        showStudentPage(toView);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter valid ID.");
                    }
                } catch (NumberFormatException f) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid 6-digit ID.");
                }
            }
        });
    }


    // show the students in table
    public void makeStudentTable(Container contentPane, SpringLayout layout, ArrayList<Student> students) {
        // create students table
        String[] studentTableHeader = {"First Name", "Last Name", "Student ID"};
        Object[][] studentData = new Object[students.size() + 1][3];
        int rowCounter = 0;
        for (Student student : students) {
            studentData[rowCounter][0] = student.firstName;
            studentData[rowCounter][1] = student.lastName;
            studentData[rowCounter][2] = student.studentID;
            rowCounter += 1;
        }

        JTable studentTable = new JTable(studentData, studentTableHeader);
        studentTable.setPreferredSize(new Dimension(800, 400));
        studentTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Enrolled Students:"));
        studentTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        contentPane.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 140, SpringLayout.NORTH, contentPane);
    }

    // DISPLAY AN INDIVIDUAL STUDENT: ---------------------------------------
    public void showStudentPage(Student student) {
        singleStudentFrame = new JFrame("Viewing " + student.firstName + " " + student.lastName);
        singleStudentFrame.setFont(new Font("Proxima Nova", Font.PLAIN, 13));
        singleStudentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        singleStudentFrame.setForeground(myColor);
        SpringLayout layout = new SpringLayout();

        JPanel singleStudentPanel = new JPanel();
        singleStudentPanel.setLayout(layout);

        makeNameTitle(student, singleStudentPanel, layout);
        makeStudentDataBlock(student, singleStudentPanel, layout);
        makeEnrollNewCourseSection(student, singleStudentPanel, layout);
        makePayTuitionSection(student, singleStudentPanel, layout);
        makeEnrolledCoursesTable(student, singleStudentPanel, layout);
        makeTuitionHistoryTable(student, singleStudentPanel, layout);

        // end stuff
        singleStudentFrame.add(singleStudentPanel);
        singleStudentFrame.pack();
        singleStudentFrame.setSize(new Dimension(735, 610));
        singleStudentFrame.setVisible(true);
    }

    // makes and adds the name title to student panel
    public void makeNameTitle(Student student, JPanel singleStudentPanel, SpringLayout layout) {
        String fullName = student.firstName + " " + student.lastName;
        JLabel nameTitle = new JLabel(fullName + "'s Profile", JLabel.CENTER);
        nameTitle.setFont(new Font("Proxima Nova", Font.BOLD, 17));
        singleStudentPanel.add(nameTitle);
        layout.putConstraint(SpringLayout.WEST, nameTitle, 30, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, nameTitle, 15, SpringLayout.NORTH, singleStudentPanel);
    }

    // shows student ID and outstanding tuition for single student
    public void makeStudentDataBlock(Student student, JPanel singleStudentPanel, SpringLayout layout) {
        JLabel studentIdLabel = new JLabel("Student ID: " + student.studentID);
        singleStudentPanel.add(studentIdLabel);
        layout.putConstraint(SpringLayout.WEST, studentIdLabel, 30, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, studentIdLabel, 45, SpringLayout.NORTH, singleStudentPanel);

        JLabel outstandingTuitionLabel = new JLabel("Outstanding Tuition: $" + student.outstandingTuition);
        singleStudentPanel.add(outstandingTuitionLabel);
        layout.putConstraint(SpringLayout.WEST, outstandingTuitionLabel, 30,
                SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, outstandingTuitionLabel, 65,
                SpringLayout.NORTH, singleStudentPanel);
    }

    // shows section where user can enroll student into new course
    public void makeEnrollNewCourseSection(Student student, JPanel singleStudentPanel, SpringLayout layout) {
        JLabel enrollNewCourseLabel = new JLabel("Enroll to Course: ");
        enrollNewCourseLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        singleStudentPanel.add(enrollNewCourseLabel);
        layout.putConstraint(SpringLayout.WEST, enrollNewCourseLabel, 30, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, enrollNewCourseLabel, 90, SpringLayout.NORTH, singleStudentPanel);

        JTextField newCourseName = new JTextField("e.g. 'CPSC-210'", 10);
        singleStudentPanel.add(newCourseName);
        layout.putConstraint(SpringLayout.WEST, newCourseName, 150, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, newCourseName, 85, SpringLayout.NORTH, singleStudentPanel);

        JButton submitNewCourse = new JButton("Enroll");
        singleStudentPanel.add(submitNewCourse);
        layout.putConstraint(SpringLayout.WEST, submitNewCourse, 280, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, submitNewCourse, 85, SpringLayout.NORTH, singleStudentPanel);

        // ADD BUTTON LISTENER TO ENROLL STUDENT:
        submitNewCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseInput = newCourseName.getText();
                Course theCourse = findCourseByName(courseInput);
                if (theCourse != null) {
                    if (student.enroll(theCourse)) {
                        JOptionPane.showMessageDialog(singleStudentFrame, "Course enrolled in successfully.");
                        singleStudentFrame.dispose();
                        showStudentPage(student);
                    } else {
                        JOptionPane.showMessageDialog(singleStudentFrame, "Sorry, this course is full.");
                    }
                } else {
                    System.out.println("Sorry, course not found. Try viewing all courses offered.");
                }
            }
        });
    }


    // EFFECTS: takes the name of a course and searches for it in school. Return course if found, else return null
    private Course findCourseByName(String name) {
        for (Course course : mySchool.courses) {
            if (course.courseName.equals(name)) {
                return course;
            }
        }
        return null;
    }

    // shows section where user can record student tuition payment
    public void makePayTuitionSection(Student student, JPanel singleStudentPanel, SpringLayout layout) {
        JLabel payTuitionLabel = new JLabel("Pay Tuition: ");
        payTuitionLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        singleStudentPanel.add(payTuitionLabel);
        layout.putConstraint(SpringLayout.WEST, payTuitionLabel, 30, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, payTuitionLabel, 120, SpringLayout.NORTH, singleStudentPanel);

        JTextField amountPaid = new JTextField("amount($)", 10);
        singleStudentPanel.add(amountPaid);
        layout.putConstraint(SpringLayout.WEST, amountPaid, 150, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, amountPaid, 115, SpringLayout.NORTH, singleStudentPanel);

        JButton submitAmountPaid = new JButton("Pay Amount");
        singleStudentPanel.add(submitAmountPaid);
        layout.putConstraint(SpringLayout.WEST, submitAmountPaid, 280, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, submitAmountPaid, 115, SpringLayout.NORTH, singleStudentPanel);

        // ADD BUTTON LISTENER TO PAY TUITION
        submitAmountPaid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int paymentAmount = Integer.valueOf(amountPaid.getText());
                    if (paymentAmount < 0) {
                        JOptionPane.showMessageDialog(singleStudentFrame, "Amount must be positive.");
                    } else {
                        student.payTuition(paymentAmount, mySchool);
                        JOptionPane.showMessageDialog(singleStudentFrame, "Amount paid successfully.");
                        singleStudentFrame.dispose();
                        showStudentPage(student);
                    }
                } catch (NumberFormatException f) {
                    JOptionPane.showMessageDialog(singleStudentFrame, "Amount must be a positive integer.");
                }
            }
        });
    }

    // makes and adds the enrolled courses table to panel
    public void makeEnrolledCoursesTable(Student student, JPanel singleStudentPanel, SpringLayout layout) {
        String[] tableHeader = {"Course Name"};
        Object[][] courseData = new Object[student.coursesEnrolled.size() + 1][1];
        int rowCounter = 0;
        for (String course : student.coursesEnrolled) {
            courseData[rowCounter][0] = course;
            rowCounter += 1;
        }
        JTable enrolledCoursesTable = new JTable(courseData, tableHeader);
        enrolledCoursesTable.setPreferredSize(null);
        enrolledCoursesTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(enrolledCoursesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Courses Enrolled:"));
        enrolledCoursesTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(150, 400));

        singleStudentPanel.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 550, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 160, SpringLayout.NORTH, singleStudentPanel);
    }

    // makes and adds tuition history table to panel for passed in student
    public void makeTuitionHistoryTable(Student student, JPanel singleStudentPanel, SpringLayout layout) {
        String[] tableHeader = {"Amount Paid ($)", "Timestamp", "Transaction ID"};
        Object[][] tuitionData = new Object[student.tuitionRecord.size() + 1][3];
        int rowCounter = 0;
        for (Transaction transaction : student.tuitionRecord) {
            tuitionData[rowCounter][0] = transaction.amount;
            tuitionData[rowCounter][1] = transaction.timestamp;
            tuitionData[rowCounter][2] = transaction.transactionID;
            rowCounter += 1;
        }
        JTable tuitionHistoryTable = new JTable(tuitionData, tableHeader);
        tuitionHistoryTable.setPreferredSize(null);
        tuitionHistoryTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(tuitionHistoryTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tuition History:"));
        tuitionHistoryTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        singleStudentPanel.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 30, SpringLayout.WEST, singleStudentPanel);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 160, SpringLayout.NORTH, singleStudentPanel);
    }

    //EFFECTS: returns a 6 digit number that is the last ID generated incremented by 1
    // ID starts with '1' to represent it is a student ID
    public int generateStudentID() {
        int newID;
        if (mySchool.lastStudentIDGenerated != 0) {
            newID = mySchool.lastStudentIDGenerated + 1;
            mySchool.lastStudentIDGenerated += 1;
        } else {
            newID = 100000;
            mySchool.lastStudentIDGenerated = 100000;
        }
        return newID;
    }
}