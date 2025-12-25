package com.ua.rush.modul2.config;

public class Settings {

    private final SimulationMode mode;
    private final int islandWidth;
    private final int islandHeight;

    private Settings(SimulationMode mode, int islandWidth, int islandHeight) {
        this.mode = mode;
        this.islandWidth = islandWidth;
        this.islandHeight = islandHeight;
    }

    // --- factory methods ---

    public static Settings defaultSettings() {
        return new Settings(SimulationMode.DEFAULT, 10, 10);
    }

    public static Settings customSettings(int islandWidth, int islandHeight) {
        return new Settings(SimulationMode.CUSTOM, islandWidth, islandHeight);
    }

    // --- getters ---

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
