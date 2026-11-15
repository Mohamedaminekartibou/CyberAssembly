
package org.view;

import org.model.concurrency.CentralSoldadura;
import org.model.concurrency.CintaTransportadora;
import org.model.concurrency.SupervisorCentral;

public class ConsoleView {

    public void mostrarEstado(
            CentralSoldadura centralSoldadura,
            CintaTransportadora cintaTransportadora,
            SupervisorCentral supervisorCentral) {



        System.out.println("       ESTADO DE LA FÁBRICA");


        // Mostrar estaciones de soldadura
        boolean[] estaciones = centralSoldadura.getEstadoEstaciones();

        System.out.println("\nESTACIONES DE SOLDADURA:");

        for (int i = 0; i < estaciones.length; i++) {

            if (estaciones[i]) {
                System.out.println("Estación " + (i + 1) + ": OCUPADA");
            } else {
                System.out.println("Estación " + (i + 1) + ": LIBRE");
            }
        }

        // Mostrar baterías disponibles
        System.out.println("\nBATERÍAS EN LA CINTA: " + cintaTransportadora.getCantidadBaterias()
                + "/" + cintaTransportadora.getCapacidad());

        // Mostrar estado de emergencia
        System.out.println("\nESTADO DE SEGURIDAD:");

        if (supervisorCentral.isEmergenciaActiva()) {
            System.out.println("¡EMERGENCIA ACTIVA!");
        } else {
            System.out.println("Funcionamiento normal.");
        }

        System.out.println("");
        System.out.println();
    }
}
