
package org.model.concurrency;

public class SupervisorCentral {

    private boolean emergenciaActiva;

    public SupervisorCentral() {
        this.emergenciaActiva = false;
    }

    // Activar la emergencia
    public synchronized void activarEmergencia() {

        emergenciaActiva = true;

        System.out.println("¡ALERTA! Sobrecalentamiento detectado.");
        System.out.println("Todos los robots deben detenerse.");
    }

    // Los robots esperan si hay una emergencia
    public synchronized void esperarSiHayEmergencia(String nombreRobot) throws InterruptedException {

        while (emergenciaActiva) {
            System.out.println(nombreRobot + " está esperando por emergencia.");
            wait();
        }
    }

    // Resolver la emergencia
    public synchronized void resolverEmergencia() {

        emergenciaActiva = false;

        System.out.println("Emergencia resuelta.");
        System.out.println("Reanudando actividad de los robots.");

        notifyAll();
    }

    public synchronized boolean isEmergenciaActiva() {
        return emergenciaActiva;
    }
}
