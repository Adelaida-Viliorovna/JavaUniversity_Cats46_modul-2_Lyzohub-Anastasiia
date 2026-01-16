package com.ua.rush.modul2.model.animal;

import com.ua.rush.modul2.model.location.Location;
//Гусінь — травоїдна тварина
public class Caterpillar extends Herbivore {

    public static final int MAX_ON_LOCATION = 1000;

    public Caterpillar() {
        super(0.01,
                1000,
                0,
                0,
                3);
    }

    @Override
    public void eat(Location location) {
        if (location.getPlantCount() > 0) {
            location.removePlants(1);
        }
    }

    @Override
    public void liveTick(Location location) {
        age++;

        eat(location);

        if (age >= maxAge) {
            die(location);
        }
    }

    @Override
    public void move(Location location) {
        // не рухається
    }

    @Override
    public void reproduce(Location location) {
        // Розмноження керується завданням `CaterpillarTask` на рівні локації,
        // тому тут немає локальної реалізації — метод залишений порожнім умисно.
    }
}
