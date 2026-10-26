public class Fase1_Secuencial {
    public static void main(String[] args) {
        System.out.println("INICIO FASE 1: SECUENCIAL");
        // 1. Creamos las instancias de nuestros dispositivos
        Temperatura sensorTemp = new Temperatura();
        Humedad sensorHum = new Humedad();
        Luz sensorLuz = new Luz();
        ProgramadorRiego riego = new ProgramadorRiego();

        // 2. Tomamos el tiempo exacto antes de empezar. Así calculamos cuánto tarda todo
        long tiempoInicio = System.currentTimeMillis();

        // 3. Ejecutamos las tareas una detrás de otra
        // Llamos a run(), no a start(). Si llamamos a run() directamente no crea hilo, todo esto lo hará el hilo
        // principal
        System.out.println("\n"+"Arrancando temperatura");
        sensorTemp.run();
        System.out.println("\n"+"Arrancando humedad");
        sensorHum.run();
        System.out.println("\n"+"Arrancando luz");
        sensorLuz.run();
        System.out.println("\n"+"Arrancando riego");
        riego.run();

        // 4. Tomamos el tiempo exacto al terminar y calculamos el total
        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;

        // 5. Imprimimos los informes de todos los dispositivos
        System.out.println("\n"+"INFORMES FINALES");
        System.out.println(sensorTemp.informe());
        System.out.println(sensorHum.informe());
        System.out.println(sensorLuz.informe());
        System.out.println(riego.informe());

        // 6. Mostramos el tiempo total invertido
        // El tiempo total es la suma de todos los tiempos (400*5 + 600*5 + 500*5 + 700*5 = 11.000 ms aprox)
        System.out.println("\nTIEMPO TOTAL SECUENCIAL: " + tiempoTotal + " ms");

        // ¿Qué nombre de hilo aparece en todo el log? ¿Por qué?
        // Siempre sale el hilo "main" por que al llamar a run() no hemos creado hilos nuevos, el programa pricipal
        // (main) se lo está tragando todo solo
    }
}

