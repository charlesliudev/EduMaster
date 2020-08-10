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

// builds and runs the overview page
public class OverviewApp {
    School mySchool;
    Color myColor = new Color(52, 79, 235);
    JFrame frame;

    // runs overview app
    public OverviewApp(School school) {
        this.mySchool = school;
        runOverviewApp();
    }

    // builds the students page
    public void runOverviewApp() {
        frame = new JFrame("Overview");
        frame.setFont(new Font("Proxima Nova", Font.PLAIN, 15));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setForeground(myColor);
        frame.setBackground(myColor);

        frame.setLayout(new BorderLayout());
        frame.add(makeUpperPanel(), BorderLayout.NORTH);
        frame.add(makeLowerPanel(), BorderLayout.CENTER);

        frame.pack();
        frame.getContentPane().requestFocusInWindow();
        frame.setSize(800, 710);
        frame.setVisible(true);
    }

    // makes upper panel of frame
    public Component makeUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.setBackground(myColor);
        upperPanel.setPreferredSize(new Dimension(800, 150));

        JLabel mainTitleLabel = new JLabel(" Overview");
        mainTitleLabel.setIcon(createImageIcon("./images/overviewIconWhite.png"));
        mainTitleLabel.setFont(new Font("Proxima Nova", Font.BOLD, 30));
        mainTitleLabel.setForeground(Color.white);
        mainTitleLabel.setBorder(new EmptyBorder(70, 70, 70, 0));
        upperPanel.add(mainTitleLabel, BorderLayout.CENTER);

        return upperPanel;
    }

    // makes lower panel of frame
    public Component makeLowerPanel() {
        JPanel lowerPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        lowerPanel.setLayout(layout);

        makeEnactNewTrans(lowerPanel, layout);
        makeStartNewYear(lowerPanel, layout);
        makeNumberCourses(lowerPanel, layout);
        makeStudentTable(lowerPanel, layout);
        makeTeacherTable(lowerPanel, layout);
        makeTransactionHistoryTable(lowerPanel, layout);

        return lowerPanel;
    }


    // makes and adds the master command Enact New Transactions
    public void makeEnactNewTrans(JPanel lowerPanel, SpringLayout layout) {
        JLabel enactNewTransLabel = new JLabel("Enact Outstanding Transactions:", JLabel.CENTER);
        enactNewTransLabel.setFont(new Font("Proxima Nova", Font.BOLD, 14));
        lowerPanel.add(enactNewTransLabel);
        layout.putConstraint(SpringLayout.WEST, enactNewTransLabel, 80, SpringLayout.WEST, lowerPanel);
        layout.putConstraint(SpringLayout.NORTH, enactNewTransLabel, 20, SpringLayout.NORTH, lowerPanel);

        JButton enactNewTransBtn = new JButton("Enact Now");
        lowerPanel.add(enactNewTransBtn);
        layout.putConstraint(SpringLayout.WEST, enactNewTransBtn, 325, SpringLayout.WEST, lowerPanel);
        layout.putConstraint(SpringLayout.NORTH, enactNewTransBtn, 15, SpringLayout.NORTH, lowerPanel);

        // ADD BUTTON LISTENER TO ENACT NEW TRANSACTIONS:
        enactNewTransBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                mySchool.enactNewOutstandingTransactions();
                JOptionPane.showMessageDialog(frame,
                        "Transactions enacted. Your finances are up to date!");
                frame.dispose();
                new OverviewApp(mySchool);
            }
        });
    }

    // makes and adds the master command Start New Year
    public void makeStartNewYear(JPanel lowerPanel, SpringLayout layout) {
        JLabel startNewYearLabel = new JLabel("Start New Financial Year:", JLabel.CENTER);
        startNewYearLabel.setFont(new Font("Proxima Nova", Font.BOLD, 14));
        lowerPanel.add(startNewYearLabel);
        layout.putConstraint(SpringLayout.WEST, startNewYearLabel, 80, SpringLayout.WEST, lowerPanel);
        layout.putConstraint(SpringLayout.NORTH, startNewYearLabel, 50, SpringLayout.NORTH, lowerPanel);

        JButton startNewYearBtn = new JButton("Start Now ");
        lowerPanel.add(startNewYearBtn);
        layout.putConstraint(SpringLayout.WEST, startNewYearBtn, 325, SpringLayout.WEST, lowerPanel);
        layout.putConstraint(SpringLayout.NORTH, startNewYearBtn, 45, SpringLayout.NORTH, lowerPanel);

        // ADD BUTTON LISTENER TO START NEW YEAR:
        startNewYearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HomeApp.playSound("src/main/ui/sounds/button-30.wav");
                mySchool.startNewYear();
                JOptionPane.showMessageDialog(frame,
                        "Welcome to a new financial year.");
                frame.dispose();
                new OverviewApp(mySchool);
            }
        });
    }

    // makes and adds the course number count
    public void makeNumberCourses(JPanel lowerPanel, SpringLayout layout) {
        JLabel numberCourses = new JLabel("Number of Courses Offered: " + mySchool.courses.size(), JLabel.CENTER);
        lowerPanel.add(numberCourses);
        layout.putConstraint(SpringLayout.WEST, numberCourses, 525, SpringLayout.WEST, lowerPanel);
        layout.putConstraint(SpringLayout.NORTH, numberCourses, 50, SpringLayout.NORTH, lowerPanel);
    }


    // makes and adds student data block to panel in table
    public Component makeStudentTable(JPanel lowerPanel, SpringLayout layout) {
        String[] tableHeader = {"Item", "Value"};
        Object[][] studentData = new Object[3][2];
        studentData[0][0] = "Number of Students:";
        studentData[0][1] = mySchool.students.size();
        studentData[1][0] = "Total Outstanding Tuition:";
        studentData[1][1] = "$" + mySchool.getAllOutstandingTuition();
        studentData[2][0] = "Accumulated Annual Tuition:";
        studentData[2][1] = "$" + mySchool.accumulatedAnnualTuition;

        JTable studentTable = new JTable(studentData, tableHeader);
        studentTable.setPreferredSize(new Dimension(300, 100));
        studentTable.setDefaultEditor(Object.class, null);
        studentTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(230);


        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Students"));
        studentTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        lowerPanel.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 80, SpringLayout.WEST, lowerPanel);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 90, SpringLayout.NORTH, lowerPanel);

        return scrollPane;
    }

    // makes and adds teacher data block to panel in table
    public Component makeTeacherTable(JPanel lowerPanel, SpringLayout layout) {
        String[] tableHeader = {"Item", "Value"};
        Object[][] teacherData = new Object[3][2];
        teacherData[0][0] = "Number of Teachers:";
        teacherData[0][1] = mySchool.teachers.size();
        teacherData[1][0] = "Total Outstanding Salaries:";
        teacherData[1][1] = "$" + mySchool.getAllOutstandingSalaries();
        teacherData[2][0] = "Accumulated Annual Salaries:";
        teacherData[2][1] = "$" + mySchool.accumulatedAnnualSalary;

        JTable teacherTable = new JTable(teacherData, tableHeader);
        teacherTable.setPreferredSize(new Dimension(300, 100));
        teacherTable.setDefaultEditor(Object.class, null);
        teacherTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        teacherTable.getColumnModel().getColumn(0).setPreferredWidth(230);


        JScrollPane scrollPane = new JScrollPane(teacherTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Teachers"));
        teacherTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        lowerPanel.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 410, SpringLayout.WEST, lowerPanel);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 90, SpringLayout.NORTH, lowerPanel);

        return scrollPane;
    }

    // makes and adds transaction history data block to panel in table

    // makes and adds tuition history table to panel for passed in student
    public void makeTransactionHistoryTable(JPanel lowerPanel, SpringLayout layout) {
        String[] tableHeader = {"Transaction Summary"};
        Object[][] transactionData = new Object[mySchool.transactionRecordSummary.size() + 1][1];
        int rowCounter = 1;
        for (String transaction : mySchool.transactionRecordSummary) {
            transactionData[rowCounter][0] = transaction;
            rowCounter += 1;
        }
        JTable transactionRecordTable = new JTable(transactionData, tableHeader);
        transactionRecordTable.setPreferredSize(null);
        transactionRecordTable.setDefaultEditor(Object.class, null);

        JScrollPane scrollPane = new JScrollPane(transactionRecordTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transaction Record:"));
        transactionRecordTable.setFillsViewportHeight(true);
        scrollPane.setPreferredSize(new Dimension(630, 300));

        lowerPanel.add(scrollPane);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 80, SpringLayout.WEST, lowerPanel);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 205, SpringLayout.NORTH, lowerPanel);
    }


    // takes png image, scales it and returns it as an ImageIcon object
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = HomeApp.class.getResource(path);
        if (imgURL != null) {
            ImageIcon unscaledIcon = new ImageIcon(imgURL);
            Image image = unscaledIcon.getImage();
            Image newImg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(newImg);

            return scaledIcon;

        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


}
