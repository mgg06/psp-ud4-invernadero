// implements Runnable: Transforma esta clase en una tarea que luego un hilo (Thread) podrá ejecutar
public abstract class Sensor implements Runnable {

    // Lo hacemos fijo para que todos los sensores tomen 5 lecturas
    public static final int LECTURAS_TOTALES = 5;

    // ATRIBUTOS
    // Se utiliza el modificador "protected" para que las clases hijas (Temperatura, Humedad...) , puedan ver y utilizar
    // estas variables directamente
    protected String nombre;
    protected String unidad;
    // El tiempo de espera en milisegundos
    protected int periodoMs;
    // El valor mas bajo permitido antes de dar la alerta
    protected double umbralMinimo;
    // El valor mas alto " "
    protected double umbralMaximo;

    // CONSTRUCTOR
    public Sensor(String nombre, String unidad, int periodoMs, double umbralMinimo, double umbralMaximo){
        this.nombre = nombre;
        this.unidad = unidad;
        this.periodoMs= periodoMs;
        this.umbralMinimo = umbralMinimo;
        this.umbralMaximo = umbralMaximo;
    }

    // MÉTODO ABSTRACTO
    public abstract double medir();

}
