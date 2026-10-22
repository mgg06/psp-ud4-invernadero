// Al heredar de Thread, este objeto es un hilo por sí mismo, no solo una tarea que necesite ser metida en otro hilo
public class ProgramadoRiego extends Thread {

    // CONSTANTES
    // 5 ciclos, 700 ms de espera, 2.5 litros por apertura
    public static final int CICLOS_TOTALES = 5;
    public static final int PERIODO_MS = 700;
    public static final double LITROS_POR_APERTURA = 2.5;

    // ATRIBUTOS PARA EL INFORME Y EL CONTROL
    private int ciclosRealizados;
    private double litrosTotales;
    // Un boolean es perfecto para alterar el estado de la válvula
    // Empezamos con true (abierta)
    private boolean estaAbierta;

    // CONSTRUCTOR
    public ProgramadoRiego(){
        // Usamos super() para darle el nombre "riego" al hilo
        super("riego");
        // Inicializamos los contadores a 0
        this.ciclosRealizados = 0;
        this.litrosTotales = 0;
        // Arranca lista para abrirse en el primer ciclo
        this.estaAbierta = true;
    }

    // MÉTODO RUN
    // Como heredamos de Thread, aquí dentro va lo que hará el hilo al arrancar
    @Override
    public void run(){
        // Hacemos 5 ciclos
        for(int i = 0; i < CICLOS_TOTALES; i++){
            ciclosRealizados++;
            String estadoValvula = "";

            // Comprobamos si en este ciclo toca abrir o cerrar
            if(estaAbierta){
                estadoValvula = "ABIERTA";
                // Sumamos los 2.5L solo cuando se abre
                litrosTotales = litrosTotales + LITROS_POR_APERTURA;
            } else {
                estadoValvula = "cerrada";
            }

            // LOG DEL RIEGO
            // Como esta clase hereda directamente de Thread, si tenemos el método getName() a nuestra disposición
            // directamente. No hace falta preguntar al currentThread()
            System.out.println("[" + getName() + "] Ciclo " + ciclosRealizados + " -> Válvula: " + estadoValvula);

            // CAMBIO DE ESTADO (El alternador)
            // Si estaba abierta (true), la ponemos a false (cerrada) para la próxima vuelta. Si estaba a false, pasa a
            // true
            estaAbierta = !estaAbierta;

            // ESPERAR
            // Detenemos el hilo 700ms usando Thread.sleep
            try{
                Thread.sleep(PERIODO_MS);
            } catch (InterruptedException e){
                return;
            }
        }
    }

    // MÉTODO INFORME
    // Devuelve un String con los ciclos totales y los litros gastados
    public String informe(){
        return "INFORME Riego -> Ciclos: "+ciclosRealizados+" | Litros gastados: " + litrosTotales + " L";
    }
}
