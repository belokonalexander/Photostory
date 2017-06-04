package ru.belokonalexander.photostory.DI.common.Scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Alexander on 17.05.2017.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface TopicScope {
}
