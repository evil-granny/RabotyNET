package ua.softserve.ita.controller.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public String userLoginPost() {
        return "OK";
    }

}

