package com.ua.rush.modul2;

// Задача для виконання дій однієї тварини в рамках одного тика
public class AnimalTask implements Runnable {

    // Посилання на острів
    private final Island island;

    // Локація, в якій тварина знаходиться
    private final Location location;

    // Поточні координати тварини
    private final int x;
    private final int y;

    // Тварина, для якої виконується задача
    private final Animal animal;

    // Налаштування симуляції
    private final Settings settings;

    // Конструктор задачі тварини
    public AnimalTask(Island island, Location location, int x, int y, Animal animal, Settings settings) {
        this.island = island;
        this.location = location;
        this.x = x;
        this.y = y;
        this.animal = animal;
        this.settings = settings;
    }

    // Виконує крок поведінки тварини: рух, їжа, старіння, перевірка смерті
    @Override
    public void run() {
        // Рух тварини
        animal.move(island, x, y);

        // Харчування (поліморфізм у підкласах)
        animal.eat(location, settings);

        // Старіння та витрата калорій
        animal.liveOneTick(settings);

        // Видалення тварини при смерті
        if (animal.isDead()) {
            location.removeAnimal(animal);
        }
    }
}
