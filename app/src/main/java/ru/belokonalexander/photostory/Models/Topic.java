package ru.belokonalexander.photostory.Models;

import java.io.Serializable;
import java.util.Random;

import ru.belokonalexander.photostory.Views.Search.SearchEntity;

/**
 * Created by Alexander on 22.04.2017.
 */

public class Topic implements Serializable, SearchEntity {


    String title;
    Long id;

    public Topic() {
        id = Math.abs(new Random().nextLong()%1000);
        title = "Dummy - " + id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
}
