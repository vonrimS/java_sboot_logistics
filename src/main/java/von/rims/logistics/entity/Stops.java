package von.rims.logistics.entity;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Stops {
    private final Map<Integer, Integer> stops;

    public Stops() {
        this.stops = new HashMap<>();
    }

    // Метод для добавления новой остановки
    public void add(int stopId, int counter) {
        stops.put(stopId, counter);
    }

    // Метод для проверки наличия прямого маршрута между остановками 'from' и 'to'
    public boolean hasDirectRoute(int from, int to) {
        Integer fromOrder = stops.get(from);
        Integer toOrder = stops.get(to);

        return fromOrder != null && toOrder != null && fromOrder < toOrder;
    }

    @Override
    public String toString() {
        return "{stops=" + stops + "}";
    }
}
