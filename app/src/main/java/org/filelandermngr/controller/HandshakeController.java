package org.filelandermngr.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.filelandermngr.multicast.MetaObject;
import org.filelandermngr.service.HandshakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class HandshakeController {

    private final HandshakeService handshakeService;

    @PostMapping(path = "/handshake")
    @ResponseStatus(HttpStatus.CREATED)
    public void handshake(@RequestBody MetaObject metaObject) {
        log.info("Received Handshake from Agent");
        handshakeService.registerAgent(metaObject);
    }
}

