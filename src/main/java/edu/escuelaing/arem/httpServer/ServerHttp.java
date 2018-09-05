package edu.escuelaing.arem.httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Esta clase crea el in y el out del cliente del servidor,
 * ademas llama la instacia para crear los sockets de cliente 
 * y de servidor uno tras de otro, analisa el input que
 * recibe el servidor desde el cliente y muestra en pantalla
 * la solicitud que realza el cliente.
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

        String inputLine, outputLine, reqSelected = "";

        while ((inputLine = in.readLine()) != null) {
            if(inputLine.contains("GET")){
                if(!inputLine.contains("favicon.ico")){
                    reqSelected = inputLine.split(" ")[1];
                }
            }
            System.out.println("Received: " + inputLine);
            if (!in.ready()) {
                break;
            }
        }
        pagWeb pagWeb = new pagWeb(clientServerSocket.getClientSocket());
        pagWeb.getPagWeb(reqSelected);
        out.println(pagWeb.getPagToReturn());

        in.close();
        out.close();
        clientServerSocket.getClientSocket().close();
        clientServerSocket.getServerSocket().close();

    }

}
