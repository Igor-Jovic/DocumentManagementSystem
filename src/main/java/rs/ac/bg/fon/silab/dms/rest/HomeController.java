package rs.ac.bg.fon.silab.dms.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-status")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Server is up and running.");
    }
}
