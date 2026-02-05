package com.ua.rush.modul2;

import java.util.Scanner;

public class Runner {
    public static final String EXIT_TEXT = "Exiting...";
    private boolean running = true;
    private final Scanner scanner = new Scanner(System.in);


    public void run() {
        while (running) {
            mainMenu();
        }
    }

    private void mainMenu() {
        String menu = """
                Choose an option:
                1. Default simulation
                2. Custom simulation
                0. Exit
                """;

        int choice = readMenuChoice(menu);

        switch (choice) {
            case 1 -> {
                System.out.println("Running default simulation...");
                Settings settings = Settings.defaultSettings();
                Simulation simulation = new Simulation(settings);
                simulation.start();
                running = false;

            }
            case 2 -> {
                System.out.println("Running custom simulation...");
                Settings settings = Settings.customSettings(scanner);
                Simulation simulation = new Simulation(settings);
                simulation.start();
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

    private int readMenuChoice(String menu) {
        while (true) {
            System.out.println(menu);
            String line;
            try {
                line = scanner.nextLine();
            } catch (Exception e) {
                System.out.println("No input available. Exiting.");
                return 0;
            }
            if (line == null) {
                System.out.println("No input provided, please try again.");
                continue;
            }
            line = line.trim();
            if (line.isEmpty()) {
                System.out.println("Please enter 0, 1 or 2.");
                continue;
            }
            int parsed;
            try {
                parsed = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, please enter 0, 1 or 2.");
                continue;
            }
            if (parsed < 0 || parsed > 2) {
                System.out.println("Choice must be 0, 1 or 2.");
                continue;
            }
            return parsed;
        }
    }
}
