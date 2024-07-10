package serverTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.serverSide.components.observers.JsonManagerObs;

import java.io.IOException;
import java.text.ParseException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JsonManagerObsTests {

    @ParameterizedTest
    @DisplayName("Should find all emails")
    @ValueSource(strings = {"franviola98@gmail.com","chiaradesantis@gmail.com"})
    public void checkJsonForEmail(String email) throws IOException {
        assertFalse(JsonManagerObs.checkJsonForEmail(email));
    }

    @ParameterizedTest
    @DisplayName("Should not find all emails")
    @NullAndEmptySource
    @ValueSource(strings = {"prova1","prova2"})
    public void checkJsonForEmailNegative(String email) throws IOException{
        assertTrue(JsonManagerObs.checkJsonForEmail(email));
    }

    @ParameterizedTest
    @DisplayName("Should return a user")
    @MethodSource("provideCredentials")
    public void checkCredentials(String email, String password) throws IOException {
        assertNotNull(JsonManagerObs.checkCredentials(email, password));
    }

    private static Stream<Arguments> provideCredentials(){
        return Stream.of(
                Arguments.of("franviola98@gmail.com","palmi24"),
                Arguments.of("chiaradesantis@gmail.com","chiarasonoio")
        );
    }

    @ParameterizedTest
    @DisplayName("Should return null")
    @MethodSource("provideWrongCredentials")
    public void checkCredentialsNegative(String email, String password) throws IOException{
        assertNull(JsonManagerObs.checkCredentials(email,password));
    }
    private static Stream<Arguments> provideWrongCredentials(){
        return Stream.of(
                Arguments.of("nonsonounemail","palmi24"),
                Arguments.of("chiaradesantis@gmail.com","passworderrata"),
                Arguments.of("","")
        );
    }

    @ParameterizedTest
    @DisplayName("Should allow to purchase ticket")
    @ValueSource(strings = {"emailprova@gmail.net","chiaradesantis@gmail.com","io@gmail.it","io@gmail.org"})
    public void checkForTicketPurchase(String email) throws IOException {
        assertTrue(JsonManagerObs.checkForTicketPurchase("SUFLIN1308","79927398713",email));
    }

    @ParameterizedTest
    @DisplayName("Should not allow to purchase ticket")
    @MethodSource("provideWrongPurchaseCredentials")
    public void checkForTicketPurchaseNegativeEmail(String flightId, String cardN, String email) throws IOException{
        assertFalse(JsonManagerObs.checkForTicketPurchase(flightId, cardN, email));
    }

    private static Stream<Arguments> provideWrongPurchaseCredentials(){
        return Stream.of(
                Arguments.of("NOVALID","79927398713","io@gmail.com"),
                Arguments.of("SUFLIN1308","111","io@gmail.com"),
                Arguments.of("SUFLIN1308","79927398713","notavalidemail@."),
                Arguments.of("SUFLING1308","79927398713","notavalidemail@.uk")
        );
    }

    @ParameterizedTest
    @DisplayName("Should find that users have fidelity")
    @ValueSource(strings = {"franviola98@gmail.com","chiaradesantis@gmail.com"})
    public void checkForFidelity(String email) throws IOException {
        assertTrue(JsonManagerObs.checkForFidelity(email,"SUFLIN1308"));
    }

    @ParameterizedTest
    @DisplayName("Should find that users do not have fidelity")
    @ValueSource(strings = {"nouserfound","checkcheckcheck@gmail.com"})
    public void checkForFidelityNegative(String email) throws IOException{
        assertFalse(JsonManagerObs.checkForFidelity(email, "SUFLIN1308"));
    }

    @ParameterizedTest
    @DisplayName("Should find possible new flights")
    @ValueSource(strings = {"13/08/2024 00:00:00","15/07/2024 00:00:00"})
    public void isDateChangePossible(String date) throws ParseException {
        assertFalse(JsonManagerObs.isDateChangePossible("YRPPP7EM",date).isEmpty());
    }

    @ParameterizedTest
    @DisplayName("Should not find any possible new flights")
    @ValueSource(strings = {"13/08/2025 00:00:00","15/07/2026 00:00:00"})
    public void isDateChangePossibleNegative(String date) throws ParseException {
        assertTrue(JsonManagerObs.isDateChangePossible("YRPPP7EM",date).isEmpty());
    }
}
