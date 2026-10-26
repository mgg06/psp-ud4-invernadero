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
            // Salimos para no mostrar datos finales sin haber terminado la espera
            return;
        }

        // 4. Vuelve a imprimirlo
        // Ahora el "hilo-ExpA" ya ha terminado todo su trabajo
        System.out.println("Informe después de esperar: " + sensorA.informe());

        System.out.println("\n"+"EXPERIMENTO B");

        // 1. Crea un riego de 3 ciclos
        ProgramadorRiego riegoB = new ProgramadorRiego();
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

        System.out.println("\n"+"EXPERIMENTO C");

        // 1. Arranca un riego
        ProgramadorRiego riegoC = new ProgramadorRiego();
        // Le ponemos 1 solo ciclo para que termine rápido
        riegoC.ciclosTotales = 1;

        System.out.println("Arrancando riegoC por primera vez...");
        riegoC.start();

        // 2. Espera a que termine
        try{
            riegoC.join();
        } catch (InterruptedException e){
            System.out.println("Espera interrumpida");
            // Salimos para no mostrar datos finales sin haber terminado la espera
            return;
        }

        System.out.println("El riego C ha terminado. Su estado actual es: "+riegoC.getState());

        // 3. Vuelve a llamar a start() sobre el mismo objeto
        System.out.println("Intentando arrancar riego C por segunda vez...");

        // Usamos try-catch para "capturar" la excepción que pide el enunciado
        try{
            riegoC.start();
            // Capturamos la excepción de intentar arrancar otra vez el mismo hilo
        }catch(IllegalThreadStateException e){
            // Le decimos que la capture, que la muestre y que explique que significa
            System.out.println("EXCEPCIÓN CAPTURADA");
            // Muestra el nombre del error
            System.out.println("Tipo de excepción: "+e.getClass().getSimpleName());
            System.out.println("Explicación: el mismo hilo no se puede arrancar dos veces. Para volver a regar hay que crear otro ProgramadorRiego.");

            // CONTESTACIÓN: Salta un "IllegalThreadStateException" Un hilo que ha llegado a estado TERMINATED, es
            // un hilo muerto que no puede resucitar. Si se quiere "volver a regar", se tiene que crear un
            // ProgramadorRiego() nuevo

        }
    }
}

