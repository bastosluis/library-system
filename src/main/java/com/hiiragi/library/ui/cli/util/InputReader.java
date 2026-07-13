package com.hiiragi.library.ui.cli.util;

// Singleton Pattern
import java.util.Scanner;

public enum InputReader {
    INSTANCE;
    private static final Scanner SCANNER = new Scanner(System.in);

    public static String readString(String prompt){

        System.out.print(prompt);

        return SCANNER.nextLine();
    }

    public static int readInt(String prompt){

        System.out.print(prompt);

        return Integer.parseInt(SCANNER.nextLine());
    }
}