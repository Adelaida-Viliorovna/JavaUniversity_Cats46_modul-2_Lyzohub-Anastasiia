package com.ua.rush.modul2;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Settings {
    private static final Logger LOGGER = Logger.getLogger(Settings.class.getName());

    // Ширина острова в клітинках
    private final int islandWidth;

    // Висота острова в клітинках
    private final int islandHeight;

    // Максимальна кількість тиков симуляції
    private final int maxTicks;

    // Тривалість одного тика в мілісекундах
    private final int tickDurationMs;

    // Початкова кількість рослин на локації
    private final int initialPlants;

    // Мінімальний ріст рослин за цикл
    private final int plantGrowthMin;
    // Множник росту рослин від поточної кількості
    private final double plantGrowthFactor;
    // Шанс самосіву в процентах
    private final int plantSelfSowChancePercent;
    // Порог місткості сусідньої клітини для вважання "зеленою"
    private final int plantNeighborGreenThreshold;

    // Порог голоду (фракція) та метаболізм
    private final double hungryThreshold;
    private final double metabolismPerTick;
    private final double plantSatietyValue;

    // Параметри розмноження
    private final int reproductionChancePercent;
    private final double reproductionOccupancyThreshold;

    // Обмеження на кількість вбивств за тик
    private final int maxPredatorKillsPerTick;
    private final int maxHerbivoreMeatKillsPerTick;

    // Конструктор налаштувань (переважно приватний, викликається фабриками)
    private Settings(int width, int height, int ticks, int speed,
                     int initialPlants,
                     int plantGrowthMin, double plantGrowthFactor, int plantSelfSowChancePercent, int plantNeighborGreenThreshold,
                     double hungryThreshold, double metabolismPerTick, double plantSatietyValue,
                     int reproductionChancePercent, double reproductionOccupancyThreshold,
                     int maxPredatorKillsPerTick, int maxHerbivoreMeatKillsPerTick) {
        this.islandWidth = width;
        this.islandHeight = height;
        this.maxTicks = ticks;
        this.tickDurationMs = speed;

        this.initialPlants = initialPlants;
        this.plantGrowthMin = plantGrowthMin;
        this.plantGrowthFactor = plantGrowthFactor;
        this.plantSelfSowChancePercent = plantSelfSowChancePercent;
        this.plantNeighborGreenThreshold = plantNeighborGreenThreshold;

        this.hungryThreshold = hungryThreshold;
        this.metabolismPerTick = metabolismPerTick;
        this.plantSatietyValue = plantSatietyValue;

        this.reproductionChancePercent = reproductionChancePercent;
        this.reproductionOccupancyThreshold = reproductionOccupancyThreshold;

        this.maxPredatorKillsPerTick = maxPredatorKillsPerTick;
        this.maxHerbivoreMeatKillsPerTick = maxHerbivoreMeatKillsPerTick;
    }

    // Формує ім'я файлу звіту залежно від конфігурації
    public String getFileName() {
        return String.format("stats_%s_%dx%d_%dticks.txt",
                (islandWidth == 10 && islandHeight == 10 && maxTicks == 20) ? "DEFAULT" : "CUSTOM",
                islandWidth, islandHeight, maxTicks);
    }

    // Повертає стандартні налаштування
    public static Settings defaultSettings() {
        return new Settings(
                10, 10, 20, 500,
                500,
                10, 0.5, 15, 100,
                0.75, 0.25, 0.5,
                50, 0.8,
                10, 5
        );
    }

    // Зчитування користувацьких налаштувань через Scanner
    public static Settings customSettings(Scanner scanner) {
        LOGGER.info("--- Налаштування симуляції ---");
        int w = readInt(scanner, "Ширина острова (5-100): ", 5, 100);
        int h = readInt(scanner, "Висота острова (5-100): ", 5, 100);
        int t = readInt(scanner, "Кількість кроків (1-1000): ", 1, 1000);
        Settings base = defaultSettings();
        return new Settings(w, h, t, base.getTickDurationMs(),
                base.getInitialPlants(),
                base.getPlantGrowthMin(), base.getPlantGrowthFactor(), base.getPlantSelfSowChancePercent(), base.getPlantNeighborGreenThreshold(),
                base.getHungryThreshold(), base.getMetabolismPerTick(), base.getPlantSatietyValue(),
                base.getReproductionChancePercent(), base.getReproductionOccupancyThreshold(),
                base.getMaxPredatorKillsPerTick(), base.getMaxHerbivoreMeatKillsPerTick());
    }

    // Допоміжна функція для безпечного зчитування числа з проміжком
    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            LOGGER.log(Level.INFO, prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
                LOGGER.warning("Помилка: число поза діапазоном.");
            } catch (Exception e) {
                LOGGER.warning("Помилка: введіть ціле число.");
            }
        }
    }

    // Геттери доступу до полів конфігурації
    public int getIslandWidth() { return islandWidth; }
    public int getIslandHeight() { return islandHeight; }
    public int getMaxTicks() { return maxTicks; }
    public int getTickDurationMs() { return tickDurationMs; }

    public int getInitialPlants() { return initialPlants; }

    public int getPlantGrowthMin() { return plantGrowthMin; }
    public double getPlantGrowthFactor() { return plantGrowthFactor; }
    public int getPlantSelfSowChancePercent() { return plantSelfSowChancePercent; }
    public int getPlantNeighborGreenThreshold() { return plantNeighborGreenThreshold; }

    public double getHungryThreshold() { return hungryThreshold; }
    public double getMetabolismPerTick() { return metabolismPerTick; }
    public double getPlantSatietyValue() { return plantSatietyValue; }

    public int getReproductionChancePercent() { return reproductionChancePercent; }
    public double getReproductionOccupancyThreshold() { return reproductionOccupancyThreshold; }

    public int getMaxPredatorKillsPerTick() { return maxPredatorKillsPerTick; }
    public int getMaxHerbivoreMeatKillsPerTick() { return maxHerbivoreMeatKillsPerTick; }

    // Максимальний розмір приплоду для певного типу
    public int getMaxLitterSize(Type type) {
        return switch (type) {
            case CATERPILLAR -> 10;
            case MOUSE -> 4;
            case RABBIT -> 3;
            case DUCK -> 3;
            case GOAT, SHEEP, DEER -> 2;
            default -> 1;
        };
    }
}