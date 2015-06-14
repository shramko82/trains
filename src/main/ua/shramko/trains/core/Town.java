package ua.shramko.trains.core;

import java.util.*;

public class Town {
    String key;
    Map<Town, Route> routes;

    public Town(String key) {
        this.key = key;
        routes = new HashMap<Town, Route>();
    }

    public String getKey() {
        return key;
    }

    public Map<Town, Route> getRoutes() {
        return routes;
    }

    public Route getRoute(Town destination) {
        return routes.get(destination);
    }

    public Set<Town> getDestinations() {
        Set<Town> destinations = new HashSet<>();
        for (Route route : getRoutes().values()) {
            destinations.add(route.getDestination());
        }
        return destinations;
    }

    public void addRoute(Town destination, int distance) {
        Route route = new Route(destination, distance);
        routes.put(destination, route);
    }
}
