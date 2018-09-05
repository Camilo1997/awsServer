package edu.escuelaing.arem.httpServer;

import java.io.IOException;

/**
 *
 * @author camilolopez
 */
public class MainServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        ServerHttp server;
        while (true) {
            server = new ServerHttp();
        }
    }

    /**
     * This method will set a default port to be open on Heroku
     *
     * @return The default port
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }
}
