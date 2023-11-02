import java.io.IOException;
import java.net.InetAddress;

public class VerificarConexion {

    public String estadoConeccion;
    public VerificarConexion(){

        try {
            InetAddress.getByName("www.google.com"); // Intenta resolver la dirección IP de un sitio web
            this.estadoConeccion = "Online";
        } catch (IOException e) {
            this.estadoConeccion = "Offline";
        }
    }
}
