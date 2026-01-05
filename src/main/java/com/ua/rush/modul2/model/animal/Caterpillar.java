package com.ua.rush.modul2.model.animal;

import com.ua.rush.modul2.model.location.Location;

public class Caterpillar extends Herbivore {

    public static final double WEIGHT = 0.01;
    public static final int MAX_ON_LOCATION = 1000;
    private static final int MAX_AGE = 3;

    public Caterpillar() {
        super(WEIGHT, MAX_ON_LOCATION, 0, 0);
    }

    public void growOlder() {
        age++;
    }

    public boolean shouldDie() {
        return age >= 3;
    }

    @Override
    public void eat(Location location) {
        if (location.getPlantCount() > 0) {
            location.removePlants(1);
        }
    }

    @Override
    public void liveTick(Location location) {
        incrementAge();

        eat(location);

        if (age >= MAX_AGE) {
            die(location);
        }
    }

    private void die(Location location) {
        alive = false;
        location.removeAnimal(this);
    }

    @Override
    public void reproduce(Location location) {
        long count = location.getAnimals().stream()
                .filter(a -> a instanceof Caterpillar)
                .count();

//        if (count >= 2) {
//            int newborns = (int) (count / 2);
//            for (int i = 0; i < newborns; i++) {
//                if (count + i < maxOnLocation) {
//                    location.addAnimal(new Caterpillar());
//                }
//            }
//        }
    }

    @Override
    public void move(Location location) {
        // не рухається
    }
}
