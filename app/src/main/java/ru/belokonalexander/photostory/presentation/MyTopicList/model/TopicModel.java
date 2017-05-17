package ru.belokonalexander.photostory.presentation.MyTopicList.model;

/**
 * Created by Alexander on 16.05.2017.
 */

public class TopicModel {

    private String title;
    private Long id;

    public TopicModel(String title, Long id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
