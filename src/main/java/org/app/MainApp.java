
package org.app;

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


        System.out.println("     INICIO DE LA FÁBRICA");

        consoleView.mostrarEstado(
                centralSoldadura,
                cintaTransportadora,
                supervisorCentral
        );

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

        // Iniciar los hilos de los AGV
        hiloAGV1.start();
        hiloAGV2.start();

        // Iniciar los hilos de los robots
        hiloRobot1.start();
        hiloRobot2.start();

        // Esperar a que todos los hilos terminen
        try {
            hiloAGV1.join();
            hiloAGV2.join();

            hiloRobot1.join();
            hiloRobot2.join();

        } catch (InterruptedException e) {
            System.out.println("El programa principal ha sido interrumpido.");
            Thread.currentThread().interrupt();
        }

        // Mostrar el estado final
        System.out.println("\n");
        System.out.println("       FÁBRICA FINALIZADA");
        System.out.println("");

        consoleView.mostrarEstado(
                centralSoldadura,
                cintaTransportadora,
                supervisorCentral
        );
    }
}
