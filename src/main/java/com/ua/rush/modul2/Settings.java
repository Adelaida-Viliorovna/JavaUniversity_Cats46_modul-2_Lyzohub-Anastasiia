package com.ua.rush.modul2;

import java.util.Scanner;

public class Settings {
    private final int islandWidth;
    private final int islandHeight;
    private final int maxTicks;
    private final int tickDurationMs;

    private Settings(int width, int height, int ticks, int speed) {
        this.islandWidth = width;
        this.islandHeight = height;
        this.maxTicks = ticks;
        this.tickDurationMs = speed;
    }

    public String getFileName() {
        return String.format("stats_%s_%dx%d_%dticks.txt",
                (islandWidth == 10 && islandHeight == 10 && maxTicks == 20) ? "DEFAULT" : "CUSTOM",
                islandWidth, islandHeight, maxTicks);
    }

    public static Settings defaultSettings() {
        return new Settings(10, 10, 20, 500);
    }

    public static Settings customSettings(Scanner scanner) {
        System.out.println("--- Налаштування симуляції ---");
        int w = readInt(scanner, "Ширина острова (5-100): ", 5, 100);
        int h = readInt(scanner, "Висота острова (5-100): ", 5, 100);
        int t = readInt(scanner, "Кількість кроків (1-1000): ", 1, 1000);
        return new Settings(w, h, t, 500);
    }

    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.println("Помилка: число поза діапазоном.");
            } catch (Exception e) {
                System.out.println("Помилка: введіть ціле число.");
            }
        }
    }

    public int getIslandWidth() { return islandWidth; }
    public int getIslandHeight() { return islandHeight; }
    public int getMaxTicks() { return maxTicks; }
    public int getTickDurationMs() { return tickDurationMs; }
}