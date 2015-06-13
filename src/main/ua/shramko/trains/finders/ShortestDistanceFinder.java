package ua.shramko.trains.finders;

import ua.shramko.trains.core.Town;
import ua.shramko.trains.handlers.RoutesHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShortestDistanceFinder implements Finder {
    RoutesHandler routes;
    String from;
    String to;

    public ShortestDistanceFinder(RoutesHandler routes, String from, String to) {
        this.routes = routes;
        this.from = from;
        this.to = to;
    }

    public int calculate() {
        int shorterDistance = Integer.MAX_VALUE;
        Map<String, Integer> routeDistances = new HashMap<>();
        routeDistances.put(from, 0);
        Set<Town> currentTowns = new HashSet<>();
        currentTowns.add(routes.getTown(from));
        while(true) {
            int minDistanceAtThisScope = Integer.MAX_VALUE;
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
                    if (destinationKey.equals(to)) {
                        shorterDistance = Math.min(shorterDistance,currentDistance);
                    }
                }
            }
            routeDistances = newRouteDistances;
            currentTowns = destinations;
            if (minDistanceAtThisScope >= shorterDistance) {
                break;
            }
        }

        return shorterDistance;
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
}
