

package org.view;

import org.model.concurrency.CentralSoldadura;
import org.model.concurrency.CintaTransportadora;
import org.model.concurrency.SupervisorCentral;

    public class ConsoleView {

        public void mostrarEstado(
                CentralSoldadura centralSoldadura,
                CintaTransportadora cintaTransportadora,
                SupervisorCentral supervisorCentral) {

            boolean[] estaciones =
                    centralSoldadura.getEstadoEstaciones();

            int ocupadas = 0;
// Donar un poco de orden para  el trabajo
            System.out.println();
            System.out.println("======================================");
            System.out.println("          ESTADO DE LA FÁBRICA");
            System.out.println("======================================");

            // Mostrar estaciones
            System.out.println("\nESTACIONES DE SOLDADURA:");

            for (int i = 0; i < estaciones.length; i++) {

                if (estaciones[i]) {
                    System.out.println("Estación " + (i + 1) + ": OCUPADA");
                    ocupadas++;
                } else {
                    System.out.println("Estación " + (i + 1) + ": LIBRE");
                }
            }

            System.out.println("Estaciones ocupadas: " + ocupadas
                    + "/" + estaciones.length);

            // Mostrar baterías
            System.out.println("\nBATERÍAS EN LA CINTA:");

            System.out.println("Normales: "
                    + cintaTransportadora.getCantidadBateriasNormales());

            System.out.println("Críticas: "
                    + cintaTransportadora.getCantidadBateriasCriticas());

            System.out.println("Total: "
                    + cintaTransportadora.getCantidadBaterias()
                    + "/" + cintaTransportadora.getCapacidad());

            // Mostrar emergencia
            System.out.println("\nESTADO DE SEGURIDAD:");

            if (supervisorCentral.isEmergenciaActiva()) {
                System.out.println("¡EMERGENCIA ACTIVA!");
            } else {
                System.out.println("Funcionamiento normal.");
            }

            System.out.println("======================================");
        }
    }
