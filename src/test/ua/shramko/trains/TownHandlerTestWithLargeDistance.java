package ua.shramko.trains;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.shramko.trains.enums.CompareTypes;
import ua.shramko.trains.enums.LimitsBy;
import ua.shramko.trains.handlers.RoutesHandler;
import ua.shramko.trains.services.TownService;

import static org.junit.Assert.assertEquals;

public class TownHandlerTestWithLargeDistance {
    static String inputString;
    static RoutesHandler routesHandler;
    static TownService townService;

    @BeforeClass
    public static void initialize() {
        inputString = "AB90, BC250, AC500";
        routesHandler = new RoutesHandler();
        townService = new TownService();
        townService.fillRoutes(inputString);
    }


    @Test
    public void distance_A_B_ShouldEquals_90() {
        int expectedDistance = 90;
        int actualDistance = routesHandler.getDistance(townService, "A-B");

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void distance_A_B_C_ShouldEquals_340() {
        int expectedDistance = 340;
        int actualDistance = routesHandler.getDistance(townService, "A-B-C");

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void shortestRouteFrom_A_To_C_ShouldEquals_340() {
        int expected = 340;
        int actual = routesHandler.calculateShortestRoute(townService, "A", "C");

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfTripsFrom_A_To_C_withMaximumStopsOf_1_ShouldEquals_1() {
        int expected = 1;
        int actual = routesHandler.getNumberOfTrips(townService, "A", "C", 1, CompareTypes.LESS_OR_EQUALS, LimitsBy.STOPS);

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfTripsFrom_A_To_C_withMaximumStopsOf_2_ShouldEquals_2() {
        int expected = 2;
        int actual = routesHandler.getNumberOfTrips(townService, "A", "C", 2, CompareTypes.LESS_OR_EQUALS, LimitsBy.STOPS);

        assertEquals(expected, actual);
    }

}
