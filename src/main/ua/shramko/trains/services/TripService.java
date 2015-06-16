package ua.shramko.trains.services;

import ua.shramko.trains.core.Route;
import ua.shramko.trains.core.Town;

import java.util.ArrayList;
import java.util.List;

public class TripService {
    TownService townService;
    List<Town> towns;
    int distance;

    public TripService(TripService trip) {
        this.townService = trip.townService;
        this.towns = new ArrayList<>(trip.towns);
        this.distance = trip.distance;
    }

    public TripService(TownService townService, String fullRoute) {
        this.townService = townService;
        fillByRepresentation(fullRoute);
    }

    public int getDistance() {
        return distance;
    }

    private void fillByRepresentation(String fullRoute) {
        this.towns = new ArrayList<>();
        this.distance = 0;
        String[] townArray = fullRoute.split("-");
        Town nextTown = townService.getTown(townArray[0]);
        if (nextTown == null) {
            this.distance = 0;
            return;
        }
        this.towns.add(nextTown);
        for (int i=1; i<townArray.length; i++) {
            nextTown = townService.getTown(townArray[i]);
            if (!addNextTown(nextTown)) {
                this.distance = 0;
                return;
            }
        }
    }

    public boolean addNextTown(Town nextTown) {
        Town lastTown = getLastTown();
        Route route = lastTown.getRoute(nextTown);
        if (route == null) {
            return false;
        } else {
            this.distance += route.getDistance();
            this.towns.add(nextTown);
            return true;
        }
    }

    public Town getLastTown() {
        return towns.get(towns.size()-1);
    }

    public TripService getNewTripWithNewDestination(Town town) {
        TripService newTrip = new TripService(this);
        newTrip.addNextTown(town);
        return newTrip;
    }

}
