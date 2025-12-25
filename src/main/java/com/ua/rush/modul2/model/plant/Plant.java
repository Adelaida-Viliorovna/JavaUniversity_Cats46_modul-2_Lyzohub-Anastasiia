package com.ua.rush.modul2.model.plant;

public class Plant {

    private final double weight;
    private final int maxOnLocation;

    public Plant(double weight, int maxOnLocation) {
        this.weight = weight;
        this.maxOnLocation = maxOnLocation;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxOnLocation() {
        return maxOnLocation;
    }
}

