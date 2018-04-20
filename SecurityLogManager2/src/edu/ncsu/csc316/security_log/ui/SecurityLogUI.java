package edu.ncsu.csc316.security_log.ui;

import java.util.Scanner;

import edu.ncsu.csc316.security_log.manager.SecurityLogManager;

/**
 * Class that handles interaction with user. Current implementation has no error handling.
 * 
 * @author Noah Benveniste
 */
public class SecurityLogUI {

    /**
     * Creates SecurityLogManager object using a user-specified file name. User can then
     * choose to generate an operational profile over a time interval, generate a user
     * activity report for a given user, or quit the program.
     * @param args NONE
     */
    public static void main( String[] args ) {
        System.out.print("Enter file name: ");
        Scanner s = new Scanner(System.in);
        SecurityLogManager manager = new SecurityLogManager(s.next());
        boolean runAgain = true;
        while (runAgain) {
            System.out.print("Press <o> for operational profile, <u> for user report, or <q> to quit: ");
            String ans = s.next();
            if (ans.equals("q")) {
                runAgain = false;
            } else if (ans.equals("o")) {
                System.out.print("\nEnter start date/time (e.g. 01/01/2000 12:00:00AM): ");
                String startDate = s.next();
                String startTime = s.next();
                System.out.print("\nEnter end date/time (e.g. 01/01/2000 12:00:00AM): ");
                String endDate = s.next();
                String endTime = s.next();
                System.out.println(manager.generateOperationalProfile(startDate + " " + startTime, endDate + " " + endTime));
            } else if (ans.equals("u")) {
                System.out.print("\nEnter user name: ");
                String userName = s.next();
                System.out.println(manager.getUserReport(userName));
            }
        }
        s.close();
    }
    
}