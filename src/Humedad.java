import java.util.concurrent.ThreadLocalRandom;

public class Humedad extends Sensor {

    // Según la tabla, genera valores de 40.0 a 90.0
    public static final double MIN_GENERADO = 40.0;
    public static final double MAX_GENERADO = 90.0;

    public Humedad(){
        // Datos de la tabla para humedad: 600 ms, rango correcto de 50.0 a 80.0
        super("Humedad", "%", 600, 50.0, 80.0);
    }

    @Override
    public double medir(){
        return ThreadLocalRandom.current().nextDouble(MIN_GENERADO, MAX_GENERADO);
    }
}
