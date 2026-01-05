package com.ua.rush.modul2.simulation;

import com.ua.rush.modul2.config.Settings;
import com.ua.rush.modul2.island.Island;
import com.ua.rush.modul2.model.animal.Caterpillar;
import com.ua.rush.modul2.simulation.tasks.CaterpillarTask;
import com.ua.rush.modul2.simulation.tasks.PlantGrowthTask;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private final Settings settings;
    private Island island;
    private ScheduledExecutorService executor;
    private final StatisticsPrinter printer = new StatisticsPrinter();
    private final StatisticsWriter writer = new StatisticsWriter();

    private int currentTick = 0;

    public Simulation(Settings settings) {
        this.settings = settings;
    }

    public void start() {
        System.out.println("Simulation started");
        initWorld();
        initCaterpillars();
        startSimulationLoop();
    }

    private void initWorld() {
        island = new Island(
                settings.getIslandWidth(),
                settings.getIslandHeight(),
                settings.getInitialPlantsPerLocation()
//                settings.getInitialCaterpillars()
        );
    }

    private void initCaterpillars() {
        Random random = new Random();

        for (int i = 0; i < settings.getInitialCaterpillars(); i++) {
            int x = random.nextInt(island.getWidth());
            int y = random.nextInt(island.getHeight());
            island.getLocation(x, y).addAnimal(new Caterpillar());
        }
    }

    private void startSimulationLoop() {
        // Need at least 3 threads: plant growth, caterpillar task, and simulation tick
        executor = Executors.newScheduledThreadPool(3);

        executor.scheduleAtFixedRate(
                new PlantGrowthTask(island),
                0,
                1,
                TimeUnit.SECONDS
        );

        executor.scheduleAtFixedRate(
                new CaterpillarTask(island),
                0,
                1,
                TimeUnit.SECONDS
        );

        executor.scheduleAtFixedRate(
                this::simulationTick,
                0,
                1,
                TimeUnit.SECONDS
        );
    }

    private void simulationTick() {
        currentTick++;

        String stats = printer.buildStatistics(island);
        System.out.println(stats);
        writer.write(stats);

        if (currentTick >= settings.getMaxTicks()) {
            stop();
        }
    }

    public void stop() {
        System.out.println("Simulation finished after " + currentTick + " ticks");
        executor.shutdown();
    }
}
