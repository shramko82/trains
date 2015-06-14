package ua.shramko.trains.core;

import ua.shramko.trains.handlers.RoutesHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Trip {
    RoutesHandler routes;
    List<Town> towns;
    int distance;

    public Trip(Trip trip) {
        this.routes = trip.routes;
        this.towns = new ArrayList<>(trip.towns);
        this.distance = trip.distance;
    }

    public Trip(RoutesHandler routes, String fullRoute) {
        this.routes = routes;
        fillByRepresentation(fullRoute);
    }

    public int getDistance() {
        return distance;
    }

    private void fillByRepresentation(String fullRoute) {
        this.towns = new ArrayList<>();
        this.distance = 0;
        String[] townArray = fullRoute.split("-");
        Town nextTown = routes.getTown(townArray[0]);
        if (nextTown == null) {
            this.distance = 0;
            return;
        }
        this.towns.add(nextTown);
        for (int i=1; i<townArray.length; i++) {
            nextTown = routes.getTown(townArray[i]);
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

    public Trip addDestination(Town town) {
        Trip newTrip = new Trip(this);
        newTrip.addNextTown(town);
        return newTrip;
    }

}
