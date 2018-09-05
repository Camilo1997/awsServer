package edu.escuelaing.arem.httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * This class creates an HttpServer to open local pages
 *
 * @author camilolopez
 */
public class ServerHttp {

    public ServerHttp() throws IOException {
        clientServerSocket clientServerSocket = new clientServerSocket();

        PrintWriter out;
        BufferedReader in;

        out = new PrintWriter(clientServerSocket.getClientSocket().getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(
                        clientServerSocket.getClientSocket().getInputStream()));

        String inputLine, outputLine, pagSelected = "";

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.equals("GET /dog.png HTTP/1.1")) {
                pagSelected = "dog";
            } else if (inputLine.equals("GET /index.html HTTP/1.1")) {
                pagSelected = "index";
            }
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
        pagWeb pagWeb = new pagWeb(clientServerSocket.getClientSocket());
        pagWeb.getPagWeb(pagSelected);
        out.println(pagWeb.getPagToReturn());

        in.close();
        out.close();
        clientServerSocket.getClientSocket().close();
        clientServerSocket.getServerSocket().close();

    }

}
