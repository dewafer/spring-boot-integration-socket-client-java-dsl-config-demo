package com.dewafer.demo.server;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 演示用Socket服务器
 */
public class DemoSocketServer implements Runnable {

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9900);
            while (true) {
                Socket accept = serverSocket.accept();

                Thread thread = new Thread(new EchoServerWorker(accept));
                thread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
