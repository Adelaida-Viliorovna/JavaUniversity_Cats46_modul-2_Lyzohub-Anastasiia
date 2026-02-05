package com.ua.rush.modul2;

import java.util.concurrent.ThreadLocalRandom;

public class PlantTask {
    public static void run(Island island) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int maxPlants = Type.PLANT.getMaxCount();

        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                int current = location.getPlants();

                if (current > 0) {
                    // Традиційний ріст: +25% від поточної кількості
//                    int growth = Math.max(10, current / 4);
                    int growth = Math.max(10, current / 2); // Збільшено темп росту до 50%
                    location.growPlants(growth, maxPlants);
                } else {
                    // САМОПОСІВ: якщо на сусідній клітинці багато трави,
                    // вітер може занести насіння (шанс 15%)
                    if (hasGreenNeighbor(island, x, y) && random.nextInt(100) < 15) {
                        location.growPlants(random.nextInt(5, 15), maxPlants);
                    }
                }
            }
        }
    }

    private static boolean hasGreenNeighbor(Island island, int x, int y) {
        // Перевірка сусідніх клітинок
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            if (nx >= 0 && nx < island.getWidth() && ny >= 0 && ny < island.getHeight()) {
                if (island.getLocation(nx, ny).getPlants() > 100) return true;
            }
        }
        return false;
    }
}