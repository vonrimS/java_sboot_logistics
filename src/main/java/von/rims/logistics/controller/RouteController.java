package von.rims.logistics.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import von.rims.logistics.service.RouteService;

@RestController
@RequiredArgsConstructor
public class RouteController {
    private RouteService routeService;

    @GetMapping("/api/direct")
    public String checkDirectRoute(
            @RequestParam("from") int fromStop,
            @RequestParam("to") int toStop) {
//        return routeService.hasDirectRoute(fromStop, toStop);
        return "test";
    }
}
