package com.dewafer.demo;

import com.dewafer.demo.gateway.TcpClientGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private TcpClientGateway tcpClientGateway;

    @PostMapping
    public String sendMessage(@RequestBody String message) {
        return tcpClientGateway.sendMessage(message);
    }
}
