package com.ua.rush.modul2;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal {
    protected Predator(Type type) { super(type); }

    @Override
    public void eat(Location location) {
        if (!isHungry()) return;

        int killsThisTick = 0;
        int MAX_KILLS = 10; // Максимальна кількість дрібних жертв за такт

        var preyMap = location.getAnimals();

        boolean foundFood = true;
        while (isHungry() && killsThisTick < MAX_KILLS && foundFood) {
            foundFood = false;
            for (var entry : preyMap.entrySet()) {
                Type preyType = entry.getKey();
                int chance = EatTable.getChance(this.type, preyType);

                if (chance > 0 && !entry.getValue().isEmpty()) {
                    if (ThreadLocalRandom.current().nextInt(100) < chance) {
                        Animal victim = entry.getValue().get(0);
                        this.satiety = Math.min(type.foodNeeded, this.satiety + victim.getType().getWeight());
                        location.removeAnimal(victim);

                        killsThisTick++;
                        foundFood = true;
                        this.hasEatenThisTick = true;

                        if (!isHungry()) break;
                    }
                }
            }
        }
    }
}