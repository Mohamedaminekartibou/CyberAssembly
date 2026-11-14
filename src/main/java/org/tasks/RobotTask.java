
package org.tasks;

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
}
