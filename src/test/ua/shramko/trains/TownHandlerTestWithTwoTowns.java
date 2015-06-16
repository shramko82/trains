package ua.shramko.trains;

import org.junit.BeforeClass;
import org.junit.Test;
import ua.shramko.trains.core.Route;
import ua.shramko.trains.core.Town;
import ua.shramko.trains.handlers.RoutesHandler;
import ua.shramko.trains.services.TownService;

import static org.junit.Assert.assertEquals;

public class TownHandlerTestWithTwoTowns {
    static String inputString;
    static RoutesHandler routesHandler;
    static TownService townService;

    @BeforeClass
    public static void initialize() {
        inputString = "AB5";
        routesHandler = new RoutesHandler();
        townService = new TownService();
        townService.fillRoutes(inputString);
    }

    @Test
     public void shouldBeTwoTownsInTownsMap() {
        townService.getTowns();

        int expectedMapSize = 2;
        int actualMapSize = townService.getTowns().size();

        assertEquals(expectedMapSize, actualMapSize);
    }

    @Test
    public void shouldBeOneRoute() {
        int expectedRoutes = 1;
        int actualRoutes = 0;
        for (Town town : townService.getTowns().values()) {
            actualRoutes+=town.getRoutes().size();
        }
        townService.getTowns().size();

        assertEquals(expectedRoutes, actualRoutes);
    }

    @Test
    public void townA_ShouldHaveOneRoute() {
        int expectedSize = 1;
        int actualSize = townService.getTown("A").getRoutes().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void townA_DestinationB_ShouldBeEquals_TownB() {
        Town expectedTown = townService.getTown("B");
        Town actualTown = townService.getTown("A").getRoute(expectedTown).getDestination();

        assertEquals(expectedTown, actualTown);
    }

    @Test
    public void townA_RouteC_ShouldBeEquals_Null() {
        Route expectedRoute = null;
        Route actualRoute = townService.getTown("A").getRoute(townService.getTown("C"));

        assertEquals(expectedRoute, actualRoute);
    }

    @Test
    public void distance_A_B_ShouldEquals_5() {
        int expectedDistance = 5;
        int actualDistance = routesHandler.getDistance(townService, "A-B");

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void distance_A_C_ShouldEquals_0() {
        int expectedDistance = 0;
        int actualDistance = routesHandler.getDistance(townService, "A-C");

        assertEquals(expectedDistance, actualDistance);
    }

}
