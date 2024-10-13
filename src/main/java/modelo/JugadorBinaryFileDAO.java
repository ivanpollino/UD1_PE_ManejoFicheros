/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la interfaz JugadorDAO para manejar la 
 * persistencia de jugadores en un archivo binario.
 * Permite agregar, eliminar, modificar, buscar y obtener 
 * todos los jugadores del archivo.
 * 
 * @author Ivan Pollino
 */
public class JugadorBinaryFileDAO implements JugadorDAO {

    private static final String FILE_PATH = "DATOS/jugadores.dat";

    public JugadorBinaryFileDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    /**
     * Agrega un nuevo jugador al archivo binario.
     * 
     * @param jugador El objeto Jugador que se desea agregar.
     */
    @Override
    public void agregarJugador(Jugador jugador) {
        File archivo = new File(FILE_PATH);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        List<Jugador> jugadores = obtenerTodosLosJugadores();
        int nuevoId = 1;
        if (!jugadores.isEmpty()) {
            Jugador ultimoJugador = jugadores.get(jugadores.size() - 1);
            nuevoId = ultimoJugador.getId() + 1;
        }

        jugador.setId(nuevoId);
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_PATH, true))) {
            dos.writeInt(jugador.getId());
            dos.writeUTF(jugador.getNick());
            dos.writeInt(jugador.getExperiencia());
            dos.writeInt(jugador.getNivelVida());
            dos.writeInt(jugador.getMonedas());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un jugador del archivo binario según su ID.
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
     * Modifica los datos de un jugador existente en el archivo binario.
     * 
     * @param jugador El objeto Jugador con los datos actualizados.
     */
    @Override
    public void modificarJugador(Jugador jugador) {
        eliminarJugador(jugador.getId());
        agregarJugador(jugador);
    }

    /**
     * Busca un jugador en el archivo binario según su ID.
     * 
     * @param id El ID del jugador que se desea buscar.
     * @return El objeto Jugador encontrado o null si no se encuentra.
     */
    @Override
    public Jugador buscarJugador(int id) {
        return obtenerTodosLosJugadores().stream()
                .filter(j -> j.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene una lista de todos los jugadores almacenados en el archivo binario.
     * 
     * @return Una lista de objetos Jugador.
     */
    @Override
    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        File archivo = new File(FILE_PATH);

        if (!archivo.exists()) {
            return jugadores;
        }

        try (DataInputStream dis = new DataInputStream(new FileInputStream(FILE_PATH))) {
            while (dis.available() > 0) {
                int id = dis.readInt();
                String nick = dis.readUTF();
                int experiencia = dis.readInt();
                int nivelVida = dis.readInt();
                int monedas = dis.readInt();
                jugadores.add(new Jugador(id, nick, experiencia, nivelVida, monedas));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    /**
     * Escribe la lista de jugadores en el archivo binario.
     * 
     * @param jugadores Lista de objetos Jugador que se desea escribir.
     */
    private void escribirJugadores(List<Jugador> jugadores) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_PATH))) {
            for (Jugador jugador : jugadores) {
                dos.writeInt(jugador.getId());
                dos.writeUTF(jugador.getNick());
                dos.writeInt(jugador.getExperiencia());
                dos.writeInt(jugador.getNivelVida());
                dos.writeInt(jugador.getMonedas());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
