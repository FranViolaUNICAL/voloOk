package serverTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.serverSide.components.factories.UserServicesFactory;
import user.UserServices;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class UserServicesResponseTests {
    @ParameterizedTest
    @DisplayName("Should create notification response for registered users")
    @ValueSource(booleans = {true})
    public void createNotifyClientResponse(boolean isRegistered){
        UserServices.NotifyClientResponse r = UserServicesFactory.createNotifyClientResponse(isRegistered);
        assertFalse(r.getPromoList().isEmpty());
    }

    @ParameterizedTest
    @DisplayName("Should create notification response for unregistered users")
    @ValueSource(booleans = {false})
    public void createNotifyClientResponseUnregistered(boolean isRegistered){
        UserServices.NotifyClientResponse r = UserServicesFactory.createNotifyClientResponse(isRegistered);
        assertTrue(r.getPromoList().isEmpty());
    }

    @ParameterizedTest
    @DisplayName("Should fetch all tickets for users and return true success")
    @ValueSource(strings = {"franviola98@gmail.com","chiaradesantis@gmail.com"})
    public void createFetchAllTickets(String email){
        assertTrue(UserServicesFactory.createFetchAllTicketsResponse(email).getSuccess());
    }

    @ParameterizedTest
    @DisplayName("Should fetch no tickets and return false success. Means users did not buy any tickets yet.")
    @ValueSource(strings = {"franco@gmail.com","giovannimazzei@gmail.com"})
    public void createFetchAllTicketsNegative(String email){
        assertFalse(UserServicesFactory.createFetchAllTicketsResponse(email).getSuccess());
    }

    @ParameterizedTest
    @DisplayName("Should fetch all bookings")
    @ValueSource(strings = {"franviola98@gmail.com","chiaradesantis@gmail.com"})
    public void createFetchAllBookingsResponse(String email){
        assertTrue(UserServicesFactory.createFetchAllBookingsResponse(email).getSuccess());
    }
    @ParameterizedTest
    @DisplayName("Should fetch no bookings")
    @ValueSource(strings = {"franviola9@gmail.com","chiaradddddesantis@gmail.com"})
    public void createFetchAllBookingsResponseNegative(String email){
        assertFalse(UserServicesFactory.createFetchAllBookingsResponse(email).getSuccess());
    }

    @Test
    @DisplayName("Should find promo")
    public void checkPromoResponse(){
        assertTrue(UserServicesFactory.createPromoCheckResponse("FRANCIA20","FR","LINCDG1309",true).getSuccess());
    }

    @ParameterizedTest
    @DisplayName("Should not succeed to check Promo")
    @MethodSource("provideCheckPromoParameters")
    public void checkPromoNegative(String code, String country, String flightId, boolean fidelity){
        assertFalse(UserServicesFactory.createPromoCheckResponse(code,country,flightId,fidelity).getSuccess());
    }

    private static Stream<Arguments> provideCheckPromoParameters(){
        return Stream.of(
                Arguments.of("FRANCIA20","IT","LINCDG1309",true),
                Arguments.of("FRANCIA20","FR","LINCDG1309",false),
                Arguments.of("FRANCIA30","FR","LINCDG1309",true),
                Arguments.of("FRANCIA20","FR","SUFLIN1308",true)
        );
    }

    @ParameterizedTest
    @DisplayName("Should find flights")
    @ValueSource(strings = {"LINCDG1309","SUFLIN1308"})
    public void createSearchFlightsResponse(String flightID){
        assertTrue(UserServicesFactory.createSearchFlightResponse(flightID).getSuccess());
    }

    @ParameterizedTest
    @DisplayName("Should not find flights")
    @ValueSource(strings = {"LINCDG1309sss","SUFLIN1308dddd"})
    public void createSearchFlightsResponseNegative(String flightID){
        assertFalse(UserServicesFactory.createSearchFlightResponse(flightID).getSuccess());
    }

}
