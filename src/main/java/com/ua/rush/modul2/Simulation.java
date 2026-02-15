package com.ua.rush.modul2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Simulation {
    private static final Logger LOGGER = Logger.getLogger(Simulation.class.getName());

    // Налаштування симуляції
    private final Settings settings;

    // Острів, на якому виконується симуляція
    private Island island;

    // Пул потоків для задач тварин
    private final ExecutorService executorService;

    public Simulation(Settings settings) {
        this.settings = settings;
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    // Запускає симуляцію: ініціалізація, планування тиков і запис статистики
    public void start() {
        initIsland();
        String fileName = settings.getFileName();

        ScheduledExecutorService mainScheduledPool = Executors.newScheduledThreadPool(1);

        AtomicInteger currentTick = new AtomicInteger(1);

        final AtomicReference<java.io.BufferedWriter> writerRef = new AtomicReference<>();

        // Add a shutdown hook to try to close resources if the JVM exits unexpectedly
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (!mainScheduledPool.isShutdown()) {
                    mainScheduledPool.shutdownNow();
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Помилка під час завершення планувальника у shutdown hook", e);
            }
            try {
                if (!executorService.isShutdown()) {
                    executorService.shutdownNow();
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Помилка під час завершення пулу потоків у shutdown hook", e);
            }
            java.io.BufferedWriter w = writerRef.getAndSet(null);
            if (w != null) {
                try { w.close(); } catch (java.io.IOException e) { LOGGER.log(Level.SEVERE, "Помилка закриття файлу у shutdown hook", e); }
            }
        }));

        try {
            java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(fileName));
            writerRef.set(writer);
            StatisticsPrinter.print(island, 0, writer);

            Runnable tickTask = () -> {
                int tick = currentTick.getAndIncrement();

                if (tick > settings.getMaxTicks()) {
                    try {
                        java.io.BufferedWriter w = writerRef.getAndSet(null);
                        if (w != null) {
                            try { w.close(); } catch (java.io.IOException e) { LOGGER.log(Level.SEVERE, "Помилка закриття файлу", e); }
                        }
                    } finally {
                        mainScheduledPool.shutdown();
                        executorService.shutdown();
                    }

                    LOGGER.info("Симуляцію завершено!");
                    return;
                }

                island.resetAnimalsFlags();
                runAnimalTasks();
                runReproductionTasks();
                runPlantTasks();

                StatisticsPrinter.print(island, tick, writerRef.get());
            };

            mainScheduledPool.scheduleAtFixedRate(
                    tickTask,
                    0,
                    settings.getTickDurationMs(),
                    TimeUnit.MILLISECONDS
            );

        } catch (java.io.IOException e) {
            LOGGER.log(Level.SEVERE, "Не вдалося створити файл звіту", e);
            try {
                mainScheduledPool.shutdown();
            } catch (Exception e2) {
                LOGGER.log(Level.WARNING, "Помилка при вимкненні планувальника після помилки створення файлу", e2);
            }
            try {
                executorService.shutdown();
            } catch (Exception e2) {
                LOGGER.log(Level.WARNING, "Помилка при вимкненні пулу потоків після помилки створення файлу", e2);
            }
            java.io.BufferedWriter w = writerRef.getAndSet(null);
            if (w != null) {
                try { w.close(); } catch (java.io.IOException ex) { LOGGER.log(Level.SEVERE, "Помилка закриття файлу після помилки створення", ex); }
            }
        }
    }

    // Ініціалізує острів та стартові тварини
    private void initIsland() {
        island = new Island(settings.getIslandWidth(), settings.getIslandHeight(), settings.getInitialPlants());
        initAnimals();
    }

    // Розміщує початкові популяції тварин по острову
    private void initAnimals() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (Type type : Type.values()) {
            if (type == Type.PLANT) continue;

            int minInit = 20;
            int maxInit = 50;

            if (type == Type.CATERPILLAR) {
                minInit = 200;
                maxInit = 500;
            } else if (type == Type.MOUSE || type == Type.RABBIT || type == Type.DUCK) {
                minInit = 50;
                maxInit = 200;
            }

            int totalToCreate = random.nextInt(minInit, maxInit + 1);
            int created = 0;
            while (created < totalToCreate) {
                int x = random.nextInt(island.getWidth());
                int y = random.nextInt(island.getHeight());
                Location loc = island.getLocation(x, y);

                if (loc.getAnimalCount(type) < type.getMaxCount()) {
                    Animal a = AnimalFactory.create(type);
                    if (a != null) {
                        loc.addAnimal(a);
                        created++;
                    }
                }
            }
        }
    }

    // Викликає логіку розмноження для кожної групи тварин у локації
    private void runReproductionTasks() {
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);

                location.getAnimals().forEach((type, animals) -> {
                    if (animals.isEmpty()) return;
                    Animal representative = animals.stream().findFirst().orElse(null);
                    if (representative != null) {
                        representative.reproduce(location, settings);
                    }
                });
            }
        }
    }

    // Формує та виконує завдання для кожної тварини через пул потоків
    private void runAnimalTasks() {
        List<Callable<Void>> tasks = new ArrayList<>();
        for (int y = 0; y < island.getHeight(); y++) {
            for (int x = 0; x < island.getWidth(); x++) {
                Location location = island.getLocation(x, y);
                final int lx = x;
                final int ly = y;
                location.getAnimals().forEach((type, animals) ->
                        new ArrayList<>(animals).forEach(animal -> tasks.add(() -> {
                            new AnimalTask(island, location, lx, ly, animal, settings).run();
                            return null;
                        }))
                );
            }
        }
        try { executorService.invokeAll(tasks); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    // Запускає оновлення рослин на острові
    private void runPlantTasks() { PlantTask.run(island, settings); }
}