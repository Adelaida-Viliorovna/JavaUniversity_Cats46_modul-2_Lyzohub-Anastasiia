package com.ua.rush.modul2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {
    private final Map<Type, List<Animal>> animals = new ConcurrentHashMap<>();
    private final Plant plant;
    private final Object lock = new Object();

    public Location(int initialPlants) {
        this.plant = new Plant(initialPlants);
    }

    public void addAnimal(Animal animal) {
        animals.computeIfAbsent(animal.getType(), t -> new CopyOnWriteArrayList<>()).add(animal);
    }

    public void removeAnimal(Animal animal) {
        List<Animal> list = animals.get(animal.getType());
        if (list != null) {
            list.remove(animal);
        }
    }

    public int getAnimalCount(Type type) {
        List<Animal> list = animals.get(type);
        return list == null ? 0 : list.size();
    }

    public Map<Type, List<Animal>> getAnimals() {
        return animals;
    }

    public int getPlants() {
        return plant.getAmount();
    }

    public boolean consumePlant() {
        return plant.consume();
    }

    public void growPlants(int amount, int max) {
        plant.grow(amount, max);
    }
}