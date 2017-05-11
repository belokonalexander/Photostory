package ru.belokonalexander.photostory.Events;

import ru.belokonalexander.photostory.Models.Topic;

/**
 * Created by Alexander on 11.05.2017.
 */

public class ListEvent {
    Long topicId;

    public ListEvent(Long topicId) {
        this.topicId = topicId;
    }

    public Long getTopicId() {
        return topicId;
    }
}
