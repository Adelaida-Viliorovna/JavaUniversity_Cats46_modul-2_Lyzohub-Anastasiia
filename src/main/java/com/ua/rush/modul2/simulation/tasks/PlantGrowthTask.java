package com.ua.rush.modul2.simulation.tasks;

import com.ua.rush.modul2.island.Island;
import com.ua.rush.modul2.model.location.Location;
import com.ua.rush.modul2.model.plant.Plant;

import java.util.concurrent.ThreadLocalRandom;

public class PlantGrowthTask implements Runnable {

    private final Island island;

    public PlantGrowthTask(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                growPlantsOnLocation(island.getLocation(x, y));
            }
        }
    }

    private void growPlantsOnLocation(Location location) {
        int current = location.getPlantCount();
        if (current >= Plant.MAX_ON_LOCATION) {
            return;
        }

        int growth = ThreadLocalRandom.current().nextInt(0, 4); // 0..3
        int allowedGrowth = Math.min(growth, Plant.MAX_ON_LOCATION - current);

        location.addPlants(allowedGrowth);
    }
}
