package com.ua.rush.modul2;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtil {

    public static boolean checkChance(int percent) {
        return ThreadLocalRandom.current().nextInt(100) < percent;
    }
}
