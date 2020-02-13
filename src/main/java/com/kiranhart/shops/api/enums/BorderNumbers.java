package com.kiranhart.shops.api.enums;
/*
    Created by Kiran Hart
    Date: February / 10 / 2020
    Time: 10:44 a.m.
*/

import java.util.Arrays;
import java.util.List;

public enum BorderNumbers {

    NINE(Arrays.asList(0, 8)),
    FIFTY_FOUR(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53));


    private List<Integer> slots;

    BorderNumbers(List<Integer> slots) {
        this.slots = slots;
    }

    public List<Integer> getSlots() {
        return slots;
    }
}
