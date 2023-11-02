import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.Map;
import java.util.TreeMap;

public class ObtenerDivisas {
    Connection connection = null;

    Map<String,BigDecimal> divisas = new TreeMap<>();

    public ObtenerDivisas() throws IOException, SQLException {

        try {
            //Obtener divisas de API
            ObtenerDatosDeApi obtenerDatosDeApi = new ObtenerDatosDeApi();

            // Establece la conexión a la base de datos SQLite (crea la base de datos si no existe)
            connection = DriverManager.getConnection("jdbc:sqlite:test.db");

            // Crea una tabla llamada "Divisas"
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS divisas (id INTEGER PRIMARY KEY, key VARCHAR(3), value NUMERIC(12,12))");

            // Inserta datos en la tabla
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM divisas");
            int count = resultSet.getInt(1);
            System.out.println(count);
            if (count < 169){
                for (Map.Entry<String, BigDecimal> divisa: obtenerDatosDeApi.divisas.entrySet()) {
                    String key = divisa.getKey();
                    BigDecimal value = divisa.getValue();
                    statement.execute("INSERT INTO divisas (key, value) VALUES ('".concat(key).concat("', ").concat(value.toString()).concat(")"));
                }

            } else {
                for (Map.Entry<String, BigDecimal> divisa : obtenerDatosDeApi.divisas.entrySet()) {
                    String key = divisa.getKey();
                    BigDecimal value = divisa.getValue();
                    statement.execute("UPDATE divisas SET value =".concat(value.toString()).concat(" WHERE key ='").concat(key).concat("'"));
                }
            }
            this.divisas = obtenerDatosDeApi.divisas;


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            //En caso de que no haya internet la creacion del objeto ObtenerDivisas lanzara un NullPointerException
            //y se ejecutara este codigo que cargara la base de datos ya guardada
            //en caso de que no se encuentre una base de datos se abrira una ventana que indique que no hay internet

            Statement statement = connection.createStatement();
            String consultaSQL = "SELECT key, value FROM divisas";
            ResultSet resultado = statement.executeQuery(consultaSQL);

            while (resultado.next()) {
                String key = resultado.getString("key");
                BigDecimal value = resultado.getBigDecimal("value");
                this.divisas.put(key, value);
            }

            resultado.close();
            statement.close();


        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public BigDecimal compararMonedas(BigDecimal cantidad, String valorOrigen, String valorDestino){
        BigDecimal tasaOrigenABase = divisas.get(valorOrigen);
        BigDecimal tasaDestinoABase = divisas.get(valorDestino);

        //conversion
        BigDecimal cantidadBase = cantidad.divide(tasaOrigenABase, 3, RoundingMode.HALF_UP);
        BigDecimal cantidadConvertida = cantidadBase.multiply(tasaDestinoABase);

        return cantidadConvertida.setScale(3, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) throws IOException, SQLException {
        ObtenerDivisas insertarDatosEnBD = new ObtenerDivisas();
        //System.out.println(insertarDatosEnBD.divisas.entrySet());
        ObtenerDatosDeApi obtenerDatosDeApi = new ObtenerDatosDeApi();
        System.out.println(obtenerDatosDeApi.divisas.entrySet());
    }

}

