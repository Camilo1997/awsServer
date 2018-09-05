package edu.escuelaing.arem.httpServer;

import static edu.escuelaing.arem.httpServer.MainServer.getPort;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class creates the ClientSocket and ServerSocket
 *
 * @author camilolopez
 */
public final class clientServerSocket {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    public clientServerSocket() {
        createServer();
        createClient();
    }

    /**
     * Creates the SocketServer with the number port 35000
     */
    public void createServer() {
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
    }

    /**
     * Creates the SocketClient, connecting with the ServerSocket
     */
    public void createClient() {
        try {
            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

}
