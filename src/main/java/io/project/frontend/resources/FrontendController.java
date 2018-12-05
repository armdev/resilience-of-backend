package io.project.frontend.resources;

import io.micrometer.core.annotation.Timed;
import io.project.frontend.services.BackendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author armdev
 */
@RestController
@RequestMapping("/api/v2/frontend")
@Slf4j
public class FrontendController {
    
    @Autowired
    private BackendService backendService;
 
  
    @GetMapping(path = "/backend")
    @CrossOrigin
    @Timed
    public ResponseEntity<?> get(@RequestParam String token) {
        log.info("Hey what do you want");
        return ResponseEntity.status(HttpStatus.OK).body(backendService.methodWithRecovery(token).get());
    }

}
