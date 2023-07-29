import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;

public class ObtenerDivisas {
    private static final String API_KEY = "bff747ccfeddc6e5d586efb71794b508";

    Divisas divisas = new Divisas();

    public ObtenerDivisas() throws IOException {
        //CLIENTE
        OkHttpClient cliente = new OkHttpClient();

        //SOLICITUD GET
        Request solicitud = new Request.Builder().url("http://data.fixer.io/api/latest?access_key=" + API_KEY).build();

        //Respuesta
        Response respuesta = cliente.newCall(solicitud).execute();

        //JSON
        String datosJson = respuesta.body().string();

        //Instancia de Gson que nos permite leer la cadena Json en objetos en java
        Gson gson = new Gson();

        Divisas respuestaFixer = gson.fromJson(datosJson, Divisas.class);
        this.divisas = respuestaFixer;
    }

    public BigDecimal obtenerValor(String moneda){
        return divisas.rates.get(moneda);
    }

    public BigDecimal compararMonedas(BigDecimal cantidad, String valorOrigen, String valorDestino){
        BigDecimal tasaOrigenABase = divisas.rates.get(valorOrigen);
        BigDecimal tasaDestinoABase = divisas.rates.get(valorDestino);

        //conversion
        BigDecimal cantidadBase = cantidad.divide(tasaOrigenABase, 2, RoundingMode.HALF_UP);
        BigDecimal cantidadConvertida = cantidadBase.multiply(tasaDestinoABase);

        return cantidadConvertida.setScale(2, RoundingMode.HALF_UP);
    }
}
