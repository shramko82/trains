package ua.shramko.trains;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TownHandlerTestWithFiveTowns {
    static String inputString;
    static TownHandler townHandler;

    @BeforeClass
    public static void initialize() {
        inputString = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        townHandler = new TownHandler();
        townHandler.fillRoutes(inputString);
    }

    @Test
     public void shouldBeFiveTownsInTownsMap() {
        int expectedMapSize = 5;
        int actualMapSize = townHandler.getTowns().size();

        assertEquals(expectedMapSize, actualMapSize);
    }

    @Test
    public void shouldBeNineRoutes() {
        int expectedRoutes = 9;
        int actualRoutes = 0;
        for (Town town : townHandler.getTowns().values()) {
            actualRoutes+=town.getRoutes().size();
        }
        townHandler.getTowns().size();

        assertEquals(expectedRoutes, actualRoutes);
    }

    @Test
    public void TownA_ShouldHaveTreeRoute() {
        int expectedSize = 3;
        int actualSize = townHandler.getTown("A").getRoutes().size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void TownA_DestinationB_ShouldBeEquals_TownB() {
        Town expectedTown = townHandler.getTown("B");
        Town actualTown = townHandler.getTown("A").getRoute("B").getDestination();

        assertEquals(expectedTown, actualTown);
    }

    @Test
    public void TownC_DestinationE_ShouldBeEquals_TownE() {
        Town expectedTown = townHandler.getTown("E");
        Town actualTown = townHandler.getTown("C").getRoute("E").getDestination();

        assertEquals(expectedTown, actualTown);
    }

    @Test
    public void distance_A_B_C_ShouldEquals_9() {
        int expectedDistance = 9;
        int actualDistance = townHandler.getDistance("A-B-C");

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
    public void distance_A_B_C_D_ShouldEquals_17() {
        int expectedDistance = 17;
        int actualDistance = townHandler.getDistance("A-B-C-D");

        assertEquals(expectedDistance, actualDistance);
    }

    @Test
     public void numberOfTripsFrom_A_To_B_withExactStopsOf_1_ShouldEquals_1() {
        int expected = 1;
        int actual = townHandler.getNumberOfTrips("A", "B", 1, LimitTypes.EQUALS, LimitsBy.STOPS);

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfTripsFrom_A_To_C_withExactStopsOf_2_ShouldEquals_2() {
        int expected = 2;
        int actual = townHandler.getNumberOfTrips("A", "C", 2, LimitTypes.EQUALS, LimitsBy.STOPS);

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfTripsFrom_C_To_C_withMaximumStopsOf_3_ShouldEquals_2() {
        int expected = 2;
        int actual = townHandler.getNumberOfTrips("C", "C", 3, LimitTypes.LESS_OR_EQUALS, LimitsBy.STOPS);

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfTripsFrom_A_To_C_withExactStopsOf_4_ShouldEquals_3() {
        int expected = 3;
        int actual = townHandler.getNumberOfTrips("A", "C", 4, LimitTypes.EQUALS, LimitsBy.STOPS);

        assertEquals(expected, actual);
    }

    @Test
    public void shortestRouteFrom_A_To_B_ShouldEquals_5() {
        int expected = 5;
        int actual = townHandler.calculateShortestRoute("A", "B");

        assertEquals(expected, actual);
    }

    @Test
    public void shortestRouteFrom_A_To_C_ShouldEquals_9() {
        int expected = 9;
        int actual = townHandler.calculateShortestRoute("A", "C");

        assertEquals(expected, actual);
    }

    @Test
    public void shortestRouteFrom_B_To_B_ShouldEquals_9() {
        int expected = 9;
        int actual = townHandler.calculateShortestRoute("B", "B");

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfRoutesFrom_A_To_B_WithDistanceLessThan_15_ShouldEquals_4() {
        int expected = 4;
        int actual = townHandler.getNumberOfTrips("A", "B", 15, LimitTypes.LESS, LimitsBy.DISTANCE);

        assertEquals(expected, actual);
    }

    @Test
    public void numberOfRoutesFrom_C_To_C_WithDistanceLessThan_30_ShouldEquals_7() {
        int expected = 7;
        int actual = townHandler.getNumberOfTrips("C", "C", 30, LimitTypes.LESS, LimitsBy.DISTANCE);

        assertEquals(expected, actual);
    }
}
