package edu.escuelaing.arem.httpServer;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;

/**
 * This class creates and modifies the header HTTP/1.1 to display png images or
 * html pages.
 *
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
        if (param.equals("dog")) {
            bytesPageToShow = Files.readAllBytes(new File("./pitbullDog.png").toPath());
            contentLength = "" + bytesPageToShow.length;
            bytesEncabezado = encabezadoImagen.getBytes();
        } else if (param.equals("index")) {
            bytesPageToShow = Files.readAllBytes(new File("./urlWeb.html").toPath());
            contentLength = "" + bytesPageToShow.length;
            bytesEncabezado = encabezadoTexto.getBytes();
        } else {
            bytesPageToShow = Files.readAllBytes(new File("./pageNotFound.html").toPath());
            contentLength = "" + bytesPageToShow.length;
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
