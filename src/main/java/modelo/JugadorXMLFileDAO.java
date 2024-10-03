/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Vespertino
 */
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JugadorXMLFileDAO implements JugadorDAO {
    private static final String FILE_PATH = "DATOS/jugadores.xml";

    public JugadorXMLFileDAO() {
        JugadorDAOUtils.verificarYCrearDirectorio();
    }

    @Override
    public void agregarJugador(Jugador jugador) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        jugadores.add(jugador);
        escribirJugadores(jugadores);
    }

    @Override
    public void eliminarJugador(int id) {
        List<Jugador> jugadores = obtenerTodosLosJugadores();
        jugadores.removeIf(j -> j.getId() == id);
        escribirJugadores(jugadores);
    }

    @Override
    public void modificarJugador(Jugador jugador) {
        eliminarJugador(jugador.getId());
        agregarJugador(jugador);
    }

    @Override
    public Jugador buscarJugador(int id) {
        return obtenerTodosLosJugadores().stream()
                .filter(j -> j.getId() == id)
                .findFirst()
                .orElse(null);
    }

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
