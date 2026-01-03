package com.ua.rush.modul2.config;

public class Settings {

    private final SimulationMode mode;
    private final int islandWidth;
    private final int islandHeight;
    private final int initialPlantsPerLocation;
    private final int maxTicks;

    private Settings(SimulationMode mode,
                     int islandWidth,
                     int islandHeight,
                     int initialPlantsPerLocation,
                     int maxTicks) {
        this.mode = mode;
        this.islandWidth = islandWidth;
        this.islandHeight = islandHeight;
        this.initialPlantsPerLocation = initialPlantsPerLocation;
        this.maxTicks = maxTicks;
    }

    // --- factory methods ---

    public static Settings defaultSettings() {
        return new Settings(
                SimulationMode.DEFAULT,
                10,
                10,
                0,
                10 // дефолт: 10 тактів
        );
    }

    public static Settings customSettings(int islandWidth,
                                          int islandHeight,
                                          int maxTicks) {
        return new Settings(
                SimulationMode.CUSTOM,
                islandWidth,
                islandHeight,
                0,
                maxTicks
        );
    }

    // --- getters ---

    public int getMaxTicks() {
        return maxTicks;
    }

    public SimulationMode getMode() {
        return mode;
    }

    public int getIslandWidth() {
        return islandWidth;
    }

    public int getIslandHeight() {
        return islandHeight;
    }
}
