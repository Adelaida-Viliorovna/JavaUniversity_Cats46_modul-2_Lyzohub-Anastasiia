package com.ua.rush.modul2.model.animal;

import com.ua.rush.modul2.model.location.Location;

public abstract class Predator extends Animal {

    protected Predator(double weight,
                       int maxOnLocation,
                       int speed,
                       double foodNeeded) {
        super(weight, maxOnLocation, speed, foodNeeded);
    }

    @Override
    public void eat(Location location) {
        // Загальна логіка хижака:
        // 1. знайти потенційну жертву
        // 2. спробувати з'їсти з імовірністю
        // 3. якщо успіх — збільшити ситість
    }
}
