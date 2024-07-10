package serverTests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;
import org.serverSide.components.singletonLists.FlightList;
import org.serverSide.components.units.Flight;

import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FlightListTests {
    private FlightList f;

    @BeforeEach
    public void setup(){
        f = FlightList.getInstance();
    }

    @Test
    @DisplayName("Should find two flights matching parameters")
    public void checkAvailabilityOriginDestination(){
        List<Flight> l = f.checkAvailabilityOriginDestination("SUF","LIN");
        assertEquals(3,l.size());
        assertFalse(l.isEmpty());
    }

    @Test
    @DisplayName("Should not find any flights matching parameters")
    public void checkAvailabilityOriginDestinationNegative(){
        List<Flight> l = f.checkAvailabilityOriginDestination("CDG","LIN");
        assertTrue(l.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"13/08/2024 00:00:00","15/07/2024 00:00:00"})
    @DisplayName("Should find at least one flight available.")
    public void checkAvailabilityWithDateParameterized(String date) throws ParseException {
        List<Flight> l = f.checkAvailabilityFromDate("SUF","LIN",date);
        assertFalse(l.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"13/08/2025 00:00:00","15/07/2025 00:00:00"})
    @DisplayName("Should not find any flights within this departure date")
    public void checkAvailabilityFromDateNegative(String date) throws ParseException{
        List<Flight> l = f.checkAvailabilityFromDate("SUF","LIN",date);
        assertTrue(l.isEmpty());
    }

    @Test
    @DisplayName("Should say that flight already happened.")
    public void hasFlightHappened(){
        assertTrue(f.hasFlightHappened("SUFLIN1506"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"SUFLIN1507","SUFLIN1308"})
    @DisplayName("Should say that flights have not happened")
    public void hasFlightHappenedNegative(String flightID){
        assertFalse(f.hasFlightHappened(flightID));
    }


}
