package org.model.concurrency;



import java.util.concurrent.Semaphore;

public class CentralSoldadura {

    private final Semaphore semaforo;
    private final boolean[] estacionesOcupadas;

    public CentralSoldadura(int numeroEstaciones) {
        this.semaforo = new Semaphore(numeroEstaciones);
        this.estacionesOcupadas = new boolean[numeroEstaciones];
    }

    // La parte que se encarga ocupar una estacion
    public int ocuparEstacion() throws InterruptedException {

        semaforo.acquire();

        synchronized (this) {

            for (int i = 0; i < estacionesOcupadas.length; i++) {

                if (!estacionesOcupadas[i]) {

                    estacionesOcupadas[i] = true;

                    return i;
                }
            }
        }

        semaforo.release();

        throw new IllegalStateException("No hay estaciones disponibles");
    }


}
