package com.utility;

import java.io.PrintWriter;

public class Loger {

    private static PrintWriter print = new PrintWriter(System.out, true);

    public static void printInfo(String text) {
        print.println(text);
    }

}