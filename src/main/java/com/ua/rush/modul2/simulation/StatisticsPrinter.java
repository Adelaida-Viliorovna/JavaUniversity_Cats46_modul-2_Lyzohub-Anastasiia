package com.ua.rush.modul2.simulation;

import com.ua.rush.modul2.island.Island;
import com.ua.rush.modul2.model.animal.Animal;
import com.ua.rush.modul2.model.animal.Herbivore;
import com.ua.rush.modul2.model.animal.Predator;
import com.ua.rush.modul2.model.location.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsPrinter {

    private int tick = 0;

    public String buildStatistics(Island island) {
        tick++;

        StringBuilder sb = new StringBuilder();

        sb.append("Такт ").append(tick).append(":\n");
        sb.append("\tРозмір острову: ")
                .append(island.getWidth()).append("x")
                .append(island.getHeight()).append("\n");

        // --- locations ---
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                buildLocationStatistics(sb, x, y, island.getLocation(x, y));
            }
        }

        // --- maps ---
        buildPlantMap(sb, island);
        buildAnimalMaps(sb, island);

        return sb.toString();
    }

    // ---------------- helpers ----------------

    private void buildLocationStatistics(StringBuilder sb, int x, int y, Location location) {
        sb.append("\tТочка ").append(x).append("x").append(y).append(":\n");
        sb.append("\t\tРослини: ").append(location.getPlantCount()).append("\n");

        buildAnimalGroups(sb, location.getAnimals());

        sb.append("\t\tЩо відбулося на такті ").append(tick).append(": ...\n");
    }

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

    private void appendGroup(StringBuilder sb, String title, Map<String, Integer> group) {
        sb.append("\t\t\t").append(title).append(": ");
        if (group.isEmpty()) {
            sb.append("немає\n");
            return;
        }
        group.forEach((name, count) ->
                sb.append(name).append(" - ").append(count).append(", "));
        sb.setLength(sb.length() - 2); // прибрати ", "
        sb.append("\n");
    }

    // ---------------- maps ----------------

    private void buildPlantMap(StringBuilder sb, Island island) {
        sb.append("\n\tКарта рослин:\n");
        for (int y = 0; y < island.getHeight(); y++) {
            sb.append("\t");
            for (int x = 0; x < island.getWidth(); x++) {
                sb.append("[")
                        .append(island.getLocation(x, y).getPlantCount())
                        .append("]");
            }
            sb.append("\n");
        }
    }

    private void buildAnimalMaps(StringBuilder sb, Island island) {
        // поки загальна карта тварин (пізніше можна розширити по видах)
        sb.append("\n\tКарта тварин:\n");
        for (int y = 0; y < island.getHeight(); y++) {
            sb.append("\t");
            for (int x = 0; x < island.getWidth(); x++) {
                sb.append("[")
                        .append(island.getLocation(x, y).getAnimals().size())
                        .append("]");
            }
            sb.append("\n");
        }
    }
}
