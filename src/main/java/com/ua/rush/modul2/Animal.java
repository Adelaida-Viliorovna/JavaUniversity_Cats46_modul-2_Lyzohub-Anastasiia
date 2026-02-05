package com.ua.rush.modul2;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {
    protected final Type type;
    protected double satiety;
    protected int age;
    protected boolean hasEatenThisTick = false;

    protected Animal(Type type) {
        this.type = type;
        this.satiety = type.foodNeeded * 0.8;
        this.age = 0;
    }

    public void resetTickState() {
        this.hasEatenThisTick = false;
    }

    public boolean isHungry() {
        // Тварина хоче їсти, якщо ситість менше 75%
        return satiety < (type.foodNeeded * 0.75);
    }

    public void move(Island island, int currentX, int currentY) {
        if (type.speed <= 0) return;

        int dx = ThreadLocalRandom.current().nextInt(-type.speed, type.speed + 1);
        int dy = ThreadLocalRandom.current().nextInt(-type.speed, type.speed + 1);

        int newX = Math.max(0, Math.min(currentX + dx, island.getWidth() - 1));
        int newY = Math.max(0, Math.min(currentY + dy, island.getHeight() - 1));

        if (newX == currentX && newY == currentY) return;

        Location to = island.getLocation(newX, newY);
        if (to.getAnimalCount(this.type) < this.type.getMaxCount()) {
            island.getLocation(currentX, currentY).removeAnimal(this);
            to.addAnimal(this);
            satiety -= type.foodNeeded * 0.05;
        }
    }

    public void liveOneTick() {
        age++;
        satiety -= type.foodNeeded * 0.25; // Метаболізм
        if (satiety < 0) satiety = 0;
    }

    public boolean isDead() {
        return satiety <= 0 || age >= type.maxAge;
    }

    public abstract void eat(Location location);
    public Type getType() { return type; }
}