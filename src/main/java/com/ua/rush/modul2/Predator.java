package com.ua.rush.modul2;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal {
    // Базовий клас для хижаків
    protected Predator(Type type) { super(type); }

    // Логіка годування хижака: шукає жертву в локації та по можливості її з'їдає
    @Override
    public void eat(Location location, Settings settings) {
        if (!isHungry(settings)) return;

        int killsThisTick = 0;
        int maxKills = settings.getMaxPredatorKillsPerTick();

        var preyMap = location.getAnimals();

        boolean foundFood = true;
        while (isHungry(settings) && killsThisTick < maxKills && foundFood) {
            foundFood = false;
            for (var entry : preyMap.entrySet()) {
                Type preyType = entry.getKey();
                int chance = EatTable.getChance(this.type, preyType);

                var list = entry.getValue();
                if (chance > 0 && !list.isEmpty()) {
                    if (ThreadLocalRandom.current().nextInt(100) < chance) {
                        Animal victim = list.stream().findFirst().orElse(null);
                        if (victim != null) {
                            this.satiety = Math.min(type.getFoodNeeded(), this.satiety + victim.getType().getWeight());
                            location.removeAnimal(victim);

                            killsThisTick++;
                            foundFood = true;
                            this.hasEatenThisTick = true;

                            if (!isHungry(settings)) break;
                        }
                    }
                }
            }
        }
    }
}