package ua.shramko.trains.finders;

import ua.shramko.trains.core.Town;
import ua.shramko.trains.services.TownService;
import ua.shramko.trains.services.TripService;

import java.util.HashSet;
import java.util.Set;

public class ShortestDistanceFinder implements Finder {
    TownService towns;
    String from;
    String to;
    int shorterDistance;

    public ShortestDistanceFinder(TownService towns, String from, String to) {
        this.towns = towns;
        this.from = from;
        this.to = to;
        shorterDistance = Integer.MAX_VALUE;
    }

    public int calculate() {
        Set<TripService> trips = new HashSet<>();
        trips.add(new TripService(towns,from));
        Set<Town> currentTowns = new HashSet<>();
        currentTowns.add(towns.getTown(from));
        while(true) {
            Set<Town> destinations = new HashSet<>();
            Set<TripService> newTrips = new HashSet<>();
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

    private void addTrips(Set<TripService> trips, Set<TripService> newTrips, Town currentTown, Town currentDestination) {
        for (TripService trip : trips) {
            Town lastTown = trip.getLastTown();
            if (currentTown == lastTown) {
                TripService newTrip = trip.getNewTripWithNewDestination(currentDestination);
                newTrips.add(newTrip);
            }
        }
    }

    private int getMinDistance(Set<TripService> trips) {
        int minDistance = Integer.MAX_VALUE;
        for (TripService trip : trips) {
            minDistance = Math.min(minDistance, trip.getDistance());
            if (trip.getLastTown() == towns.getTown(to)) {
                shorterDistance = Math.min(shorterDistance,trip.getDistance());
            }
        }
        return minDistance;
    }
}
