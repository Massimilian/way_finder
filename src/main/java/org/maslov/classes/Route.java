package org.maslov.classes;

import java.util.ArrayList;

public class Route implements Comparable<Route>, Cloneable{
    private ArrayList<Point> points = new ArrayList<>();
    private int waySize;


    public ArrayList<Point> getPoints() {
        return points;
    }


    public int getWaySize() {
        return waySize;
    }

    public void setWaySize(int waySize) {
        this.waySize = waySize;
    }

    @Override
    public int compareTo(Route o) {
        return this.getWaySize() - o.getWaySize();
    }

    @Override
    public Route clone() {
        Route result = new Route();
        result.setWaySize(this.getWaySize());
        this.getPoints().forEach(p -> result.getPoints().add(p));
        return result;
    }
}
