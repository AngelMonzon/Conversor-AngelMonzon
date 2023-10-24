public class ConversionUnidadesResultado {
    private String measure;
    public FromUnit from;
    public ToUnit to;

    private double value;
    private String result;

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
// Agrega los getters y setters para los campos del objeto

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


}
