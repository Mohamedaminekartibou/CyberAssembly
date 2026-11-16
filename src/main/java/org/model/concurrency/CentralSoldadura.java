    
package org.model.concurrency;

import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

public class CentralSoldadura {

    private final Semaphore semaforo;
    private final boolean[] estacionesOcupadas;

    private final PriorityQueue<Solicitud> colaPrioridad;
    private long contadorSolicitudes = 0;

    public CentralSoldadura(int numeroEstaciones) {

        if (numeroEstaciones <= 0) {
            throw new IllegalArgumentException(
                    "Debe haber al menos una estación");
        }

        this.semaforo = new Semaphore(numeroEstaciones);
        this.estacionesOcupadas = new boolean[numeroEstaciones];

        this.colaPrioridad = new PriorityQueue<>((s1, s2) -> {

            // Las solicitudes críticas van primero
            if (s1.esCritica != s2.esCritica) {
                return s1.esCritica ? -1 : 1;
            }

            // Si tienen la misma prioridad, se respeta el orden
            return Long.compare(s1.orden, s2.orden);
        });
    }

    // Solicitar una estación con prioridad
    public int ocuparEstacion(boolean bateriaCritica)
            throws InterruptedException {

        Solicitud solicitud;

        synchronized (this) {

            solicitud = new Solicitud(
                    bateriaCritica,
                    contadorSolicitudes++
            );

            colaPrioridad.add(solicitud);

            if (bateriaCritica) {
                System.out.println(
                        "Prioridad de emergencia: solicitud de estación para batería crítica.");
            }
        }

        try {

            synchronized (this) {

                while (colaPrioridad.peek() != solicitud
                        || !semaforo.tryAcquire()) {

                    wait();
                }

                colaPrioridad.poll();

                for (int i = 0; i < estacionesOcupadas.length; i++) {

                    if (!estacionesOcupadas[i]) {

                        estacionesOcupadas[i] = true;

                        notifyAll();

                        return i;
                    }
                }

                semaforo.release();
                notifyAll();

                throw new IllegalStateException(
                        "No hay estaciones disponibles");
            }

        } catch (InterruptedException e) {

            synchronized (this) {
                colaPrioridad.remove(solicitud);
                notifyAll();
            }

            throw e;
        }
    }

    // Mantener compatibilidad con solicitudes normales
    public int ocuparEstacion() throws InterruptedException {
        return ocuparEstacion(false);
    }

    // Liberar una estación
    public void liberarEstacion(int numeroEstacion) {

        synchronized (this) {

            if (numeroEstacion < 0
                    || numeroEstacion >= estacionesOcupadas.length) {

                throw new IllegalArgumentException(
                        "Estación no válida");
            }

            if (!estacionesOcupadas[numeroEstacion]) {

                throw new IllegalStateException(
                        "La estación ya está libre");
            }

            estacionesOcupadas[numeroEstacion] = false;

            semaforo.release();

            notifyAll();
        }
    }

    public synchronized boolean[] getEstadoEstaciones() {
        return estacionesOcupadas.clone();
    }

    public int getNumeroEstaciones() {
        return estacionesOcupadas.length;
    }

    // Clase interna para guardar las solicitudes
    private static class Solicitud {

        private final boolean esCritica;
        private final long orden;

        public Solicitud(boolean esCritica, long orden) {
            this.esCritica = esCritica;
            this.orden = orden;
        }
    }
}
