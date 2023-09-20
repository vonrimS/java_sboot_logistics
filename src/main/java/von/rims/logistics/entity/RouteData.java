package von.rims.logistics.entity;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class RouteData {
    private final Map<Integer, Stops> routes = new HashMap<>();
    // Обратная хэш-таблица (K- id остановки, V- сет с перечислением маршрутов где она встречается)
    private final Map<Integer, Set<Integer>> stopToRoutes = new HashMap<>();

    // Метод для добавления маршрута
    public void addRoute(int routeId, Stops stops) {
        routes.put(routeId, stops);
        //  Обновляем обратную хэш-таблицу
        updateStopToRoutes(routeId, stops);
    }

     // Обновление обратной хэш-таблицы при добавлении маршрута
    private void updateStopToRoutes(int routeId, Stops stops) {
        for (int stop : stops.getStops()) {
            stopToRoutes.computeIfAbsent(stop, k -> new HashSet<>()).add(routeId);
        }
    }

    public void printAllRoutes() {
        for (Map.Entry<Integer, Stops> entry : routes.entrySet()) {
            int routeId = entry.getKey();
            Stops stops = entry.getValue();

            System.out.println("Route " + routeId + ":");
            System.out.println(stops); // Печатаем объект Stops, предполагая, что у вас есть метод toString() для Stops
            System.out.println();
        }
    }


    // Метод для получения объекта Stops для указанного маршрута
    public Stops getStopsForRoute(int routeId) {
        return routes.get(routeId);
    }

    // Метод для проверки наличия прямого маршрута между остановками 'from' и 'to' для каждого маршрута
    @Cacheable("directRouteCache")
    public boolean hasDirectRoute(int from, int to) {
        // Получаем маршруты, проходящие через остановку 'from'
        Set<Integer> fromRoutes = stopToRoutes.getOrDefault(from, Collections.emptySet());

        // Получаем маршруты, проходящие через остановку 'to'
        Set<Integer> toRoutes = stopToRoutes.getOrDefault(to, Collections.emptySet());

        // Проверяем наличие общих маршрутов
        fromRoutes.retainAll(toRoutes);

        // Проверяем каждый оставшийся маршрут на прямой маршрут
        for (int routeId : fromRoutes) {
            Stops stops = routes.get(routeId);
            if (stops != null && stops.hasDirectRoute(from, to)) {
                return true;
            }
        }

        return false;
    }
}
