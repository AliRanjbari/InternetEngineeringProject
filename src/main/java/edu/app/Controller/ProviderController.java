package edu.app.Controller;

import edu.app.model.Provider;
import edu.app.service.BalootService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/provider")
public class ProviderController extends HttpServlet{

    @GetMapping("/{id}")
    public  ResponseEntity getProviderById(@PathVariable long id){

        try {
            Provider provider = BalootService.getInstance().getDatabase().findProvider(id);
            return ResponseEntity.status(HttpStatus.OK).body(provider);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}

