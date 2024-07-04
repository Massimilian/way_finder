package org.maslov.classes.mapMaker;

import org.maslov.classes.Map;

public interface MapMaker {
    Map createMap();
    void writeMap(Map map);
}
