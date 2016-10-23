package com.dewafer.demo.gateway;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "requestChannel", defaultReplyChannel = "replyChannel")
public interface TcpClientGateway {

    String sendMessage(String content);
}
