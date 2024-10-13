/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la interfaz JugadorDAO para gestionar la 
 * persistencia de objetos Jugador en un archivo XML.
 * Proporciona métodos para agregar, eliminar, modificar, buscar 
 * y obtener todos los jugadores.
 * 
 * @author Ivan Pollino
 */
public class JugadorXMLFileDAO implements JugadorDAO {
    private static final String FILE_PATH = "DATOS/jugadores.xml";

    /**
     * Constructor que verifica y crea el directorio donde se 
     * almacenará el archivo XML.
     */
    public JugadorXMLFileDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    /**
     * Agrega un nuevo jugador al archivo XML.
     * Asigna un nuevo ID basado en el último jugador existente.
     * 
     * @param jugador el objeto Jugador a agregar.
     */
    @Override
    public void agregarJugador(Jugador jugador) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();

        // Asignar un nuevo ID basado en el último jugador de la lista
        int nuevoId = 1;
        if (!jugadores.isEmpty()) {
            Jugador ultimoJugador = jugadores.get(jugadores.size() - 1);
            nuevoId = ultimoJugador.getId() + 1;
        }
        jugador.setId(nuevoId); // Establecer el nuevo ID al jugador
        
        jugadores.add(jugador);
        escribirJugadores(jugadores);
    }

    /**
     * Elimina un jugador del archivo XML según su ID.
     * 
     * @param id el ID del jugador a eliminar.
     */
    @Override
    public void eliminarJugador(int id) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        jugadores.removeIf(j -> j.getId() == id);
        escribirJugadores(jugadores);
    }

    /**
     * Modifica un jugador en el archivo XML.
     * Elimina primero el jugador existente y luego lo agrega de nuevo.
     * 
     * @param jugador el objeto Jugador con los datos actualizados.
     */
    @Override
    public void modificarJugador(Jugador jugador) {
        eliminarJugador(jugador.getId());
        agregarJugador(jugador);
    }

    /**
     * Busca un jugador en el archivo XML según su ID.
     * 
     * @param id el ID del jugador a buscar.
     * @return el objeto Jugador si se encuentra, o null si no se encuentra.
     */
    @Override
    public Jugador buscarJugador(int id) {
        return obtenerTodosLosJugadores().stream()
                .filter(j -> j.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene una lista de todos los jugadores almacenados en el archivo XML.
     * 
     * @return una lista de objetos Jugador.
     */
    @Override
    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        try {
            File xmlFile = new File(FILE_PATH);
            if (xmlFile.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);

                NodeList nList = doc.getElementsByTagName("jugador");
                for (int i = 0; i < nList.getLength(); i++) {
                    Node node = nList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        int id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                        String nick = eElement.getElementsByTagName("nick").item(0).getTextContent();
                        int experiencia = Integer.parseInt(eElement.getElementsByTagName("experiencia").item(0).getTextContent());
                        int nivelVida = Integer.parseInt(eElement.getElementsByTagName("nivelVida").item(0).getTextContent());
                        int monedas = Integer.parseInt(eElement.getElementsByTagName("monedas").item(0).getTextContent());
                        jugadores.add(new Jugador(id, nick, experiencia, nivelVida, monedas));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    /**
     * Escribe la lista de jugadores en el archivo XML.
     * 
     * @param jugadores la lista de objetos Jugador a escribir.
     */
    private void escribirJugadores(List<Jugador> jugadores) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("jugadores");
            doc.appendChild(rootElement);

            for (Jugador jugador : jugadores) {
                Element jugadorElement = doc.createElement("jugador");
                rootElement.appendChild(jugadorElement);

                Element id = doc.createElement("id");
                id.appendChild(doc.createTextNode(String.valueOf(jugador.getId())));
                jugadorElement.appendChild(id);

                Element nick = doc.createElement("nick");
                nick.appendChild(doc.createTextNode(jugador.getNick()));
                jugadorElement.appendChild(nick);

                Element experiencia = doc.createElement("experiencia");
                experiencia.appendChild(doc.createTextNode(String.valueOf(jugador.getExperiencia())));
                jugadorElement.appendChild(experiencia);

                Element nivelVida = doc.createElement("nivelVida");
                nivelVida.appendChild(doc.createTextNode(String.valueOf(jugador.getNivelVida())));
                jugadorElement.appendChild(nivelVida);

                Element monedas = doc.createElement("monedas");
                monedas.appendChild(doc.createTextNode(String.valueOf(jugador.getMonedas())));
                jugadorElement.appendChild(monedas);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(FILE_PATH));

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
