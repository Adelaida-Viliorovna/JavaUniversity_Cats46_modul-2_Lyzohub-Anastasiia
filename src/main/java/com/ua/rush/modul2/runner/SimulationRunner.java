package com.ua.rush.modul2.runner;

import java.util.Scanner;

public class SimulationRunner {
    public static final String EXIT_TEXT = "Exiting...";
    private boolean running = true;
    private final Scanner scanner = new Scanner(System.in);


    public void run() {
        while (running) {
            mainMenu();
        }
    }

    private void mainMenu() {
        System.out.println("""
                Choose an option:
                1. Default simulation
                2. Custom simulation
                0. Exit
                """);

        String line = scanner.nextLine().trim();
        int choice;
        try {
            choice = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice.");
            return;
        }

        switch (choice) {
            case 1 -> {
                System.out.println("Running default simulation...");
                running = false;

            }
            case 2 -> {
                System.out.println("Running custom simulation...");
                running = false;

            }
            case 0 -> {
                System.out.println(EXIT_TEXT);
                scanner.close();
                running = false;
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}
