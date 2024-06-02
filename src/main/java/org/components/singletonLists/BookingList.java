package org.components.singletonLists;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.components.factories.SingletonListsFactory;
import org.components.observers.AbstractSubject;
import org.components.observers.Observer;
import org.components.singletons.ObjectMapperSingleton;
import org.components.units.Booking;
import org.components.units.Ticket;
import org.components.units.Unit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingList extends AbstractSubject implements SingletonList {
    List<Observer> observers;
    List<Unit> bookingList;
    private static BookingList instance;

    private BookingList(){
        try{
        observers = new ArrayList<>();
        bookingList = SingletonListsFactory.createSingletonList("src/bookingDatabase.json","bookingList");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
