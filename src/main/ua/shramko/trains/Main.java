package ua.shramko.trains;

import ua.shramko.trains.enums.CompareTypes;
import ua.shramko.trains.enums.LimitsBy;
import ua.shramko.trains.handlers.RoutesHandler;
import ua.shramko.trains.services.TownService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Enter the routes graph");
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        RoutesHandler routesHandler = new RoutesHandler();
        TownService townService = new TownService();
        townService.fillRoutes(inputString);
        System.out.println(routesHandler.getFormattedDistance(townService, "A-B-C"));
        System.out.println(routesHandler.getFormattedDistance(townService, "A-D"));
        System.out.println(routesHandler.getFormattedDistance(townService, "A-D-C"));
        System.out.println(routesHandler.getFormattedDistance(townService, "A-E-B-C-D"));
        System.out.println(routesHandler.getFormattedDistance(townService, "A-E-D"));
        System.out.println(routesHandler.getNumberOfTrips(townService, "C", "C", 3, CompareTypes.LESS_OR_EQUALS, LimitsBy.STOPS));
        System.out.println(routesHandler.getNumberOfTrips(townService, "A", "C", 4, CompareTypes.EQUALS, LimitsBy.STOPS));
        System.out.println(routesHandler.calculateShortestRoute(townService, "A", "C"));
        System.out.println(routesHandler.calculateShortestRoute(townService, "B", "B"));
        System.out.println(routesHandler.getNumberOfTrips(townService, "C", "C", 30, CompareTypes.LESS, LimitsBy.DISTANCE));
    }
}
