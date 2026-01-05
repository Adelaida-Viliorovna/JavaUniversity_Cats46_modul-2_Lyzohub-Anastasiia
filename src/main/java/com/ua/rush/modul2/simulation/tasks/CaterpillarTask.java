package com.ua.rush.modul2.simulation.tasks;

import com.ua.rush.modul2.island.Island;
import com.ua.rush.modul2.model.animal.Animal;
import com.ua.rush.modul2.model.animal.Caterpillar;
import com.ua.rush.modul2.model.location.Location;

import java.util.ArrayList;
import java.util.List;

public class CaterpillarTask implements Runnable {

    private final Island island;

    public CaterpillarTask(Island island) {
        this.island = island;
    }

    @Override
    public void run() {
        for (Location location : island.getAllLocations()) {

            List<Caterpillar> caterpillars = location.getAnimals().stream()
                    .filter(a -> a instanceof Caterpillar)
                    .map(a -> (Caterpillar) a)
                    .toList();

            // життєвий цикл
            for (Caterpillar caterpillar : new ArrayList<>(caterpillars)) {
                if (caterpillar.isAlive()) {
                    caterpillar.liveTick(location);
                }
            }

            // розмноження
            int count = caterpillars.size();
            if (count >= 2) {
                int newborns = count / 2;
                for (int i = 0; i < newborns && count + i < Caterpillar.MAX_ON_LOCATION; i++) {
                    location.addAnimal(new Caterpillar());
                }
            }
        }

    }
}

