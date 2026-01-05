package com.ua.rush.modul2.model.animal;

import com.ua.rush.modul2.model.location.Location;

public abstract class Herbivore extends Animal {

    protected Herbivore(double weight,
                        int maxOnLocation,
                        int speed,
                        double foodNeeded) {
        super(weight, maxOnLocation, speed, foodNeeded);
    }

    @Override
    public void eat(Location location) {
        // Загальна логіка травоїдного:
        // 1. якщо є рослини — їсти
        // 2. збільшити ситість
    }


}
