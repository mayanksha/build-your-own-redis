package org.redis.scratchbook;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

@Slf4j
public class Client implements Closeable {
    private final Socket clientSocket;
    private final PrintWriter out;
    private final BufferedReader in;

    public Client(final String ip, final int port) throws IOException {
        this.clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(final String msg) throws IOException {
        log.info("[Client] send: {}", msg);
        out.println(msg);
        String s = in.readLine();
        log.info("[Client] recv: {}", s);
        return s;
    }

    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
