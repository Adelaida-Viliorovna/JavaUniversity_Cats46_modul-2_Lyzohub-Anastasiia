package com.ua.rush.modul2;

public class Island {

    private final int width;
    private final int height;
    private final Location[][] locations;

    public Island(int width, int height, int initialPlants) {
        this.width = width;
        this.height = height;
        locations = new Location[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                locations[y][x] = new Location(initialPlants);
            }
        }
    }

    public Location getLocation(int x, int y) {
        return locations[y][x];
    }

    public Location[][] getLocations() {
        return locations;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void resetAnimalsFlags() {
        for (Location[] row : locations) {
            for (Location loc : row) {
                loc.getAnimals().values().forEach(list ->
                        list.forEach(Animal::resetTickState)
                );
            }
        }
    }
}
