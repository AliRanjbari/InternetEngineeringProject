package edu.app.Controller;

import edu.app.api.Commodity;
import edu.app.api.Provider;
import edu.app.service.BalootService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;
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

