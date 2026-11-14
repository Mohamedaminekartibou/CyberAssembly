
package org.tasks;

import org.model.concurrency.CintaTransportadora;

public class AGVTask implements Runnable {

    private final String nombre;
    private final int bateriasATransportar;
    private final CintaTransportadora cintaTransportadora;

    public AGVTask(String nombre, int bateriasATransportar, CintaTransportadora cintaTransportadora) {

        this.nombre = nombre;
        this.bateriasATransportar = bateriasATransportar;
        this.cintaTransportadora = cintaTransportadora;
    }

    @Override
    public void run() {

        System.out.println(nombre + " ha comenzado a trabajar.");

        try {
            for (int i = 1; i <= bateriasATransportar; i++) {

                String bateria = nombre + "-Bateria-" + i;

                System.out.println(nombre + " está transportando "
                        + bateria);

                Thread.sleep(1000);

                cintaTransportadora.afegirBateria(bateria);

                System.out.println(nombre + " ha dejado "
                        + bateria + " en la cinta.");
            }

        } catch (InterruptedException e) {
            System.out.println(nombre + " ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }

        System.out.println(nombre + " ha terminado su trabajo.");
    }
}
