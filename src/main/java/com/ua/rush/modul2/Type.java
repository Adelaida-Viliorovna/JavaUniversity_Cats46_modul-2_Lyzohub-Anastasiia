package com.ua.rush.modul2;

public enum Type {
    WOLF("🐺", 50, 30, 3, 8, 8),
    BOA("🐍", 15, 30, 1, 3, 6),
    FOX("🦊", 8, 30, 2, 2, 8),
    BEAR("🐻", 500, 5, 2, 80, 9),
    EAGLE("🦅", 6, 20, 3, 1, 6),
    HORSE("🐎", 400, 20, 4, 60, 7),
    DEER("🦌", 300, 20, 4, 50, 6),
    RABBIT("🐇", 2, 150, 2, 0.45, 4),
    MOUSE("🐁", 0.05, 500, 1, 0.01, 3),
    GOAT("🐐", 60, 140, 3, 10, 7),
    SHEEP("🐑", 70, 140, 3, 15, 7),
    BOAR("🐗", 400, 50, 2, 50, 7),
    BUFFALO("🐂", 700, 10, 3, 100, 8),
    DUCK("🦆", 1, 200, 4, 0.15, 4),
    CATERPILLAR("🐛", 0.01, 1000, 0, 5, 3),
    PLANT("🌿", 1, 200, 0, 0, 0);

    protected final String emoji;
    protected final double weight;
    protected final int maxCount;
    protected final int speed;
    protected final double foodNeeded;
    protected final int maxAge;

    Type(String emoji, double weight, int maxCount, int speed, double foodNeeded, int maxAge) {
        this.emoji = emoji;
        this.weight = weight;
        this.maxCount = maxCount;
        this.speed = speed;
        this.foodNeeded = foodNeeded;
        this.maxAge = maxAge;
    }

    public String getEmoji() {
        return emoji;
    }

    public double getWeight() {
        return weight;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getSpeed() {
        return speed;
    }

    public double getFoodNeeded() {
        return foodNeeded;
    }

    public int getMaxAge() {
        return maxAge;
    }
}
