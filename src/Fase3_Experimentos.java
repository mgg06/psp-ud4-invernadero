public class Fase3_Experimentos {
    public static void main(String[] args){
        System.out.println("EXPERIMENTO A");

        // 1. Lanza un sensor en un hilo (usaremos el de luz por ejemplo)
        Luz sensorA = new Luz();
        Thread hiloA = new Thread(sensorA, "hilo-ExpA");
        // El hilo arranca y empieza a trabajar por su cuenta
        hiloA.start();

        // 2. Imprime su informe() sin esperar
        // El hilo main lee esta línea casi instantánemente después del start(), y el "hilo-ExpA" apenas está iniciandose
        System.out.println("Informe antes de esperar: "+sensorA.informe());

        // 3. Después espera
        // Usamos join() para que el hilo main se bloquee hasta que "hilo-ExpA" acabe (5 lecturas de 500ms)
        try{
            hiloA.join();
        } catch (InterruptedException e){
            System.out.println("Espera interrumpida");
        }

        // 4. Vuelve a imprimirlo
        // Ahora el "hilo-ExpA" ya ha terminado todo su trabajo
        System.out.println("Informe después de esperar: " + sensorA.informe());

        System.out.println("\n"+"EXPERIMENTO B");

        // 1. Crea un riego de 3 ciclos
        ProgramadoRiego riegoB = new ProgramadoRiego();
        // Modificamos los ciclos para este experimento
        riegoB.ciclosTotales = 3;

        // 2. Mide cuánto tarda
        long tiempoInicioB = System.currentTimeMillis();

        // 3. Llama a riego.run() en vez de riego.start()
        System.out.println("Llamando a run() directamente...");
        riegoB.run();

        // Calculamos el tiempo que ha tardado
        long tiempoFinB = System.currentTimeMillis();
        long tiempoTotalB = tiempoFinB - tiempoInicioB;
        System.out.println("Tiempo total experimento B: "+tiempoTotalB+" ms");

        // 4. Muestra riego.getState() al final
        // El método getState() nos devuelve en qué estado de su ciclo de vida está el hilo
        System.out.println("Estado del hilo riegoB al terminar: "+riegoB.getState());
    }
}
