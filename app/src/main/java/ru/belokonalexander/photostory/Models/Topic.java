package ru.belokonalexander.photostory.Models;

import java.util.Random;

/**
 * Created by Alexander on 22.04.2017.
 */

public class Topic {

    Long id = Math.abs(new Random().nextLong()%1000);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
