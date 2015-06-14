package ua.shramko.trains.finders;

import ua.shramko.trains.core.Town;
import ua.shramko.trains.core.Trip;
import ua.shramko.trains.handlers.RoutesHandler;

import java.util.HashSet;
import java.util.Set;

public class ShortestDistanceFinder implements Finder {
    RoutesHandler routes;
    String from;
    String to;
    int shorterDistance;

    public ShortestDistanceFinder(RoutesHandler routes, String from, String to) {
        this.routes = routes;
        this.from = from;
        this.to = to;
        shorterDistance = Integer.MAX_VALUE;
    }

    public int calculate() {
        Set<Trip> trips = new HashSet<>();
        trips.add(new Trip(routes,from));
        Set<Town> currentTowns = new HashSet<>();
        currentTowns.add(routes.getTown(from));
        while(true) {
            Set<Town> destinations = new HashSet<>();
            Set<Trip> newTrips = new HashSet<>();
            for (Town currentTown : currentTowns) {
                Set<Town> currentDestinations = currentTown.getDestinations();
                for (Town currentDestination : currentDestinations) {
                    destinations.add(currentDestination);
                    addTrips(trips, newTrips, currentTown, currentDestination);
                }
            }
            trips = newTrips;
            currentTowns = destinations;
            int minDistanceAtThisScope = getMinDistance(newTrips);
            if (minDistanceAtThisScope >= shorterDistance) {
                break;
            }
        }

        return shorterDistance;
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

    private int getMinDistance(Set<Trip> trips) {
        int minDistance = Integer.MAX_VALUE;
        for (Trip trip : trips) {
            minDistance = Math.min(minDistance, trip.getDistance());
            if (trip.getLastTown() == routes.getTown(to)) {
                shorterDistance = Math.min(shorterDistance,trip.getDistance());
            }
        }
        return minDistance;
    }
}
