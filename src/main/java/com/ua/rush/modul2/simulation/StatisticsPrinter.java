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

    public void print(Island island) {
        tick++;

        System.out.println("Такт " + tick + ":");
        System.out.println("\tРозмір острову: " +
                island.getWidth() + "x" + island.getHeight());

        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                printLocation(x, y, island.getLocation(x, y));
            }
        }

        System.out.println();
    }

    private void printLocation(int x, int y, Location location) {
        System.out.println("\tТочка " + x + "x" + y + ":");
        System.out.println("\t\tРослини: " + location.getPlantCount());
        printAnimals(location.getAnimals());
        System.out.println("\t\tЩо відбулося на такті " + tick + ": ...");
    }

    private void printAnimals(List<Animal> animals) {
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

        System.out.println("\t\tТварини:");

        printGroup("Травоїдні", herbivores);
        printGroup("Хижаки", predators);
    }

    private void printGroup(String title, Map<String, Integer> animals) {
        if (animals.isEmpty()) {
            System.out.println("\t\t\t" + title + ": немає");
            return;
        }

        System.out.print("\t\t\t" + title + ": ");
        animals.forEach((name, count) ->
                System.out.print(name + " - " + count + " "));
        System.out.println();
    }
}
