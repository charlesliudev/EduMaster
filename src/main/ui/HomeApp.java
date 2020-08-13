package ui;

import com.google.gson.Gson;
import model.School;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

// *NOTE* HomeApp() layout was inspired by a KeepToo project home page. Used for design inspiration only.
// ALL CODE INDEPENDENTLY WRITTEN.
// creates and opens the main home frame
public class HomeApp {
    School mySchool;
    Color myColor = new Color(52, 79, 235);

    // EFFECTS: runs home app
    public HomeApp() {
        runHomeApp();
    }

    // EFFECTS: creates the home frame
    public void runHomeApp() {
        loadAll();
        JFrame frame = new JFrame("EduMaster Home");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mySchool.saveAll("./data/school.json");
                System.exit(0);
            }
        });
        frame.setLayout(new BorderLayout());
        frame.setBackground(myColor);

        frame.add(makeUpperPanel(), BorderLayout.NORTH);
        frame.add(makeLowerPanel(), BorderLayout.CENTER);

        frame.pack();
        frame.getContentPane().requestFocusInWindow();
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    // EFFECTS: makes and returns the upper half of home page
    public Component makeUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());
        upperPanel.setBackground(myColor);

        // create and add main title
        JLabel mainTitleLabel = new JLabel(" EduMaster Home");
        mainTitleLabel.setIcon(createImageIcon("./images/homeMainIcon.png"));
        mainTitleLabel.setFont(new Font("Proxima Nova", Font.BOLD, 30));
        mainTitleLabel.setForeground(Color.white);
        mainTitleLabel.setBorder(new EmptyBorder(70, 50, 70, 0));
        upperPanel.add(mainTitleLabel, BorderLayout.CENTER);

        return upperPanel;
    }

    // EFFECTS: makes and returns the lower half of home page, with options
    public Component makeLowerPanel() {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setBorder(BorderFactory.createEmptyBorder(40, 55, 55, 55));
        GridLayout layout = new GridLayout(1, 4, 15, 0);
        lowerPanel.setLayout(layout);

        JButton overviewBtn = overviewBtn();
        JButton studentsBtn = studentBtn();
        JButton teachersBtn = teacherBtn();
        JButton coursesBtn = courseBtn();

        lowerPanel.add(overviewBtn);
        lowerPanel.add(studentsBtn);
        lowerPanel.add(teachersBtn);
        lowerPanel.add(coursesBtn);

        return lowerPanel;
    }

    // EFFECTS: makes overview btn with action listener
    public JButton overviewBtn() {
        JButton overviewBtn = makeBtn("Overview", "overviewIconBlue.png");
        overviewBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("src/main/ui/sounds/button-30.wav");
                new OverviewApp(mySchool);
            }
        });
        return overviewBtn;
    }

    // EFFECTS: makes student btn with action listener
    public JButton studentBtn() {
        JButton studentsBtn = makeBtn("Students", "studentsIconBlue.png");
        studentsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("src/main/ui/sounds/button-30.wav");
                new StudentsApp(mySchool);
            }
        });
        return studentsBtn;
    }

    // EFFECTS: makes teacher btn with action listener
    public JButton teacherBtn() {
        JButton teachersBtn = makeBtn("Teachers", "teachersIconBlue.png");
        teachersBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("src/main/ui/sounds/button-30.wav");
                new TeachersApp(mySchool);
            }
        });
        return teachersBtn;
    }

    // EFFECTS: makes courses btn with action listener
    public JButton courseBtn() {
        JButton coursesBtn = makeBtn("Courses", "courseIconBlue.png");
        coursesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("src/main/ui/sounds/button-30.wav");
                new CoursesApp(mySchool);
            }
        });
        return coursesBtn;
    }

    // EFFECTS: returns a button for home page, with button text and the appropriate icon, but without listeners
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
            init();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes School
    private void init() {
        mySchool = new School("UBC");
    }

    // EFFECTS: takes a file path and plays a sound file
    public static void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    // EFFECTS: takes png image, scales it and returns it as an ImageIcon object
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