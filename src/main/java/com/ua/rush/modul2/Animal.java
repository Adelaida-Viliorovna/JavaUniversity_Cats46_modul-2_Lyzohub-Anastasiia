package com.ua.rush.modul2;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {
    // Тип тварини
    protected final Type type;

    // Поточний рівень ситості
    protected double satiety;

    // Вік тварини у тиках
    protected int age;

    // Позначка, чи їла тварина в поточному тику
    protected boolean hasEatenThisTick = false;

    // Конструктор: ініціалізує тип, початкову ситість та вік
    protected Animal(Type type) {
        this.type = type;
        this.satiety = type.getFoodNeeded() * 0.8;
        this.age = 0;
    }

    // Скидає стан тварини для нового тика
    public void resetTickState() {
        this.hasEatenThisTick = false;
    }

    // Перевіряє, чи тварина вважається голодною за поточними налаштуваннями
    public boolean isHungry(Settings settings) {
        return satiety < (type.getFoodNeeded() * settings.getHungryThreshold());
    }

    // Переміщує тварину в іншу локацію острова випадковим чином
    public void move(Island island, int currentX, int currentY) {
        if (type.getSpeed() <= 0) return;

        int dx = ThreadLocalRandom.current().nextInt(-type.getSpeed(), type.getSpeed() + 1);
        int dy = ThreadLocalRandom.current().nextInt(-type.getSpeed(), type.getSpeed() + 1);

        int newX = Math.max(0, Math.min(currentX + dx, island.getWidth() - 1));
        int newY = Math.max(0, Math.min(currentY + dy, island.getHeight() - 1));

        if (newX == currentX && newY == currentY) return;

        Location to = island.getLocation(newX, newY);
        if (to.getAnimalCount(this.type) < this.type.getMaxCount()) {
            island.getLocation(currentX, currentY).removeAnimal(this);
            to.addAnimal(this);
            satiety -= type.getFoodNeeded() * 0.05;
        }
    }

    // Проходження одного тика: старіння і зменшення ситості згідно налаштувань
    public void liveOneTick(Settings settings) {
        age++;
        satiety -= type.getFoodNeeded() * settings.getMetabolismPerTick();
        if (satiety < 0) satiety = 0;
    }

    // Перевіряє умови смерті (голод або старість)
    public boolean isDead() {
        return satiety <= 0 || age >= type.getMaxAge();
    }

    // Метод годування, кожен підклас реалізує відповідну поведінку
    public abstract void eat(Location location, Settings settings);

    // Базова поведінка розмноження; може бути перевизначена в підкласах
    public void reproduce(Location location, Settings settings) {
        int n = location.getAnimalCount(this.type);
        if (n < 2) return;

        if (n > this.type.getMaxCount() * settings.getReproductionOccupancyThreshold()) return;

        int pairs = n / 2;
        int newBornsTotal = 0;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int maxLitter = settings.getMaxLitterSize(this.type);

        for (int i = 0; i < pairs; i++) {
            if (random.nextInt(100) < settings.getReproductionChancePercent()) {
                newBornsTotal += random.nextInt(1, maxLitter + 1);
            }
        }

        int spaceLeft = this.type.getMaxCount() - n;
        int actuallyToAdd = Math.min(newBornsTotal, spaceLeft);

        for (int i = 0; i < actuallyToAdd; i++) {
            Animal baby = com.ua.rush.modul2.AnimalFactory.create(this.type);
            if (baby != null) location.addAnimal(baby);
        }
    }

    // Повертає тип тварини
    public Type getType() { return type; }
}