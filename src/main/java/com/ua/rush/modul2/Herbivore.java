package com.ua.rush.modul2;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {
    // Базовий клас для травоїдних
    protected Herbivore(Type type) {
        super(type);
    }

    // Логіка годування травоїдного: спочатку дрібні тварини/комахи, потім рослини
    @Override
    public void eat(Location location, Settings settings) {
        if (!isHungry(settings)) return;

        // Скільки дрібного м'яса можна з'їсти за тик
        int meatKills = 0;
        int maxMeatKills = settings.getMaxHerbivoreMeatKillsPerTick();

        while (isHungry(settings) && meatKills < maxMeatKills) {
            boolean killed = false;
            for (var entry : location.getAnimals().entrySet()) {
                int chance = EatTable.getChance(this.type, entry.getKey());
                if (chance > 0 && !entry.getValue().isEmpty()) {
                    if (ThreadLocalRandom.current().nextInt(100) < chance) {
                        Animal victim = entry.getValue().get(0);
                        this.satiety = Math.min(type.getFoodNeeded(), this.satiety + victim.getType().getWeight());
                        location.removeAnimal(victim);
                        meatKills++;
                        killed = true;
                        this.hasEatenThisTick = true;
                    }
                }
            }
            if (!killed) break; // Немає більше їжі з тварин
        }

        // Доїдаємо травою до насичення або поки є рослини
        while (isHungry(settings) && location.getPlants() > 0) {
            if (location.consumePlant()) {
                this.satiety = Math.min(type.getFoodNeeded(), this.satiety + settings.getPlantSatietyValue());
                this.hasEatenThisTick = true;
            } else {
                break;
            }
        }
    }
}