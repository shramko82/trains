package ua.shramko.trains.finders;

import ua.shramko.trains.core.Town;
import ua.shramko.trains.enums.CompareTypes;
import ua.shramko.trains.enums.LimitsBy;
import ua.shramko.trains.handlers.RoutesHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NumberOfTripsFinder implements Finder {
    RoutesHandler routes;
    String from;
    String to;
    int limit;
    CompareTypes limitType;
    LimitsBy limitBy;

    public NumberOfTripsFinder(RoutesHandler routes, String from, String to, int limit, CompareTypes limitType, LimitsBy limitBy) {
        this.routes = routes;
        this.from = from;
        this.to = to;
        this.limit = limit;
        this.limitType = limitType;
        this.limitBy = limitBy;
    }

    public int calculate() {
        int numberOfStops = 0;
        int numberOfTrips = 0;
        Set<Town> currentTowns = new HashSet<>();
        currentTowns.add(routes.getTown(from));
        Map<String, Integer> routeDistances = new HashMap<>();
        routeDistances.put(from, 0);
        while(true) {
            int minDistanceAtThisScope = Integer.MAX_VALUE;
            numberOfStops++;
            Set<Town> destinations = new HashSet<>();
            Map<String, Integer> newRouteDistances = new HashMap<>();

            for (Town currentTown : currentTowns) {
                Set<Town> currentDestinations = currentTown.getDestinations();
                for (Town currentDestination : currentDestinations) {
                    destinations.add(currentDestination);
                    String currentKey = currentTown.getKey();
                    String destinationKey = currentDestination.getKey();
                    int currentDistance = addTripsToMap(routeDistances, newRouteDistances, currentKey, destinationKey);
                    minDistanceAtThisScope = Math.min(minDistanceAtThisScope,currentDistance);
                }
            }
            if (limitBy == LimitsBy.DISTANCE) {
                numberOfTrips += calculateTrips(newRouteDistances, to, limit, limitType);
            }
            if (limitBy == LimitsBy.STOPS && checkForIterate(numberOfStops, limit, limitType)) {
                numberOfTrips += getTripsFromSetEndsWith(newRouteDistances.keySet(), to);
            }

            currentTowns = destinations;
            routeDistances = newRouteDistances;
            int valueForBreakChecking = (limitBy == LimitsBy.DISTANCE) ? minDistanceAtThisScope : numberOfStops;
            if (checkForBreak(valueForBreakChecking, limit, limitType)) {
                break;
            }
        }

        return numberOfTrips;
    }

    private boolean checkForIterate(int number, int limiter, CompareTypes stopLimitType) {
        if ((number <= limiter && stopLimitType == CompareTypes.LESS_OR_EQUALS)
                || (number == limiter && stopLimitType == CompareTypes.EQUALS)
                || (number < limiter && stopLimitType == CompareTypes.LESS)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkForBreak(int number, int limit, CompareTypes stopLimitType) {
        if (number >= limit &&
                (stopLimitType == CompareTypes.EQUALS ||stopLimitType == CompareTypes.LESS_OR_EQUALS )) {
            return true;
        } else if (number >= limit-1 && stopLimitType == CompareTypes.LESS) {
            return true;
        } else {
            return false;
        }
    }

    private int addTripsToMap(Map<String, Integer> routeDistances, Map<String, Integer> newRouteDistances, String currentKey, String destinationKey) {
        int minDistance = Integer.MAX_VALUE;
        for (String trip : routeDistances.keySet()) {
            String lastTownKey = trip.substring(trip.length()-1);
            if (currentKey.equals(lastTownKey)) {
                String newTrip = trip.concat(destinationKey);
                int distance = routeDistances.get(trip) + routes.getTown(lastTownKey).getRoute(destinationKey).getDistance();
                newRouteDistances.put(newTrip,distance);
                minDistance = Math.min(minDistance,distance);
            }
        }
        return minDistance;
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

    private int calculateTrips(Map<String, Integer> routeDistances, String currentKey, int limit, CompareTypes limitType) {
        int result = 0;
        for (String trip : routeDistances.keySet()) {
            String lastTownKey = trip.substring(trip.length()-1);
            if (currentKey.equals(lastTownKey)) {
                int distance = routeDistances.get(trip);
                if (checkForIterate(distance,limit,limitType)) {
                    result++;
                }
            }
        }

        return result;
    }
}
