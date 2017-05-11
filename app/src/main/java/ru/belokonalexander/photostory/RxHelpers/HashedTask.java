package ru.belokonalexander.photostory.RxHelpers;

/**
 * Created by Alexander on 11.05.2017.
 */

public class HashedTask<T> {

    private String tag;
    private SimpleDisposableObserver<T> task;

    public HashedTask(String tag, SimpleDisposableObserver<T> task) {
        this.tag = tag;
        this.task = task;
    }

    public String getTag() {
        return tag;
    }

    public SimpleDisposableObserver<T> getTask() {
        return task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashedTask that = (HashedTask) o;

        if (!tag.equals(that.tag)) return false;
        return task.equals(that.task);

    }

    @Override
    public int hashCode() {
        int result = tag.hashCode();
        result = 31 * result + task.hashCode();
        return result;
    }
}
