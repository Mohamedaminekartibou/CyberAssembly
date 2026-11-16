
package org.view;

import org.model.concurrency.CentralSoldadura;
import org.model.concurrency.CintaTransportadora;
import org.model.concurrency.SupervisorCentral;

public class ConsoleView {

    public synchronized void mostrarEstado(
            CentralSoldadura centralSoldadura,
            CintaTransportadora cintaTransportadora,
            SupervisorCentral supervisorCentral) {

        boolean[] estaciones =
                centralSoldadura.getEstadoEstaciones();

        int ocupadas = 0;

        System.out.println();
        System.out.println("ESTADO ACTUAL DE LA FÁBRICA");
        System.out.println("---------------------------");

        // Mostrar estaciones de soldadura
        System.out.println("\nEstaciones de soldadura:");

        for (int i = 0; i < estaciones.length; i++) {

            if (estaciones[i]) {
                System.out.println(
                        "  Estación " + (i + 1) + ": Ocupada"
                );
                ocupadas++;
            } else {
                System.out.println(
                        "  Estación " + (i + 1) + ": Libre"
                );
            }
        }

        System.out.println(
                "  Total ocupadas: "
                + ocupadas + "/" + estaciones.length
        );

        // Mostrar baterías de la cinta
        System.out.println("\nCinta transportadora:");

        System.out.println(
                "  Baterías normales: "
                + cintaTransportadora.getCantidadBateriasNormales()
        );

        System.out.println(
                "  Baterías críticas: "
                + cintaTransportadora.getCantidadBateriasCriticas()
        );

        System.out.println(
                "  Total de baterías: "
                + cintaTransportadora.getCantidadBaterias()
                + "/" + cintaTransportadora.getCapacidad()
        );

        // Mostrar estado de seguridad
        System.out.println("\nSeguridad:");

        if (supervisorCentral.isEmergenciaActiva()) {
            System.out.println("  ¡EMERGENCIA ACTIVA!");
        } else {
            System.out.println("  Funcionamiento normal");
        }

        System.out.println("---------------------------");
    }
}
