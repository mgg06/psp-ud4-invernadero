import java.util.concurrent.ThreadLocalRandom;

public class Luz extends Sensor{

    // Según la tabla, genera valores de 0 a 1000. Aunque ponga 0, usamos 0.0, para mantener el formato decimal (double)
    public static final double MIN_GENERADO = 0.0;
    public static final double MAX_GENERADO = 1000.0;

    public Luz(){
        // Datos para la tabla de luz: 500 ms, rango correcto de 200.0 a 900.0
        super("Luz", "lux", 500, 200.0, 900.0);
    }

    @Override
    public double medir(){
        return ThreadLocalRandom.current().nextDouble(MIN_GENERADO, MAX_GENERADO);
    }
}
