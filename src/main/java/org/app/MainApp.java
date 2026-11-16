
package org.app;

import java.util.concurrent.atomic.AtomicBoolean;

import org.model.concurrency.CentralSoldadura;
import org.model.concurrency.CintaTransportadora;
import org.model.concurrency.SupervisorCentral;
import org.model.concurrency.TaulerRegistreCentral;
import org.tasks.AGVTask;
import org.tasks.RobotTask;
import org.view.ConsoleView;

public class MainApp {

    public static void main(String[] args) {

        // Crear los recursos compartidos de la fábrica
        CentralSoldadura centralSoldadura =
                new CentralSoldadura(3);

        CintaTransportadora cintaTransportadora =
                new CintaTransportadora(3);

        TaulerRegistreCentral taulerRegistreCentral =
                new TaulerRegistreCentral();

        SupervisorCentral supervisorCentral =
                new SupervisorCentral();

        // Crear la vista de consola
        ConsoleView consoleView = new ConsoleView();

        System.out.println("INICIO DE LA FÁBRICA");

        // Crear las tareas de los robots
        RobotTask robot1 = new RobotTask(
                "Robot 1",
                2,
                centralSoldadura,
                cintaTransportadora,
                taulerRegistreCentral,
                supervisorCentral
        );

        RobotTask robot2 = new RobotTask(
                "Robot 2",
                2,
                centralSoldadura,
                cintaTransportadora,
                taulerRegistreCentral,
                supervisorCentral
        );

        // Crear las tareas de los AGV
        AGVTask agv1 = new AGVTask(
                "AGV 1",
                2,
                cintaTransportadora
        );

        AGVTask agv2 = new AGVTask(
                "AGV 2",
                2,
                cintaTransportadora
        );

        // Crear los hilos
        Thread hiloRobot1 = new Thread(robot1);
        Thread hiloRobot2 = new Thread(robot2);

        Thread hiloAGV1 = new Thread(agv1);
        Thread hiloAGV2 = new Thread(agv2);

        // Controlar el funcionamiento del panel
        AtomicBoolean panelActivo = new AtomicBoolean(true);

        // Crear el hilo del panel de control
        Thread hiloPanel = new Thread(() -> {

            while (panelActivo.get()) {

                consoleView.mostrarEstado(
                        centralSoldadura,
                        cintaTransportadora,
                        supervisorCentral
                );

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

        }, "Panel-Control");

        // Iniciar los hilos de los AGV
        hiloAGV1.start();
        hiloAGV2.start();

        // Iniciar los hilos de los robots
        hiloRobot1.start();
        hiloRobot2.start();

        // Crear el hilo de emergencia
        Thread hiloEmergencia = new Thread(() -> {

            try {
                Thread.sleep(1500);

                supervisorCentral.activarEmergencia();

                Thread.sleep(4000);

                supervisorCentral.resolverEmergencia();

            } catch (InterruptedException e) {
                System.out.println(
                        "El supervisor ha sido interrumpido."
                );
                Thread.currentThread().interrupt();
            }

        }, "Supervisor-Emergencia");

        hiloEmergencia.start();

        // Iniciar el panel de control
        hiloPanel.start();

        // Esperar a que todos los trabajadores terminen
        try {
            hiloAGV1.join();
            hiloAGV2.join();

            hiloRobot1.join();
            hiloRobot2.join();

            hiloEmergencia.join();

        } catch (InterruptedException e) {
            System.out.println(
                    "El programa principal ha sido interrumpido."
            );

            Thread.currentThread().interrupt();

        } finally {

            // Detener el panel de control
            panelActivo.set(false);
        }

        // Esperar a que termine el panel
        try {
            hiloPanel.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Mostrar el estado final
        System.out.println("\nFÁBRICA FINALIZADA");

        consoleView.mostrarEstado(
                centralSoldadura,
                cintaTransportadora,
                supervisorCentral
        );
    }
}
