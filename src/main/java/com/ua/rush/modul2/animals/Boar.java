package com.ua.rush.modul2.animals;

import com.ua.rush.modul2.Herbivore;
import com.ua.rush.modul2.Island;
import com.ua.rush.modul2.Location;
import com.ua.rush.modul2.Type;
import com.ua.rush.modul2.Animal;
import com.ua.rush.modul2.EatTable;
import com.ua.rush.modul2.RandomUtil;

import java.util.List;
import java.util.Map;

public class Boar extends Herbivore {
    public Boar() {
        super(Type.BOAR);
    }

    @Override
    public void eat(Location location) {
        for (Map.Entry<Type, List<Animal>> entry : location.getAnimals().entrySet()) {
            int chance = EatTable.getChance(type, entry.getKey());
            if (chance <= 0 || entry.getValue().isEmpty()) continue;

            if (RandomUtil.checkChance(chance)) {
                Animal victim = entry.getValue().get(0);
                location.removeAnimal(victim);
                satiety += victim.getType().getWeight();
                return;
            }
        }

        super.eat(location);
    }
}
