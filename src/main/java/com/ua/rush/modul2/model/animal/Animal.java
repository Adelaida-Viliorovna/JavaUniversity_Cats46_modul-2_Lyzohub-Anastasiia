package com.ua.rush.modul2.model.animal;

import com.ua.rush.modul2.model.location.Location;

public abstract class Animal {

    protected final double weight;
    protected final int maxOnLocation;
    protected final int speed;
    protected final double foodNeeded;

    protected double currentFood;
    protected boolean alive = true;

    protected int age = 0;

    protected Animal(double weight,
                     int maxOnLocation,
                     int speed,
                     double foodNeeded) {
        this.weight = weight;
        this.maxOnLocation = maxOnLocation;
        this.speed = speed;
        this.foodNeeded = foodNeeded;
        this.currentFood = foodNeeded; // стартує ситим
    }

    // --- behavior ---
    public abstract void eat(Location location);
    public abstract void move(Location location);
    public abstract void reproduce(Location location);
    public abstract void liveTick(Location location);

    public void incrementAge() {
        age++;
    }

    // --- life cycle ---
    public boolean isAlive() {
        return alive;
    }

    protected void die() {
        alive = false;
    }


    // --- hunger logic ---
    protected void reduceFood(double amount) {
        currentFood -= amount;
        if (currentFood <= 0) {
            die();
        }
    }

    protected void increaseFood(double amount) {
        currentFood = Math.min(foodNeeded, currentFood + amount);
    }

    // --- getters ---
    public double getWeight() {
        return weight;
    }

    public int getMaxOnLocation() {
        return maxOnLocation;
    }

    public int getSpeed() {
        return speed;
    }

    public double getFoodNeeded() {
        return foodNeeded;
    }

}

