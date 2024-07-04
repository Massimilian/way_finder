package org.maslov.classes;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Map {
    private Set<Point> points = new HashSet<>();

    public Map() {
    }

    public Map(Set<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void addPoints(Collection<Point> points) {
        points.forEach(this::addPoint);
    }

    public Set<Point> getPoints() {
        return points;
    }

    public void addWayToPoints(Way way) {
        Point one = way.getOne();
        Point two = way.getTwo();
        if (points.contains(one) && points.contains(two)) { // проверяем саму возможность построения маршрута
            Consumer<Point> consumer = point -> {
                if (point.equals(one) || point.equals(two)) {
                    point.addWay(way);
                }
            };
            this.points.forEach(consumer);
        }

    }

    public List<Route> findRoutes(String from, String to, int size) {
        ArrayList<Route> routes;
        Point f = this.findByName(from);
        Point t = this.findByName(to);
        if (f == null || t == null) {
            return null;
        }
        ArrayList<Route> allRoutes = new ArrayList<>();
        routes = findRouteByRounding(f, t, allRoutes, null);
        return routes.stream().sorted().limit(size).collect(Collectors.toList());
    }

    public Route findRoute(String from, String to) {
        List<Route> temp = this.findRoutes(from, to, 1);
        return temp == null? null : temp.get(0);
    }

    private ArrayList<Route> findRouteByRounding(Point f, Point t, ArrayList<Route> allRoutes, Route workRoute) {
        // f - начальный город, w - пустой
        if (workRoute == null) {
            Iterator<Way> iterator = f.getWays().iterator();
            while (iterator.hasNext()) {
                Way temp = iterator.next();
                Route route = new Route();
                if (f.equals(temp.getOne())) { // при необходимости корректируем порядок добавляния
                    route.getPoints().add(temp.getOne()); // добавляесм проходимые пункты в маршрут
                    if (!route.getPoints().contains(temp.getTwo())) {
                        route.getPoints().add(temp.getTwo());
                    }
                } else {
                    route.getPoints().add(temp.getTwo());
                    if (!route.getPoints().contains(temp.getOne())) {
                        route.getPoints().add(temp.getOne());
                    }
                }
                route.setWaySize(temp.getDistance()); // добавляем расстояние между пунктами
                if (route.getPoints().contains(t)) { // проверяем, что мы достигли конечного пункта - и добавляем его в результирующий массив при необходимости
                    allRoutes.add(route);
                } else {
                    findRouteByRounding(route.getPoints().get(route.getPoints().size() - 1), t, allRoutes, route); // иначе продолжаем поиск
                }
            }
            // f - неначальный город, w - непустой
        } else {
            Iterator<Way> iterator = f.getWays().iterator();
            while (iterator.hasNext()) {
                Way temp = iterator.next();
                if (workRoute.getPoints().contains(temp.getOne()) && workRoute.getPoints().contains(temp.getTwo())) { // если маршрут закольцевался - выходим из цикла
                    continue;
                }
                Route newRoute = workRoute.clone();
                if (!workRoute.getPoints().contains(temp.getOne())) { // если такого пункта у нас нет - добавляем в маршрут
                    newRoute.getPoints().add(temp.getOne());
                }
                if (!workRoute.getPoints().contains(temp.getTwo())) {
                    newRoute.getPoints().add(temp.getTwo());
                }
                newRoute.setWaySize(newRoute.getWaySize() + temp.getDistance()); // добавляем расстояние
                if (newRoute.getPoints().contains(t)) { // проверяем, что мы достигли конечного пункта - и добавляем его в результирующий массив при необходимости
                    allRoutes.add(newRoute);
                } else {
                    findRouteByRounding(newRoute.getPoints().get(newRoute.getPoints().size() - 1), t, allRoutes, newRoute); // иначе продолжаем поиск
                }
            }
        }
        return allRoutes;
    }

    private Point findByName(String name) {
        return points.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}
