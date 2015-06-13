package ua.shramko.trains.core;

public class Route {
    private Town destination;
    private int distance;

    public Route(Town destination, int distance) {
        this.destination = destination;
        this.distance = distance;
    }

    public Town getDestination() {
        return destination;
    }

    public void setDestination(Town destination) {
        this.destination = destination;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
