package com.ua.rush.modul2;

import java.util.EnumMap;
import java.util.Map;

public class EatTable {

    // Таблиця шансів (%) хижак -> жертва
    private static final Map<Type, Map<Type, Integer>> TABLE = new EnumMap<>(Type.class);

    private EatTable() {
        // утилітний клас, не дозволяємо створювати екземпляри
    }

    static {
        TABLE.put(Type.WOLF, Map.of(
                Type.HORSE, 10,
                Type.DEER, 15,
                Type.RABBIT, 60,
                Type.MOUSE, 80,
                Type.GOAT, 60,
                Type.SHEEP, 70,
                Type.BOAR, 15,
                Type.BUFFALO, 10,
                Type.DUCK, 40
        ));
        TABLE.put(Type.BOA, Map.of(
                Type.FOX, 15,
                Type.RABBIT, 20,
                Type.MOUSE, 40,
                Type.DUCK, 10
        ));
        TABLE.put(Type.FOX, Map.of(
                Type.RABBIT, 70,
                Type.MOUSE, 90,
                Type.DUCK, 60,
                Type.CATERPILLAR, 40
        ));
        TABLE.put(Type.BEAR, Map.of(
                Type.BOA, 80,
                Type.HORSE, 40,
                Type.DEER, 80,
                Type.RABBIT, 80,
                Type.MOUSE, 90,
                Type.GOAT, 70,
                Type.SHEEP, 70,
                Type.BOAR, 50,
                Type.BUFFALO, 20,
                Type.DUCK, 10
        ));
        TABLE.put(Type.EAGLE, Map.of(
                Type.FOX, 10,
                Type.RABBIT, 90,
                Type.MOUSE, 90,
                Type.DUCK, 80
        ));
        TABLE.put(Type.HORSE, Map.of(
                Type.PLANT, 100
        ));
        TABLE.put(Type.DEER, Map.of(
                Type.PLANT, 100
        ));
        TABLE.put(Type.RABBIT, Map.of(
                Type.PLANT, 100
        ));
        TABLE.put(Type.MOUSE, Map.of(
                Type.PLANT, 100,
                Type.CATERPILLAR, 90
        ));
        TABLE.put(Type.GOAT, Map.of(
                Type.PLANT, 100
        ));
        TABLE.put(Type.SHEEP, Map.of(
                Type.PLANT, 100
        ));
        TABLE.put(Type.BOAR, Map.of(
                Type.PLANT, 100,
                Type.MOUSE, 50,
                Type.CATERPILLAR, 90
        ));
        TABLE.put(Type.BUFFALO, Map.of(
                Type.PLANT, 100
        ));
        TABLE.put(Type.DUCK, Map.of(
                Type.PLANT, 100,
                Type.CATERPILLAR, 90
        ));
        TABLE.put(Type.CATERPILLAR, Map.of(
                Type.PLANT, 100
        ));
    }

    // Повертає шанс у відсотках, що хижак з'їсть жертву
    public static int getChance(Type predator, Type prey) {
        return TABLE.getOrDefault(predator, Map.of())
                .getOrDefault(prey, 0);
    }
}
