package com.ua.rush.modul2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Simulation {
    private final Settings settings;
    private Island island;
    private final ExecutorService executorService;

    public Simulation(Settings settings) {
        this.settings = settings;
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void start() {
        initIsland();

        String fileName = settings.getFileName();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

            StatisticsPrinter.print(island, 0, writer);

            for (int tick = 1; tick <= settings.getMaxTicks(); tick++) {
                island.resetAnimalsFlags();
                runAnimalTasks();
                runReproductionTasks();
                runPlantTasks();

                StatisticsPrinter.print(island, tick, writer);

                sleepTick();
            }

        } catch (IOException e) {
            System.err.println("Не вдалося створити файл звіту: " + e.getMessage());
        }

        executorService.shutdown();
    }

    private void initIsland() {
        island = new Island(settings.getIslandWidth(), settings.getIslandHeight(), 500);
        initAnimals();
    }

    private void initAnimals() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (Type type : Type.values()) {
            if (type == Type.PLANT) continue;

            int minInit = 20;
            int maxInit = 50;

            if (type == Type.CATERPILLAR) {
                minInit = 200; // Гусені треба БАГАТО
                maxInit = 500;
            } else if (type == Type.MOUSE || type == Type.RABBIT || type == Type.DUCK) {
                minInit = 50; // Мишей та кролів теж більше
                maxInit = 200;
            }

            int totalToCreate = random.nextInt(minInit, maxInit + 1);
            int created = 0;
            while (created < totalToCreate) {
                int x = random.nextInt(island.getWidth());
                int y = random.nextInt(island.getHeight());
                Location loc = island.getLocation(x, y);

                if (loc.getAnimalCount(type) < type.getMaxCount()) {
                    loc.addAnimal(createAnimal(type));
                    created++;
                }
            }
        }
    }

    private void runReproductionTasks() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);

                location.getAnimals().forEach((type, animals) -> {
                    int n = animals.size();
                    // Якщо клітинка вже заповнена більше ніж на 80%, ніхто не народжується
                    if (n > type.getMaxCount() * 0.8) {
                        return;
                    }
                    if (n >= 2) {
                        int pairs = n / 2;
                        int newBornsTotal = 0;

                        // Визначаємо максимальний приплід для виду
                        int maxLitter = getMaxLitterSize(type);

                        for (int i = 0; i < pairs; i++) {
                            // 50% шанс, що пара дасть потомство
                            if (random.nextBoolean()) {
                                // Кожна пара дає від 1 до maxLitter дітей
                                newBornsTotal += random.nextInt(1, maxLitter + 1);
                            }
                        }

                        int spaceLeft = type.getMaxCount() - n;
                        int actuallyToAdd = Math.min(newBornsTotal, spaceLeft);

                        for (int i = 0; i < actuallyToAdd; i++) {
                            location.addAnimal(createAnimal(type));
                        }
                    }
                });
            }
        }
    }

    // Допоміжний метод для визначення плодючості
    private int getMaxLitterSize(Type type) {
        return switch (type) {
            case CATERPILLAR -> 10;
            case MOUSE -> 4;
            case RABBIT -> 3;
            case DUCK -> 3;
            case GOAT, SHEEP, DEER -> 2;
            default -> 1; // Хижаки та великі звірі - по 1
        };
    }

    private void runAnimalTasks() {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                final int lx = x, ly = y;
                location.getAnimals().forEach((type, animals) -> {
                    new ArrayList<>(animals).forEach(animal -> {
                        tasks.add(() -> {
                            new AnimalTask(island, location, lx, ly, animal).run();
                            return null;
                        });
                    });
                });
            }
        }
        try { executorService.invokeAll(tasks); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    private Animal createAnimal(Type type) {
        try {
            String name = type.name().toLowerCase();
            String className = "com.ua.rush.modul2.animals."
                    + name.substring(0, 1).toUpperCase() + name.substring(1);
            return (Animal) Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) { return null; }
    }

    private void runPlantTasks() { PlantTask.run(island); }
    private void sleepTick() { try { Thread.sleep(settings.getTickDurationMs()); } catch (InterruptedException ignored) {} }
}