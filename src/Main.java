import model.*;
import controller.GameController;
import view.MainFrame;

import javax.swing.*;
import java.util.List;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Inicializar lógica
        Jugador jugador = new Jugador("Jugador 1");

        //Cargar arbol evolutivo desde JSON
        List<ElementoEvolutivo> elementos = ElementoLoader.cargarDesdeJson();

        GameController controller = new GameController(jugador, elementos);

        // Arrancar la interfaz
        SwingUtilities.invokeLater(() -> {
            new MainFrame(controller);
        });
    }

}