package von.rims.logistics.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    @GetMapping("/api/direct")
    public String checkDirectRoute(
            @RequestParam("from") int fromStop,
            @RequestParam("to") int toStop) {
        return "it's test";
    }
}
