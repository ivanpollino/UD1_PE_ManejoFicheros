/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author Vespertino
 */
import java.util.Scanner;
import modelo.Jugador;

public class JugadorView {
    private final Scanner scanner = new Scanner(System.in);

    public int mostrarMenuPrincipal() {
        System.out.println("Menú Principal:");
        System.out.println("1. Alta de jugadores");
        System.out.println("2. Baja de jugadores");
        System.out.println("3. Modificación de jugadores");
        System.out.println("4. Listado por ID");
        System.out.println("5. Listado general");
        System.out.println("6. Configuración (Seleccionar tipo de archivo)");
        System.out.println("0. Salir");
        return scanner.nextInt();
    }

    public int mostrarSubmenuConfiguracion() {
        System.out.println("Seleccione el tipo de almacenamiento:");
        System.out.println("1. Archivo de texto");
        System.out.println("2. Archivo binario");
        System.out.println("3. Archivo de objetos");
        System.out.println("4. Archivo de acceso aleatorio");
        System.out.println("5. Archivo XML");
        return scanner.nextInt();
    }

    public Jugador obtenerDatosJugador() {
        System.out.println("Ingrese los datos del jugador:");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        System.out.print("Nick: ");
        String nick = scanner.next();
        System.out.print("Experiencia: ");
        int experiencia = scanner.nextInt();
        System.out.print("Nivel de vida: ");
        int nivelVida = scanner.nextInt();
        System.out.print("Monedas: ");
        int monedas = scanner.nextInt();

        return new Jugador(id, nick, experiencia, nivelVida, monedas);
    }

    public int solicitarIDJugador() {
        System.out.print("Ingrese el ID del jugador: ");
        return scanner.nextInt();
    }

    public void mostrarJugador(Jugador jugador) {
        System.out.println(jugador);
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
