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
 * persistencia de objetos Jugador en un archivo de texto.
 * Proporciona métodos para agregar, eliminar, modificar, buscar 
 * y obtener todos los jugadores.
 * 
 * @author Ivan Pollino
 */
public class JugadorTextFileDAO implements JugadorDAO {

    /**
     * Ruta del archivo donde se almacenan los datos de los jugadores.
     */
    private static final String FILE_PATH = "DATOS/jugadores.txt";

    /**
     * Constructor de la clase JugadorTextFileDAO.
     * Verifica que el directorio donde se guardan los archivos de jugadores exista,
     * y si no, lo crea.
     */
    public JugadorTextFileDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    /**
     * Agrega un nuevo jugador al archivo de texto.
     * Este método asigna automáticamente un ID al nuevo jugador, basado en el último
     * jugador existente en el archivo. Si el archivo está vacío, el ID será 1. Luego,
     * el jugador se agrega al archivo de forma persistente.
     * 
     * @param jugador El objeto Jugador que se agregará.
     */
    @Override
    public void agregarJugador(Jugador jugador) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        int nuevoId = 1;
        if (!jugadores.isEmpty()) {
            Jugador ultimoJugador = jugadores.get(jugadores.size() - 1);
            nuevoId = ultimoJugador.getId() + 1;
        }
        jugador.setId(nuevoId);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(jugador.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un jugador del archivo de texto según su ID.
     * Este método busca al jugador en el archivo según su ID, lo elimina de la lista
     * de jugadores y reescribe el archivo con la lista actualizada.
     * 
     * @param id El ID del jugador que se desea eliminar.
     */
    @Override
    public void eliminarJugador(int id) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        boolean jugadorEliminado = jugadores.removeIf(j -> j.getId() == id);
        if (jugadorEliminado) {
            escribirJugadores(jugadores);
        }
    }

    /**
     * Modifica un jugador existente en el archivo de texto.
     * Este método reemplaza los datos de un jugador existente con la información
     * del objeto Jugador modificado. Si el jugador con el ID especificado se encuentra,
     * se actualizan sus datos en el archivo.
     * 
     * @param jugadorModificado El jugador con los datos actualizados.
     */
    @Override
    public void modificarJugador(Jugador jugadorModificado) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        boolean jugadorEncontrado = false;

        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getId() == jugadorModificado.getId()) {
                jugadores.set(i, jugadorModificado);
                jugadorEncontrado = true;
                break;
            }
        }

        if (jugadorEncontrado) {
            escribirJugadores(jugadores);
        }
    }

    /**
     * Busca un jugador en el archivo de texto según su ID.
     * Este método busca y retorna el jugador con el ID proporcionado, o devuelve
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
     * Obtiene todos los jugadores almacenados en el archivo de texto.
     * Este método lee el archivo de texto y devuelve una lista de todos los jugadores
     * almacenados. Si el archivo no existe o está vacío, retorna una lista vacía.
     * 
     * @return Una lista de objetos Jugador leídos del archivo.
     */
    @Override
    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (file.exists() && file.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    jugadores.add(parsearJugador(linea));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jugadores;
    }

    /**
     * Convierte una línea del archivo de texto en un objeto Jugador.
     * Este método toma una línea que contiene la representación de un jugador
     * y la transforma en un objeto Jugador. La línea debe seguir un formato
     * específico para que pueda ser parseada correctamente.
     * 
     * @param linea La línea del archivo de texto que contiene los datos del jugador.
     * @return El objeto Jugador correspondiente a la línea proporcionada.
     */
    private Jugador parsearJugador(String linea) {
        String[] partes = linea.replace("[", "").replace("]", "").split(", ");
        int id = Integer.parseInt(partes[0].split(": ")[1]);
        String nick = partes[1].split(": ")[1];
        int experiencia = Integer.parseInt(partes[2].split(": ")[1]);
        int nivelVida = Integer.parseInt(partes[3].split(": ")[1]);
        int monedas = Integer.parseInt(partes[4].split(": ")[1]);

        return new Jugador(id, nick, experiencia, nivelVida, monedas);
    }

    /**
     * Escribe la lista completa de jugadores en el archivo de texto.
     * Este método sobrescribe el archivo de texto con la lista de jugadores
     * actualizada. Cada jugador se almacena en una línea del archivo.
     * 
     * @param jugadores La lista de jugadores que se escribirá en el archivo.
     */
    private void escribirJugadores(List<Jugador> jugadores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Jugador jugador : jugadores) {
                writer.write(jugador.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
