/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Vespertino
 */

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class JugadorRandomAccessDAO implements JugadorDAO {
    private static final String FILE_PATH = "DATOS/jugadores.dat";

    public JugadorRandomAccessDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    @Override
    public void agregarJugador(Jugador jugador) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {
            raf.seek(raf.length()); // Posicionar al final del archivo
            escribirJugador(raf, jugador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarJugador(int id) {
        // Este método puede marcar al jugador como eliminado o sobrescribir su ID con un valor especial
        // Aquí vamos a simplemente sobrescribir el ID con -1 (indicando que está "eliminado")
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {
            long posicion = buscarPosicionPorId(raf, id);
            if (posicion >= 0) {
                raf.seek(posicion); // Posicionar en el registro del jugador
                raf.writeInt(-1); // Marcar como eliminado escribiendo -1 en el ID
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificarJugador(Jugador jugador) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {
            long posicion = buscarPosicionPorId(raf, jugador.getId());
            if (posicion >= 0) {
                raf.seek(posicion); // Mover a la posición del jugador
                escribirJugador(raf, jugador); // Sobreescribir el jugador en la posición encontrada
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Jugador buscarJugador(int id) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "r")) {
            long posicion = buscarPosicionPorId(raf, id);
            if (posicion >= 0) {
                raf.seek(posicion); // Mover el puntero a la posición del jugador
                return leerJugador(raf); // Leer y devolver el jugador
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "r")) {
            while (raf.getFilePointer() < raf.length()) { // Recorrer el archivo completo
                Jugador jugador = leerJugador(raf);
                if (jugador != null && jugador.getId() != -1) { // No incluir jugadores eliminados
                    jugadores.add(jugador);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    // Método para escribir un jugador en el archivo sin tamaño fijo
    private void escribirJugador(RandomAccessFile raf, Jugador jugador) throws IOException {
        raf.writeInt(jugador.getId());
        raf.writeUTF(jugador.getNick()); // writeUTF escribe dinámicamente la longitud
        raf.writeInt(jugador.getExperiencia());
        raf.writeInt(jugador.getNivelVida());
        raf.writeInt(jugador.getMonedas());
    }

    // Método para leer un jugador desde el archivo
    private Jugador leerJugador(RandomAccessFile raf) throws IOException {
        int id = raf.readInt();
        String nick = raf.readUTF(); // Leer el nick dinámicamente con writeUTF
        int experiencia = raf.readInt();
        int nivelVida = raf.readInt();
        int monedas = raf.readInt();
        return new Jugador(id, nick, experiencia, nivelVida, monedas);
    }

    // Método para buscar la posición de un jugador por ID
    private long buscarPosicionPorId(RandomAccessFile raf, int id) throws IOException {
        raf.seek(0); // Empezar desde el principio del archivo
        while (raf.getFilePointer() < raf.length()) {
            long posicionActual = raf.getFilePointer();
            int idLeido = raf.readInt();
            if (idLeido == id) {
                return posicionActual; // Retornar la posición si encontramos el ID
            }
            // Avanzar en el archivo, saltar los campos del jugador
            raf.readUTF(); // Saltar el nick
            raf.readInt(); // Saltar experiencia
            raf.readInt(); // Saltar nivelVida
            raf.readInt(); // Saltar monedas
        }
        return -1; // No encontrado
    }
}

