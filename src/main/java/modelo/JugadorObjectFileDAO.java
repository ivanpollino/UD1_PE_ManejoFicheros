/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Vespertino
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorObjectFileDAO implements JugadorDAO {
    private static final String FILE_PATH = "DATOS/jugadores.obj";

    public JugadorObjectFileDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    @Override
    public void agregarJugador(Jugador jugador) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        jugadores.add(jugador);
        escribirJugadores(jugadores);
    }

    @Override
    public void eliminarJugador(int id) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        jugadores.removeIf(j -> j.getId() == id);
        escribirJugadores(jugadores);
    }

    @Override
    public void modificarJugador(Jugador jugador) {
        eliminarJugador(jugador.getId());
        agregarJugador(jugador);
    }

    @Override
    public Jugador buscarJugador(int id) {
        return obtenerTodosLosJugadores().stream()
                .filter(j -> j.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            jugadores = (List<Jugador>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    private void escribirJugadores(List<Jugador> jugadores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(jugadores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

