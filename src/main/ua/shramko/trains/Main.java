package ua.shramko.trains;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine();
        TownHandler townHandler = new TownHandler();
        townHandler.fillRoutes(inputString);
        System.out.println(townHandler.getFormattedDistance("A-B-C"));
        System.out.println(townHandler.getFormattedDistance("A-D"));
        System.out.println(townHandler.getFormattedDistance("A-D-C"));
        System.out.println(townHandler.getFormattedDistance("A-E-B-C-D"));
        System.out.println(townHandler.getFormattedDistance("A-E-D"));
        System.out.println(townHandler.getNumberOfTrips("C", "C", 3, StopLimitTypes.LESS_OR_EQUALS));
        System.out.println(townHandler.getNumberOfTrips("A", "C", 4, StopLimitTypes.EQUALS));
        System.out.println(townHandler.calculateShortestRoute("A", "C"));
        System.out.println(townHandler.calculateShortestRoute("B", "B"));
        System.out.println(townHandler.numberOfRoutes("C", "C", 30, StopLimitTypes.LESS));
    }
}
