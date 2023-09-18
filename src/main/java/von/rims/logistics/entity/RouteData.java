package von.rims.logistics.entity;

import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class RouteData {
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
        return Collections.unmodifiableMap(routes);
    }

    // Метод для получения всех остановок
    public Set<Integer> getStops() {
        return Collections.unmodifiableSet(stops);
    }

    // Метод для проверки наличия прямого маршрута между остановками x и y
    public boolean hasDirectRoute(int x, int y) {
        for (Set<Integer> routeStops : routes.values()) {
            List<Integer> stopsList = new ArrayList<>(routeStops);

            int xIndex = stopsList.indexOf(x);
            int yIndex = stopsList.indexOf(y);

            if (xIndex != -1 && yIndex != -1 && xIndex < yIndex) {
                // Найден прямой маршрут, где x идет раньше y
                return true;
            }
        }

        // Прямого маршрута не найдено
        return false;
    }
}
