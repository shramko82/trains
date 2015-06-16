package ua.shramko.trains.finders;

import ua.shramko.trains.core.Town;
import ua.shramko.trains.services.TownService;
import ua.shramko.trains.services.TripService;
import ua.shramko.trains.enums.CompareTypes;
import ua.shramko.trains.enums.LimitsBy;

import java.util.HashSet;
import java.util.Set;

public class NumberOfTripsFinder implements Finder {
    TownService townService;
    String from;
    String to;
    int limit;
    CompareTypes limitType;
    LimitsBy limitBy;

    public NumberOfTripsFinder(TownService townService, String from, String to, int limit, CompareTypes limitType, LimitsBy limitBy) {
        this.townService = townService;
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
        currentTowns.add(townService.getTown(from));
        Set<TripService> trips = new HashSet<>();
        trips.add(new TripService(townService,from));
        while(true) {
            numberOfStops++;
            Set<Town> destinations = new HashSet<>();
            Set<TripService> newTrips = new HashSet<>();

            for (Town currentTown : currentTowns) {
                Set<Town> currentDestinations = currentTown.getDestinations();
                for (Town currentDestination : currentDestinations) {
                    destinations.add(currentDestination);
                    addTrips(trips, newTrips, currentTown, currentDestination);
                }
            }
            currentTowns = destinations;
            trips = newTrips;


            if (limitBy == LimitsBy.DISTANCE) {
                numberOfTrips += calculateTrips(newTrips, townService.getTown(to), limit, limitType);
            }
            if (limitBy == LimitsBy.STOPS && checkForIterate(numberOfStops, limit, limitType)) {
                numberOfTrips += getTripsEndsWith(newTrips, townService.getTown(to));
            }
            int minDistanceAtThisScope = getMinDistance(newTrips);

            int valueForBreakChecking = (limitBy == LimitsBy.DISTANCE) ? minDistanceAtThisScope : numberOfStops;
            if (checkForBreak(valueForBreakChecking, limit, limitType)) {
                break;
            }
        }

        return numberOfTrips;
    }

    private int getMinDistance(Set<TripService> trips) {
        int minDistance = Integer.MAX_VALUE;
        for (TripService trip : trips) {
            minDistance = Math.min(minDistance, trip.getDistance());
        }
        return minDistance;
    }

    private void addTrips(Set<TripService> trips, Set<TripService> newTrips, Town currentTown, Town currentDestination) {
        for (TripService trip : trips) {
            Town lastTown = trip.getLastTown();
            if (currentTown == lastTown) {
                TripService newTrip = trip.getNewTripWithNewDestination(currentDestination);
                newTrips.add(newTrip);
            }
        }
    }

    private int calculateTrips(Set<TripService> trips, Town destination, int limit, CompareTypes limitType) {
        int result = 0;
        for (TripService trip : trips) {
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

    private int getTripsEndsWith(Set<TripService> trips, Town destination) {
        int number = 0;
        for (TripService trip : trips) {
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
