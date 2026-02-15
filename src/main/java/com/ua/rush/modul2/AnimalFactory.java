package com.ua.rush.modul2;

public final class AnimalFactory {
    // Приватний конструктор, щоб заборонити створення екземплярів фабрики
    private AnimalFactory() {}

    // Створює екземпляр тварини за типом (без рефлексії)
    public static Animal create(Type type) {
        return switch (type) {
            case WOLF -> new com.ua.rush.modul2.animals.Wolf();
            case BOA -> new com.ua.rush.modul2.animals.Boa();
            case FOX -> new com.ua.rush.modul2.animals.Fox();
            case BEAR -> new com.ua.rush.modul2.animals.Bear();
            case EAGLE -> new com.ua.rush.modul2.animals.Eagle();
            case HORSE -> new com.ua.rush.modul2.animals.Horse();
            case DEER -> new com.ua.rush.modul2.animals.Deer();
            case RABBIT -> new com.ua.rush.modul2.animals.Rabbit();
            case MOUSE -> new com.ua.rush.modul2.animals.Mouse();
            case GOAT -> new com.ua.rush.modul2.animals.Goat();
            case SHEEP -> new com.ua.rush.modul2.animals.Sheep();
            case BOAR -> new com.ua.rush.modul2.animals.Boar();
            case BUFFALO -> new com.ua.rush.modul2.animals.Buffalo();
            case DUCK -> new com.ua.rush.modul2.animals.Duck();
            case CATERPILLAR -> new com.ua.rush.modul2.animals.Caterpillar();
            default -> null;
        };
    }
}
