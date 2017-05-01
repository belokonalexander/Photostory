package ru.belokonalexander.photostory.Models;

import java.io.Serializable;
import java.util.Random;

import ru.belokonalexander.photostory.R;
import ru.belokonalexander.photostory.Views.Search.SearchEntity;
import ru.belokonalexander.photostory.Views.Search.SearchField;

/**
 * Created by Alexander on 22.04.2017.
 */

public class Topic implements Serializable, SearchEntity {


    @SearchField(lazySearch = true, alias = R.string.empty_data, order = 2)
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
        title =  id + "Dummy - ";

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
