package ui;

import java.util.Scanner;

import model.School;
import model.Student;
import model.Teacher;
import model.Course;
import model.Transaction;

// School Management Application
public class SchoolApp {
    private Scanner input;
    School mySchool;

    // EFFECTS: runs the school application
    public SchoolApp() {
        runSchool();
    }

    // EFFECTS:
    // MODIFIES:
    private void runSchool() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);
        mySchool = new School();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
                System.out.println("Okay, have a good day :)");
            } else {
                handleCommand(command);
            }
        }
    }

    // EFFECTS: displays a menu of the options available to user
    private void displayMenu() {
        System.out.println("Welcome to EduMaster Home!");
        System.out.println("What would you like to do today?");
        System.out.println("\to -> Financial Overview");
        System.out.println("\ts -> Students");
        System.out.println("\tt -> Teachers");
        System.out.println("\tc -> Courses");
        System.out.println("\tx -> Enact New Outstanding Fees");
        System.out.println("\tq -> Quit");

    }

    // MODIFIES: this
    // EFFECTS: takes user input command and calls on the correct method to handle user desire
    private void handleCommand(String command) {
        if (command.equals("o")) {
            financialOverview();
        } else if (command.equals("s")) {
            students();
        } else if (command.equals("t")) {
            teachers();
        } else if (command.equals("c")) {
            courses();
        } else if (command.equals("x")) {
            enactNewOutstandingFees();
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    // SCHOOL FINANCIAL OVERVIEW BRANCH (O) ----------------------------------------

    public void financialOverview() {
        System.out.println("-------- Financial Overview --------");
        System.out.println("Number of Students: " + mySchool.students.size());
        System.out.println("Number of Teachers: " + mySchool.teachers.size());
        System.out.println("Number of Courses Offered: " + mySchool.courses.size());
        System.out.println("Total Annual Student Tuition: " + mySchool.accumulatedAnnualTuition);
        System.out.println("Total Annual Teacher Salary: " + mySchool.accumulatedAnnualSalary);
        System.out.println("------------------------------------");
        System.out.println("Full Transaction Record: ");
        for (String summary : mySchool.transactionRecordSummary) {
            System.out.println(summary);
        }
        System.out.println("_____________________________________");
    }

    // ENACT OUTSTANDING FEES BRANCH (X) -------------------------------------------

    // EFFECTS: enacts the annual outstanding tuition to students for the year based on their courses taken
    //          enacts the annual outstanding salaries to teachers for the year based on their courses taught
    public void enactNewOutstandingFees() {
        mySchool.enactNewOutstandingTransactions();
        System.out.println("New outstanding tuition fees and salaries for the year have been enacted.");
    }

    // TEACHERS BRANCH -------------------------------------------------------------

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
        System.out.println("What would you like to do with the teachers?");
        System.out.println("\tv -> View all teachers");
        System.out.println("\te -> Edit / View a teacher profile");
        System.out.println("\ta -> Add new teachers");
        System.out.println("\tb -> Back to main menu");
    }

    // MODIFIES: this
    // EFFECTS: takes user input from teacher menu and calls on appropriate methods to handle user desire
    private void handleTeacherCommand(String command) {
        if (command.equals("v")) {
            displayAllTeachers();
        } else if (command.equals("a")) {
            addNewTeacher();
        } else if (command.equals("e")) {
            System.out.println("Enter teacher's ID: ");
            String unprocessedInput = input.next();
            try {
                int id = Integer.valueOf(unprocessedInput);
                Teacher thisTeacher = findTeacherByID(id);
                showTeacherPage(thisTeacher);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid 6-digit ID. You can view all teachers to find valid IDs.");
            }
        } else {
            System.out.println("Please enter a valid command.");
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
        for (Course course : teacher.coursesTaught) {
            System.out.println(course.courseName);
        }
    }

    // EFFECTS: takes a teacher and shows user the salary history of the teacher
    private void showSalaryHistory(Teacher teacher) {
        System.out.println(teacher.firstName + "'s payment history:");
        System.out.println("-------------------------");
        for (Transaction payment : teacher.salaryRecord) {
            System.out.println(payment.timestamp + ": $" + payment.amount);
        }
    }

    // MODIFIES: this
    // EFFECTS:
    private void recordTeacherSalaryPayment(Teacher teacher) {
        try {
            System.out.println("How much was the teacher paid?");
            int paymentAmount = Integer.valueOf(input.next());
            if (paymentAmount < 0) {
                System.out.println("Error occurred. Amount must be positive.");
            } else {
                teacher.collectSalary(paymentAmount, mySchool);
                System.out.println("Salary paid successfully.");
                System.out.println("Remaining outstanding salary: $" + teacher.outstandingSalary + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a positive number amount.");
        }
    }

    private void assignTeacherToCourse(Teacher teacher) {
        System.out.println("Assigning " + teacher.firstName + teacher.lastName + " to teach a new course:");
        System.out.println("Course name: ");
        String courseName = input.next();
        for (Course course : mySchool.courses) {
            if (course.courseName.equals(courseName)) {
                teacher.coursesTaught.add(course);
                course.teachers.add(teacher);
                System.out.println("Course assigned to successfully.");
            } else {
                System.out.println("Course not found.");
            }
        }
    }

    private void displayAllTeachers() {
        System.out.println("Here are the teachers in this school:");
        System.out.println("\tTeacher ID, First Name, Last Name");
        System.out.println("\t-----------------------------------");
        for (Teacher teacher : mySchool.teachers) {
            System.out.println("\t" + teacher.teacherID + ", " + teacher.firstName + ", " + teacher.lastName);
        }
    }

    private void addNewTeacher() {
        System.out.println("What is the teacher's first name?");
        String firstName = input.next().toLowerCase();
        System.out.println("What is the teacher's last name?");
        String lastName = input.next().toLowerCase();
        Teacher newTeacher = new Teacher(firstName, lastName);
        mySchool.addTeacher(newTeacher);
        System.out.println("Teacher successfully added.");
    }

    private Teacher findTeacherByID(int id) {
        for (Teacher teacher : mySchool.teachers) {
            if (teacher.teacherID == id) {
                return teacher;
            }
        }
        return null;
    }

    private void displayTeacherInfo(Teacher teacher) {
        System.out.println("Currently viewing " + teacher.firstName + " " + teacher.lastName + "'s profile:");
        System.out.println(teacher.firstName + " " + teacher.lastName);
        System.out.println("Teacher ID: " + teacher.teacherID);
        System.out.println("Outstanding Salary to Pay: $" + teacher.outstandingSalary);
        System.out.println("Options: ");
    }

    private void showIndividualTeacherOptions(Teacher teacher) {
        System.out.println("\ta -> Assign teacher to a course");
        System.out.println("\tc -> See courses taught");
        System.out.println("\tp -> Record payment of salary");
        System.out.println("\th -> View full salary record");
        System.out.println("\tb -> Back");
    }

    // COURSES BRANCH  -------------------------------------------------------------

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

    private void displayCourseMenu() {
        System.out.println("What would you like to do with the courses?");
        System.out.println("\tv -> View all courses");
        System.out.println("\te -> View a course");
        System.out.println("\ta -> Add new courses");
        System.out.println("\tb -> Back to main menu");
    }

    private void handleCourseCommand(String command) {
        if (command.equals("v")) {
            displayAllCourses();
        } else if (command.equals("a")) {
            addNewCourse();
        } else if (command.equals("e")) {
            System.out.println("Enter course's name: ");
            String id = input.next();
            Course thisCourse = findCourseByName(id);
            showCoursePage(thisCourse);
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

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

    private void handleEditCourseCommand(String command, Course course) {
        if (command.equals("s")) {
            displayStudents(course);
        } else if (command.equals("t")) {
            displayTeachers(course);
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

    private void displayAllCourses() {
        System.out.println("Here are the courses offered in this school:");
        System.out.println("\tCourse Name");
        System.out.println("\t-----------");
        for (Course course : mySchool.courses) {
            System.out.println("\t" + course.courseName);
        }
    }

    private void addNewCourse() {
        try {
            System.out.println("What is the name of the course?");
            String courseName = input.next().toLowerCase();
            System.out.println("What is the course's tuition fee?");
            int courseCost = Integer.valueOf(input.next().toLowerCase());
            System.out.println("What is the course's salary pay?");
            int courseSalary = Integer.valueOf(input.next().toLowerCase());
            System.out.println("What is the course's seat limit?");
            int maxStudents = Integer.valueOf(input.next().toLowerCase());
            Course newCourse = new Course(courseName, courseCost, courseSalary, maxStudents);
            mySchool.addCourse(newCourse);
            System.out.println("Course successfully added.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid input.");
        }
    }

    private Course findCourseByName(String id) {
        for (Course course : mySchool.courses) {
            if (course.courseName == id) {
                return course;
            }
        }
        return null;
    }

    private void displayCourseInfo(Course course) {
        System.out.println("Currently viewing " + course.courseName + " profile:");
        System.out.println(course.courseName);
        System.out.println("Options: ");
    }


    private void displayStudents(Course course) {
        System.out.println("Viewing students enrolled in: " + course.courseName);
        System.out.println("------------------------------------");
        for (Student student : course.students) {
            System.out.println("\t" + student.studentID + ", " + student.firstName + ", " + student.lastName);
        }
    }

    private void displayTeachers(Course course) {
        System.out.println("Viewing teachers that teach in: " + course.courseName);
        System.out.println("------------------------------------");
        for (Teacher teacher : course.teachers) {
            System.out.println("\t" + teacher.teacherID + ", " + teacher.firstName + ", " + teacher.lastName);
        }
    }

    private void showIndividualCourseOptions(Course course) {
        System.out.println("\ts -> View students enrolled");
        System.out.println("\tt -> View teachers of course");
        System.out.println("\tb -> Back");
    }


    // STUDENTS BRANCH -------------------------------------------------------------
    private void students() {
        boolean stayOnStudents = true;

        while (stayOnStudents) {
            displayStudentMenu();
            String command = input.next();

            if (command.equals("b")) {
                stayOnStudents = false;
            } else {
                handleStudentCommand(command);
            }
        }
    }

    private void displayStudentMenu() {
        System.out.println("What would you like to do with the students?");
        System.out.println("\tv -> View all students");
        System.out.println("\te -> Edit / View a student profile");
        System.out.println("\ta -> Add new students");
        System.out.println("\tb -> Back to main menu");
    }

    private void handleStudentCommand(String command) {
        if (command.equals("v")) {
            displayAllStudents();
        } else if (command.equals("a")) {
            addNewStudent();
        } else if (command.equals("e")) {
            System.out.println("Enter student's ID: ");
            String unprocessedInput = input.next();
            try {
                int id = Integer.valueOf(unprocessedInput);
                Student thisStudent = findStudentByID(id);
                showStudentPage(thisStudent);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid 6-digit ID. You can view all students to find valid IDs.");
            }
        } else {
            System.out.println("Please enter a valid command.");
        }
    }

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

    private void showEnrolledCourses(Student student) {
        System.out.println("\t" + student.firstName + "'s enrolled courses:");
        System.out.println("\t--------------------------------");
        for (Course course : student.coursesEnrolled) {
            System.out.println("\t" + course.courseName);
        }
    }

    private void showTuitionHistory(Student student) {
        System.out.println(student.firstName + "'s payment history:");
        System.out.println("-------------------------");
        for (Transaction payment : student.tuitionRecord) {
            System.out.println(payment.timestamp + ": $" + payment.amount);
        }
    }

    private void recordStudentTuitionPayment(Student student) {
        try {
            System.out.println("How much did the student pay?");
            int paymentAmount = Integer.valueOf(input.next());
            if (paymentAmount < 0) {
                System.out.println("Error occurred. Amount must be positive.");
            } else {
                student.payTuition(paymentAmount, mySchool);
                System.out.println("Tuition paid successfully.");
                System.out.println("Remaining outstanding tuition: $" + student.outstandingTuition + ".");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a positive number amount.");
        }
    }

    private void enrollStudentInCourse(Student student) {
        System.out.println("Enrolling " + student.firstName + student.lastName + " into a new course:");
        System.out.println("Course name: ");
        String courseName = input.next();
        for (Course course : mySchool.courses) {
            if (course.courseName.equals(courseName)) {
                student.coursesEnrolled.add(course);
                course.students.add(student);
                System.out.println("Course enrolled in successfully.");
            }
        }
    }


    private void displayAllStudents() {
        System.out.println("Here are the students in this school:");
        System.out.println("\tStudent ID, First Name, Last Name");
        System.out.println("\t-----------------------------------");
        for (Student student : mySchool.students) {
            System.out.println("\t" + student.studentID + ", " + student.firstName + ", " + student.lastName);
        }
    }

    private void addNewStudent() {
        System.out.println("What is the student's first name?");
        String firstName = input.next().toLowerCase();
        System.out.println("What is the student's last name?");
        String lastName = input.next().toLowerCase();
        Student newStudent = new Student(firstName, lastName);
        mySchool.addStudent(newStudent);
        System.out.println("Student successfully added.");
    }

    private Student findStudentByID(int id) {
        for (Student student : mySchool.students) {
            if (student.studentID == id) {
                return student;
            }
        }
        return null;
    }

    private void displayStudentInfo(Student student) {
        System.out.println("Currently viewing " + student.firstName + " " + student.lastName + "'s profile:");
        System.out.println(student.firstName + " " + student.lastName);
        System.out.println("Student ID: " + student.studentID);
        System.out.println("Outstanding Tuition Fees: $" + student.outstandingTuition);
        System.out.println("Options: ");
    }

    private void showIndividualStudentOptions(Student student) {
        System.out.println("\ta -> Enroll student into a course");
        System.out.println("\tc -> See enrolled courses");
        System.out.println("\tp -> Record student tuition payment");
        System.out.println("\th -> View full tuition record");
        System.out.println("\tb -> Back");
    }
}