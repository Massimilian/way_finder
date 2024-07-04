package org.maslov.classes.mapMaker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.maslov.classes.Map;
import org.maslov.classes.Point;
import org.maslov.classes.Way;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class JsonMapMaker implements MapMaker {
    private final String address;

    public JsonMapMaker(String address) {
        this.address = address;
    }

    @Override
    public Map createMap() {
        try {
            Type type = new TypeToken<Set<Way>>() {}.getType();
            String s = Files.readString(Path.of(address));
            HashSet<Way> ways = new Gson().fromJson(s, type);
            HashSet<Point> points = new HashSet<>();
            Consumer<Way> consumer = way -> {
                points.add(way.getOne());
                points.add(way.getTwo());
            };
            ways.forEach(consumer); // заполняем points (добавляем названия городов)
            points.forEach(p -> p.setWays(new HashSet<>())); // добавляем в points пустые Set для хранения way
            points.forEach(p -> p.addWays(ways)); // добавляем в points все необходимые пути
            return new Map(points);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeMap(Map map) {
        HashSet<Way> ways = new HashSet<>();
        map.getPoints().forEach(t -> ways.addAll(t.getWays()));
        map.getPoints().forEach(t -> t.setWays(null));
        String json = new Gson().toJson(ways);
        try {
            Files.writeString(Path.of(address), json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
