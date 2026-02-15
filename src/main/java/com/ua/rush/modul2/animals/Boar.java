package com.ua.rush.modul2.animals;

import com.ua.rush.modul2.Herbivore;
import com.ua.rush.modul2.Location;
import com.ua.rush.modul2.Type;
import com.ua.rush.modul2.Animal;
import com.ua.rush.modul2.EatTable;
import com.ua.rush.modul2.RandomUtil;
import com.ua.rush.modul2.Settings;

import java.util.List;
import java.util.Map;

// Клас кабан — всеїдна тварина, що може їсти дрібних тварин та рослини
public class Boar extends Herbivore {
    // Конструктор: створює кабана з відповідним типом
    public Boar() {
        super(Type.BOAR);
    }

    @Override
    public void eat(Location location, Settings settings) {
        for (Map.Entry<Type, List<Animal>> entry : location.getAnimals().entrySet()) {
            int chance = EatTable.getChance(type, entry.getKey());
            var list = entry.getValue();
            if (chance <= 0 || list.isEmpty()) continue;

            if (RandomUtil.checkChance(chance)) {
                Animal victim = list.get(0);
                location.removeAnimal(victim);
                this.satiety = Math.min(type.getFoodNeeded(), this.satiety + victim.getType().getWeight());
                this.hasEatenThisTick = true;
                return;
            }
        }

        super.eat(location, settings);
    }
}
