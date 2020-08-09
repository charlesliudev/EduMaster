package ui;

//import javax.swing.*;
//import java.awt.*;
//
//public class GUI extends JPanel {
//    public GUI() {
//        super(new GridLayout(1, 1));
//        JTabbedPane tabbedPane = new JTabbedPane();
//
//        JComponent overview = new JLabel("School Overview");
//        tabbedPane.addTab("Tab 1", null, overview,
//                "Does nothing");
//    }
//}

import com.google.gson.Gson;
import model.School;
import model.Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class TabbedPaneDemo extends JPanel {
    School mySchool;

    public TabbedPaneDemo() {
        loadAll();


        JTabbedPane tabbedPane = new JTabbedPane();

        //PENDING: Add icons to tabs.

        Component overview = makeTextPanel("Overview");
        //ImageIcon homeIcon = createImageIcon("./images/home.png");
        tabbedPane.addTab("Overview", null, overview);
        tabbedPane.setSelectedIndex(0);

        Component students = makeTextPanel("Students");
        //ImageIcon studentIcon = createImageIcon("./images/users.png");
        tabbedPane.addTab("Students", null, students());

        Component teachers = makeTextPanel("Teachers");
        //ImageIcon teacherIcon = createImageIcon("./images/teachers.png");
        tabbedPane.addTab("Teachers", null, teachers);

        Component courses = makeTextPanel("Courses");
        //ImageIcon courseIcon = createImageIcon("./images/courses.png");
        tabbedPane.addTab("Courses", null, courses);

        //Add the tabbed pane to this panel.
        setLayout(new GridLayout(1, 0));
        add(tabbedPane);
    }

    private void loadAll() {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("./data/school.json");
            mySchool = gson.fromJson(reader, School.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // STUDENTS ------------------------------------------------

    // returns the student panel
    public Component students() {
        // main student panel
        JPanel studentPanel = new JPanel(new BorderLayout());
        studentPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Students Page",
                TitledBorder.CENTER,
                TitledBorder.TOP));


        // create students table
        String[] studentTableHeader = {"First Name", "Last Name", "Student ID"};
        Object[][] studentData = new Object[3][mySchool.students.size() + 1];
        int rowCounter = 0;
        for (Student student : mySchool.students) {
            studentData[rowCounter][0] = student.firstName;
            studentData[rowCounter][1] = student.lastName;
            studentData[rowCounter][2] = student.studentID;
            rowCounter += 1;
        }

        JTable studentTable = new JTable(studentData, studentTableHeader);

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Enrolled Students:"));
        studentTable.setFillsViewportHeight(true);
        // add table to studentPanel
        // have a scrollpane object that contains the entire table and its border

        // CREATING THE PANELS
        SpringLayout layout = new SpringLayout();
        JPanel mainContentPanel = new JPanel();

        JLabel addNewStudentLabel = new JLabel("Add New Student: ", JLabel.CENTER);
        JTextField studentFirstNameTxtField = new JTextField("First Name", 10);
        JTextField studentLastNameTxtField = new JTextField("Last Name",10);
        JButton submitNewStudentBtn = new JButton("Add Student");

        mainContentPanel.add(addNewStudentLabel);
        mainContentPanel.add(studentFirstNameTxtField);
        mainContentPanel.add(studentLastNameTxtField);
        mainContentPanel.add(submitNewStudentBtn);


//        JPanel upperPanel = new JPanel(new GridLayout(0, 4, 5, 5));


//        JPanel upperLeftPanel = new JPanel(new BorderLayout());
//        upperLeftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//        JPanel upperRightPanel = new JPanel(new BorderLayout());
//        upperRightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//


//        upperPanel.add(addNewStudentLabel);
//        upperPanel.add(studentFirstNameTxtField);
//        upperPanel.add(studentLastNameTxtField);
//        upperPanel.add(submitNewStudentBtn);


//
//        // adding items to panels
//        upperLeftPanel.add(addNewStudentLabel, BorderLayout.WEST);
//        upperLeftPanel.add(studentFirstNameTxtField, BorderLayout.CENTER);
//        upperLeftPanel.add(studentLastNameTxtField, BorderLayout.EAST);
//        upperRightPanel.add(submitNewStudentBtn, BorderLayout.EAST);
//
//
//        upperPanel.add(upperLeftPanel, BorderLayout.WEST);
//        upperPanel.add(upperRightPanel, BorderLayout.EAST);
        studentPanel.add(mainContentPanel, BorderLayout.NORTH);


        return studentPanel;
    }



    // end of students ----------------------------------------

    protected Component makeTextPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 0));
        panel.add(filler);
        return panel;
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        /*
         * Create a window.  Use JFrame since this window will include
         * lightweight components.
         */
        JFrame frame = new JFrame("EduMaster");

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frame.addWindowListener(l);
        frame.add("Center", new TabbedPaneDemo());
        frame.setSize(800, 700);
        frame.show();

    }

}