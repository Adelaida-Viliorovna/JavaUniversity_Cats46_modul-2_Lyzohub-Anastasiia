package com.ua.rush.modul2.model.location;

import com.ua.rush.modul2.model.animal.Animal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Location {

    private final List<Animal> animals = new ArrayList<>();
    private int plantCount;

    public Location(int initialPlantCount) {
        this.plantCount = initialPlantCount;
    }

    // --- animals ---
    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public List<Animal> getAnimals() {
        // return a snapshot copy to avoid ConcurrentModificationException
        // when other threads modify the underlying list concurrently
        return Collections.unmodifiableList(new ArrayList<>(animals));
    }

    // --- plants ---
    public int getPlantCount() {
        return plantCount;
    }

    public void addPlants(int amount) {
        if (amount > 0) {
            plantCount += amount;
        }
    }

    public void removePlants(int amount) {
        if (amount > 0) {
            plantCount -= amount;
            if (plantCount < 0) {
                plantCount = 0;
            }
        }
    }

}
