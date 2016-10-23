package com.dewafer.demo.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 演示用Socket回声测试（客户端发来什么返回什么），使用了{@linkplain java.io.DataInput#readUTF}方法
 */
public class EchoServerWorker implements Runnable {

    private Socket accept;

    public EchoServerWorker(Socket socket) {
        this.accept = socket;
    }

    @Override
    public void run() {

        try {
            DataInputStream inputStream = new DataInputStream(accept.getInputStream());
            String content = inputStream.readUTF();

            DataOutputStream outputStream = new DataOutputStream(accept.getOutputStream());
            outputStream.writeUTF("ECHO: " + content);
            outputStream.flush();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                accept.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
