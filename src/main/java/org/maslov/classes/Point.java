package org.maslov.classes;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Point {
    private final String name;
    private Set<Way> ways = new HashSet<>();

    public Point(String name) {
        this.name = name;
    }

    public Set<Way> getWays() {
        return ways;
    }

    public void setWays(Set<Way> ways) {
        this.ways = ways;
    }

    public String getName() {
        return name;
    }

    public void addWay(Way way) {
        if (way != null) {
            if (way.getOne().equals(this)) {
                way.setOne(this); // обновляем данные в самом объекте типа Way на случай, если Point поменял настройки (например, появились новые маршруты)
                ways.add(way);
            }
            if (way.getTwo().equals(this)) {
                way.setTwo(this);
                ways.add(way);
            }
        }
    }

    public void addWays(Set<Way> ways) {
        ways.forEach(this::addWay);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(name, point.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
