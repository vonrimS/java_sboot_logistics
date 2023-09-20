package von.rims.logistics.entity;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    // Метод для получения множества всех остановок
    public Set<Integer> getStops() {
        return Collections.unmodifiableSet(stops.keySet());
    }


    // Метод для проверки наличия прямого маршрута между остановками 'from' и 'to'
    public boolean hasDirectRoute(int from, int to) {
        Integer fromOrder = stops.get(from);
        Integer toOrder = stops.get(to);

        if (fromOrder != null && toOrder != null){
            return fromOrder < toOrder;
        }

        return false;
    }

    @Override
    public String toString() {
        return "{stops=" + stops + "}";
    }
}
