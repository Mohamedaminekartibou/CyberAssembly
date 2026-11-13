
package org.model.concurrency;

import java.util.concurrent.Semaphore;

public class TaulerRegistreCentral {

    private final Semaphore semaforo;

    public TaulerRegistreCentral() {
        this.semaforo = new Semaphore(1);  // lo que enviamos pasa en el semaforo
    }

    public void registrarCertificat(String nombreRobot) throws InterruptedException {

        semaforo.acquire();

        try {
            System.out.println(nombreRobot + " está firmando el certificado .");

            Thread.sleep(1000);

            System.out.println(nombreRobot + "  termino de firmar el certificado.");

        } finally {
            semaforo.release();
        }
    }
}
