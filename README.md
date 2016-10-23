## Socket Client with Spring Boot and Spring Integration using Java Config Demo Project

使用Spring Boot以及Spring Integration构建的Socket通信客户端演示项目（使用Java配置）

### 使用方法

1. `mvn spring-boot:run`
2. `curl -X POST -H 'Content-Type: text/plain' -d "消息内容" http://localhost:8080`

### 说明

* 注意在`DemoApplication`上增加了`@IntegrationComponentScan`注解才能正确扫描到`@MessagingGateway`，使用方法与`@ComponentScan`相同。
* TCP通信使用到了`org.springframework.integration:spring-integration-ip`包，需要在`pom.xml`中增加。
* `com.dewafer.demo.server`包下的内容为演示使用的远程Socket仿真服务器，该服务器在程序启动时会一起启动。
* 配置文件主要参考`com.dewafer.demo.config.IntegrationConfig`。
* `TcpClientGateway`使用方法的演示在`DemoController`中。
* 整个Integration flow大致为 http POST request --> `DemoController` --> `TcpClientGateway` --> `requestChannel` --> `tcpOutboundGateway` --> `tcpInboundChannel` --> `objectToStringTransformer` --> `replyChannel` --> `TcpClientGateway` --> `DemoController` --> http response
* 因为flow比较简单没有使用DSL进行配置。
* 所有的Channel皆为`DirectChannel`，整个flow在一个thread上完成。
* 因为演示用的Socket仿真服务器使用了`java.io.DataInputStream#readUTF`方法，所以为`TcpOutboundGateway`配置了`HEADER_SIZE_UNSIGNED_SHORT`的`ByteArrayLengthHeaderSerializer`，请根据实际情况替换Serializer。请参考[相关的官方文档](http://docs.spring.io/spring-integration/docs/4.3.4.RELEASE/reference/html/ip.html#connection-factories)。
