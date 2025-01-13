//Calculate the avarage temperature, and give how many days are above the avarage temperature
//  1. Take an input from the user (eg. 5)
//  2. Prompt the user to enter all the 5 numbers (temperature values)
//  3. Calculate the avarage temperature
//  4. Given the avarage temperature, how many of the numbers in the line 2 are above the avarage temperature?

import java.util.Scanner;

public class Assignment1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfDays = 0;
        boolean validInput = false;

        System.out.println("Please enter the number of days: ");

        while (!validInput) {
            try {
                numberOfDays = scanner.nextInt();
                if (numberOfDays > 0) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a number of valid days");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number");
                scanner.nextLine();
            }
        }

        double[] temperatures = new double[numberOfDays];

        for (int i = 0; i < numberOfDays; i++ ) {
            validInput = false;
            while (!validInput) {
                try {
                    System.out.println("Please enter a temperature for day " + (i + 1) + ": ");
                    temperatures[i] = scanner.nextDouble();
                    validInput = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number");
                    scanner.nextLine();
                }
            }
        }

        double sum = 0;
        for (double temp : temperatures) {
            sum += temp;
        }
        double average = sum / numberOfDays;
        System.out.println("Average temperature is: " + average);

        int daysAboveAverage = 0;
        for (double temp : temperatures) {
            if (temp > average) {
                daysAboveAverage++;
            }
        }
        System.out.println("Number of days above average: " + daysAboveAverage);
    }
}