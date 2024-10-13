/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import vista.JugadorView;
import modelo.*;

/**
 * Controlador que gestiona las operaciones relacionadas con los jugadores.
 * Coordina las interacciones entre la vista y el modelo (DAO).
 * 
 * @author Ivan Pollino
 */
public class JugadorController {

    private JugadorDAO jugadorDAO;
    private JugadorView vista;

    /**
     * Constructor que inicializa el controlador.
     * 
     * @param vista La vista asociada al controlador.
     */
    public JugadorController(JugadorView vista) {
        this.vista = vista;
        configurarAlmacenamiento(); // Configuración al inicio
    }

    /**
     * Método principal que inicia la aplicación.
     */
    public void iniciar() {
        mostrarMenuPrincipal(); // Iniciar el ciclo del menú principal
    }

    /**
     * Mostrar el menú principal y delegar las acciones según la opción seleccionada.
     */
    private void mostrarMenuPrincipal() {
        int opcion;
        do {
            opcion = vista.mostrarMenuPrincipal(); // Mostrar el menú principal
            switch (opcion) {
                case 1 -> altaJugador();
                case 2 -> bajaJugador();
                case 3 -> modificarJugador();
                case 4 -> listarJugadorPorID();
                case 5 -> listarTodosLosJugadores();
                case 6 -> configurarAlmacenamiento(); // Volver a mostrar la configuración si se elige
                case 0 -> vista.mostrarMensaje("Saliendo...");
                default -> vista.mostrarMensaje("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0); // Repetir hasta que el usuario elija salir
    }

    /**
     * Mostrar y procesar el submenú de configuración de almacenamiento.
     */
    private void configurarAlmacenamiento() {
        int opcion = vista.mostrarSubmenuConfiguracion();
        switch (opcion) {
            case 1 -> setTipoAlmacenamiento("texto");
            case 2 -> setTipoAlmacenamiento("binario");
            case 3 -> setTipoAlmacenamiento("objetos");
            case 4 -> setTipoAlmacenamiento("aleatorio");
            case 5 -> setTipoAlmacenamiento("xml");
            default -> setTipoAlmacenamiento("texto");
        }
        vista.mostrarMensaje("Almacenamiento configurado.");
    }

    /**
     * Alta de jugadores.
     */
    private void altaJugador() {
        Jugador jugador = vista.obtenerDatosJugador();
        jugadorDAO.agregarJugador(jugador);
        vista.mostrarMensaje("Jugador agregado.");
    }

    /**
     * Baja de jugadores.
     */
    private void bajaJugador() {
        int id = vista.solicitarIDJugador();
        Jugador jugador = jugadorDAO.buscarJugador(id);
        if (jugador != null) {
            jugadorDAO.eliminarJugador(id);
            vista.mostrarMensaje("Jugador eliminado.");
        } else {
            vista.mostrarMensaje("Jugador no encontrado.");
        }
    }

    /**
     * Modificación de jugadores.
     */
    private void modificarJugador() {
        int id = vista.solicitarIDJugador();
        Jugador jugador = jugadorDAO.buscarJugador(id);

        if (jugador != null) {
            // Mostrar los datos del jugador actual
            vista.mostrarJugador(jugador);

            // Solicitar nuevos datos
            Jugador nuevosDatos = vista.obtenerDatosJugador();
            nuevosDatos.setId(jugador.getId()); // Asegurarse de mantener el mismo ID

            // Modificar el jugador en el DAO
            jugadorDAO.modificarJugador(nuevosDatos);
            vista.mostrarMensaje("Jugador modificado.");
        } else {
            vista.mostrarMensaje("Jugador no encontrado.");
        }
    }

    /**
     * Listar jugador por ID.
     */
    private void listarJugadorPorID() {
        int id = vista.solicitarIDJugador();
        Jugador jugador = jugadorDAO.buscarJugador(id);
        if (jugador != null) {
            vista.mostrarJugador(jugador);
        } else {
            vista.mostrarMensaje("Jugador no encontrado.");
        }
    }

    /**
     * Listar todos los jugadores.
     */
    private void listarTodosLosJugadores() {
        List<Jugador> jugadores = jugadorDAO.obtenerTodosLosJugadores();
        if (jugadores.isEmpty()) {
            vista.mostrarMensaje("No hay jugadores registrados.");
            return;
        }

        for (Jugador jugador : jugadores) {
            vista.mostrarJugador(jugador);
        }
    }

    /**
     * Cambiar el tipo de almacenamiento de jugadores.
     * 
     * @param tipoAlmacenamiento El tipo de almacenamiento deseado.
     */
    private void setTipoAlmacenamiento(String tipoAlmacenamiento) {
        switch (tipoAlmacenamiento) {
            case "binario":
                this.jugadorDAO = new JugadorBinaryFileDAO();
                break;
            case "objetos":
                this.jugadorDAO = new JugadorObjectFileDAO();
                break;
            case "aleatorio":
                this.jugadorDAO = new JugadorRandomAccessDAO();
                break;
            case "xml":
                this.jugadorDAO = new JugadorXMLFileDAO();
                break;
            default:
                this.jugadorDAO = new JugadorTextFileDAO();
        }
    }
}
