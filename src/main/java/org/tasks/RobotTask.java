
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

                // Comprobar si existe una emergencia
                supervisorCentral.esperarSiHayEmergencia(nombre);

                System.out.println(nombre + " solicita una batería.");

                Bateria bateria = cintaTransportadora.recollirBateria();

                System.out.println(nombre + " ha recogido " + bateria);

                // Volver a comprobar antes de soldar
                supervisorCentral.esperarSiHayEmergencia(nombre);

                int estacion = -1;
                boolean estacionOcupada = false;

                try {
                    estacion = centralSoldadura.ocuparEstacion();
                    estacionOcupada = true;

                    System.out.println(nombre +
                            " está soldando en la estación " + (estacion + 1));

                    Thread.sleep(2000);

                    System.out.println(nombre +
                            " ha terminado de fabricar el coche " + i);

                } finally {
                    if (estacionOcupada) {
                        centralSoldadura.liberarEstacion(estacion);
                    }
                }

                // Comprobar antes de registrar el certificado
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

/* Orden
Comprobar emergencia

Recoger batería de la cinta

Solicitar estación de soldadura

Fabricar el coche

Liberar estación

Firmar certificado de calidad

Repetir hasta terminar los coches */
