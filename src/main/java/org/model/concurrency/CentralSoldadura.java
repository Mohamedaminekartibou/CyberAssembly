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

                if (!estacionesOcupadas[i]) {  // si no esta ocupada pasa = !true= false eso es igual a no ocupada

                    estacionesOcupadas[i] = true;

                    return i;
                }
            }
        }

        semaforo.release();

        throw new IllegalStateException("No hay estaciones disponibles");
    }



    public void liberarEstacion(int numeroEstacion) {

        synchronized (this) { // serve para que no entren mas de un hilo ya que cuando entra uno bloquea

            if (numeroEstacion < 0 || numeroEstacion >= estacionesOcupadas.length) {

                throw new IllegalArgumentException("Estación no válida");
            }

            if (!estacionesOcupadas[numeroEstacion]) { // si esta libre que lançe el error
                throw new IllegalStateException("La estación ya está libre");
            }

            estacionesOcupadas[numeroEstacion] = false;
        }

        semaforo.release();
    }



}
