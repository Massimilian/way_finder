package org.maslov.classes;


public class Way {

    private Point one;
    private Point two;
    private final int distance;

    public Way(Point one, Point two, int distance) {
        this.one = one;
        this.two = two;
        this.distance = distance;
    }

    public Point getOne() {
        return one;
    }

    public Point getTwo() {
        return two;
    }

    public void setOne(Point one) {
        this.one = one;
    }

    public void setTwo(Point two) {
        this.two = two;
    }

    public int getDistance() {
        return this.distance;
    }
}
