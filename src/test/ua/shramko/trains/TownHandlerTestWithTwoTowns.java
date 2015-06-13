package ua.shramko.trains;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.shramko.trains.core.Route;
import ua.shramko.trains.core.Town;
import ua.shramko.trains.handlers.RoutesHandler;

import static org.junit.Assert.assertEquals;

public class TownHandlerTestWithTwoTowns {
    static String inputString;
    static RoutesHandler routesHandler;

    @BeforeClass
    public static void initialize() {
        inputString = "AB5";
        routesHandler = new RoutesHandler();
        routesHandler.fillRoutes(inputString);
    }

    @Test
     public void shouldBeTwoTownsInTownsMap() {
        routesHandler.getTowns();

        int expectedMapSize = 2;
        int actualMapSize = routesHandler.getTowns().size();

        assertEquals(expectedMapSize, actualMapSize);
    }

    @Test
    public void shouldBeOneRoute() {
        int expectedRoutes = 1;
        int actualRoutes = 0;
        for (Town town : routesHandler.getTowns().values()) {
            actualRoutes+=town.getRoutes().size();
        }
        routesHandler.getTowns().size();

        assertEquals(expectedRoutes, actualRoutes);
    }

    @Test
    public void townA_ShouldHaveOneRoute() {
        int expectedSize = 1;
        int actualSize = routesHandler.getTown("A").getRoutes().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void townA_DestinationB_ShouldBeEquals_TownB() {
        Town expectedTown = routesHandler.getTown("B");
        Town actualTown = routesHandler.getTown("A").getRoute("B").getDestination();

        assertEquals(expectedTown, actualTown);
    }

    @Test
    public void townA_RouteC_ShouldBeEquals_Null() {
        Route expectedRoute = null;
        Route actualRoute = routesHandler.getTown("A").getRoute("C");

        assertEquals(expectedRoute, actualRoute);
    }

    @Test
    public void distance_A_B_ShouldEquals_5() {
        int expectedDistance = 5;
        int actualDistance = routesHandler.getDistance("A-B");

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void distance_A_C_ShouldEquals_0() {
        int expectedDistance = 0;
        int actualDistance = routesHandler.getDistance("A-C");

        assertEquals(expectedDistance, actualDistance);
    }

}
