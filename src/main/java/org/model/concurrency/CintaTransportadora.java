
package org.model.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CintaTransportadora {

    private final Queue<String> baterias;
    private final int capacidad;

    private final Lock lock;
    private final Condition cintaNoPlena;
    private final Condition cintaNoBuida;

    public CintaTransportadora(int capacidad) {

        this.capacidad = capacidad;
        this.baterias = new LinkedList<>();

        this.lock = new ReentrantLock();
        this.cintaNoPlena = lock.newCondition();
        this.cintaNoBuida = lock.newCondition();
    }

    // Los AGV introducen baterías
    public void afegirBateria(String bateria)
            throws InterruptedException {

        lock.lock();

        try {

            while (baterias.size() == capacidad) {
                cintaNoPlena.await();
            }

            baterias.add(bateria);

            System.out.println("Batería añadida: " + bateria);

            cintaNoBuida.signal();

        } finally {
            lock.unlock();
        }
    }

    // Los robots recogen baterías
    public String recollirBateria()
            throws InterruptedException {

        lock.lock();

        try {

            while (baterias.isEmpty()) {
                cintaNoBuida.await();
            }

            String bateria = baterias.poll();

            System.out.println("Batería recogida: " + bateria);

            cintaNoPlena.signal();

            return bateria;

        } finally {
            lock.unlock();
        }
    }

    public int getCantidadBaterias() {

        lock.lock();

        try {
            return baterias.size();

        } finally {
            lock.unlock();
        }
    }

    public int getCapacidad() {
        return capacidad;
    }
}
