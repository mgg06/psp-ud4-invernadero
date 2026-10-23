public class Fase2_Concurrente {
    public static void main(String[] args){
        System.out.println ("INICIO FASE 2: CONCURRENTE");

        // 1. Creamos objetos nuevos de los sensores y del riego. Temperatura, Humedad y Luz son tareas (runnable)
        // Riego ya es un hilo (Thread)
        Temperatura tareaTemp = new Temperatura();
        Humedad tareaHum = new Humedad();
        Luz tareaLuz = new Luz();
        ProgramadoRiego hiloRiego = new ProgramadoRiego();

        // 2. Metemos cada sensor en un Thread
        Thread hiloTemp = new Thread(tareaTemp, "sensor-Temperatura");
        Thread hiloHum = new Thread(tareaHum, "sensor-Humedad");
        Thread hiloLuz = new Thread(tareaLuz, "sensor-Luz");
        // Tomamos el tiempo de inicio
        long tiempoInicio = System.currentTimeMillis();

        // 3. Arrancamos los 4 hilos. Usamos start() para que se ejecuten en paralelo, nunca run()
        System.out.println("Arrancando todos los sistemas en paralelo");
        hiloTemp.start();
        hiloHum.start();
        hiloLuz.start();
        hiloRiego.start();

        // 4. El informe y el tiempo total solo pueden imprimirse cuando los cuatro hayan terminado. Usamos join() para
        // que el hilo main se bloquee y espere obligatoriamente a que terminen estos 4 hilos antes de seguir leyendo el
        // código
        try{
            hiloTemp.join();
            hiloHum.join();
            hiloLuz.join();
            hiloRiego.join();
        } catch (InterruptedException e){
            System.out.println("El hilo main fue interrumpido mientras esperaba");
        }

        // Una vez que los cuatro hilos han terminado, y main ha salido del join, tomamos el tiempo final
        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;

        // Imprimomos los informes finales
        System.out.println("\n"+"INFORMES FINALES CONCURRENTES");
        System.out.println(tareaTemp.informe());
        System.out.println(tareaHum.informe());
        System.out.println(tareaLuz.informe());
        System.out.println(hiloRiego.informe());

        // El tiempo total ahora será muchísimo menor, rondara el del dispositivo más lento, que es el riego, más o
        System.out.println("\nTIEMPO TOTAL CONCURRENTE: " + tiempoTotal + " ms");
    }
}
