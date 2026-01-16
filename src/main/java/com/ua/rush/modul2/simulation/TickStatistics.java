package com.ua.rush.modul2.simulation;

import java.util.HashMap;
import java.util.Map;

/**
 * Статистика подій на одній локації за один такт
 */
public class TickStatistics {

    public int plantsGrown = 0;
    public int plantsEaten = 0;

    public int animalsBorn = 0;
    public int animalsDied = 0;

    public Map<String, Integer> bornByType = new HashMap<>();
    public Map<String, Integer> diedByType = new HashMap<>();

}
