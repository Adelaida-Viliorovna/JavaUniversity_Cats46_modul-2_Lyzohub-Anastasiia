package com.ua.rush.modul2.simulation;

import com.ua.rush.modul2.config.Settings;
import com.ua.rush.modul2.island.Island;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private final Settings settings;
    private Island island;
    private ScheduledExecutorService executor;
    private final StatisticsPrinter printer = new StatisticsPrinter();

    public Simulation(Settings settings) {
        this.settings = settings;
    }

    public void start() {
        System.out.println("Simulation started");
        initWorld();
        startSimulationLoop();
    }

    private void initWorld() {
        island = new Island(
                settings.getIslandWidth(),
                settings.getIslandHeight(),
                10 // дефолтна кількість рослин у клітинці
        );
    }

    private void startSimulationLoop() {
        executor = Executors.newSingleThreadScheduledExecutor();

        executor.scheduleAtFixedRate(
                this::simulationTick,
                0,
                1,
                TimeUnit.SECONDS
        );
    }

    private void simulationTick() {
        printer.print(island);
    }

    public void stop() {
        executor.shutdownNow();
    }
}
