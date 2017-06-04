package ru.belokonalexander.photostory.business.MappersUtils;

import com.mikepenz.fastadapter.IItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by Alexander on 02.06.2017.
 */

public class ItemsToFlexibleMapper {




    public Function<List, SingleSource<List<IItem>>> toFlexible(){
        return list -> Single.fromCallable(() -> {
            List<IItem> result = new ArrayList<>();
            for(Object item : list){
                if(item instanceof IItem) {
                    result.add((IItem) item);
                } else throw new UnsupportedOperationException();
            }
            return result;
        });
    }

}
