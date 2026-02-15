package com.ua.rush.modul2;

import java.util.concurrent.ThreadLocalRandom;

public class PlantTask {
    // Утилітний клас для оновлення рослинності на острові
    private PlantTask() {}

    // Виконує цикл росту/самосіву рослин по всіх локаціях
    public static void run(Island island, Settings settings) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int maxPlants = Type.PLANT.getMaxCount();

        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                int current = location.getPlants();

                if (current > 0) {
                    int growth = Math.max(settings.getPlantGrowthMin(), (int) Math.round(current * settings.getPlantGrowthFactor()));
                    location.growPlants(growth, maxPlants);
                } else {
                    if (hasGreenNeighbor(island, x, y, settings) && random.nextInt(100) < settings.getPlantSelfSowChancePercent()) {
                        location.growPlants(random.nextInt(5, 15), maxPlants);
                    }
                }
            }
        }
    }

    // Перевіряє чи є сусідня локація з достатньою кількістю рослин
    private static boolean hasGreenNeighbor(Island island, int x, int y, Settings settings) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < island.getWidth() && ny >= 0 && ny < island.getHeight()) {
                if (island.getLocation(nx, ny).getPlants() > settings.getPlantNeighborGreenThreshold()) return true;
            }
        }
        return false;
    }
}