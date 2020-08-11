package ui;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import static ui.HomeApp.createImageIcon;

// EFFECTS: builds and runs the teachers page
public class TeachersApp extends JPanel {
    School mySchool;
    Color myColor = new Color(52, 79, 235);
    JFrame frame;
    JFrame singleTeacherFrame;

    // EFFECTS: runs teacher app
    public TeachersApp(School school) {
        this.mySchool = school;
        runTeachersApp();
    }

    // EFFECTS: builds the teachers page
    public void runTeachersApp() {
        frame = new JFrame("Teachers");
        frame.setFont(new Font("Proxima Nova", Font.PLAIN, 13));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setForeground(myColor);
        frame.setBackground(myColor);
        frame.setLayout(new BorderLayout());

        frame.add(makeUpperPanel(), BorderLayout.NORTH);
        frame.add(makeLowerPanel(), BorderLayout.CENTER);

        frame.pack();
        frame.getContentPane().requestFocusInWindow();
        frame.setSize(800, 750);
        frame.setVisible(true);
    }

    // EFFECTS: makes the upper panel and returns it
    public Component makeUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.setBackground(myColor);
        upperPanel.setPreferredSize(new Dimension(800, 150));

        JLabel mainTitleLabel = new JLabel(" Teachers");
        mainTitleLabel.setIcon(createImageIcon("./images/teachersIconWhite.png"));
        mainTitleLabel.setFont(new Font("Proxima Nova", Font.BOLD, 30));
        mainTitleLabel.setForeground(Color.white);
        mainTitleLabel.setBorder(new EmptyBorder(70, 70, 70, 0));
        upperPanel.add(mainTitleLabel, BorderLayout.CENTER);

        return upperPanel;
    }

    //EFFECTS:  makes the lower panel and returns it
    public Component makeLowerPanel() {
        JPanel contentPane = new JPanel();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        makeAddTeacherSection(contentPane, layout);
        makeRemoveTeacherSection(contentPane, layout);
        makeGetTeacherSection(contentPane, layout);
        makeTeacherTable(contentPane, layout, mySchool.teachers);

        return contentPane;
    }

    // MODIFIES: contentPane
    // EFFECTS: displays the add teacher section
    public void makeAddTeacherSection(Container contentPane, SpringLayout layout) {
        // make components
        JLabel addNewTeacherLabel = new JLabel("Add New Teacher: ", JLabel.CENTER);
        addNewTeacherLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        JTextField firstNameTxtField = addFocusFirstName();
        JTextField lastNameTxtField = addFocusLastName();
        JButton submitNewTeacherBtn = addActionListenerSubmitBtn(firstNameTxtField, lastNameTxtField);

        // add components
        contentPane.add(addNewTeacherLabel);
        contentPane.add(firstNameTxtField);
        contentPane.add(lastNameTxtField);
        contentPane.add(submitNewTeacherBtn);

        // move components
        layout.putConstraint(SpringLayout.WEST, addNewTeacherLabel, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, addNewTeacherLabel, 30, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, firstNameTxtField, 15, SpringLayout.EAST, addNewTeacherLabel);
        layout.putConstraint(SpringLayout.NORTH, firstNameTxtField, 25, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, lastNameTxtField, 13, SpringLayout.EAST, firstNameTxtField);
        layout.putConstraint(SpringLayout.NORTH, lastNameTxtField, 25, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, submitNewTeacherBtn, 10, SpringLayout.EAST, lastNameTxtField);
        layout.putConstraint(SpringLayout.NORTH, submitNewTeacherBtn, 25, SpringLayout.NORTH, contentPane);
    }

    // EFFECTS: adds focus listener to first name text field and returns it
    public JTextField addFocusFirstName() {
        JTextField firstNameTxtField = new JTextField("First Name", 10);
        firstNameTxtField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                firstNameTxtField.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (firstNameTxtField.getText().equals("")) {
                    firstNameTxtField.setText("First Name");
                }
            }
        });
        return firstNameTxtField;
    }

    // EFFECTS: adds focus listener to last name text field and returns it
    public JTextField addFocusLastName() {
        JTextField lastNameTxtField = new JTextField("Last Name", 10);
        lastNameTxtField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                lastNameTxtField.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (lastNameTxtField.getText().equals("")) {
                    lastNameTxtField.setText("Last Name");
                }
            }
        });
        return lastNameTxtField;
    }

    // EFFECTS: adds action listener to button and returns it
    public JButton addActionListenerSubmitBtn(JTextField firstNameTxtField, JTextField lastNameTxtField) {
        JButton submitNewTeacherBtn = new JButton("Add Teacher");
        submitNewTeacherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                String firstName = firstNameTxtField.getText().substring(0, 1).toUpperCase()
                        + firstNameTxtField.getText().substring(1).toLowerCase();
                String lastName = lastNameTxtField.getText().substring(0, 1).toUpperCase()
                        + lastNameTxtField.getText().substring(1).toLowerCase();

                if (!(firstName.equals("")) && !(lastName.equals(""))) {
                    Teacher newTeacher = new Teacher(firstName, lastName, generateTeacherID());
                    mySchool.addTeacher(newTeacher);
                    JOptionPane.showMessageDialog(frame, firstName + " " + lastName + " added with an ID of "
                            + newTeacher.teacherID);
                    new TeachersApp(mySchool);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter teacher's name.");
                }
            }
        });
        return submitNewTeacherBtn;
    }

    // MODIFIES: contentPane
    // EFFECTS: displays the remove teacher section
    public void makeRemoveTeacherSection(Container contentPane, SpringLayout layout) {
        JLabel removeTeacherLabel = new JLabel("Remove a Teacher: ", JLabel.CENTER);
        removeTeacherLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        JTextField teacherIdTxtField = addFocusTeacherIdTxtField();
        JButton removeTeacherBtn = addActionListenerRemoveTeacherBtn(teacherIdTxtField);

        contentPane.add(removeTeacherLabel);
        contentPane.add(teacherIdTxtField);
        contentPane.add(removeTeacherBtn);

        layout.putConstraint(SpringLayout.WEST, removeTeacherLabel, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, removeTeacherLabel, 65, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, teacherIdTxtField, 10, SpringLayout.EAST, removeTeacherLabel);
        layout.putConstraint(SpringLayout.NORTH, teacherIdTxtField, 60, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, removeTeacherBtn, 10, SpringLayout.EAST, teacherIdTxtField);
        layout.putConstraint(SpringLayout.NORTH, removeTeacherBtn, 60, SpringLayout.NORTH, contentPane);
    }

    // EFFECTS: adds focus listener to text field then returns it
    public JTextField addFocusTeacherIdTxtField() {
        JTextField teacherIdTxtField = new JTextField("Teacher ID", 22);
        teacherIdTxtField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                teacherIdTxtField.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (teacherIdTxtField.getText().equals("")) {
                    teacherIdTxtField.setText("Teacher ID");
                }
            }
        });
        return teacherIdTxtField;
    }

    // EFFECTS: adds action listener to remove teacher btn and returns btn
    public JButton addActionListenerRemoveTeacherBtn(JTextField teacherIdTxtField) {
        JButton removeTeacherBtn = new JButton("Remove Teacher");
        // ADD BUTTON LISTENER TO REMOVE TEACHER:
        removeTeacherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                try {
                    int teacherId = Integer.valueOf(teacherIdTxtField.getText());
                    if (findTeacherByID(teacherId) != null) {
                        Teacher toRemove = findTeacherByID(teacherId);
                        mySchool.removeTeacher(toRemove);
                        toRemove.schoolAttended = null;
                        JOptionPane.showMessageDialog(frame, teacherId + " was succesfully removed.");
                        new TeachersApp(mySchool);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter valid ID.");
                    }
                } catch (NumberFormatException f) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid 6-digit ID.");
                }
            }
        });
        return removeTeacherBtn;
    }

    // EFFECTS: takes an teacherID, finds teacher in school database. If found, return the teacher, else return null
    private Teacher findTeacherByID(int id) {
        for (Teacher teacher : mySchool.teachers) {
            if (teacher.teacherID == id) {
                return teacher;
            }
        }
        return null;
    }

    // MODIFIES: contentPane
    // EFFECTS: get a teacher and display profile
    public void makeGetTeacherSection(Container contentPane, SpringLayout layout) {
        JLabel getTeacherLabel = new JLabel("Get a Teacher: ", JLabel.CENTER);
        getTeacherLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        JTextField teacherIdTxtField = addFocusTeacherIdTxtField();
        JButton getTeacherBtn = addActionListenerGetTeacherBtn(teacherIdTxtField);

        contentPane.add(getTeacherLabel);
        contentPane.add(teacherIdTxtField);
        contentPane.add(getTeacherBtn);

        layout.putConstraint(SpringLayout.WEST, getTeacherLabel, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, getTeacherLabel, 100, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, teacherIdTxtField, 40, SpringLayout.EAST, getTeacherLabel);
        layout.putConstraint(SpringLayout.NORTH, teacherIdTxtField, 95, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, getTeacherBtn, 10, SpringLayout.EAST, teacherIdTxtField);
        layout.putConstraint(SpringLayout.NORTH, getTeacherBtn, 95, SpringLayout.NORTH, contentPane);
    }

    // EFFECTS: adds action listener to get teacher button and returns the button
    public JButton addActionListenerGetTeacherBtn(JTextField teacherIdTxtField) {
        JButton getTeacherBtn = new JButton("Get Teacher");
        getTeacherBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                try {
                    int teacherId = Integer.valueOf(teacherIdTxtField.getText());
                    if (findTeacherByID(teacherId) != null) {
                        Teacher toView = findTeacherByID(teacherId);
                        showTeacherPage(toView);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter valid ID.");
                    }
                } catch (NumberFormatException f) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid 6-digit ID.");
                }
            }
        });
        return getTeacherBtn;
    }

    // MODIFIES: contentPane
    // EFFECTS: show the teachers in table
    public void makeTeacherTable(Container contentPane, SpringLayout layout, ArrayList<Teacher> teachers) {
        // create teachers table
        String[] teacherTableHeader = {"First Name", "Last Name", "Teacher ID"};
        Object[][] teacherData = new Object[teachers.size() + 1][3];
        int rowCounter = 0;
        for (Teacher teacher : teachers) {
            teacherData[rowCounter][0] = teacher.firstName;
            teacherData[rowCounter][1] = teacher.lastName;
            teacherData[rowCounter][2] = teacher.teacherID;
            rowCounter += 1;
        }

        JTable teacherTable = new JTable(teacherData, teacherTableHeader);
        teacherTable.setPreferredSize(new Dimension(800, 400));
        teacherTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(teacherTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Teachers:"));
        teacherTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        contentPane.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 140, SpringLayout.NORTH, contentPane);
    }

    // DISPLAY AN INDIVIDUAL TEACHER: --------------------------------------------

    // EFFECTS: shows the profile of one teacher
    public void showTeacherPage(Teacher teacher) {
        singleTeacherFrame = new JFrame("Viewing " + teacher.firstName + " " + teacher.lastName);
        singleTeacherFrame.setFont(new Font("Proxima Nova", Font.PLAIN, 13));
        singleTeacherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        singleTeacherFrame.setForeground(myColor);
        SpringLayout layout = new SpringLayout();

        JPanel singleTeacherPanel = new JPanel();
        singleTeacherPanel.setLayout(layout);

        makeNameTitle(teacher, singleTeacherPanel, layout);
        makeTeacherDataBlock(teacher, singleTeacherPanel, layout);
        makeAssignNewCourseSection(teacher, singleTeacherPanel, layout);
        makePaySalarySection(teacher, singleTeacherPanel, layout);
        makeAssignedCoursesTable(teacher, singleTeacherPanel, layout);
        makeSalaryHistoryTable(teacher, singleTeacherPanel, layout);

        // end stuff
        singleTeacherFrame.add(singleTeacherPanel);
        singleTeacherFrame.pack();
        singleTeacherFrame.getContentPane().requestFocusInWindow();
        singleTeacherFrame.setSize(new Dimension(735, 610));
        singleTeacherFrame.setVisible(true);
    }

    // MODIFIES: singleTeacherPanel
    // EFFECTS: makes and adds the name title to teacher panel
    public void makeNameTitle(Teacher teacher, JPanel singleTeacherPanel, SpringLayout layout) {
        String fullName = teacher.firstName + " " + teacher.lastName;
        JLabel nameTitle = new JLabel(fullName + "'s Profile", JLabel.CENTER);
        nameTitle.setFont(new Font("Proxima Nova", Font.BOLD, 17));
        singleTeacherPanel.add(nameTitle);
        layout.putConstraint(SpringLayout.WEST, nameTitle, 30, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, nameTitle, 15, SpringLayout.NORTH, singleTeacherPanel);
    }

    // MODIFIES: singleTeacherPanel
    // EFFECTS: shows teacher ID and outstanding salaries due for single teacher
    public void makeTeacherDataBlock(Teacher teacher, JPanel singleTeacherPanel, SpringLayout layout) {
        JLabel teacherIdLabel = new JLabel("Teacher ID: " + teacher.teacherID);
        singleTeacherPanel.add(teacherIdLabel);
        layout.putConstraint(SpringLayout.WEST, teacherIdLabel, 30, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, teacherIdLabel, 45, SpringLayout.NORTH, singleTeacherPanel);

        JLabel outstandingSalaryLabel = new JLabel("Outstanding Salary: $" + teacher.outstandingSalary);
        singleTeacherPanel.add(outstandingSalaryLabel);
        layout.putConstraint(SpringLayout.WEST, outstandingSalaryLabel, 30,
                SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, outstandingSalaryLabel, 65,
                SpringLayout.NORTH, singleTeacherPanel);
    }

    // MODIFIES: singleTeacherPanel
    // EFFECTS: shows section where user can assign teacher into a new course
    public void makeAssignNewCourseSection(Teacher teacher, JPanel singleTeacherPanel, SpringLayout layout) {
        JLabel assignNewCourseLabel = new JLabel("Assign to Course: ");
        assignNewCourseLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        singleTeacherPanel.add(assignNewCourseLabel);
        layout.putConstraint(SpringLayout.WEST, assignNewCourseLabel, 30, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, assignNewCourseLabel, 90, SpringLayout.NORTH, singleTeacherPanel);

        JTextField newCourseName = addFocusListenerCourseNameTxtField();
        singleTeacherPanel.add(newCourseName);
        layout.putConstraint(SpringLayout.WEST, newCourseName, 150, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, newCourseName, 85, SpringLayout.NORTH, singleTeacherPanel);

        JButton submitNewCourse = addActionListenerSubmitNewCourseBtn(newCourseName, teacher);
        singleTeacherPanel.add(submitNewCourse);
        layout.putConstraint(SpringLayout.WEST, submitNewCourse, 280, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, submitNewCourse, 85, SpringLayout.NORTH, singleTeacherPanel);
    }

    // EFFECTS: adds focus listener to new course name text field and returns it
    public JTextField addFocusListenerCourseNameTxtField() {
        JTextField newCourseName = new JTextField("e.g.'CPSC-210'", 10);
        newCourseName.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                newCourseName.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (newCourseName.getText().equals("")) {
                    newCourseName.setText("e.g.'CPSC-210'");
                }
            }
        });
        return newCourseName;
    }

    // EFFECTS: adds action listener to submit new course button and returns it
    public JButton addActionListenerSubmitNewCourseBtn(JTextField newCourseName, Teacher teacher) {
        JButton submitNewCourse = new JButton("Assign");
        submitNewCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                String courseInput = newCourseName.getText();
                Course theCourse = findCourseByName(courseInput);
                if (theCourse != null) {
                    teacher.assignCourse(theCourse);
                    JOptionPane.showMessageDialog(singleTeacherFrame, "Course assigned to successfully.");
                    singleTeacherFrame.dispose();
                    showTeacherPage(teacher);
                } else {
                    JOptionPane.showMessageDialog(singleTeacherFrame,
                            "Sorry, course not found. Try viewing all courses offered.");
                }
            }
        });
        return submitNewCourse;
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

    // MODIFIES: singleTeacherPanel
    // EFFECTS: shows section where user can record teacher salary payment
    public void makePaySalarySection(Teacher teacher, JPanel singleTeacherPanel, SpringLayout layout) {
        JLabel paySalaryLabel = new JLabel("Pay Salary: ");
        paySalaryLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        singleTeacherPanel.add(paySalaryLabel);
        layout.putConstraint(SpringLayout.WEST, paySalaryLabel, 30, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, paySalaryLabel, 120, SpringLayout.NORTH, singleTeacherPanel);

        JTextField amountPaid = addFocusListenerAmountTxtField();
        singleTeacherPanel.add(amountPaid);
        layout.putConstraint(SpringLayout.WEST, amountPaid, 150, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, amountPaid, 115, SpringLayout.NORTH, singleTeacherPanel);

        JButton submitAmountPaid = addActionListenerPaySalaryBtn(amountPaid, teacher);
        singleTeacherPanel.add(submitAmountPaid);
        layout.putConstraint(SpringLayout.WEST, submitAmountPaid, 280, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, submitAmountPaid, 115, SpringLayout.NORTH, singleTeacherPanel);
    }

    // EFFECTS: adds focus listener to salary amount text field and returns it
    public JTextField addFocusListenerAmountTxtField() {
        JTextField amountPaid = new JTextField("amount($)", 10);
        // ADD FOCUS LISTENER
        amountPaid.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                amountPaid.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (amountPaid.getText().equals("")) {
                    amountPaid.setText("amount($)");
                }
            }
        });
        return amountPaid;
    }

    // EFFECTS: adds action listener to pay salary button and returns it
    public JButton addActionListenerPaySalaryBtn(JTextField amountPaid, Teacher teacher) {
        JButton submitAmountPaid = new JButton("Pay Amount");
        submitAmountPaid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                try {
                    int paymentAmount = Integer.valueOf(amountPaid.getText());
                    if (paymentAmount < 0) {
                        JOptionPane.showMessageDialog(singleTeacherFrame, "Amount must be positive.");
                    } else {
                        teacher.collectSalary(paymentAmount, mySchool);
                        JOptionPane.showMessageDialog(singleTeacherFrame, "Amount paid successfully.");
                        singleTeacherFrame.dispose();
                        showTeacherPage(teacher);
                    }
                } catch (NumberFormatException f) {
                    JOptionPane.showMessageDialog(singleTeacherFrame, "Amount must be a positive integer.");
                }
            }
        });
        return submitAmountPaid;
    }

    // MODIFIES: singleTeacherPanel
    // EFFECTS: makes and adds the assigned courses to teach table to panel
    public void makeAssignedCoursesTable(Teacher teacher, JPanel singleTeacherPanel, SpringLayout layout) {
        String[] tableHeader = {"Course Name"};
        Object[][] courseData = new Object[teacher.coursesTaught.size() + 1][1];
        int rowCounter = 0;
        for (String course : teacher.coursesTaught) {
            courseData[rowCounter][0] = course;
            rowCounter += 1;
        }
        JTable assignedCoursesTable = new JTable(courseData, tableHeader);
        assignedCoursesTable.setPreferredSize(null);
        assignedCoursesTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(assignedCoursesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Courses Taught:"));
        assignedCoursesTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(150, 400));

        singleTeacherPanel.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 550, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 160, SpringLayout.NORTH, singleTeacherPanel);
    }

    // MODIFIES: singleTeacherPanel
    // EFFECTS: makes and adds the salary history table to panel for passed in teacher
    public void makeSalaryHistoryTable(Teacher teacher, JPanel singleTeacherPanel, SpringLayout layout) {
        String[] tableHeader = {"Amount Paid ($)", "Timestamp", "Transaction ID"};
        Object[][] salaryData = new Object[teacher.salaryRecord.size() + 1][3];
        int rowCounter = 0;
        for (Transaction transaction : teacher.salaryRecord) {
            salaryData[rowCounter][0] = transaction.amount;
            salaryData[rowCounter][1] = transaction.timestamp;
            salaryData[rowCounter][2] = transaction.transactionID;
            rowCounter += 1;
        }
        JTable salaryHistoryTable = new JTable(salaryData, tableHeader);
        salaryHistoryTable.setPreferredSize(null);
        salaryHistoryTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(salaryHistoryTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Salary History:"));
        salaryHistoryTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        singleTeacherPanel.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 30, SpringLayout.WEST, singleTeacherPanel);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 160, SpringLayout.NORTH, singleTeacherPanel);
    }

    //EFFECTS: returns a 6 digit number that is the last ID generated incremented by 1
    // ID starts with '2' to represent it is a teacher ID
    public int generateTeacherID() {
        int newID;
        if (mySchool.lastTeacherIDGenerated != 0) {
            newID = mySchool.lastTeacherIDGenerated + 1;
            mySchool.lastTeacherIDGenerated += 1;
        } else {
            newID = 200000;
            mySchool.lastTeacherIDGenerated = 200000;
        }
        return newID;
    }
}