package ua.shramko.trains;

import java.util.*;

public class Town {
    String key;
    Map<String, Route> routes;

    public Town(String key) {
        this.key = key;
        routes = new HashMap<String, Route>();
    }

    public String getKey() {
        return key;
    }

    public Map<String, Route> getRoutes() {
        return routes;
    }

    public Route getRoute(String key) {
        return routes.get(key);
    }

    public Set<Town> getDestinations() {
        Set<Town> destinations = new HashSet<Town>();
        for (Route route : getRoutes().values()) {
            destinations.add(route.getDestination());
        }
        return destinations;
    }

    public void addRoute(String key, Town destination, int distance) {
        Route route = new Route(destination, distance);
        routes.put(key, route);
    }
}
