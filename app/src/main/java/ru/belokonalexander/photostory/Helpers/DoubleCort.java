package ru.belokonalexander.photostory.Helpers;

/**
 * Created by Alexander on 23.04.2017.
 */

public class DoubleCort<T,V> {
    final private T one;
    final private V two;

    public DoubleCort(T one, V two) {
        this.one = one;
        this.two = two;
    }

    public T getOne() {
        return one;
    }

    public V getTwo() {
        return two;
    }
}
