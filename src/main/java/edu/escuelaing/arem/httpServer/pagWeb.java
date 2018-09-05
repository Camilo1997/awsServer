package edu.escuelaing.arem.httpServer;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

/**
 * Esta clase se encarga de crear el despliegue que se
 * mostrara en el navegador para una peticion realizada
 * por el cliente, en este caso peticiones de tipo GET
 * en un navegador, esta maneja el tipo de texto o imagen
 * que sera desplegado en el navegador y se encarga de dejarlo
 * para que el servidor sea capaz de desplegarlo en el navegador
 * sin ningun problema.
 * @author camilolopez
 */
public class pagWeb {

    private String contentLength = "";

    private final String encabezadoTexto = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: text/html\r\n"
            + "\r\n";

    private final String encabezadoImagen = "HTTP/1.1 200 OK\r\n"
            + "Content-Type: image/png\r\n"
            + "\r\n";

    private String pagToReturn;
    private byte[] bytesPageToShow, bytesEncabezado, bytesPage;

    private final Socket socketAccepted;

    public pagWeb(Socket socketAccepted) {
        this.socketAccepted = socketAccepted;
    }

    /**
     * This method edits the header if this want to display an image or a html
     * page, and convert the header and the image or page to bytes to be
     * displayed on the browser.
     *
     * @param param
     * @throws IOException
     */
    public void getPagWeb(String param) throws IOException {
        try {
            bytesPageToShow = Files.readAllBytes(new File("./" + param).toPath());
        } catch (IOException ex) {
            System.err.println("File not found" + ex);
            bytesPageToShow = Files.readAllBytes(new File("./pageNotFound.html").toPath());
        }
        contentLength = "" + bytesPageToShow.length;
        if (param.contains(".png")) {
            bytesEncabezado = encabezadoImagen.getBytes();
        } else {
            bytesEncabezado = encabezadoTexto.getBytes();
        }
        createBytesPage();
    }

    /**
     * This method creates the bytes of each one of the objetcs i.e the header
     * and the web page or png image.
     *
     * @throws IOException
     */
    public void createBytesPage() throws IOException {
        bytesPage = new byte[bytesPageToShow.length + bytesEncabezado.length];
        for (int i = 0; i < bytesEncabezado.length; i++) {
            bytesPage[i] = bytesEncabezado[i];
        }
        for (int i = bytesEncabezado.length; i < bytesEncabezado.length + bytesPageToShow.length; i++) {
            bytesPage[i] = bytesPageToShow[i - bytesEncabezado.length];
        }
        socketAccepted.getOutputStream().write(bytesPage);
        socketAccepted.close();
    }

    public String getPagToReturn() {
        return pagToReturn;
    }

    public void setPagToReturn(String pagToReturn) {
        this.pagToReturn = pagToReturn;
    }

}
