package com.ua.rush.modul2.island;

import com.ua.rush.modul2.model.location.Location;

public class Island {

    private final int width;
    private final int height;
    private final Location[][] locations;

    public Island(int width, int height, int initialPlantCount) {
        this.width = width;
        this.height = height;
        this.locations = new Location[width][height];

        initLocations(initialPlantCount);
    }

    private void initLocations(int initialPlantCount) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y] = new Location(initialPlantCount);
            }
        }
    }

    public Location getLocation(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IndexOutOfBoundsException(
                    "Invalid location coordinates: x=" + x + ", y=" + y
            );
        }
        return locations[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
