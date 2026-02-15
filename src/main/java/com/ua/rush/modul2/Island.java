package com.ua.rush.modul2;

// Клас, що представляє острів як сітку локацій
public class Island {

    // Ширина острова (кількість колонок)
    private final int width;

    // Висота острова (кількість рядків)
    private final int height;

    // Двовимірний масив локацій
    private final Location[][] locations;

    // Конструктор: створює масив локацій та ініціалізує їх початковою кількістю рослин
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

    // Повертає локацію за координатами x,y
    public Location getLocation(int x, int y) {
        return locations[y][x];
    }

    // Повертає масив локацій
    public Location[][] getLocations() {
        return locations;
    }

    // Повертає ширину острова
    public int getWidth() {
        return width;
    }

    // Повертає висоту острова
    public int getHeight() {
        return height;
    }

    // Скидає прапорець 'hasEatenThisTick' у всіх тварин по всіх локаціях
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
