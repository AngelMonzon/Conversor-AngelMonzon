import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ObtenerDivisas {

    private static final String API_KEY = "bff747ccfeddc6e5d586efb71794b508";

    Divisas divisas;

    public ObtenerDivisas() throws IOException {
        //CLIENTE
        OkHttpClient cliente = new OkHttpClient();

        //SOLICITUD GET
        Request solicitud = new Request.Builder().url("http://data.fixer.io/api/latest?access_key=" + API_KEY).build();

        //Respuesta
        Response respuesta = cliente.newCall(solicitud).execute();

        //JSON
        assert respuesta.body() != null;
        String datosJson = respuesta.body().string();

        //Instancia de Gson que nos permite leer la cadena Json en objetos en java
        Gson gson = new Gson();

        this.divisas = gson.fromJson(datosJson, Divisas.class);
    }


    public BigDecimal compararMonedas(BigDecimal cantidad, String valorOrigen, String valorDestino){
        BigDecimal tasaOrigenABase = divisas.rates.get(valorOrigen);
        BigDecimal tasaDestinoABase = divisas.rates.get(valorDestino);

        //conversion
        BigDecimal cantidadBase = cantidad.divide(tasaOrigenABase, 3, RoundingMode.HALF_UP);
        BigDecimal cantidadConvertida = cantidadBase.multiply(tasaDestinoABase);

        return cantidadConvertida.setScale(3, RoundingMode.HALF_UP);
    }
}
