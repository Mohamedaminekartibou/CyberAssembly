
package org.model;

public class Bateria {

    private final String identificador;
    private final int porcentajeCarga;

    public Bateria(String identificador, int porcentajeCarga) {

        if (porcentajeCarga < 0 || porcentajeCarga > 100) {
            throw new IllegalArgumentException(
                    "El porcentaje debe estar entre 0 y 100."
            );
        }

        this.identificador = identificador;
        this.porcentajeCarga = porcentajeCarga;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getPorcentajeCarga() {
        return porcentajeCarga;
    }

    public boolean esCritica() {
        return porcentajeCarga < 10;
    }

    @Override
    public String toString() {
        return identificador + " (" + porcentajeCarga + "%)";
    }
}
