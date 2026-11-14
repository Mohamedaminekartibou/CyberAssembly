
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

    

    public synchronized boolean isEmergenciaActiva() {
        return emergenciaActiva;
    }
}
