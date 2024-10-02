/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Vespertino
 */
import java.io.File;

public class JugadorDAOUtils {
    public static void verificarYCrearDirectorio() {
        File directorio = new File("H:\\MULWEB 2\\AcDa\\UD1_PE_ManejoFicheros\\Ivan_Pollino_Carreras_UD1_PE_ManejoFicheros\\DATOS");
        if (!directorio.exists()) {
            directorio.mkdir();
        }
    }
}

