package ui;

import model.Course;
import model.School;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import static ui.HomeApp.createImageIcon;

// EFFECTS: builds and runs the courses page
public class CoursesApp extends JPanel {
    School mySchool;
    Color myColor = new Color(52, 79, 235);
    JFrame frame;
    JFrame singleCourseFrame;

    // EFFECTS: runs course app
    public CoursesApp(School school) {
        this.mySchool = school;
        runCoursesApp();
    }

    // EFFECTS: builds the courses page
    public void runCoursesApp() {
        frame = new JFrame("Courses");
        frame.setFont(new Font("Proxima Nova", Font.PLAIN, 13));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setForeground(myColor);
        frame.setBackground(myColor);
        frame.setLayout(new BorderLayout());

        frame.add(makeUpperPanel(), BorderLayout.NORTH);
        frame.add(makeLowerPanel(), BorderLayout.CENTER);

        frame.pack();
        frame.getContentPane().requestFocusInWindow();
        frame.setSize(800, 770);
        frame.setVisible(true);
    }

    // EFFECTS: makes the upper panel and returns it
    public Component makeUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.setBackground(myColor);
        upperPanel.setPreferredSize(new Dimension(800, 150));

        JLabel mainTitleLabel = new JLabel(" Courses");
        mainTitleLabel.setIcon(createImageIcon("./images/coursesIconWhite.png"));
        mainTitleLabel.setFont(new Font("Proxima Nova", Font.BOLD, 30));
        mainTitleLabel.setForeground(Color.white);
        mainTitleLabel.setBorder(new EmptyBorder(70, 70, 70, 0));
        upperPanel.add(mainTitleLabel, BorderLayout.CENTER);

        return upperPanel;
    }

    // EFFECTS: makes the lower panel and returns it
    public Component makeLowerPanel() {
        JPanel contentPane = new JPanel();
        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        makeAddCourseSection(contentPane, layout);
        makeRemoveCourseSection(contentPane, layout);
        makeGetCourseSection(contentPane, layout);
        makeCourseTable(contentPane, layout, mySchool.courses);

        return contentPane;
    }

    // MODIFIES: contentPane
    // EFFECTS: displays the add course section and add to contentPane
    public void makeAddCourseSection(Container contentPane, SpringLayout layout) {
        JLabel addNewCourseLabel = new JLabel("Add New Course: ", JLabel.CENTER);
        addNewCourseLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        JTextField courseNameTxtField = addFocusCourseNameTxtField();
        JTextField tuitionTxtField = addFocusTuitionTxtField();
        JTextField salaryTxtField = addFocusSalaryTxtField();
        JTextField maxStudentsTxtField = addFocusMaxStudentsTxtField();
        JButton submitNewCourseBtn = addActionListenerSubmitCourseBtn(courseNameTxtField,
                tuitionTxtField, salaryTxtField, maxStudentsTxtField);

        addContentToPane(addNewCourseLabel, courseNameTxtField, tuitionTxtField, salaryTxtField,
                maxStudentsTxtField, submitNewCourseBtn, contentPane);

        layout.putConstraint(SpringLayout.WEST, addNewCourseLabel, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, addNewCourseLabel, 30, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, courseNameTxtField, 20, SpringLayout.EAST, addNewCourseLabel);
        layout.putConstraint(SpringLayout.NORTH, courseNameTxtField, 25, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, tuitionTxtField, 15, SpringLayout.EAST, courseNameTxtField);
        layout.putConstraint(SpringLayout.NORTH, tuitionTxtField, 25, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, salaryTxtField, 20, SpringLayout.EAST, addNewCourseLabel);
        layout.putConstraint(SpringLayout.NORTH, salaryTxtField, 50, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, maxStudentsTxtField, 15, SpringLayout.EAST, salaryTxtField);
        layout.putConstraint(SpringLayout.NORTH, maxStudentsTxtField, 50, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, submitNewCourseBtn, 15, SpringLayout.EAST, maxStudentsTxtField);
        layout.putConstraint(SpringLayout.NORTH, submitNewCourseBtn, 50, SpringLayout.NORTH, contentPane);
    }

    // MODIFIES: contentPane
    // EFFECTS: takes components and adds them to contentPane
    public void addContentToPane(JLabel addNewCourseLabel, JTextField courseNameTxtField, JTextField tuitionTxtField,
                                 JTextField salaryTxtField, JTextField maxStudentsTxtField,
                                 JButton submitNewCourseBtn, Container contentPane) {
        contentPane.add(addNewCourseLabel);
        contentPane.add(courseNameTxtField);
        contentPane.add(tuitionTxtField);
        contentPane.add(salaryTxtField);
        contentPane.add(maxStudentsTxtField);
        contentPane.add(submitNewCourseBtn);
    }

    // EFFECTS: adds focus listener to course name text field and returns text field
    public JTextField addFocusCourseNameTxtField() {
        JTextField courseNameTxtField = new JTextField("e.g.'CPSC-121'", 10);
        courseNameTxtField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                courseNameTxtField.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (courseNameTxtField.getText().equals("")) {
                    courseNameTxtField.setText("e.g.'CPSC-121'");
                }
            }
        });
        return courseNameTxtField;
    }

    // EFFECTS: adds focus listener to tuition text field and returns text field
    public JTextField addFocusTuitionTxtField() {
        JTextField tuitionTxtField = new JTextField("Annual Tuition", 10);
        tuitionTxtField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                tuitionTxtField.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (tuitionTxtField.getText().equals("")) {
                    tuitionTxtField.setText("Annual Tuition");
                }
            }
        });
        return tuitionTxtField;
    }

    // EFFECTS: adds focus listener to salary text field and returns text field
    public JTextField addFocusSalaryTxtField() {
        JTextField salaryTxtField = new JTextField("Annual Salary", 10);
        salaryTxtField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                salaryTxtField.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (salaryTxtField.getText().equals("")) {
                    salaryTxtField.setText("Annual Salary");
                }
            }
        });
        return salaryTxtField;
    }

    // EFFECTS: adds focus listener to max students text field and returns text field
    public JTextField addFocusMaxStudentsTxtField() {
        JTextField maxStudentsTxtField = new JTextField("Seat Limit", 10);
        maxStudentsTxtField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                maxStudentsTxtField.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (maxStudentsTxtField.getText().equals("")) {
                    maxStudentsTxtField.setText("Seat Limit");
                }
            }
        });
        return maxStudentsTxtField;
    }

    // EFFECTS: adds action listener to submit new course button and returns the button
    public JButton addActionListenerSubmitCourseBtn(JTextField courseNameTxtField, JTextField tuitionTxtField,
                                                    JTextField salaryTxtField, JTextField maxStudentsTxtField) {
        JButton submitNewCourseBtn = new JButton("Add Course");
        submitNewCourseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                try {
                    String courseToAddName = courseNameTxtField.getText();
                    int courseTuition = Integer.valueOf(tuitionTxtField.getText());
                    int courseSalary = Integer.valueOf(salaryTxtField.getText());
                    int maxStudents = Integer.valueOf(maxStudentsTxtField.getText());

                    if (isValid(courseTuition, courseSalary, maxStudents)) {
                        addCourseDecreaseMethodSize(courseToAddName, courseTuition, courseSalary, maxStudents);
                        new CoursesApp(mySchool);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter valid positive integers.");
                    }
                } catch (Exception f) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid inputs.");
                }
            }
        });
        return submitNewCourseBtn;
    }

    // EFFECTS: adds the course to the school when button is clicked
    // *NOTE* this method exists to decrease size of addActionListenerSubmitCourseBtn
    public void addCourseDecreaseMethodSize(String courseToAddName, int courseTuition,
                                            int courseSalary, int maxStudents) {
        Course newCourse = new Course(courseToAddName, courseTuition, courseSalary, maxStudents);
        mySchool.addCourse(newCourse);
        JOptionPane.showMessageDialog(frame, courseToAddName + " was successfully added.");
    }

    // EFFECTS: checks if all int inputs are all > 0, return true if they are, false otherwise
    public boolean isValid(int courseTuition, int courseSalary, int maxStudents) {
        if ((courseTuition < 0) || (courseSalary < 0) || (maxStudents < 0)) {
            return false;
        }
        return true;
    }

    // MODIFIES: contentPane
    // EFFECTS: displays the remove course section and adds to contentPane
    public void makeRemoveCourseSection(Container contentPane, SpringLayout layout) {
        JLabel removeCourseLabel = new JLabel("Remove a Course: ", JLabel.CENTER);
        removeCourseLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        JTextField courseNameTxtField = addFocusLongCourseNameTxtField();
        JButton removeCourseBtn = addActionListenerRemoveCourseBtn(courseNameTxtField);

        contentPane.add(removeCourseLabel);
        contentPane.add(courseNameTxtField);
        contentPane.add(removeCourseBtn);

        layout.putConstraint(SpringLayout.WEST, removeCourseLabel, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, removeCourseLabel, 90, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, courseNameTxtField, 15, SpringLayout.EAST, removeCourseLabel);
        layout.putConstraint(SpringLayout.NORTH, courseNameTxtField, 85, SpringLayout.NORTH, contentPane);

        layout.putConstraint(SpringLayout.WEST, removeCourseBtn, 15, SpringLayout.EAST, courseNameTxtField);
        layout.putConstraint(SpringLayout.NORTH, removeCourseBtn, 85, SpringLayout.NORTH, contentPane);
    }

    // EFFECTS: adds focus listener to course name txt field
    public JTextField addFocusLongCourseNameTxtField() {
        JTextField courseNameTxtField = new JTextField("e.g.'CPSC-210'", 22);
        courseNameTxtField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                courseNameTxtField.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (courseNameTxtField.getText().equals("")) {
                    courseNameTxtField.setText("e.g.'CPSC-210'");
                }
            }
        });
        return courseNameTxtField;
    }

    // EFFECTS: adds action listener to remove course button, makes the button and returns it
    public JButton addActionListenerRemoveCourseBtn(JTextField courseNameTxtField) {
        JButton removeCourseBtn = new JButton("Remove Course");
        removeCourseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                String courseName = courseNameTxtField.getText();
                if (findCourseByName(courseName) != null) {
                    Course toRemove = findCourseByName(courseName);
                    mySchool.removeCourse(toRemove);
                    JOptionPane.showMessageDialog(frame, courseName + " was succesfully removed.");
                    new CoursesApp(mySchool);
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Course not found. Try again.");
                }
            }
        });
        return removeCourseBtn;
    }

    // MODIFIES: contentPane
    // EFFECTS: gets a course and displays course profile
    public void makeGetCourseSection(Container contentPane, SpringLayout layout) {
        JLabel getCourseLabel = new JLabel("Get a Course: ", JLabel.CENTER);
        getCourseLabel.setFont(new Font("Proxima Nova", Font.BOLD, 13));
        JTextField courseNameTxtField = addFocusLongCourseNameTxtField();
        JButton getCourseBtn = addActionListenerGetCourseBtn(courseNameTxtField);

        contentPane.add(getCourseLabel);
        contentPane.add(courseNameTxtField);
        contentPane.add(getCourseBtn);

        layout.putConstraint(SpringLayout.WEST, getCourseLabel, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, getCourseLabel, 125, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, courseNameTxtField, 45, SpringLayout.EAST, getCourseLabel);
        layout.putConstraint(SpringLayout.NORTH, courseNameTxtField, 120, SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, getCourseBtn, 15, SpringLayout.EAST, courseNameTxtField);
        layout.putConstraint(SpringLayout.NORTH, getCourseBtn, 120, SpringLayout.NORTH, contentPane);


    }

    // EFFECTS: adds action listener to get course button and returns button
    public JButton addActionListenerGetCourseBtn(JTextField courseNameTxtField) {
        JButton getCourseBtn = new JButton("Get Course");
        getCourseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                String courseName = courseNameTxtField.getText();
                if (findCourseByName(courseName) != null) {
                    Course toView = findCourseByName(courseName);
                    showCoursePage(toView);
                } else {
                    JOptionPane.showMessageDialog(frame, "Course not found. Try again.");
                }
            }
        });
        return getCourseBtn;
    }

    // EFFECTS: takes the name of a course and searches for it in school. Return course if found, else return null
    private Course findCourseByName(String id) {
        for (Course course : mySchool.courses) {
            if (course.courseName.equals(id)) {
                return course;
            }
        }
        return null;
    }

    // MODIFIES: contentPane
    // EFFECTS: show all the courses offered in a table and adds to contentPane
    public void makeCourseTable(Container contentPane, SpringLayout layout, ArrayList<Course> courses) {
        // create courses table
        String[] courseTableHeader = {"Course", "# of Students Enrolled", "Max. Students"};
        Object[][] courseData = new Object[courses.size()][3];
        int rowCounter = 0;
        for (Course course : courses) {
            courseData[rowCounter][0] = course.courseName;
            courseData[rowCounter][1] = course.students.size();
            courseData[rowCounter][2] = course.maxStudents;
            rowCounter += 1;
        }
        JTable courseTable = new JTable(courseData, courseTableHeader);
        courseTable.setPreferredSize(new Dimension(800, 400));
        courseTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Courses Offered"));
        courseTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        contentPane.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 100, SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 170, SpringLayout.NORTH, contentPane);
    }

    // DISPLAY AN INDIVIDUAL COURSE: --------------------------------------------------
    // EFFECTS: makes show individual course profile page
    public void showCoursePage(Course course) {
        singleCourseFrame = new JFrame("Viewing " + course.courseName);
        singleCourseFrame.setFont(new Font("Proxima Nova", Font.PLAIN, 13));
        singleCourseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        singleCourseFrame.setForeground(myColor);
        SpringLayout layout = new SpringLayout();

        JPanel singleCoursePanel = new JPanel();
        singleCoursePanel.setLayout(layout);

        makeNameTitle(course, singleCoursePanel, layout);
        makeCourseDataBlock(course, singleCoursePanel, layout);
        makeStudentTeacherTable(course, singleCoursePanel, layout);

        // end stuff
        singleCourseFrame.add(singleCoursePanel);
        singleCourseFrame.pack();
        singleCourseFrame.getContentPane().requestFocusInWindow();
        singleCourseFrame.setSize(new Dimension(365, 560));
        singleCourseFrame.setVisible(true);
    }

    // MODIFIES: singleCoursePanel
    // EFFECTS: makes and adds the name title to course panel
    public void makeNameTitle(Course course, JPanel singleCoursePanel, SpringLayout layout) {
        JLabel nameTitle = new JLabel(course.courseName + " Profile", JLabel.CENTER);
        nameTitle.setFont(new Font("Proxima Nova", Font.BOLD, 17));
        singleCoursePanel.add(nameTitle);
        layout.putConstraint(SpringLayout.WEST, nameTitle, 30, SpringLayout.WEST, singleCoursePanel);
        layout.putConstraint(SpringLayout.NORTH, nameTitle, 15, SpringLayout.NORTH, singleCoursePanel);
    }

    // MODIFIES: singleCoursePanel
    // EFFECTS: shows the tuition fees, salary pay, and max student count of course
    public void makeCourseDataBlock(Course course, JPanel singleCoursePanel, SpringLayout layout) {
        JLabel courseTuitionLabel = new JLabel("Annual Tuition: $" + course.courseCost);
        singleCoursePanel.add(courseTuitionLabel);
        layout.putConstraint(SpringLayout.WEST, courseTuitionLabel, 30, SpringLayout.WEST, singleCoursePanel);
        layout.putConstraint(SpringLayout.NORTH, courseTuitionLabel, 45, SpringLayout.NORTH, singleCoursePanel);

        JLabel courseSalaryLabel = new JLabel("Annual Salary: $" + course.courseSalary);
        singleCoursePanel.add(courseSalaryLabel);
        layout.putConstraint(SpringLayout.WEST, courseSalaryLabel, 30, SpringLayout.WEST, singleCoursePanel);
        layout.putConstraint(SpringLayout.NORTH, courseSalaryLabel, 65, SpringLayout.NORTH, singleCoursePanel);

        JLabel courseMaxStudentLabel = new JLabel("Seat Limit: " + course.maxStudents + " students");
        singleCoursePanel.add(courseMaxStudentLabel);
        layout.putConstraint(SpringLayout.WEST, courseMaxStudentLabel, 30, SpringLayout.WEST, singleCoursePanel);
        layout.putConstraint(SpringLayout.NORTH, courseMaxStudentLabel, 85, SpringLayout.NORTH, singleCoursePanel);
    }

    // MODIFIES: singleCoursePanel
    // EFFECTS: makes and adds the students enrolled in course and teachers that teach the course into a table
    public void makeStudentTeacherTable(Course course, JPanel singleCoursePanel, SpringLayout layout) {
        String[] tableHeader = {"Students by ID", "Teachers by ID"};
        Object[][] tableData = new Object[returnLargerLength(course.students, course.teachers)][2];
        int studentRowCounter = 0;
        int teacherRowCounter = 0;
        for (String student : course.students) {
            tableData[studentRowCounter][0] = student;
            studentRowCounter += 1;
        }
        for (String teacher : course.teachers) {
            tableData[teacherRowCounter][1] = teacher;
            teacherRowCounter += 1;
        }

        JTable theTable = new JTable(tableData, tableHeader);
        theTable.setPreferredSize(null);
        theTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(theTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Course Population:"));
        theTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(300, 360));

        singleCoursePanel.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 30, SpringLayout.WEST, singleCoursePanel);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 115, SpringLayout.NORTH, singleCoursePanel);
    }

    // EFFECTS: takes two lists of strings and returns longer length
    public int returnLargerLength(ArrayList<String> listOne, ArrayList<String> listTwo) {
        if (listOne.size() > listTwo.size()) {
            return listOne.size();
        } else {
            return listTwo.size();
        }
    }
}