package com.ua.rush.modul2;

public class Plant {

    // Кількість одиниць рослинності на локації
    private int amount;

    // Конструктор: задає початкову кількість рослин
    public Plant(int initialAmount) {
        this.amount = initialAmount;
    }

    // Синхронізований ріст рослин з обмеженням максимуму
    public synchronized void grow(int value, int max) {
        amount = Math.min(amount + value, max);
    }

    // Синхронізоване споживання однієї одиниці рослин
    public synchronized boolean consume() {
        if (amount > 0) {
            amount--;
            return true;
        }
        return false;
    }

    // Повертає поточну кількість рослин
    public int getAmount() {
        return amount;
    }
}
