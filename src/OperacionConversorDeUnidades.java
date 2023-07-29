import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OperacionConversorDeUnidades {
    OperacionConversorDeUnidades() throws IOException {

    }
    public Double convertir(String areaConvesor, double cantidad, String origen, String destino) throws IOException {
        String conversor = areaConvesor;
        double cantidadAConvertir = cantidad;
        String unidadOrigen = origen;
        String unidadDestino = destino;


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://measurement-unit-converter.p.rapidapi.com/"+ conversor + "?value=" + cantidadAConvertir + "&from="+ unidadOrigen + "&to="+ unidadDestino)
                .get()
                .addHeader("X-RapidAPI-Key", "5b57b6bad8mshe2220a0167bcf0ap1f104ejsn3086ae05902d")
                .addHeader("X-RapidAPI-Host", "measurement-unit-converter.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        // Usamos Gson para deserializar el JSON a un objeto Java
        Gson gson = new GsonBuilder().create();
        ConversionResultado conversionResultado = gson.fromJson(responseBody, ConversionResultado.class);

        System.out.println(conversionResultado.getValue());
        return conversionResultado.getValue();
    }
}
