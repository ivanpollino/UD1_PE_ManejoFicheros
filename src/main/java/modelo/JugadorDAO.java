/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

import java.util.List;

/**
 * Interfaz para la gestión de operaciones relacionadas con los jugadores.
 * Define métodos para agregar, eliminar, modificar, buscar y obtener 
 * todos los jugadores en el sistema de almacenamiento.
 * 
 * @author Ivan Pollino
 */
public interface JugadorDAO {
    
    /**
     * Agrega un nuevo jugador al sistema de almacenamiento.
     * 
     * @param jugador El objeto Jugador que se desea agregar.
     */
    void agregarJugador(Jugador jugador);
    
    /**
     * Elimina un jugador del sistema de almacenamiento según su ID.
     * 
     * @param id El ID del jugador que se desea eliminar.
     */
    void eliminarJugador(int id);
    
    /**
     * Modifica los datos de un jugador existente en el sistema de almacenamiento.
     * 
     * @param jugador El objeto Jugador con los datos actualizados.
     */
    void modificarJugador(Jugador jugador);
    
    /**
     * Busca un jugador en el sistema de almacenamiento según su ID.
     * 
     * @param id El ID del jugador que se desea buscar.
     * @return El objeto Jugador encontrado o null si no se encuentra.
     */
    Jugador buscarJugador(int id);
    
    /**
     * Obtiene una lista de todos los jugadores almacenados en el sistema.
     * 
     * @return Una lista de objetos Jugador.
     */
    List<Jugador> obtenerTodosLosJugadores();
}
