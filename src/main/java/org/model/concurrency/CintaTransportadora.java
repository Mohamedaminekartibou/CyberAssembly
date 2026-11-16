
package org.model.concurrency;

import org.model.Bateria;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CintaTransportadora {

    private final Queue<Bateria> bateriasCriticas;
    private final Queue<Bateria> bateriasNormales;

    private final int capacidad;

    private final Lock lock;
    private final Condition cintaNoPlena;
    private final Condition cintaNoBuida;

    public CintaTransportadora(int capacidad) {

        if (capacidad <= 0) {
            throw new IllegalArgumentException(
                    "La capacidad debe ser mayor que cero."
            );
        }

        this.capacidad = capacidad;

        this.bateriasCriticas = new LinkedList<>();
        this.bateriasNormales = new LinkedList<>();

        this.lock = new ReentrantLock();
        this.cintaNoPlena = lock.newCondition();
        this.cintaNoBuida = lock.newCondition();
    }

    public void afegirBateria(Bateria bateria)
            throws InterruptedException {

        lock.lock();

        try {
            while (getCantidadBateriasInterno() == capacidad) {
                cintaNoPlena.await();
            }

            if (bateria.esCritica()) {
                bateriasCriticas.add(bateria);

                System.out.println(
                        "Batería crítica añadida: " + bateria
                );

            } else {
                bateriasNormales.add(bateria);

                System.out.println(
                        "Batería normal añadida: " + bateria
                );
            }

            cintaNoBuida.signal();

        } finally {
            lock.unlock();
        }
    }

    public Bateria recollirBateria()
            throws InterruptedException {

        lock.lock();

        try {
            while (getCantidadBateriasInterno() == 0) {
                cintaNoBuida.await();
            }

            Bateria bateria;

            if (!bateriasCriticas.isEmpty()) {
                bateria = bateriasCriticas.poll();

                System.out.println(
                        "Prioridad: recogiendo batería crítica."
                );

            } else {
                bateria = bateriasNormales.poll();
            }

            System.out.println("Batería recogida: " + bateria);

            cintaNoPlena.signal();

            return bateria;

        } finally {
            lock.unlock();
        }
    }

    private int getCantidadBateriasInterno() {
        return bateriasCriticas.size()
                + bateriasNormales.size();
    }

    public int getCantidadBaterias() {

        lock.lock();

        try {
            return getCantidadBateriasInterno();

        } finally {
            lock.unlock();
        }
    }

    public int getCapacidad() {
        return capacidad;
    }




    public int getCantidadBateriasCriticas() {

        lock.lock();

        try {
            return bateriasCriticas.size();

        } finally {
            lock.unlock();
        }
    }

    public int getCantidadBateriasNormales() {

        lock.lock();

        try {
            return bateriasNormales.size();

        } finally {
            lock.unlock();
        }
    }



}
