public class Fase4_Vigilante {
    public static void main(String[] args){
        System.out.println("INICIO FASE 4: EL VIGILANTE DEMONIO");

        // 1. Creamos las tareas de los sensores
        Temperatura tareaTemp = new Temperatura();
        Humedad tareaHum = new Humedad();
        Luz tareaLuz = new Luz();

        // 2. Creamos el hilo del riego (este no es tarea, es hilo directo)
        ProgramadoRiego hiloRiego = new ProgramadoRiego();

        // 3. Metemos las tareas en hilos para poder monitorizar su estado
        Thread hiloTemp = new Thread(tareaTemp,"sensor-Temperatura");
        Thread hiloHum = new Thread(tareaHum,"sensor-Humedad");
        Thread hiloLuz = new Thread(tareaLuz,"sensor-Luz");

        // CREACIÓN DEL HILO VIGILANTE (usando la interfaz Runnable como clase anónima)
        // Para no crear un archivo nuevo solo para el vigilante, lo creamos directamente aquí
        Thread hiloVigilante = new Thread(new Runnable(){
            @Override
            public void run(){
                // Bucle infinito
                while(true){
                    System.out.println("\n"+"[VIGILANTE] "+"ESTADOS ACTUALES");
                    // Mostramos el estado de los cuatro hilos del invernadero usando getState()
                    System.out.println("Temperatura: " + hiloTemp.getState());
                    System.out.println("Humedad: " + hiloHum.getState());
                    System.out.println("Luz: " + hiloLuz.getState());
                    System.out.println("Riego: " + hiloRiego.getState());

                    // El vigilante espera 300 ms antes de volver a espiar
                    try{
                        Thread.sleep(300);
                    } catch(InterruptedException e){
                        // Si interrumpen al vigilante, se apaga
                        return;
                    }
                }
            }
        }, "Vigilante");

        // Para que el bucle while(true) no deje el programa bloqueado para siempre, convertimos a este hilo en un
        // demonio. Así Java lo apagará de golpe cuando terminen los sensores y el riego
        hiloVigilante.setDaemon(true);

        // Arrancamos el vigilante antes que nadie para que lo grabe todo
        hiloVigilante.start();

        // Mostramos los estados antes de arrancar los hilos
        System.out.println("\n"+"[main] Estados antes del start():");
        // Nos sale NEW
        System.out.println("Temperatura: " + hiloTemp.getState());
        System.out.println("Riego: " + hiloRiego.getState());

        // Arrancamos los dispositivos
        hiloTemp.start();
        hiloHum.start();
        hiloLuz.start();
        hiloRiego.start();

        // Esperamos a que terminen todos usando join()
        try{
            hiloTemp.join();
            hiloHum.join();
            hiloLuz.join();
            hiloRiego.join();
        }catch(InterruptedException e){
            System.out.println("Main interrumpido");
        }

        // Mostramos los estados después de esperarlos
        System.out.println("\n"+"[main] Estados después del join():");
        // Nos sale terminated
        System.out.println("Temperatura: " + hiloTemp.getState());
        System.out.println("Riego: " + hiloRiego.getState());

        // En cuanto el hilo main termine esta línea, el programa se va a acabar. Como hiloVigilante es un demonio,
        // se va a terminar al instante, rompiendo el bucle infinito y cerrando el programa sin problemas

    }
}
