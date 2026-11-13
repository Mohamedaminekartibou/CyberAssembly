package org.model.concurrency;



import java.util.concurrent.Semaphore;

public class CentralSoldadura {

    private final Semaphore semaforo;
    private final boolean[] estacionesOcupadas;

    public CentralSoldadura(int numeroEstaciones) {
        this.semaforo = new Semaphore(numeroEstaciones);
        this.estacionesOcupadas = new boolean[numeroEstaciones];
    }
}
