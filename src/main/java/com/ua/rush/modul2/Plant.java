package com.ua.rush.modul2;

public class Plant {

    private int amount;

    public Plant(int initialAmount) {
        this.amount = initialAmount;
    }

    public synchronized void grow(int value, int max) {
        amount = Math.min(amount + value, max);
    }

    public synchronized boolean consume() {
        if (amount > 0) {
            amount--;
            return true;
        }
        return false;
    }

    public int getAmount() {
        return amount;
    }
}
