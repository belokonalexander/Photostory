package ru.belokonalexander.photostory.Helpers.Mapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Alexander on 07.06.2017.
 */

public class Mapper {

    public static <T extends Collection> T getEmptyMask(T collection, Set<Integer> mask){

        Iterator iterator = collection.iterator();

        int pos = 0;

        while (iterator.hasNext()){
            Object obj = iterator.next();

            if(!mask.contains(pos)){
                iterator.remove();
            }

            pos++;
        }

        return collection;
    };



}
