package com.utility;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScannerUtility {

    private Scanner scanner;

    public ScannerUtility() {
        this.scanner = new Scanner(System.in);
    }

    public String scanString() {
        return scanner.next();
    }

    public int scanInteger() {
        try {
            int result = scanner.nextInt();
            if (result <= 0) {
                throw new Exception();
            }
            return result;
        } catch (InputMismatchException e) {
            System.out.println("Incorrect type , please enter number.");
            //cleaning scanner
            scanner.next();
            return scanInteger();
        } catch (Exception e) {
            System.out.println("Incorrect value, please enter positive number.");
            return scanInteger();
        }
    }


    public double scanDouble() {
        try {
            double result = scanner.nextDouble();
            if (result <= 0) {
                throw new Exception();
            }
            return result;
        } catch (InputMismatchException e) {
            System.out.println("Incorrect type , please enter number.");
            //cleaning scanner
            scanner.next();
            return scanDouble();
        } catch (Exception e) {
            System.out.println("Incorrect value, please enter positive number.");
            return scanDouble();
        }
    }
}
