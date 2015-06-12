package ua.shramko.trains;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TownHandler {
    Map<String, Town> towns;

    public TownHandler() {
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
            int distance = Integer.parseInt(node.substring(2,3));
            townFrom.addRoute(keyTo, townTo, distance);
        }
    }

    public int getDistance(String fullRoute) {
        int distance = 0;
        String[] towns = fullRoute.split("-");
        Town nextTown = getTown(towns[0]);
        if (nextTown == null) {
            return 0;
        }
        for (int i=1; i<towns.length; i++) {
            Route route = nextTown.getRoute(towns[i]);
            if (route == null) {
                return 0;
            } else {
                distance += route.getDistance();
                nextTown = getTown(towns[i]);
            }
        }
        return distance;
    }

    public String getFormattedDistance(String fullRoute) {
        int distance = getDistance(fullRoute);
        return (distance > 0) ? String.valueOf(distance) : "NO SUCH ROUTE";
    }

    public int getNumberOfTrips(String from, String to, int limiter, StopLimitTypes stopLimitType) {
        int numberOfStops = 0;
        int numberOfTrips = 0;
        Set<Town> currentTowns = new HashSet<Town>();
        currentTowns.add(getTown(from));
        Set<String> currentTrips = new HashSet<String>();
        currentTrips.add(from);
        while(true) {
            numberOfStops++;
            Set<Town> destinations = new HashSet<Town>();
            Set<String> newTrips = new HashSet<String>();
            for (Town currentTown : currentTowns) {
                Set<Town> currentDestinations = currentTown.getDestinations();
                for (Town currentDestination : currentDestinations) {
                    destinations.add(currentDestination);
                    String currentKey = currentTown.getKey();
                    String destinationKey = currentDestination.getKey();
                    addTripsToSet(currentTrips, newTrips, currentKey, destinationKey);
                    if (destinationKey.equals(to)) {
                        if (checkForIterate(numberOfStops, limiter, stopLimitType)) {
                            numberOfTrips += getTripsFromSetEndsWith(currentTrips, currentKey);
                        }
                    }
                }
            }
            currentTrips = newTrips;
            currentTowns = destinations;
            if (checkForBreak(numberOfStops, limiter, stopLimitType)) {
                break;
            }
        }

        return numberOfTrips;
    }

    private int getTripsFromSetEndsWith(Set<String> currentTrips, String currentKey) {
        int number = 0;
        for (String trip : currentTrips) {
            if (currentKey.equals(trip.substring(trip.length()-1))) {
                number++;
            }
        }

        return number;
    }

    private void addTripsToSet(Set<String> trips, Set<String> newTrips, String currentKey, String destinationKey) {
        for (String trip : trips) {
            if (currentKey.equals(trip.substring(trip.length()-1))) {
               newTrips.add(trip.concat(destinationKey));
            }
        }
    }

    private boolean checkForIterate(int numberOfStops, int limiter, StopLimitTypes stopLimitType) {
        if ((numberOfStops <= limiter && stopLimitType == StopLimitTypes.LESS_OR_EQUALS)
                || (numberOfStops == limiter && stopLimitType == StopLimitTypes.EQUALS)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkForBreak(int numberOfTrips, int limiter, StopLimitTypes stopLimitType) {
        if (numberOfTrips == limiter &&
                (stopLimitType == StopLimitTypes.EQUALS ||stopLimitType == StopLimitTypes.LESS_OR_EQUALS )) {
            return true;
        } else {
            return false;
        }
    }

    public int calculateShortestRoute(String from, String to) {


        return 0;
    }
}
