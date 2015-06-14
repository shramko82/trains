package ua.shramko.trains.handlers;

import ua.shramko.trains.core.Town;
import ua.shramko.trains.core.Trip;
import ua.shramko.trains.enums.CompareTypes;
import ua.shramko.trains.enums.LimitsBy;
import ua.shramko.trains.finders.NumberOfTripsFinder;
import ua.shramko.trains.finders.ShortestDistanceFinder;

import java.util.HashMap;
import java.util.Map;

public class RoutesHandler {
    Map<String, Town> towns;

    public RoutesHandler() {
        towns = new HashMap();
    }

    public Map<String, Town> getTowns() {
        return towns;
    }

    public Town getTown(String key) {
        return towns.get(key);
    }

    public void fillRoutes(String inputString) {
        String[] nodes = inputString.split(", ");
        for (int i=0; i<nodes.length; i++) {
            String node = nodes[i];
            String keyFrom = node.substring(0, 1);
            Town townFrom = towns.get(keyFrom);
            if (townFrom == null) {
                townFrom = new Town(keyFrom);
                towns.put(keyFrom,townFrom);
            }
            String keyTo = node.substring(1, 2);
            Town townTo = towns.get(keyTo);
            if (townTo == null) {
                townTo = new Town(keyTo);
                towns.put(keyTo,townTo);
            }
            int distance = Integer.parseInt(node.substring(2));
            townFrom.addRoute(townTo, distance);
        }
    }

    public int getDistance(String fullRoute) {
        return new Trip(this, fullRoute).getDistance();
    }

    public String getFormattedDistance(String fullRoute) {
        int distance = getDistance(fullRoute);
        return (distance > 0) ? String.valueOf(distance) : "NO SUCH ROUTE";
    }

    public int calculateShortestRoute(String from, String to) {
        return new ShortestDistanceFinder(this, from, to).calculate();
    }

    public int getNumberOfTrips(String from, String to, int limit, CompareTypes limitType, LimitsBy limitBy) {
        return new NumberOfTripsFinder(this, from, to, limit, limitType, limitBy).calculate();
    }
}
