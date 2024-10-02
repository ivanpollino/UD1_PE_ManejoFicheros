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

public class JugadorBinaryFileDAO implements JugadorDAO {
    private static final String FILE_PATH = "DATOS/jugadores.bin";

    public JugadorBinaryFileDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    @Override
    public void agregarJugador(Jugador jugador) {
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

