package com.ua.rush.modul2.model.animal;

import com.ua.rush.modul2.model.location.Location;

public class Duck extends Herbivore {
    public Duck() {
        super(0, 0, 0, 0);
    }

    @Override
    public void eat(Location location) {
        // спочатку гусінь
        // якщо немає — рослини
    }

    @Override
    public void move(Location location) {

    }

    @Override
    public void reproduce(Location location) {

    }

    @Override
    public void liveTick(Location location) {

    }
}
