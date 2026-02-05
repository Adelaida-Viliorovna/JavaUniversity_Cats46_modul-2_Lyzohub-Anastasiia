package com.ua.rush.modul2;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {
    protected Herbivore(Type type) {
        super(type);
    }

    @Override
    public void eat(Location location) {
        if (!isHungry()) return;

        // 1. Спроба поїсти комах/дрібних тварин (Качка, Миша, Кабан)
        int meatKills = 0;
        int MAX_MEAT_KILLS = 5; // Качка може з'їсти до 5 гусенів за такт

        while (isHungry() && meatKills < MAX_MEAT_KILLS) {
            boolean killed = false;
            for (var entry : location.getAnimals().entrySet()) {
                int chance = EatTable.getChance(this.type, entry.getKey());
                if (chance > 0 && !entry.getValue().isEmpty()) {
                    if (ThreadLocalRandom.current().nextInt(100) < chance) {
                        Animal victim = entry.getValue().get(0);
                        this.satiety = Math.min(type.foodNeeded, this.satiety + victim.getType().getWeight());
                        location.removeAnimal(victim);
                        meatKills++;
                        killed = true;
                        this.hasEatenThisTick = true;
                    }
                }
            }
            if (!killed) break; // Більше немає кого їсти
        }

        // 2. Якщо все ще голодний — "доганяємося" травою
        while (isHungry() && location.getPlants() > 0) {
            if (location.consumePlant()) {
                this.satiety = Math.min(type.foodNeeded, this.satiety + 0.5);
                this.hasEatenThisTick = true;
            } else {
                break;
            }
        }
    }
}