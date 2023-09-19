package von.rims.logistics.entity;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class RouteData {
    private final Map<Integer, Stops> routes = new HashMap<>();

    // Метод для добавления маршрута
    public void addRoute(int routeId, Stops stops) {
        routes.put(routeId, stops);
    }

    // Метод для получения объекта Stops для указанного маршрута
    public Stops getStopsForRoute(int routeId) {
        return routes.get(routeId);
    }

    // Метод для проверки наличия прямого маршрута между остановками 'from' и 'to' для каждого маршрута
    @Cacheable("directRouteCache")
    public boolean hasDirectRoute(int from, int to) {
        for (Map.Entry<Integer, Stops> entry : routes.entrySet()) {
            Stops stops = entry.getValue();
            // Если словарь остановок сформирован и остановки удовлетворяют условиям проверки
            if (stops != null && stops.hasDirectRoute(from , to)) {
                return true;
            }
        }
        return false;
    }
}
