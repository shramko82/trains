package ua.shramko.trains.handlers;

import ua.shramko.trains.enums.CompareTypes;
import ua.shramko.trains.enums.LimitsBy;
import ua.shramko.trains.finders.NumberOfTripsFinder;
import ua.shramko.trains.finders.ShortestDistanceFinder;
import ua.shramko.trains.services.TownService;
import ua.shramko.trains.services.TripService;

public class RoutesHandler {

    public int getDistance(TownService townService, String fullRoute) {
        return new TripService(townService, fullRoute).getDistance();
    }

    public String getFormattedDistance(TownService townService, String fullRoute) {
        int distance = getDistance(townService, fullRoute);
        return (distance > 0) ? String.valueOf(distance) : "NO SUCH ROUTE";
    }

    public int calculateShortestRoute(TownService townService, String from, String to) {
        return new ShortestDistanceFinder(townService, from, to).calculate();
    }

    public int getNumberOfTrips(TownService townService, String from, String to, int limit, CompareTypes limitType, LimitsBy limitBy) {
        return new NumberOfTripsFinder(townService, from, to, limit, limitType, limitBy).calculate();
    }
}
