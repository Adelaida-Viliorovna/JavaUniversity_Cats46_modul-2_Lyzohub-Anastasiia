package com.ua.rush.modul2.config;

public class Settings {

    private final SimulationMode mode;
    private final int islandWidth;
    private final int islandHeight;
    private final int initialPlantsPerLocation;
    private final int maxTicks;
    private final int initialCaterpillars;

    private Settings(SimulationMode mode,
                     int islandWidth,
                     int islandHeight,
                     int initialPlantsPerLocation,
                     int maxTicks,
                     int initialCaterpillars) {
        this.mode = mode;
        this.islandWidth = islandWidth;
        this.islandHeight = islandHeight;
        this.initialPlantsPerLocation = initialPlantsPerLocation;
        this.maxTicks = maxTicks;
        this.initialCaterpillars = initialCaterpillars;
    }

    // --- factory methods ---

    public static Settings defaultSettings() {
        return new Settings(
                SimulationMode.DEFAULT,
                10,
                10,
                0, // дефолт: 0 рослин у клітинці
                10, // дефолт: 10 тактів
                100   // дефолт: 50 гусениць
        );
    }

    public static Settings customSettings(int islandWidth,
                                          int islandHeight,
                                          int maxTicks,
                                          int initialPlantsPerLocation,
                                          int initialCaterpillars) {
        return new Settings(
                SimulationMode.CUSTOM,
                islandWidth,
                islandHeight,
                initialPlantsPerLocation,
                maxTicks,
                initialCaterpillars
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

    public int getInitialPlantsPerLocation() {
        return initialPlantsPerLocation;
    }

    public int getInitialCaterpillars() {
        return initialCaterpillars;
    }


}
