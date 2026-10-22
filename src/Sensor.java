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

    // ATRIBUTOS PARA LAS ESTADÍSTICAS
    protected int numLecturas;
    protected double sumaValores;
    protected double minimo;
    protected double maximo;
    protected int numAlertas;

    // CONSTRUCTOR
    public Sensor(String nombre, String unidad, int periodoMs, double umbralMinimo, double umbralMaximo){
        this.nombre = nombre;
        this.unidad = unidad;
        this.periodoMs= periodoMs;
        this.umbralMinimo = umbralMinimo;
        this.umbralMaximo = umbralMaximo;
        // Inicializamos las etadísticas a 0 al crear el sensor
        this.numLecturas = 0;
        this.sumaValores = 0.0;
        this.minimo = 0;
        this.maximo = 0;
        this.numAlertas = 0;
    }

    // MÉTODO ABSTRACTO
    public abstract double medir();

    // MÉTODO RUN
    // Al implementar la interfaz Runnable, estamos obligando a escribir este método
    // Aquí dentro va todo el trabajo "pesado" que hará el sensor de forma independiente
    @Override
    public void run(){
        // Como nuestro sensor es una tarea (Runnable) y no un hilo (Thread), no tiene un nombre propio. Para saber que
        // hilo está ejecutando esta tarea en este momento, le preguntamos a Java que nos de el hilo actual
        // (currentThread) y saca su nombre (getName)
        String hilo = Thread.currentThread().getName();

        // Va a dar tantas vueltas como indique la constante LECTURAS_TOTALES (5 vueltas)
        for (int i = 0; i < LECTURAS_TOTALES; i++){
            // 1. Llamamos a nuestro método abstracto para obtener un número decimal. Como esta clase es abstracta,
            // ejecutará el medir() de la clase hija (Temperatura, Luz...)
            double valorActual = medir();

            // 2. Actualizar estadísticas
            if(numLecturas == 0){
                // Si el contador de lecturas es 0, significa que es la primera vez que medimos
                // Por lo tanto, este primer valor es nuestro record de mínimo y máximo temporal
                minimo = valorActual;
                maximo = valorActual;
            } else{
                // Para la segunda, tercera, etc. lectura, comparamos:
                if(valorActual < minimo){
                    minimo = valorActual; // Si el nuevo valor es menor, actualizamos el récord mínimo
                }
                if(valorActual > maximo){
                    maximo = valorActual; // Si el nuevo valor es mayor, actualizamos el récord máximo
                }
            }

            // Sumamos el valor a nuestro acumulador y aumentamos el contador de lecturas
            sumaValores = sumaValores + valorActual;
            numLecturas++;

            // 3. Comprobar los umbrales
            // Comparamos el valor que acabamos de leer con los topes que le pasamos al constructor
            String mensajeAlerta = "";
            if(valorActual < umbralMinimo || valorActual > umbralMaximo){
                mensajeAlerta = " ⚠ ALERTA";
                // Sumamos 1 a nuestra estadística de alertas
                numAlertas++;
            }

            // 4. Imprimir el log
            // Esto nos da un número largo con los milisegundos actuales
            long hora = System.currentTimeMillis();

            // Imprimimos
            System.out.println("[" + hora + "] [" + hilo + "] " + nombre + ": " + valorActual + " " + unidad + mensajeAlerta);

            // 5. Esperar
            // Usamos Thread.sleep() para detener temporalmente el hilo. Siempre que usamos sleep(), hay que envolverlo
            // en un bloque try-catch, por si otro hilo interrumpe a este mientras duerme
            try{
                Thread.sleep(periodoMs);
            } catch (InterruptedException e){
                // Si hay un error o nos interrumpen, salimos del método run() y la tarea termina
                return;
            }
        }

    }

    // MÉTODO INFORME
    // Este método debe devolver una línea de texto con las estadísticas
    public String informe(){
        // Calculamos la media
        // Si el número de lecturas es 0 (por ejemplo, si falló antes de empezar) la media es 0.0 para evitar
        // dividir entre cero. Si es distinto a 0, calculamos sumando los valores y dividiendo entre la cantidad
        double media = 0.0;
        if(numLecturas > 0){
            media = sumaValores / numLecturas;
        }

        // Devolvemos los valores
        return "INFORME: "+nombre+" -> "+
                "Lecturas: "+numLecturas+
                " | Media: "+media+
                " | Min: "+minimo+
                " | Max: "+maximo+
                " | Alertas: "+numAlertas;
    }

}
