package von.rims.logistics.entity;

import java.io.Serializable;
import java.util.*;

public class RouteData implements Serializable {
    private final Map<Integer, Set<Integer>> routes = new HashMap<>();
    private final Set<Integer> stops = new LinkedHashSet<>();

    // Метод для добавления маршрута
    public void addRoute(int routeId, Set<Integer> routeStops) {
        routes.put(routeId, routeStops);
        stops.addAll(routeStops);
    }

    // Метод для добавления остановки
    public void addStop(int stopId) {
        stops.add(stopId);
    }

    // Метод для получения всех маршрутов
    public Map<Integer, Set<Integer>> getRoutes() {
        return routes;
    }

    // Метод для получения всех остановок
    public Set<Integer> getStops() {
        return stops;
    }

}
