package org.maslov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maslov.classes.*;
import org.maslov.classes.Map;
import org.maslov.classes.Point;
import org.maslov.classes.mapMaker.JsonMapMaker;
import org.maslov.classes.mapMaker.MapMaker;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestMain {
    Map map = new Map();
    Point t1 = new Point("One");
    Point t2 = new Point("Two");
    Point t3 = new Point("Three");
    Point t4 = new Point("Four");


    @BeforeEach
    public void creation() {
        Set<Way> ways = new HashSet<>(Set.of(new Way(t1, t2, 5), new Way(t2, t4, 9), new Way(t1, t3, 7), new Way(t3, t4, 6)));
        t1.addWays(ways);
        t2.addWays(ways);
        t3.addWays(ways);
        t4.addWays(ways);
        map.addPoint(t1);
        map.addPoint(t2);
        map.addPoint(t3);
        map.addPoint(t4);
    }

    @Test
    public void whenTryToStartThenStart() {
        assertEquals(2, t1.getWays().size());
        assertEquals(2, t2.getWays().size());
        assertEquals(2, t3.getWays().size());
        assertEquals(2, t4.getWays().size());
    }

    @Test
    public void whenTryToFindRouteThenTakeIt() {
        List<Route> routes = map.findRoutes(t1.getName(), t4.getName(), 3);
        assertEquals(routes.size(), 2);
    }

    @Test
    public void whenTryToFindWayFromImpossibleTownThenReturnNull() {
        List<Route> routes = map.findRoutes(t2.getName(), "Abracadabra", 1);
        assertNull(routes);
    }

    @Test
    public void whenTryTiFindMostShortWayThenDoIt() {
        Route route = map.findRoute("Two", "Three");
        assertEquals(12, route.getWaySize());

    }
    @Test
    public void whenTryToGetFromJsonMapMakerThenDoIt() throws IOException {
        Point t5 = new Point("Five");
        Point t6 = new Point("Six");
        map.addPoints(List.of(t5, t6));
        map.addWayToPoints(new Way(t5, t6, 1));
        map.addWayToPoints(new Way(t5, t4, 3));
        map.addWayToPoints(new Way(t3, t6, 11));
        MapMaker jmm = new JsonMapMaker("src/main/resources/ways.json");
        jmm.writeMap(map);
        Map map2 = jmm.createMap();
        List<Route> r = map2.findRoutes("One", "Four", 5);
        Route one = map2.findRoute("Six", "One");
        assertEquals(3, r.size());
        assertEquals(17, one.getWaySize());
    }
}
