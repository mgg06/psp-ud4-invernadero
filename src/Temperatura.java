import java.util.concurrent.ThreadLocalRandom;

// Usamos "extends" para heredar de sensor, como sensor ya implementa Runnable, temperatura también será automáticamente
// una tarea (Runnable)
public class Temperatura extends Sensor{
    // CONSTANTES
    // Si miramos la tabla, la temperatura genera valores entre 15.0 y 35.0
    public static final double MIN_GENERADO = 15.0;
    public static final double MAX_GENERADO = 35.0;

    // CONSTRUCTOR
    public Temperatura(){
        // Le pasamos los datos exactos de la tabla del documento: Nombre, unidad, periodo en ms (400), umbral mínimo
        // correcto (18.0) y máximo correcto (30.0)
        super("Temperatura", "ºC",400,18.0,30.0);
    }

    // MÉTODO MEDIR
    @Override
    public double medir(){
        // Generamos el número aleatorio decimal
        return ThreadLocalRandom.current().nextDouble(MIN_GENERADO, MAX_GENERADO);
    }


}
