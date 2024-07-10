package org.serverSide.components.singletonLists;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.serverSide.components.factories.SingletonListsFactory;
import org.serverSide.components.units.Booking;
import org.serverSide.components.units.Unit;
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

    public void deductBookingNumber(Booking b){
        this.list.remove(b);
        b.deductBookedTicketsNum();
        BookingList.getInstance().add(b);
    }


}
