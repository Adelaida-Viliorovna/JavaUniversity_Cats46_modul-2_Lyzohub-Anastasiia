package com.ua.rush.modul2.model.animal;

import com.ua.rush.modul2.model.location.Location;
// качка — травоїдна тварина
public class Duck extends Herbivore {

//    private static final int MAX_AGE = 4;

    public Duck() {
        super(1, 200, 4, 0.15, 4);
    }


    @Override
    public void eat(Location location) {
        // спочатку гусінь
        // якщо немає — рослини
//        if (location.getPlantCount() > 0) {
//            location.removePlants(1);
//        }
        //визначити чи є на клітинці гусінь, якщо є гусінь - їмо гусінь
        //якщо гусені немає - визначаємо чи є рослина і якщо рослина є - їмо рослину
        //якщо їжі немає - переходимо до руху

    }

    @Override
    public void move(Location location) {
        //вибираємо напрям руху: верх, вниз, вліво, вправо
        //перевіряємо чи можна рухатись в обраному напрямку (чи не виходимо за межі острова)
        //якщо можна - рухаємось на рандомну кількість клітинок (від 1 до максимальної швидкості)
        //якщо не можна - обираємо інший напрямок
    }

    @Override
    public void reproduce(Location location) {
        //якщо качка сита і на клітинці є ще одна качка - народжуємо каченят (від 1 до 3)
    }

    @Override
    public void liveTick(Location location) {
        //збільшуємо вік
        //якщо качка досягла максимального віку - вмирає
//        incrementAge();
//
//        eat(location);
//
//        if (age >= MAX_AGE) {
//            die(location);
//        }
    }

}
