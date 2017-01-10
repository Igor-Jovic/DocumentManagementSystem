package rs.ac.bg.fon.silab.dms.rest.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("api-status")
    public ResponseEntity hello() {
        System.out.println("tebra?");
        return ResponseEntity.ok("Server is up and running.");
    }

    @GetMapping("admin")
    public ResponseEntity admin() {
        return ResponseEntity.ok("Admin.");
    }

}
