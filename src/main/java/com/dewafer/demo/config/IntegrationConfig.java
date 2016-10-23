package com.dewafer.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.ip.tcp.serializer.AbstractByteArraySerializer;
import org.springframework.integration.ip.tcp.serializer.ByteArrayLengthHeaderSerializer;
import org.springframework.integration.transformer.MessageTransformingHandler;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class IntegrationConfig {

    @Value("${demo.remote.host}")
    private String host;

    @Value("${demo.remote.port}")
    private int port;

    @Value("${demo.remote.charset}")
    private String charset;

    @Bean
    public MessageChannel requestChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    @ServiceActivator(inputChannel = "requestChannel")
    public MessageHandler tcpOutboundGateway() {
        TcpOutboundGateway tcpOutboundGateway = new TcpOutboundGateway();
        tcpOutboundGateway.setReplyChannel(tcpInboundChannel());
        tcpOutboundGateway.setConnectionFactory(tcpNetClientConnectionFactory());
        return tcpOutboundGateway;
    }

    @Bean
    public MessageChannel tcpInboundChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    @ServiceActivator(inputChannel = "tcpInboundChannel")
    public MessageHandler objectToStringTransformer() {
        // Bytes to String 变换
        MessageTransformingHandler messageHandler = new MessageTransformingHandler(new ObjectToStringTransformer(charset));
        messageHandler.setOutputChannel(replyChannel());
        return messageHandler;
    }

    @Bean
    public MessageChannel replyChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public TcpNetClientConnectionFactory tcpNetClientConnectionFactory() {
        TcpNetClientConnectionFactory tcpNetClientConnectionFactory = new TcpNetClientConnectionFactory(host, port);
        tcpNetClientConnectionFactory.setSingleUse(true);

        tcpNetClientConnectionFactory.setSerializer(tcpClientSerializer());
        tcpNetClientConnectionFactory.setDeserializer(tcpClientSerializer());

        return tcpNetClientConnectionFactory;
    }

    @Bean
    public AbstractByteArraySerializer tcpClientSerializer() {
        // 配合DataInput#readUTF，根据需要替换Serializer
        return new ByteArrayLengthHeaderSerializer(ByteArrayLengthHeaderSerializer.HEADER_SIZE_UNSIGNED_SHORT);
    }
}

