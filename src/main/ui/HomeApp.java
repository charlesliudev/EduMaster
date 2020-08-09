package ui;

import com.google.gson.Gson;
import model.School;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

// creatse and opens the main home frame
public class HomeApp {
    School mySchool;
    Color myColor = new Color(52, 79, 235);

    // runs home app
    public HomeApp() {
        runHomeApp();
    }

    // creates the home frame
    public void runHomeApp() {
        loadAll();
        JFrame frame = new JFrame("EduMaster Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setBackground(myColor);

        frame.add(makeUpperPanel(), BorderLayout.NORTH);
        frame.add(makeLowerPanel(), BorderLayout.CENTER);

        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    // makes and returns the lower half of home page, with options
    public Component makeLowerPanel() {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setBorder(BorderFactory.createEmptyBorder(40, 55, 55, 55));
        GridLayout layout = new GridLayout(1, 4, 15, 0);

        lowerPanel.setLayout(layout);

        JButton overviewBtn = makeBtn("Overview", "overviewBtnIcon.png");
        // studentsBtn action listener
        JButton studentsBtn = makeBtn("Students", "studentsBtnIcon.png");
        studentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentsApp(mySchool);
            }
        });

        JButton teachersBtn = makeBtn("Teachers", "teachersBtnIcon.png");
        JButton coursesBtn = makeBtn("Courses", "coursesBtnIcon.png");

        lowerPanel.add(overviewBtn);
        lowerPanel.add(studentsBtn);
        lowerPanel.add(teachersBtn);
        lowerPanel.add(coursesBtn);

        return lowerPanel;
    }

    // returns a button for home page, with button text and the appropriate icon
    public JButton makeBtn(String buttonTxt, String iconName) {
        JButton newBtn = new JButton(buttonTxt);
        newBtn.setForeground(myColor);
        newBtn.setFont(new Font("Proxima Nova", Font.BOLD, 16));
        newBtn.setIcon(createImageIcon("./images/" + iconName));
        newBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        newBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        newBtn.setFocusPainted(false);
        return newBtn;
    }

    // makes and returns the upper half of home page
    public Component makeUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.setBackground(myColor);

        // create and add main title
        JLabel mainTitleLabel = new JLabel(" EduMaster Home");
        mainTitleLabel.setIcon(createImageIcon("./images/edumasterhomeicon.png"));
        mainTitleLabel.setFont(new Font("Proxima Nova", Font.BOLD, 30));
        mainTitleLabel.setForeground(Color.white);
        mainTitleLabel.setBorder(new EmptyBorder(70, 50, 70, 0));
        upperPanel.add(mainTitleLabel, BorderLayout.CENTER);

        return upperPanel;
    }

//    public Component rightSideUpperPanel() {
//        JPanel upperRightPanel = new JPanel();
//        upperRightPanel.setLayout(new GridLayout(2, 1));
//
//        JButton newTransactionsButton = new JButton("Enact New Transactions");
//
//        upperRightPanel.add(newTransactionsButton);
//
//        JButton newYearButton = new JButton("Start New Year");
//        upperRightPanel.add(newYearButton);
//
//        return upperRightPanel;
//    }

    // MODIFIES: this
    // EFFECTS: loads School from school.json file, if that file exists.
    // otherwise, initialize new School
    // *NOTE* some loadAll() design features taken from TellerApp
    private void loadAll() {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("./data/school.json");
            mySchool = gson.fromJson(reader, School.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // takes png image, scales it and returns it as an ImageIcon object
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);
        if (imgURL != null) {
            ImageIcon unscaledIcon = new ImageIcon(imgURL);
            Image image = unscaledIcon.getImage();
            Image newImg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(newImg);

            return scaledIcon;

        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}