/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la interfaz JugadorDAO para gestionar la 
 * persistencia de objetos Jugador en un archivo utilizando la 
 * serialización de objetos. Proporciona métodos para agregar, 
 * eliminar, modificar, buscar y obtener todos los jugadores.
 * 
 * @author Ivan Pollino
 */
public class JugadorObjectFileDAO implements JugadorDAO {

    /**
     * Ruta del archivo donde se almacenan los datos de los jugadores.
     */
    private static final String FILE_PATH = "DATOS/jugadoresObject.dat";

    /**
     * Constructor de la clase JugadorObjectFileDAO.
     * Verifica que el directorio donde se guardan los archivos de jugadores exista,
     * y si no, lo crea.
     */
    public JugadorObjectFileDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    /**
     * Agrega un nuevo jugador al archivo de objetos.
     * Este método obtiene la lista de jugadores existentes, 
     * añade el nuevo jugador y sobrescribe el archivo con la lista actualizada.
     * 
     * @param jugador El objeto Jugador que se agregará.
     */
    @Override
    public void agregarJugador(Jugador jugador) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        jugadores.add(jugador);
        escribirJugadores(jugadores);
    }

    /**
     * Elimina un jugador del archivo de objetos según su ID.
     * Este método obtiene la lista de jugadores existentes, 
     * elimina el jugador con el ID especificado y sobrescribe 
     * el archivo con la lista actualizada.
     * 
     * @param id El ID del jugador que se desea eliminar.
     */
    @Override
    public void eliminarJugador(int id) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        jugadores.removeIf(j -> j.getId() == id);
        escribirJugadores(jugadores);
    }

    /**
     * Modifica un jugador existente en el archivo de objetos.
     * Este método obtiene la lista de jugadores existentes, 
     * busca el jugador por su ID y lo reemplaza con la nueva información.
     * Luego sobrescribe el archivo con la lista actualizada.
     * 
     * @param jugador El jugador con los datos actualizados.
     */
    @Override
    public void modificarJugador(Jugador jugador) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getId() == jugador.getId()) {
                jugadores.set(i, jugador);
                break;
            }
        }
        escribirJugadores(jugadores);
    }

    /**
     * Busca un jugador en el archivo de objetos según su ID.
     * Este método retorna el jugador con el ID proporcionado o 
     * null si no se encuentra.
     * 
     * @param id El ID del jugador que se desea buscar.
     * @return El jugador encontrado o null si no se encuentra.
     */
    @Override
    public Jugador buscarJugador(int id) {
        return obtenerTodosLosJugadores().stream()
                .filter(j -> j.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todos los jugadores almacenados en el archivo de objetos.
     * Este método lee el archivo y devuelve una lista de todos los jugadores.
     * Si el archivo no existe o está vacío, retorna una lista vacía.
     * 
     * @return Una lista de objetos Jugador leídos del archivo.
     */
    @Override
    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return jugadores;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            jugadores = (List<Jugador>) ois.readObject();
        } catch (EOFException e) {
            // Fin del archivo, esto es normal en archivos vacíos
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    /**
     * Escribe la lista de jugadores en el archivo de objetos.
     * Este método sobrescribe el archivo con la lista proporcionada.
     * 
     * @param jugadores La lista de jugadores a escribir en el archivo.
     */
    private void escribirJugadores(List<Jugador> jugadores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(jugadores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
