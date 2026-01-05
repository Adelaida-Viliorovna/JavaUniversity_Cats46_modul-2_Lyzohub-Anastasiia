package com.ua.rush.modul2.simulation;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class StatisticsWriter {

    private static final String FILE_NAME = "simulation_stats.txt";
    private boolean initialized = false;

    public void write(String statistics) {
        try (FileWriter writer = new FileWriter(FILE_NAME, initialized)) {
            writer.write(statistics);
            writer.write("\n------------------------------\n");
            initialized = true;
        } catch (IOException e) {
            System.err.println("Failed to write statistics: " + e.getMessage());
        }
    }
}

