/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import vista.JugadorView;
import modelo.*;

/**
 *
 * @author Vespertino
 */
public class JugadorController {
    private JugadorDAO jugadorDAO;
    private JugadorView vista;

    public JugadorController(JugadorView vista) {
        this.vista = vista;
        configurarAlmacenamiento(); // Configuración al inicio
    }

    // Método principal que inicia la aplicación
    public void iniciar() {
        mostrarMenuPrincipal(); // Iniciar el ciclo del menú principal
    }

    // Mostrar el menú principal y delegar las acciones según la opción seleccionada
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

    // Mostrar y procesar el submenú de configuración
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

    // Alta de jugadores
    private void altaJugador() {
        Jugador jugador = vista.obtenerDatosJugador();
        jugadorDAO.agregarJugador(jugador);
        vista.mostrarMensaje("Jugador agregado.");
    }

    // Baja de jugadores
    private void bajaJugador() {
        int id = vista.solicitarIDJugador();
        jugadorDAO.eliminarJugador(id);
        vista.mostrarMensaje("Jugador eliminado.");
    }

    // Modificación de jugadores
    private void modificarJugador() {
        int id = vista.solicitarIDJugador();
        Jugador jugador = jugadorDAO.buscarJugador(id);
        if (jugador != null) {
            Jugador nuevosDatos = vista.obtenerDatosJugador();
            jugadorDAO.modificarJugador(nuevosDatos);
            vista.mostrarMensaje("Jugador modificado.");
        } else {
            vista.mostrarMensaje("Jugador no encontrado.");
        }
    }

    // Listar jugador por ID
    private void listarJugadorPorID() {
        int id = vista.solicitarIDJugador();
        Jugador jugador = jugadorDAO.buscarJugador(id);
        if (jugador != null) {
            vista.mostrarJugador(jugador);
        } else {
            vista.mostrarMensaje("Jugador no encontrado.");
        }
    }

    // Listar todos los jugadores
    private void listarTodosLosJugadores() {
        for (Jugador jugador : jugadorDAO.obtenerTodosLosJugadores()) {
            vista.mostrarJugador(jugador);
        }
    }

    // Cambiar el tipo de almacenamiento
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
