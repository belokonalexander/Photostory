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

    public static int count = 0;

    public Topic() {

        id = (long) count;
        count++;
        title = "Dummy - " + id;

    }

    public Topic(Long c) {
        id = c;
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
