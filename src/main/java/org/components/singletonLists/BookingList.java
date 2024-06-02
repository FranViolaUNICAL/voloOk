package org.components.singletonLists;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.components.factories.SingletonListsFactory;
import org.components.units.Unit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookingList extends SingletonListAbstract {
    private static BookingList instance;

    private BookingList(){
        super();
        try{
        list = SingletonListsFactory.createSingletonList("src/bookingDatabase.json","bookingList");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static BookingList getInstance(){
        if(instance == null){
            instance = new BookingList();
        }
        return instance;
    }
    @JsonProperty("bookingList")
    public synchronized List<Unit> getBookingList(){
        return new ArrayList<>(list);
    }
}
