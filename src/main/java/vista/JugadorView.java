/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.util.Scanner;
import modelo.Jugador;

/**
 * Clase que representa la vista para la gestión de jugadores.
 * Proporciona métodos para mostrar menús y obtener datos de los jugadores.
 * 
 * @author Ivan Pollino
 */
public class JugadorView {

    private final Scanner scanner = new Scanner(System.in);

    /**
     * Muestra el menú principal y solicita al usuario que elija una opción.
     * 
     * @return la opción elegida por el usuario.
     */
    public int mostrarMenuPrincipal() {
        int opcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.println("\n====================");
                System.out.println("   MENÚ PRINCIPAL");
                System.out.println("====================");
                System.out.println("1. Alta de jugadores");
                System.out.println("2. Baja de jugadores");
                System.out.println("3. Modificación de jugadores");
                System.out.println("4. Listado por ID");
                System.out.println("5. Listado general");
                System.out.println("6. Configuración (Tipo de almacenamiento)");
                System.out.println("0. Salir");
                System.out.println("====================");
                System.out.print("Elige una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                // Verifica que la opción esté dentro del rango
                if (opcion >= 0 && opcion <= 6) {
                    entradaValida = true;
                } else {
                    System.out.println("\n[ERROR] Opción inválida. Debes elegir una opción entre 0 y 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Debes introducir un número válido.");
            }
        }

        return opcion;
    }

    /**
     * Muestra el submenu de configuración de almacenamiento y solicita al usuario que elija una opción.
     * 
     * @return la opción elegida por el usuario.
     */
    public int mostrarSubmenuConfiguracion() {
        int opcion = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.println("\n====================");
                System.out.println(" CONFIGURAR ALMACENAMIENTO");
                System.out.println("====================");
                System.out.println("1. Archivo de texto");
                System.out.println("2. Archivo binario");
                System.out.println("3. Archivo de objetos");
                System.out.println("4. Archivo de acceso aleatorio");
                System.out.println("5. Archivo XML");
                System.out.println("====================");
                System.out.print("Elige una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                // Verifica que la opción esté dentro del rango
                if (opcion >= 1 && opcion <= 5) {
                    entradaValida = true;
                } else {
                    System.out.println("\n[ERROR] Opción inválida. Debes elegir una opción entre 1 y 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[ERROR] Debes introducir un número válido.");
            }
        }

        return opcion;
    }

    /**
     * Solicita al usuario que ingrese los datos de un jugador.
     * 
     * @return un objeto Jugador con los datos ingresados.
     */
    public Jugador obtenerDatosJugador() {
        System.out.println("\n====================");
        System.out.println("   INGRESAR DATOS");
        System.out.println("====================");
        
        String nick;
        int experiencia, nivelVida, monedas;

        System.out.print("Nick: ");
        nick = scanner.nextLine();

        experiencia = solicitarInt("Experiencia");
        nivelVida = solicitarInt("Nivel de vida");
        monedas = solicitarInt("Monedas");

        return new Jugador(0, nick, experiencia, nivelVida, monedas); // El ID se inicializa como 0
    }

    /**
     * Solicita un número entero al usuario para un campo específico.
     * 
     * @param campo el nombre del campo para mostrar en el mensaje.
     * @return el valor entero ingresado por el usuario.
     */
    private int solicitarInt(String campo) {
        int valor = -1;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.print(campo + ": ");
                valor = Integer.parseInt(scanner.nextLine());
                if (valor >= 0) {
                    entradaValida = true; // Entrada válida
                } else {
                    System.out.println("[ERROR] El valor debe ser positivo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Debes introducir un número válido para " + campo + ".");
            }
        }

        return valor;
    }

    /**
     * Solicita al usuario que ingrese el ID de un jugador.
     * 
     * @return el ID ingresado por el usuario.
     */
    public int solicitarIDJugador() {
        return solicitarInt("ID del jugador");
    }

    /**
     * Muestra la información de un jugador en consola.
     * 
     * @param jugador el objeto Jugador que se desea mostrar.
     */
    public void mostrarJugador(Jugador jugador) {
        System.out.println(jugador);
    }

    /**
     * Muestra un mensaje en consola.
     * 
     * @param mensaje el mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        System.out.println("\n" + mensaje);
    }
}
