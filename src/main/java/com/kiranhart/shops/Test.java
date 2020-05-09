package com.kiranhart.shops;

/**
 * The current file has been created by Kiran Hart
 * Date Created: 5/7/2020
 * Time Created: 8:02 PM
 * Usage of any code found within this class is prohibited unless given explicit permission otherwise.
 */
public class Test {

    public static void main(String[] args) {

        double originalPrice = 1000.50;
        double discountPercentage = 21.5;

        double discount = originalPrice - (originalPrice * (discountPercentage / 100));

        System.out.println(discount);

    }
}
