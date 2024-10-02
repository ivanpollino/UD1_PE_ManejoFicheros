/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package run;

import controlador.JugadorController;
import vista.JugadorView;

/**
 *
 * @author Vespertino
 */
public class Run {
    public static void main(String[] args) {
        // Crear la vista
        JugadorView vista = new JugadorView();
        
        // Crear el controlador pasándole la vista
        JugadorController controlador = new JugadorController(vista);
        
        // Iniciar el controlador, que mostrará el submenú de configuración y el menú principal
        controlador.iniciar();
    }
}

