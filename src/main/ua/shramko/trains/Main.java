package ua.shramko.trains;

import ua.shramko.trains.enums.CompareTypes;
import ua.shramko.trains.enums.LimitsBy;
import ua.shramko.trains.handlers.RoutesHandler;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        RoutesHandler routesHandler = new RoutesHandler();
        routesHandler.fillRoutes(inputString);
        System.out.println(routesHandler.getFormattedDistance("A-B-C"));
        System.out.println(routesHandler.getFormattedDistance("A-D"));
        System.out.println(routesHandler.getFormattedDistance("A-D-C"));
        System.out.println(routesHandler.getFormattedDistance("A-E-B-C-D"));
        System.out.println(routesHandler.getFormattedDistance("A-E-D"));
        System.out.println(routesHandler.getNumberOfTrips("C", "C", 3, CompareTypes.LESS_OR_EQUALS, LimitsBy.STOPS));
        System.out.println(routesHandler.getNumberOfTrips("A", "C", 4, CompareTypes.EQUALS, LimitsBy.STOPS));
        System.out.println(routesHandler.calculateShortestRoute("A", "C"));
        System.out.println(routesHandler.calculateShortestRoute("B", "B"));
        System.out.println(routesHandler.getNumberOfTrips("C", "C", 30, CompareTypes.LESS, LimitsBy.DISTANCE));
    }
}
