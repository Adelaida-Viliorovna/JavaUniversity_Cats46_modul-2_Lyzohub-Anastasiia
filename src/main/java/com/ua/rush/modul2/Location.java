package com.ua.rush.modul2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {
    // Мапа тварин на локації за типом
    private final Map<Type, List<Animal>> animals = new ConcurrentHashMap<>();
    // Об'єкт рослинності на локації
    private final Plant plant;

    // Створює локацію з початковою кількістю рослин
    public Location(int initialPlants) {
        this.plant = new Plant(initialPlants);
    }

    // Додає тварину до локації
    public void addAnimal(Animal animal) {
        animals.computeIfAbsent(animal.getType(), t -> new CopyOnWriteArrayList<>()).add(animal);
    }

    // Видаляє тварину з локації
    public void removeAnimal(Animal animal) {
        List<Animal> list = animals.get(animal.getType());
        if (list != null) {
            list.remove(animal);
        }
    }

    // Повертає кількість тварин заданого типу на локації
    public int getAnimalCount(Type type) {
        List<Animal> list = animals.get(type);
        return list == null ? 0 : list.size();
    }

    // Повертає мапу тварин на локації
    public Map<Type, List<Animal>> getAnimals() {
        return animals;
    }

    // Повертає кількість рослин на локації
    public int getPlants() {
        return plant.getAmount();
    }

    // Спроба з'їсти одну рослину, повертає true якщо вдалося
    public boolean consumePlant() {
        return plant.consume();
    }

    // Ріст рослин на локації (обмежений максимумом)
    public void growPlants(int amount, int max) {
        plant.grow(amount, max);
    }
}