package ui;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import com.google.gson.Gson;
import model.School;
import model.Student;
import model.Teacher;
import model.Course;
import model.Transaction;

// School Management Application (console-ui)
public class SchoolApp {
    private Scanner input;
    School mySchool;

    // EFFECTS: runs the school application
    public SchoolApp() {
        runSchool();
    }

    // MODIFIES: this
    // EFFECTS: opens the EduMaster home menu for users and processes user command
    // *NOTE*: runSchool() method was designed based off of TellerApp() from the Teller Application
    private void runSchool() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);
        loadAll();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
                mySchool.saveAll("./data/school.json");
                System.out.println("All changes saved. Have a good day :)");
            } else {
                handleCommand(command);
            }
        }
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

    // EFFECTS: displays a menu of the options available to user
    private void displayMenu() {
        System.out.println("____________________________________");
        System.out.println("Welcome to EduMaster Home!");
        System.out.println("What would you like to do today?");
        System.out.println("\to -> Overview");
        System.out.println("\ts -> Students");
        System.out.println("\tt -> Teachers");
        System.out.println("\tc -> Courses");
        System.out.println("\tx -> Enact New Outstanding Fees");
        System.out.println("\tn -> Start New Financial Year");
        System.out.println("\tq -> Quit and Save");
    }

    // MODIFIES: this
    // EFFECTS: takes user input command and calls on the correct method to handle user desire
    private void handleCommand(String command) {
        command = command.toLowerCase();
        if (command.equals("o")) {
            overview();
        } else if (command.equals("s")) {
            students();
        } else if (command.equals("t")) {
            teachers();
        } else if (command.equals("c")) {
            courses();
        } else if (command.equals("x")) {
            enactNewOutstandingFees();
        } else if (command.equals("n")) {
            startNewYear();
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // START NEW YEAR BRANCH (n) ---------------------------------------------------

    // resets the accumulated annual cash flows record to 0, to begin recording for new year.
    public void startNewYear() {
        mySchool.startNewYear();
        System.out.println("Annual accumulated transactions have been set to zero. Welcome to a new financial year.");
    }

    // SCHOOL OVERVIEW BRANCH (o) ----------------------------------------

    // displays an overview of the school's status when called on, showing number of students, teachers, courses. Also
    // shows outstanding transactions, total student tuition for the year
    public void overview() {
        System.out.println("--------- School Overview ---------");
        System.out.println("Number of Students: " + mySchool.students.size());
        System.out.println("Number of Teachers: " + mySchool.teachers.size());
        System.out.println("Number of Courses Offered: " + mySchool.courses.size());
        System.out.println("Outstanding Student Tuition: $" + mySchool.getAllOutstandingTuition());
        System.out.println("Total Annual Student Tuition: $" + mySchool.accumulatedAnnualTuition);
        System.out.println("Outstanding Teacher Salaries: $" + mySchool.getAllOutstandingSalaries());
        System.out.println("Total Annual Teacher Salary: $" + mySchool.accumulatedAnnualSalary);
        System.out.println("------------------------------------");
        System.out.println("Full Transaction Record: ");
        for (String summary : mySchool.transactionRecordSummary) {
            System.out.println(summary);
        }
        System.out.println("End");
    }

    // ENACT OUTSTANDING FEES BRANCH (x) -------------------------------------------

    // EFFECTS: enacts the annual outstanding tuition to students for the year based on their courses taken
    //          enacts the annual outstanding salaries to teachers for the year based on their courses taught
    public void enactNewOutstandingFees() {
        mySchool.enactNewOutstandingTransactions();
        System.out.println("New outstanding tuition fees and salaries for the year have been enacted.");
    }

    // TEACHERS BRANCH (t) -------------------------------------------------------------

    // EFFECTS: opens the teachers menu, where user can then choose what they want to do with teachers
    private void teachers() {
        boolean stayOnTeachers = true;

        while (stayOnTeachers) {
            displayTeacherMenu();
            String command = input.next();

            if (command.equals("b")) {
                stayOnTeachers = false;
            } else {
                handleTeacherCommand(command);
            }
        }
    }

    // EFFECTS: displays teacher menu
    private void displayTeacherMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("What would you like to do with the teachers?");
        System.out.println("\tv -> View all teachers");
        System.out.println("\te -> Edit / View a teacher profile");
        System.out.println("\ta -> Add new teachers");
        System.out.println("\tr -> Remove a teacher");
        System.out.println("\tb -> Back to main menu");
    }

    // MODIFIES: this
    // EFFECTS: takes user input from teacher menu and calls on appropriate methods to handle user desire
    private void handleTeacherCommand(String command) {
        command = command.toLowerCase();
        if (command.equals("v")) {
            displayAllTeachers();
        } else if (command.equals("a")) {
            addNewTeacher();
        } else if (command.equals("e")) {
            handleTeacherSearch();
        } else if (command.equals("r")) {
            removeTeacher();
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // EFFECTS: lets user search for a teacher by ID and processes the search to display teacher info
    public void handleTeacherSearch() {
        System.out.println("Enter teacher's ID: ");
        String unprocessedInput = input.next();
        try {
            int id = Integer.valueOf(unprocessedInput);
            Teacher thisTeacher = findTeacherByID(id);
            showTeacherPage(thisTeacher);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid 6-digit ID. You can view all teachers to find valid IDs.");
        }
    }

    // EFFECTS: takes a teacher and displays their profile, along with a menu with actions that can be taken on teacher
    private void showTeacherPage(Teacher teacher) {
        if (teacher != null) {
            boolean stay = true;
            while (stay) {
                displayTeacherInfo(teacher);
                showIndividualTeacherOptions(teacher);
                String editTeacherCommand = input.next().toLowerCase();
                if (editTeacherCommand.equals("b")) {
                    stay = false;
                } else {
                    handleEditTeacherCommand(editTeacherCommand, teacher);
                }
            }
        } else {
            System.out.println("Cannot find teacher with this ID. Please try again.");
        }
    }

    // EFFECTS: takes user input from the teacher profile page and calls on appropriate methods to handle user desire
    private void handleEditTeacherCommand(String command, Teacher teacher) {
        command = command.toLowerCase();
        if (command.equals("a")) {
            assignTeacherToCourse(teacher);
        } else if (command.equals("p")) {
            recordTeacherSalaryPayment(teacher);
        } else if (command.equals("h")) {
            showSalaryHistory(teacher);
        } else if (command.equals("c")) {
            showAssignedCourses(teacher);
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // EFFECTS: takes teacher and shows user the courses that teacher is assigned to
    private void showAssignedCourses(Teacher teacher) {
        System.out.println(teacher.firstName + "'s assigned courses:");
        System.out.println("----------------------");
        for (String courseName : teacher.courses) {
            System.out.println(courseName);
        }
    }

    // EFFECTS: takes a teacher and shows user the salary history of the teacher
    private void showSalaryHistory(Teacher teacher) {
        System.out.println(teacher.firstName + "'s payment history:");
        System.out.println("-------------------------");
        for (Transaction payment : teacher.transactionRecord) {
            System.out.println(payment.timestamp + ": $" + payment.amount);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to write off a payment to a teacher. Adds to school's transaction records, deprecates
    //          teacher's outstanding salaries, adds to teacher's salary records
    private void recordTeacherSalaryPayment(Teacher teacher) {
        try {
            System.out.println("How much was the teacher paid?");
            int paymentAmount = Integer.valueOf(input.next());
            if (paymentAmount < 0) {
                System.out.println("Error occurred. Amount must be positive.");
            } else {
                teacher.payOutstandingTransaction(paymentAmount, mySchool);
                System.out.println("Salary paid successfully.");
                System.out.println("Remaining outstanding salary: $" + teacher.outstandingTransaction + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a positive integer amount.");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to assign a teacher to teach a new course
    private void assignTeacherToCourse(Teacher teacher) {
        System.out.println("Assigning " + teacher.firstName + teacher.lastName + " to teach a new course:");
        System.out.println("Enter course name. Format example: 'CPSC-210'");
        String courseName = input.next();
        Course theCourse = findCourseByName(courseName);
        if (theCourse != null) {
            if (theCourse.courseName.equals(courseName)) {
                teacher.assignCourse(theCourse);
                System.out.println("Course assigned to successfully.");
            }
        } else {
            System.out.println("Sorry, course not found. Try viewing all courses offered.");
        }
    }


    // EFFECTS: lists the ID, first name, last name, of all teachers at the school
    private void displayAllTeachers() {
        System.out.println("Here are the teachers in this school:");
        System.out.println("\tTeacher ID, First Name, Last Name");
        System.out.println("\t-----------------------------------");
        for (Teacher teacher : mySchool.teachers) {
            System.out.println("\t" + teacher.id + ", " + teacher.firstName + ", " + teacher.lastName);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a new teacher to the school database
    private void addNewTeacher() {
        System.out.println("What is the teacher's first name?");
        String firstName = input.next().toLowerCase();
        System.out.println("What is the teacher's last name?");
        String lastName = input.next().toLowerCase();
        Teacher newTeacher = new Teacher(firstName, lastName, generateTeacherID());
        mySchool.addTeacher(newTeacher);
        System.out.println("Teacher successfully added, with an ID of " + newTeacher.id + ".");
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove a teacher from the school
    private void removeTeacher() {
        System.out.println("Please enter teacher ID: ");
        try {
            int id = Integer.valueOf(input.next());
            if (findTeacherByID(id) != null) {
                Teacher toRemove = findTeacherByID(id);
                mySchool.removeTeacher(toRemove);
                toRemove.schoolAttended = null;
                System.out.println("Teacher successfully removed.");
            } else {
                System.out.println("Teacher not found. Try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid 6-digit teacher ID.");
        }
    }

    // EFFECTS: takes an teacherID, finds teacher in school database. If found, return the teacher, else return null
    private Teacher findTeacherByID(int id) {
        for (Teacher teacher : mySchool.teachers) {
            if (teacher.id == id) {
                return teacher;
            }
        }
        return null;
    }

    // EFFECTS: displays all info about the selected teacher: first name, last name, ID, outstanding salary to pay
    private void displayTeacherInfo(Teacher teacher) {
        System.out.println("------------------------------------");
        System.out.println("Currently viewing " + teacher.firstName + " " + teacher.lastName + "'s profile:");
        System.out.println(teacher.firstName + " " + teacher.lastName);
        System.out.println("Teacher ID: " + teacher.id);
        System.out.println("Outstanding Salary to Pay: $" + teacher.outstandingTransaction);
        System.out.println("Options: ");
    }

    // EFFECTS: displays menu with options for user to operate on the selected teacher
    private void showIndividualTeacherOptions(Teacher teacher) {
        System.out.println("\ta -> Assign teacher to a course");
        System.out.println("\tc -> See courses taught");
        System.out.println("\tp -> Record payment of salary");
        System.out.println("\th -> View full salary record");
        System.out.println("\tb -> Back");
    }

    // COURSES BRANCH (c) -------------------------------------------------------------

    // EFFECTS: opens the course menu, where user can then choose what they want to do with courses
    private void courses() {
        boolean stayOnCourses = true;

        while (stayOnCourses) {
            displayCourseMenu();
            String command = input.next();

            if (command.equals("b")) {
                stayOnCourses = false;
            } else {
                handleCourseCommand(command);
            }
        }
    }

    // EFFECTS: displays the course menu to user
    private void displayCourseMenu() {
        System.out.println("-------------------------------------------");
        System.out.println("What would you like to do with the courses?");
        System.out.println("\tv -> View all courses");
        System.out.println("\te -> View a course");
        System.out.println("\ta -> Add new courses");
        System.out.println("\tr -> Remove a course");
        System.out.println("\tb -> Back to main menu");
    }

    // EFFECTS: processes the user input from the course menu and calls on appropriate methods
    private void handleCourseCommand(String command) {
        command = command.toLowerCase();
        if (command.equals("v")) {
            displayAllCourses();
        } else if (command.equals("a")) {
            addNewCourse();
        } else if (command.equals("e")) {
            handleCourseSearch();
        } else if (command.equals("r")) {
            removeCourse();
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // EFFECTS: lets user search for a course by name and processes the search to display course info
    private void handleCourseSearch() {
        System.out.println("Enter course's name. Format example: 'CPSC-220'");
        String id = input.next();
        Course thisCourse = findCourseByName(id);
        showCoursePage(thisCourse);
    }

    // EFFECTS: shows the profile of the selected course with menu of options to operate on selected course
    private void showCoursePage(Course course) {
        if (course != null) {
            boolean stay = true;
            while (stay) {
                displayCourseInfo(course);
                showIndividualCourseOptions(course);
                String editCourseCommand = input.next().toLowerCase();
                if (editCourseCommand.equals("b")) {
                    stay = false;
                } else {
                    handleEditCourseCommand(editCourseCommand, course);
                }
            }
        } else {
            System.out.println("Cannot find course with this name. Please try again.");
        }
    }

    // EFFECTS: processes the user input from the course menu
    private void handleEditCourseCommand(String command, Course course) {
        command = command.toLowerCase();
        if (command.equals("s")) {
            displayStudents(course);
        } else if (command.equals("t")) {
            displayTeachers(course);
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // EFFECTS: displays a list of name of all courses offered in school
    private void displayAllCourses() {
        System.out.println("Here are the courses offered in this school:");
        System.out.println("\tCourse Name");
        System.out.println("\t-----------");
        for (Course course : mySchool.courses) {
            System.out.println("\t" + course.courseName);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a new course to be offered in the school
    private void addNewCourse() {
        try {
            System.out.println("What is the name of the course? Format example: 'CPSC-210'");
            String courseName = input.next().toLowerCase();
            System.out.println("What is the course's tuition fee?");
            int courseCost = Integer.valueOf(input.next());
            System.out.println("What is the course's salary pay?");
            int courseSalary = Integer.valueOf(input.next());
            System.out.println("What is the course's seat limit?");
            int maxStudents = Integer.valueOf(input.next());
            Course newCourse = new Course(courseName, courseCost, courseSalary, maxStudents);
            mySchool.addCourse(newCourse);
            System.out.println("Course successfully added.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove a course from the school
    private void removeCourse() {
        System.out.println("Please enter course name. Format example: 'CPSC-210'");
        String id = input.next();
        if (findCourseByName(id) != null) {
            mySchool.removeCourse(findCourseByName(id));
            System.out.println("Course successfully removed.");
        } else {
            System.out.println("Course not found. Try again.");
        }
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

    // EFFECTS: displays the profile of a selected course to user, with options menu to operate on selected course
    private void displayCourseInfo(Course course) {
        System.out.println("------------------------------------");
        System.out.println("Currently viewing " + course.courseName + " profile:");
        System.out.println(course.courseName);
        System.out.println("Annual Tuition Fee: $" + course.courseCost);
        System.out.println("Annual Salary Paid: $" + course.courseSalary);
        System.out.println("Options: ");
    }

    // EFFECTS: displays the students enrolled in a course
    private void displayStudents(Course course) {
        System.out.println("Viewing students enrolled in: " + course.courseName);
        System.out.println("------------------------------------");
        for (String studentID : course.students) {
            Student student = findStudentByID(Integer.valueOf(studentID));
            System.out.println("\t" + student.id + ", " + student.firstName + ", " + student.lastName);
        }
    }

    // EFFECTS: displays the teachers that teach the course
    private void displayTeachers(Course course) {
        System.out.println("Viewing teachers that teach in: " + course.courseName);
        System.out.println("------------------------------------");
        for (String teacherID : course.teachers) {
            Teacher teacher = findTeacherByID(Integer.valueOf(teacherID));
            System.out.println("\t" + teacher.id + ", " + teacher.firstName + ", " + teacher.lastName);
        }
    }

    // EFFECTS: shows the menu with options to operate on a selected course to users
    private void showIndividualCourseOptions(Course course) {
        System.out.println("\ts -> View students enrolled");
        System.out.println("\tt -> View teachers of course");
        System.out.println("\tb -> Back");
    }

    // STUDENTS BRANCH (s) -------------------------------------------------------------

    // EFFECTS: opens the students menu, where user can then choose what they want to do with students
    private void students() {
        boolean stayOnStudents = true;

        while (stayOnStudents) {
            displayStudentMenu();
            String command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                stayOnStudents = false;
            } else {
                handleStudentCommand(command);
            }
        }
    }

    // EFFECTS: displays the student menu
    private void displayStudentMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("What would you like to do with the students?");
        System.out.println("\tv -> View all students");
        System.out.println("\te -> Edit / View a student profile");
        System.out.println("\ta -> Add new students");
        System.out.println("\tr -> Remove a student");
        System.out.println("\tb -> Back to main menu");
    }

    // EFFECTS: processes the user input from the student menu and calls on appropriate methods
    private void handleStudentCommand(String command) {
        command = command.toLowerCase();
        if (command.equals("v")) {
            displayAllStudents();
        } else if (command.equals("a")) {
            addNewStudent();
        } else if (command.equals("e")) {
            handleStudentSearch();
        } else if (command.equals("r")) {
            removeStudent();
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // EFFECTS: lets user search for a student by ID and processes the search to display student info
    public void handleStudentSearch() {
        System.out.println("Enter student's ID. You may view all students to see valid IDs.");
        String unprocessedInput = input.next();
        try {
            int id = Integer.valueOf(unprocessedInput);
            Student thisStudent = findStudentByID(id);
            showStudentPage(thisStudent);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid 6-digit ID. You can view all students to find valid IDs.");
        }
    }

    // shows the profile of a single selected student along with options to operate on selected student
    private void showStudentPage(Student student) {
        if (student != null) {
            boolean stay = true;
            while (stay) {
                displayStudentInfo(student);
                showIndividualStudentOptions(student);
                String editStudentCommand = input.next().toLowerCase();
                if (editStudentCommand.equals("b")) {
                    stay = false;
                } else {
                    handleEditStudentCommand(editStudentCommand, student);
                }
            }
        } else {
            System.out.println("Cannot find student with this ID. Please try again.");
        }
    }

    // EFFECTS: processes user input from individual student options menu
    private void handleEditStudentCommand(String command, Student student) {
        if (command.equals("a")) {
            enrollStudentInCourse(student);
        } else if (command.equals("p")) {
            recordStudentTuitionPayment(student);
        } else if (command.equals("h")) {
            showTuitionHistory(student);
        } else if (command.equals("c")) {
            showEnrolledCourses(student);
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // EFFECTS: lists out the courses in which student is enrolled
    private void showEnrolledCourses(Student student) {
        System.out.println("\t" + student.firstName + "'s enrolled courses:");
        System.out.println("\t--------------------------------");
        for (String courseName : student.courses) {
            System.out.println("\t" + courseName);
        }
    }

    // EFFECTS: displays to user all tuition transactions made by student
    private void showTuitionHistory(Student student) {
        System.out.println(student.firstName + "'s payment history:");
        System.out.println("-------------------------");
        for (Transaction payment : student.transactionRecord) {
            System.out.println(payment.timestamp + ": $" + payment.amount);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to write down a student's tuition payment, adds to school's transaction records,
    //          deprecates student's outstanding tuition, adds to student's tuition history
    private void recordStudentTuitionPayment(Student student) {
        try {
            System.out.println("How much did the student pay?");
            int paymentAmount = Integer.valueOf(input.next());
            if (paymentAmount < 0) {
                System.out.println("Error occurred. Amount must be positive.");
            } else {
                student.payOutstandingTransaction(paymentAmount, mySchool);
                System.out.println("Tuition paid successfully.");
                System.out.println("Remaining outstanding tuition: $" + student.outstandingTransaction + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a positive integer amount.");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to enroll student into a new course
    private void enrollStudentInCourse(Student student) {
        System.out.println("Enrolling " + student.firstName + " " + student.lastName + " into a new course:");
        System.out.println("Enter course name. Format example: 'CPSC210' ");
        String courseName = input.next();
        Course theCourse = findCourseByName(courseName);
        if (theCourse != null) {
            if (student.assignCourse(theCourse)) {
                System.out.println("Course enrolled in successfully.");
            } else {
                System.out.println("Sorry, this course is full.");
            }
        } else {
            System.out.println("Sorry, course not found. Try viewing all courses offered.");
        }
    }

    // EFFECTS: displays a list of students ID, first name, last name, at the school to user
    private void displayAllStudents() {
        System.out.println("Here are the students in this school:");
        System.out.println("\tStudent ID, First Name, Last Name");
        System.out.println("\t-----------------------------------");
        for (Student student : mySchool.students) {
            System.out.println("\t" + student.id + ", " + student.firstName + ", " + student.lastName);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add a new student to the school
    private void addNewStudent() {
        System.out.println("What is the student's first name?");
        String firstName = input.next().toLowerCase();
        System.out.println("What is the student's last name?");
        String lastName = input.next().toLowerCase();
        Student newStudent = new Student(firstName, lastName, generateStudentID());
        mySchool.addStudent(newStudent);
        System.out.println("Student successfully added, with an ID of " + newStudent.id + ".");
    }

    // MODIFIES: this
    // EFFECTS: allows user to remove a student from the school
    private void removeStudent() {
        System.out.println("Please enter student ID: ");
        try {
            int id = Integer.valueOf(input.next());
            if (findStudentByID(id) != null) {
                Student toRemove = findStudentByID(id);
                mySchool.removeStudent(toRemove);
                toRemove.schoolAttended = null;
                System.out.println("Student successfully removed.");
            } else {
                System.out.println("Student not found. Try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid 6-digit student ID.");
        }
    }

    // EFFECTS: takes a student ID and searches for student in school. Return student if found, else return null
    private Student findStudentByID(int id) {
        for (Student student : mySchool.students) {
            if (student.id == id) {
                return student;
            }
        }
        return null;
    }

    // EFFECTS: displays the information of a selected student to the user: first name, last name, outstanding tuition,
    //          and a menu with options to operate on selected student
    private void displayStudentInfo(Student student) {
        System.out.println("-----------------------------------");
        System.out.println("Currently viewing " + student.firstName + " " + student.lastName + "'s profile:");
        System.out.println(student.firstName + " " + student.lastName);
        System.out.println("Student ID: " + student.id);
        System.out.println("Outstanding Tuition Fees: $" + student.outstandingTransaction);
        System.out.println("Options: ");
    }

    // EFFECTS: displays the menu with options to operate on a selected student.
    private void showIndividualStudentOptions(Student student) {
        System.out.println("\ta -> Enroll student into a course");
        System.out.println("\tc -> See enrolled courses");
        System.out.println("\tp -> Record student tuition payment");
        System.out.println("\th -> View full tuition record");
        System.out.println("\tb -> Back");
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

    // MODIFIES: this
    // EFFECTS: initializes School
    private void init() {
        mySchool = new School("UBC");
    }
}