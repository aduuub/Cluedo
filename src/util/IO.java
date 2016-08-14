package util;

import java.util.Scanner;

/**
 * Created by Adam on 31/07/16.
 * Handles getting input and output from the user from the command line.
 */
public class IO {

    /**
     * Get a String in from console
     * @param msg The message to prompt the user
     * @return
     */
    public static String getString(String msg) {
        while (true) {
            System.out.print(msg);
            Scanner s = new Scanner(System.in);
            String input = s.nextLine();
            if (input != null)
                return input.trim();
            System.out.println("Invalid Input. Please try again");
        }
    }

    /**
     * Gets an int in from console
     * @param msg The message to prompt the user
     * @return
     */
    public static int getInt(String msg) {
        while (true) {
            System.out.print(msg);
            Scanner s = new Scanner(System.in);
            try {
                int input = s.nextInt();
                return input;
            } catch (Exception e) {
                System.out.println("Invalid Input. Please enter a number");
            }
        }
    }

    /**
     * Gets an integer from console between the lower and upper bounds (inclusive).
     * e.g. if lower = 1 and upper = 5, acceptable values are 1,2,3,4,5.
     * @param msg
     * @param lower the lower bound (inclusive)
     * @param upper upper bound (inclusive)
     * @return
     */
    public static int getIntBetweenBounds(String msg, int lower, int upper) {
        while (true) {
            int option = getInt(msg);
            if (option > upper || option < lower) {
                System.out.println("Please enter a number between " + lower  + " and " + upper);
            }else{
                return option;
            }
        }
    }
}
