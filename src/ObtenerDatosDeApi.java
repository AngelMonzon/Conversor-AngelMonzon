import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.UnknownHostException;
import java.util.Map;

public class ObtenerDatosDeApi {

    private static final String API_KEY = "bff747ccfeddc6e5d586efb71794b508";

    Map<String, BigDecimal> divisas;

    public ObtenerDatosDeApi() throws IOException {
        //CLIENTE
        OkHttpClient cliente = new OkHttpClient();

        //SOLICITUD GET
        Request solicitud = new Request.Builder().url("http://data.fixer.io/api/latest?access_key=" + API_KEY).build();


        try{
            //Respuesta
            Response respuesta = cliente.newCall(solicitud).execute();

            //JSON
            assert respuesta.body() != null;
            String datosJson = respuesta.body().string();

            //Instancia de Gson que nos permite leer la cadena Json en objetos en java
            Gson gson = new Gson();

            Divisas divisas = gson.fromJson(datosJson, Divisas.class);
            this.divisas = divisas.rates;
        } catch  (UnknownHostException e){
            this.divisas = null;
        }



    }
}
