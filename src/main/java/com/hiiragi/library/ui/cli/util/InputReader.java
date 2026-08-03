package com.hiiragi.library.ui.cli.util;

// Singleton Pattern
import java.time.LocalDate;
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
    
    public static LocalDate readDate(String prompt){

        System.out.println(prompt);
        System.out.println("Please insert in YYYY-MM-DD format.");
        
        return LocalDate.parse(SCANNER.nextLine());
    }
}