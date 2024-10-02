/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Vespertino
 */
public class Jugador {
    private int id;
    private String nick;
    private int experiencia;
    private int nivelVida;
    private int monedas;

    public Jugador(int id, String nick, int experiencia, int nivelVida, int monedas) {
        this.id = id;
        this.nick = nick;
        this.experiencia = experiencia;
        this.nivelVida = nivelVida;
        this.monedas = monedas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getNivelVida() {
        return nivelVida;
    }

    public void setNivelVida(int nivelVida) {
        this.nivelVida = nivelVida;
    }

    public int getMonedas() {
        return monedas;
    }

    public void setMonedas(int monedas) {
        this.monedas = monedas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Jugador other = (Jugador) obj;
        return this.id == other.id;
    }

    

    @Override
    public String toString() {
        return "[ID: " + id + ", Nick: " + nick + ", Experiencia: " + experiencia +
                ", Nivel de Vida: " + nivelVida + ", Monedas: " + monedas + "]";
    }
}


