package edu.app.controller;

import edu.app.model.Provider.Provider;
import edu.app.model.Provider.ProviderDao;
import edu.app.service.BalootService;
import jakarta.servlet.http.HttpServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/provider")
public class ProviderController extends HttpServlet{

    @Autowired
    private ProviderDao providerDao;

    @GetMapping("/{id}")
    public  ResponseEntity getProviderById(@PathVariable long id){

        try {
            Provider provder = providerDao.findById(id).get();
            Provider provider = BalootService.getInstance().getDatabase().findProvider(id);
            return ResponseEntity.status(HttpStatus.OK).body(provider);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}

