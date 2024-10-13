/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la interfaz JugadorDAO para gestionar la 
 * persistencia de objetos Jugador en un archivo de acceso aleatorio.
 * Proporciona métodos para agregar, eliminar, modificar, buscar 
 * y obtener todos los jugadores.
 * 
 * @author Ivan Pollino
 */
public class JugadorRandomAccessDAO implements JugadorDAO {

    /**
     * Ruta del archivo donde se almacenan los datos de los jugadores.
     */
    private static final String FILE_PATH = "DATOS/jugadoresRandomAccess.dat";

    /**
     * Constructor de la clase JugadorRandomAccessDAO.
     * Verifica que el directorio donde se guardan los archivos de jugadores exista,
     * y si no, lo crea.
     */
    public JugadorRandomAccessDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    /**
     * Agrega un nuevo jugador al archivo de acceso aleatorio.
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
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {
            raf.seek(raf.length());
            escribirJugador(raf, jugador);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un jugador del archivo de acceso aleatorio según su ID.
     * Este método marca al jugador como eliminado sobrescribiendo su ID con -1.
     * 
     * @param id El ID del jugador que se desea eliminar.
     */
    @Override
    public void eliminarJugador(int id) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {
            long posicion = buscarPosicionPorId(raf, id);
            if (posicion >= 0) {
                raf.seek(posicion);
                raf.writeInt(-1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifica un jugador existente en el archivo de acceso aleatorio.
     * Este método busca el jugador por su ID y sobrescribe su información
     * con la del objeto Jugador modificado.
     * 
     * @param jugador El jugador con los datos actualizados.
     */
    @Override
    public void modificarJugador(Jugador jugador) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "rw")) {
            long posicion = buscarPosicionPorId(raf, jugador.getId());
            if (posicion >= 0) {
                raf.seek(posicion);
                escribirJugador(raf, jugador);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Busca un jugador en el archivo de acceso aleatorio según su ID.
     * Este método retorna el jugador con el ID proporcionado o null si no se encuentra.
     * 
     * @param id El ID del jugador que se desea buscar.
     * @return El jugador encontrado o null si no se encuentra.
     */
    @Override
    public Jugador buscarJugador(int id) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "r")) {
            long posicion = buscarPosicionPorId(raf, id);
            if (posicion >= 0) {
                raf.seek(posicion);
                return leerJugador(raf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene todos los jugadores almacenados en el archivo de acceso aleatorio.
     * Este método lee el archivo y devuelve una lista de todos los jugadores
     * que no están marcados como eliminados. Si el archivo no existe o está vacío,
     * retorna una lista vacía.
     * 
     * @return Una lista de objetos Jugador leídos del archivo.
     */
    @Override
    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        File archivo = new File(FILE_PATH);

        if (!archivo.exists()) {
            return jugadores;
        }

        try (RandomAccessFile raf = new RandomAccessFile(FILE_PATH, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                Jugador jugador = leerJugador(raf);
                if (jugador != null && jugador.getId() != -1) {
                    jugadores.add(jugador);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    /**
     * Escribe un jugador en el archivo de acceso aleatorio.
     * Este método guarda los datos del jugador en el archivo, utilizando
     * el formato apropiado para acceso aleatorio.
     * 
     * @param raf El archivo en el que se va a escribir el jugador.
     * @param jugador El jugador que se va a escribir en el archivo.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    private void escribirJugador(RandomAccessFile raf, Jugador jugador) throws IOException {
        raf.writeInt(jugador.getId());
        raf.writeUTF(jugador.getNick());
        raf.writeInt(jugador.getExperiencia());
        raf.writeInt(jugador.getNivelVida());
        raf.writeInt(jugador.getMonedas());
    }

    /**
     * Lee un jugador desde el archivo de acceso aleatorio.
     * Este método recupera los datos de un jugador desde el archivo y
     * retorna un objeto Jugador correspondiente.
     * 
     * @param raf El archivo desde el que se va a leer el jugador.
     * @return El objeto Jugador leído del archivo.
     * @throws IOException Si ocurre un error al leer del archivo.
     */
    private Jugador leerJugador(RandomAccessFile raf) throws IOException {
        int id = raf.readInt();
        String nick = raf.readUTF();
        int experiencia = raf.readInt();
        int nivelVida = raf.readInt();
        int monedas = raf.readInt();
        return new Jugador(id, nick, experiencia, nivelVida, monedas);
    }

    /**
     * Busca la posición de un jugador en el archivo de acceso aleatorio según su ID.
     * Este método retorna la posición del registro del jugador si se encuentra,
     * o -1 si no se encuentra.
     * 
     * @param raf El archivo en el que se busca el jugador.
     * @param id El ID del jugador que se desea buscar.
     * @return La posición del jugador en el archivo o -1 si no se encuentra.
     * @throws IOException Si ocurre un error al leer del archivo.
     */
    private long buscarPosicionPorId(RandomAccessFile raf, int id) throws IOException {
        raf.seek(0);
        while (raf.getFilePointer() < raf.length()) {
            long posicionActual = raf.getFilePointer();
            int idLeido = raf.readInt();
            if (idLeido == id) {
                return posicionActual;
            }
            raf.readUTF();
            raf.readInt();
            raf.readInt();
            raf.readInt();
        }
        return -1;
    }
}
