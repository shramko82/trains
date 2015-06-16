package ua.shramko.trains.services;

import ua.shramko.trains.core.Town;

import java.util.HashMap;
import java.util.Map;

public class TownService {
    Map<String, Town> towns;

    public TownService() {
        towns = new HashMap<>();
    }

    public Map<String, Town> getTowns() {
        return towns;
    }

    public Town getTown(String key) {
        return towns.get(key);
    }

    public void fillRoutes(String inputString) {
        String[] nodes = inputString.split(", ");
        for (int i=0; i<nodes.length; i++) {
            String node = nodes[i];
            String keyFrom = node.substring(0, 1);
            Town townFrom = towns.get(keyFrom);
            if (townFrom == null) {
                townFrom = new Town(keyFrom);
                towns.put(keyFrom,townFrom);
            }
            String keyTo = node.substring(1, 2);
            Town townTo = towns.get(keyTo);
            if (townTo == null) {
                townTo = new Town(keyTo);
                towns.put(keyTo,townTo);
            }
            int distance = Integer.parseInt(node.substring(2));
            townFrom.addRoute(townTo, distance);
        }
    }

}
