package com.ua.rush.modul2.simulation;

import com.ua.rush.modul2.island.Island;
import com.ua.rush.modul2.model.animal.Animal;
import com.ua.rush.modul2.model.animal.Herbivore;
import com.ua.rush.modul2.model.animal.Predator;
import com.ua.rush.modul2.model.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Відповідає ТІЛЬКИ за форматування та вивід статистики
 */
public class StatisticsPrinter {

    public String buildStatistics(Island island, int tick) {
        StringBuilder sb = new StringBuilder();

        // --- header ---
        sb.append("Такт ").append(tick).append(":\n");
        sb.append("\tРозмір острову: ")
                .append(island.getWidth()).append("x")
                .append(island.getHeight()).append("\n");

        // --- take a snapshot of the world to avoid inconsistencies caused by concurrent tasks ---
        int width = island.getWidth();
        int height = island.getHeight();

        // animalsGrid[x][y] -> list of animals snapshot for location (x,y)
        List<List<List<Animal>>> animalsGrid = new ArrayList<>(width);
        int[][] plantsGrid = new int[width][height];

        for (int x = 0; x < width; x++) {
            List<List<Animal>> column = new ArrayList<>(height);
            for (int y = 0; y < height; y++) {
                Location loc = island.getLocation(x, y);
                column.add(new ArrayList<>(loc.getAnimals())); // snapshot of animals
                plantsGrid[x][y] = loc.getPlantCount(); // snapshot of plants
            }
            animalsGrid.add(column);
        }

        // --- locations ---
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // use snapshot data instead of reading location again
                List<Animal> animalsSnapshot = animalsGrid.get(x).get(y);
                int plantCountSnapshot = plantsGrid[x][y];

                // поки події порожні
                TickStatistics stats = new TickStatistics();

                buildLocationBlock(sb, x, y, animalsSnapshot, plantCountSnapshot, stats, tick);
            }
        }

        // --- maps ---
        buildPlantMap(sb, plantsGrid, width, height);
        buildAnimalMapByType(sb, animalsGrid, width, height,
                com.ua.rush.modul2.model.animal.Caterpillar.class,
                "Карта гусені");

        return sb.toString();
    }

    // ========================================================================
    // LOCATION BLOCK
    // ========================================================================

    private void buildLocationBlock(StringBuilder sb,
                                    int x,
                                    int y,
                                    List<Animal> animalsSnapshot,
                                    int plantCountSnapshot,
                                    TickStatistics stats,
                                    int tick) {

        sb.append("\tТочка ").append(x).append("x").append(y).append(":\n");

        // --- plants ---
        sb.append("\t\tРослини: ")
                .append(plantCountSnapshot)
                .append("\n");

        // --- animals ---
        buildAnimalGroups(sb, animalsSnapshot);

        // --- events ---
        buildEventsBlock(sb, stats, tick);
    }

    // ========================================================================
    // ANIMALS
    // ========================================================================

    private void buildAnimalGroups(StringBuilder sb, List<Animal> animals) {
        Map<String, Integer> herbivores = new HashMap<>();
        Map<String, Integer> predators = new HashMap<>();

        for (Animal animal : animals) {
            String name = animal.getClass().getSimpleName();

            if (animal instanceof Herbivore) {
                herbivores.merge(name, 1, Integer::sum);
            } else if (animal instanceof Predator) {
                predators.merge(name, 1, Integer::sum);
            }
        }

        sb.append("\t\tТварини:\n");
        appendGroup(sb, "Травоядні", herbivores);
        appendGroup(sb, "Хижаки", predators);
    }

    private void appendGroup(StringBuilder sb,
                             String title,
                             Map<String, Integer> group) {

        sb.append("\t\t\t").append(title).append(": ");

        if (group.isEmpty()) {
            sb.append("немає\n");
            return;
        }

        group.forEach((name, count) ->
                sb.append(name).append(" - ").append(count).append(", ")
        );

        sb.setLength(sb.length() - 2); // remove ", "
        sb.append("\n");
    }

    // ========================================================================
    // EVENTS
    // ========================================================================

    private void buildEventsBlock(StringBuilder sb,
                                  TickStatistics stats,
                                  int tick) {

        sb.append("\t\tЩо відбулося на такті ").append(tick).append(":\n");

        sb.append("\t\t\tВиросло нових рослин: ")
                .append(stats.plantsGrown)
                .append("\n");

        sb.append("\t\t\tЗ'їдено рослин: ")
                .append(stats.plantsEaten)
                .append("\n");

        sb.append("\t\t\tНових тварин: ")
                .append(stats.animalsBorn)
                .append("\n");

        appendDetails(sb, "\t\t\t\tз них: ", stats.bornByType);

        sb.append("\t\t\tПомерло тварин: ")
                .append(stats.animalsDied)
                .append("\n");

        appendDetails(sb, "\t\t\t\tз них: ", stats.diedByType);
    }

    private void appendDetails(StringBuilder sb,
                               String prefix,
                               Map<String, Integer> details) {

        if (details.isEmpty()) {
            sb.append(prefix).append("немає\n");
            return;
        }

        sb.append(prefix);
        details.forEach((name, count) ->
                sb.append(name).append(" - ").append(count).append(", ")
        );

        sb.setLength(sb.length() - 2);
        sb.append("\n");
    }

    // ========================================================================
    // MAPS
    // ========================================================================

    private void buildPlantMap(StringBuilder sb, int[][] plantsGrid, int width, int height) {
        sb.append("\n\tКарта рослин:\n");

        for (int y = 0; y < height; y++) {
            sb.append("\t");
            for (int x = 0; x < width; x++) {
                sb.append("[")
                        .append(plantsGrid[x][y])
                        .append("]");
            }
            sb.append("\n");
        }
    }

    private void buildAnimalMapByType(StringBuilder sb,
                                      List<List<List<Animal>>> animalsGrid,
                                      int width,
                                      int height,
                                      Class<? extends Animal> animalClass,
                                      String title) {

        sb.append("\n\t").append(title).append(":\n");

        for (int y = 0; y < height; y++) {
            sb.append("\t");
            for (int x = 0; x < width; x++) {

                long count = animalsGrid.get(x).get(y).stream()
                        .filter(animalClass::isInstance)
                        .count();

                sb.append("[").append(count).append("]");
            }
            sb.append("\n");
        }
    }
}
