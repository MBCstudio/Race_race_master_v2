package org.example;

/**
 * Klasa opdowiadajaca za losowanie liczb z zadanego przedzialu
 */
public class RandInt {
    public static int random(int min, int max) {
        return (int)(Math.random()*(max - min + 1) + min);
    }
}
