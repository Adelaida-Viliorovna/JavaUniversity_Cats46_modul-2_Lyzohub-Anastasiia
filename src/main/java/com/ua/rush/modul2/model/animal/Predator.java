package com.ua.rush.modul2.model.animal;

public abstract class Predator extends Animal {

    protected Predator(double weight,
                       int maxOnLocation,
                       int speed,
                       double foodNeeded) {
        super(weight, maxOnLocation, speed, foodNeeded);
    }

    @Override
    public void eat() {
        // Загальна логіка хижака:
        // 1. знайти потенційну жертву
        // 2. спробувати з'їсти з імовірністю
        // 3. якщо успіх — збільшити ситість
        // TODO: реалізуємо, коли зʼявиться Location
    }
}
