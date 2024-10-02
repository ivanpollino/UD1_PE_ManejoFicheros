/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

/**
 *
 * @author Vespertino
 */
import java.util.List;

public interface JugadorDAO {
    void agregarJugador(Jugador jugador);
    void eliminarJugador(int id);
    void modificarJugador(Jugador jugador);
    Jugador buscarJugador(int id);
    List<Jugador> obtenerTodosLosJugadores();
}

