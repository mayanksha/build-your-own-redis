package org.redis.scratchbook;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class Server implements Closeable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public Server(final int port) throws IOException {
        start(port);
    }

    public void start(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String clientInput;
            while ((clientInput = bufferedReader.readLine()) != null) {
                if ("foobar".equals(clientInput)) {
                    printWriter.println("shoobar");
                } else if (".".equals(clientInput)) {
                    break;
                } else {
                    printWriter.println("Unrecognized input!");
                }
            }
        } catch (IOException e) {
            log.error("Error creating a server listening on port: {}", port);
        }
    }

    @Override
    public void close() throws IOException {
        log.info("[Server] Closing now! Bye!");
    }
}
