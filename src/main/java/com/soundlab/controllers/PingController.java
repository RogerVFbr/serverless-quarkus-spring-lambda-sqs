package com.soundlab.controllers;

import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ping")
public class PingController {

    private static final Logger LOG = Logger.getLogger(PingController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> ping() {
        LOG.info("PingController invoked.");
        return ResponseEntity.ok("Endpoint pinged.");
    }
}