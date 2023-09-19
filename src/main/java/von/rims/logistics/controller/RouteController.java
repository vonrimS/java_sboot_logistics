package von.rims.logistics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import von.rims.logistics.entity.RouterResponse;
import von.rims.logistics.service.RouteService;

@RestController
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    @GetMapping("/api/direct")
    public RouterResponse checkDirectRoute(
            @RequestParam("from") int fromStop,
            @RequestParam("to") int toStop) {

        boolean hasDirectRoute = routeService.hasDirectRoute(fromStop, toStop);
        System.out.println("from: " + fromStop + " -> to: " + toStop);

        // Создаем объект RouterResponse и устанавливаем значения полей
        return new RouterResponse(fromStop, toStop, hasDirectRoute);
    }
}
