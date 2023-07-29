import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ObtenerTipoConversiones {
    List<String> listaTiposConversion;
    public ObtenerTipoConversiones() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://measurement-unit-converter.p.rapidapi.com/measurements")
                .get()
                .addHeader("X-RapidAPI-Key", "5b57b6bad8mshe2220a0167bcf0ap1f104ejsn3086ae05902d")
                .addHeader("X-RapidAPI-Host", "measurement-unit-converter.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();


        // Verificar si la respuesta fue exitosa (código 200)
        if (response.isSuccessful()) {
            String responseBody = response.body().string();

            // Utilizar Gson para convertir el JSON en una lista de String
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>(){}.getType();
            List<String> measurementsLista = gson.fromJson(responseBody, listType);

            // Ahora tienes la lista de unidades de medida
            System.out.println(measurementsLista);
            this.listaTiposConversion = measurementsLista;
        } else {
            System.out.println("Error en la petición: " + response.code() + " " + response.message());
        }

    }
}
