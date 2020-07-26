package ui;

import java.util.Scanner;

// School Management Application
public class SchoolApp {
    private Scanner input;

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

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

        }
    }

    // displayMenu()
    // processCommand()



}
