
package org.tasks;

import org.model.Bateria;
import org.model.concurrency.CentralSoldadura;
import org.model.concurrency.CintaTransportadora;
import org.model.concurrency.SupervisorCentral;
import org.model.concurrency.TaulerRegistreCentral;

public class RobotTask implements Runnable {

    private final String nombre;
    private final int cochesAFabricar;

    private final CentralSoldadura centralSoldadura;
    private final CintaTransportadora cintaTransportadora;
    private final TaulerRegistreCentral taulerRegistreCentral;
    private final SupervisorCentral supervisorCentral;

    public RobotTask(String nombre, int cochesAFabricar,
                     CentralSoldadura centralSoldadura,
                     CintaTransportadora cintaTransportadora,
                     TaulerRegistreCentral taulerRegistreCentral,
                     SupervisorCentral supervisorCentral) {

        this.nombre = nombre;
        this.cochesAFabricar = cochesAFabricar;
        this.centralSoldadura = centralSoldadura;
        this.cintaTransportadora = cintaTransportadora;
        this.taulerRegistreCentral = taulerRegistreCentral;
        this.supervisorCentral = supervisorCentral;
    }

    @Override
    public void run() {

        System.out.println(nombre + " ha comenzado a trabajar.");

        try {

            for (int i = 1; i <= cochesAFabricar; i++) {

                supervisorCentral.esperarSiHayEmergencia(nombre);

                System.out.println(nombre + " solicita una batería.");

                Bateria bateria = cintaTransportadora.recollirBateria();

                System.out.println(nombre + " ha recogido " + bateria);

                supervisorCentral.esperarSiHayEmergencia(nombre);

                int estacion = -1;
                boolean estacionOcupada = false;

                try {

                    // Comprobar si la batería es crítica
                    boolean bateriaCritica = bateria.esCritica();

                    // Solicitar estación indicando la prioridad
                    estacion = centralSoldadura.ocuparEstacion(
                            bateriaCritica
                    );

                    estacionOcupada = true;

                    if (bateriaCritica) {
                        System.out.println(
                                nombre + " tiene prioridad por llevar una batería crítica."
                        );
                    }

                    System.out.println(
                            nombre + " está soldando en la estación "
                                    + (estacion + 1)
                    );

                    Thread.sleep(2000);

                    System.out.println(
                            nombre + " ha terminado de fabricar el coche " + i
                    );

                } finally {

                    if (estacionOcupada) {
                        centralSoldadura.liberarEstacion(estacion);
                    }
                }

                supervisorCentral.esperarSiHayEmergencia(nombre);

                taulerRegistreCentral.registrarCertificat(nombre);
            }

        } catch (InterruptedException e) {

            System.out.println(nombre + " ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }

        System.out.println(nombre + " ha terminado su trabajo.");
    }
}
