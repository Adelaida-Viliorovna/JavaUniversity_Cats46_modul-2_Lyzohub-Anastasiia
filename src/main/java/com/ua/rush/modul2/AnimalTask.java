package com.ua.rush.modul2;

import com.ua.rush.modul2.Animal;
import com.ua.rush.modul2.Island;
import com.ua.rush.modul2.Location;

public class AnimalTask implements Runnable {

    private final Island island;
    private final Location location;
    private final int x;
    private final int y;
    private final Animal animal;

    public AnimalTask(Island island, Location location, int x, int y, Animal animal) {
        this.island = island;
        this.location = location;
        this.x = x;
        this.y = y;
        this.animal = animal;
    }

    @Override
    public void run() {

        // 1. MOVE
        animal.move(island, x, y);

        // 2. EAT (поліморфізм)
        animal.eat(location);

        // 3. REPRODUCE
//        animal.reproduce(location);

        // 4. AGING & HUNGER
        animal.liveOneTick();

        // 5. DIE
        if (animal.isDead()) {
            location.removeAnimal(animal);
        }
    }
}
