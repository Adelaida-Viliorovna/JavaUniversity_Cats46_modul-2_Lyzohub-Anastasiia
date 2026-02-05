package com.ua.rush.modul2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class StatisticsPrinter {

    public static void print(Island island, int tick, BufferedWriter writer) {
        Map<Type, Integer> stats = new EnumMap<>(Type.class);
        int totalPlants = 0;

        for (Location[] row : island.getLocations()) {
            for (Location location : row) {
                totalPlants += location.getPlants();
                location.getAnimals().forEach(
                        (type, list) -> stats.merge(type, list.size(), Integer::sum)
                );
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== STATISTICS [TICK ").append(tick).append("] ===\n");
        sb.append("Plants 🌿: ").append(totalPlants).append("\n");

        stats.forEach((k, v) ->
                sb.append(k.getEmoji()).append(" ").append(k).append(": ").append(v).append("\n")
        );
        sb.append("==============================\n");

        String output = sb.toString();

        // 1. Консоль
        System.out.print(output);

        // 2. Файл (якщо writer не null)
        if (writer != null) {
            try {
                writer.write(output);
                writer.flush();
            } catch (IOException e) {
                System.err.println("Помилка запису: " + e.getMessage());
            }
        }
    }
}