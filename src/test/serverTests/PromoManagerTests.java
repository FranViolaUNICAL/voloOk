package serverTests;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;
import org.serverSide.components.singletonLists.FlightList;
import org.serverSide.components.units.Flight;
import org.serverSide.components.units.Promo;
import org.serverSide.grpc.PromoManager;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PromoManagerTests {

    @ParameterizedTest
    @DisplayName("Should find all airports in country")
    @ValueSource(strings = {"IT","FR","ES","US"})
    public void allAirportsInNation(String country) throws IOException {
        assertFalse(PromoManager.allAirportsInNation(country).isEmpty());
    }

    @ParameterizedTest
    @DisplayName("Should not find any airports")
    @ValueSource(strings = {"","ITALIA"})
    public void allAirportsInNationNegative(String country) throws IOException{
        assertTrue(PromoManager.allAirportsInNation(country).isEmpty());
    }

    @ParameterizedTest
    @DisplayName("Should find countries")
    @ValueSource(strings = {"LIN","CDG","SUF"})
    public void findCountry(String aita) throws IOException {
        assertNotNull(PromoManager.findCountry(aita));
    }
    @ParameterizedTest
    @DisplayName("Should not find countries")
    @ValueSource(strings = {"LINA","CDGS","SUFD"})
    public void findCountryNegative(String aita) throws IOException {
        assertNull(PromoManager.findCountry(aita));
    }
}
