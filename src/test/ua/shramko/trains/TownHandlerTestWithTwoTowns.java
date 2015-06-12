package ua.shramko.trains;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TownHandlerTestWithTwoTowns {
    static String inputString;
    static TownHandler townHandler;

    @BeforeClass
    public static void initialize() {
        inputString = "AB5";
        townHandler = new TownHandler();
        townHandler.fillRoutes(inputString);
    }

    @Test
     public void shouldBeTwoTownsInTownsMap() {
        townHandler.getTowns();

        int expectedMapSize = 2;
        int actualMapSize = townHandler.getTowns().size();

        assertEquals(expectedMapSize, actualMapSize);
    }

    @Test
    public void shouldBeOneRoute() {
        int expectedRoutes = 1;
        int actualRoutes = 0;
        for (Town town : townHandler.getTowns().values()) {
            actualRoutes+=town.getRoutes().size();
        }
        townHandler.getTowns().size();

        assertEquals(expectedRoutes, actualRoutes);
    }

    @Test
    public void townA_ShouldHaveOneRoute() {
        int expectedSize = 1;
        int actualSize = townHandler.getTown("A").getRoutes().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void townA_DestinationB_ShouldBeEquals_TownB() {
        Town expectedTown = townHandler.getTown("B");
        Town actualTown = townHandler.getTown("A").getRoute("B").getDestination();

        assertEquals(expectedTown, actualTown);
    }

    @Test
    public void townA_RouteC_ShouldBeEquals_Null() {
        Route expectedRoute = null;
        Route actualRoute = townHandler.getTown("A").getRoute("C");

        assertEquals(expectedRoute, actualRoute);
    }

    @Test
    public void distance_A_B_ShouldEquals_5() {
        int expectedDistance = 5;
        int actualDistance = townHandler.getDistance("A-B");

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void distance_A_C_ShouldEquals_0() {
        int expectedDistance = 0;
        int actualDistance = townHandler.getDistance("A-C");

        assertEquals(expectedDistance, actualDistance);
    }

}
