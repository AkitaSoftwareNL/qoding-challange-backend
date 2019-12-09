package nl.quintor.qodingchallenge.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ParticipantResource {

    @RequestMapping(path = "login",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    method = RequestMethod.GET)
    public ResponseEntity<Principal> login(Principal principal) {
        return ResponseEntity.ok().body(principal);
    }
}
