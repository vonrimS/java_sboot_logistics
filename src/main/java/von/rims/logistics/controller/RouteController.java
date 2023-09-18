package von.rims.logistics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

        // Проверяем наличие файла перед вызовом метода hasDirectRoute
//        boolean fileExists = checkFileExistence(); // Реализуйте этот метод

//        if (!fileExists) {
//            return new RouterResponse(fromStop, toStop, false); // Файл не существует, возвращаем false
//        }

        boolean hasDirectRoute = routeService.hasDirectRoute(fromStop, toStop);
        System.out.println("from " + fromStop);
        System.out.println("to " + toStop);

        // Создаем объект RouterResponse и устанавливаем значения полей
        return new RouterResponse(fromStop, toStop, hasDirectRoute);
    }
}
