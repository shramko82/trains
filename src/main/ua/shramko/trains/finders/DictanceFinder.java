package ua.shramko.trains.finders;

import ua.shramko.trains.core.Route;
import ua.shramko.trains.core.Town;
import ua.shramko.trains.handlers.RoutesHandler;

public class DictanceFinder implements Finder {
    RoutesHandler routes;
    String fullRoute;

    public DictanceFinder(RoutesHandler routes, String fullRoute) {
        this.routes = routes;
        this.fullRoute = fullRoute;
    }

    public int calculate() {
        int distance = 0;
        String[] towns = fullRoute.split("-");
        Town nextTown = routes.getTown(towns[0]);
        if (nextTown == null) {
            return 0;
        }
        for (int i=1; i<towns.length; i++) {
            Route route = nextTown.getRoute(towns[i]);
            if (route == null) {
                return 0;
            } else {
                distance += route.getDistance();
                nextTown = routes.getTown(towns[i]);
            }
        }
        return distance;
    }
}
