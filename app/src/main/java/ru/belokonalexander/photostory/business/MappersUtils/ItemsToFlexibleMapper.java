package ru.belokonalexander.photostory.business.MappersUtils;

import com.mikepenz.fastadapter.IItem;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import ru.belokonalexander.photostory.Helpers.Logger;

/**
 * Created by Alexander on 02.06.2017.
 */

public class ItemsToFlexibleMapper {




    public Function<List, SingleSource<List<IItem>>> toFlexible(){
        return list -> Single.fromCallable(() -> {
            List<IItem> result = new ArrayList<>();
            for(Object item : list){
                Logger.logThis(" RETURN: " + result.size());
                if(item instanceof IItem) {
                    result.add((IItem) item);
                    /*if(item instanceof TopicHolderModel) {
                        result.add(new TopicHolderModelImmutable());
                    }*/
                } else throw new UnsupportedOperationException();
            }

            Logger.logThis(" RETURN: " + result.size());

            return result;
        });
    }

}
