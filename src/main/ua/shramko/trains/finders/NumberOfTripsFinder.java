package ua.shramko.trains.finders;

import ua.shramko.trains.core.Town;
import ua.shramko.trains.core.Trip;
import ua.shramko.trains.enums.CompareTypes;
import ua.shramko.trains.enums.LimitsBy;
import ua.shramko.trains.handlers.RoutesHandler;

import java.util.HashSet;
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
        Set<Trip> trips = new HashSet<>();
        trips.add(new Trip(routes,from));
        while(true) {
            numberOfStops++;
            Set<Town> destinations = new HashSet<>();
            Set<Trip> newTrips = new HashSet<>();

            for (Town currentTown : currentTowns) {
                Set<Town> currentDestinations = currentTown.getDestinations();
                for (Town currentDestination : currentDestinations) {
                    destinations.add(currentDestination);
                    addTrips(trips, newTrips, currentTown, currentDestination);
                }
            }
            if (limitBy == LimitsBy.DISTANCE) {
                numberOfTrips += calculateTrips(newTrips, routes.getTown(to), limit, limitType);
            }
            if (limitBy == LimitsBy.STOPS && checkForIterate(numberOfStops, limit, limitType)) {
                numberOfTrips += getTripsEndsWith(newTrips, routes.getTown(to));
            }
            int minDistanceAtThisScope = getMinDistance(newTrips);
            currentTowns = destinations;
            trips = newTrips;
            int valueForBreakChecking = (limitBy == LimitsBy.DISTANCE) ? minDistanceAtThisScope : numberOfStops;
            if (checkForBreak(valueForBreakChecking, limit, limitType)) {
                break;
            }
        }

        return numberOfTrips;
    }

    private int getMinDistance(Set<Trip> trips) {
        int minDistance = Integer.MAX_VALUE;
        for (Trip trip : trips) {
            minDistance = Math.min(minDistance, trip.getDistance());
        }
        return minDistance;
    }

    private void addTrips(Set<Trip> trips, Set<Trip> newTrips, Town currentTown, Town currentDestination) {
        for (Trip trip : trips) {
            Town lastTown = trip.getLastTown();
            if (currentTown == lastTown) {
                Trip newTrip = trip.addDestination(currentDestination);
                newTrips.add(newTrip);
            }
        }
    }

    private int calculateTrips(Set<Trip> trips, Town destination, int limit, CompareTypes limitType) {
        int result = 0;
        for (Trip trip : trips) {
            Town lastTown = trip.getLastTown();
            if (lastTown == destination) {
                int distance = trip.getDistance();
                if (checkForIterate(distance,limit,limitType)) {
                    result++;
                }
            }
        }

        return result;
    }

    private int getTripsEndsWith(Set<Trip> trips, Town destination) {
        int number = 0;
        for (Trip trip : trips) {
            Town lastTown = trip.getLastTown();
            if (lastTown == destination) {
                number++;
            }
        }

        return number;
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

}
