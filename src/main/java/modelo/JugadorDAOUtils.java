/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.File;

/**
 * Clase de utilidad para la gestión del acceso a datos de los jugadores.
 * Proporciona métodos para verificar y crear directorios necesarios 
 * para almacenar los archivos de datos.
 * 
 * @author Ivan Pollino
 */
public class JugadorDAOUtils {
    
    /**
     * Verifica si el directorio "DATOS" existe, 
     * y si no, lo crea. Este método es utilizado 
     * para asegurar que el entorno de almacenamiento 
     * de los datos esté disponible antes de realizar 
     * operaciones de lectura o escritura en archivos.
     */
    public static void verificarYCrearDirectorio() {
        File directorio = new File("DATOS");
        if (!directorio.exists()) {
            directorio.mkdir();
        }
    }
}
