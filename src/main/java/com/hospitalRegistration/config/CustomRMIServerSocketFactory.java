package com.hospitalRegistration.config;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.server.RMIServerSocketFactory;

public class CustomRMIServerSocketFactory implements RMIServerSocketFactory {
    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        // 创建不限制客户端地址的服务器套接字
        return new ServerSocket(port);
    }
}