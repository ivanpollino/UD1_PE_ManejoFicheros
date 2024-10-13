/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;

/**
 * Clase que representa a un jugador en el sistema.
 * Implementa la interfaz Serializable para permitir la 
 * persistencia del objeto en archivos.
 * 
 * @author Ivan Pollino
 */
public class Jugador implements Serializable {
    private int id;
    private String nick;
    private int experiencia;
    private int nivelVida;
    private int monedas;

    /**
     * Constructor que inicializa un nuevo objeto Jugador.
     * 
     * @param id El ID del jugador.
     * @param nick El apodo del jugador.
     * @param experiencia La experiencia del jugador.
     * @param nivelVida El nivel de vida del jugador.
     * @param monedas La cantidad de monedas del jugador.
     */
    public Jugador(int id, String nick, int experiencia, int nivelVida, int monedas) {
        this.id = id;
        this.nick = nick;
        this.experiencia = experiencia;
        this.nivelVida = nivelVida;
        this.monedas = monedas;
    }

    /**
     * Obtiene el ID del jugador.
     * 
     * @return El ID del jugador.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del jugador.
     * 
     * @param id El nuevo ID del jugador.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el apodo del jugador.
     * 
     * @return El apodo del jugador.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Establece el apodo del jugador.
     * 
     * @param nick El nuevo apodo del jugador.
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Obtiene la experiencia del jugador.
     * 
     * @return La experiencia del jugador.
     */
    public int getExperiencia() {
        return experiencia;
    }

    /**
     * Establece la experiencia del jugador.
     * 
     * @param experiencia La nueva experiencia del jugador.
     */
    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    /**
     * Obtiene el nivel de vida del jugador.
     * 
     * @return El nivel de vida del jugador.
     */
    public int getNivelVida() {
        return nivelVida;
    }

    /**
     * Establece el nivel de vida del jugador.
     * 
     * @param nivelVida El nuevo nivel de vida del jugador.
     */
    public void setNivelVida(int nivelVida) {
        this.nivelVida = nivelVida;
    }

    /**
     * Obtiene la cantidad de monedas del jugador.
     * 
     * @return La cantidad de monedas del jugador.
     */
    public int getMonedas() {
        return monedas;
    }

    /**
     * Establece la cantidad de monedas del jugador.
     * 
     * @param monedas La nueva cantidad de monedas del jugador.
     */
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
