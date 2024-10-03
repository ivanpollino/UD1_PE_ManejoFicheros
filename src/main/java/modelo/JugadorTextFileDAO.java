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

public class JugadorTextFileDAO implements JugadorDAO {
    private static final String FILE_PATH = "DATOS/jugadores.txt";

    public JugadorTextFileDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    @Override
    public void agregarJugador(Jugador jugador) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(jugador.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                jugadores.add(parsearJugador(linea));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    private Jugador parsearJugador(String linea) {
        // Parsear la línea del archivo para obtener un objeto Jugador
        String[] partes = linea.replace("[", "").replace("]", "").split(", ");
        int id = Integer.parseInt(partes[0].split(": ")[1]);
        String nick = partes[1].split(": ")[1];
        int experiencia = Integer.parseInt(partes[2].split(": ")[1]);
        int nivelVida = Integer.parseInt(partes[3].split(": ")[1]);
        int monedas = Integer.parseInt(partes[4].split(": ")[1]);

        return new Jugador(id, nick, experiencia, nivelVida, monedas);
    }

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
