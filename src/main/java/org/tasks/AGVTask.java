
package org.tasks;

import org.model.concurrency.CintaTransportadora;

public class AGVTask implements Runnable {

    private final String nombre;
    private final int bateriasATransportar;
    private final CintaTransportadora cintaTransportadora;

    public AGVTask(String nombre, int bateriasATransportar,
                   CintaTransportadora cintaTransportadora) {

        this.nombre = nombre;
        this.bateriasATransportar = bateriasATransportar;
        this.cintaTransportadora = cintaTransportadora;
    }

    @Override
    public void run() {

        }
}
