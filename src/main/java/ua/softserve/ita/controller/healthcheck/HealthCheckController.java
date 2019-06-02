package ua.softserve.ita.controller.healthcheck;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseStatus(HttpStatus.OK)
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public String userLoginPost() {
        return "OK";
    }

}

