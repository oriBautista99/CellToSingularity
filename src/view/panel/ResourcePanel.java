package view.panel;

import controller.GameController;
import model.ElementoEvolutivo;
import model.Jugador;

import javax.swing.*;
import java.awt.*;

public class ResourcePanel extends JPanel {

    private final JLabel entropyLabel   = new JLabel("Entropía: 0");
    private final JLabel productionLabel= new JLabel("Producción: 0");
    private final JLabel levelLabel     = new JLabel("Clicks: 0");

    public ResourcePanel(GameController gameController) {
        setLayout(new GridLayout(3,1));
        add(entropyLabel);
        add(productionLabel);
        add(levelLabel);

        // Simular actualización periódica
        Timer t = new Timer(500, e -> refresh(gameController));
        t.start();
    }

    private void refresh(GameController gameController) {

        Jugador jugador = gameController.getJugador();
        double entropia = jugador.getCantidadRecurso("Entropia");
        double produccion = jugador.getElementosActivos().stream()
                .mapToDouble(ElementoEvolutivo::getProduccionBase).sum();
        int clicks = jugador.getTotalClicks();

        entropyLabel.setText("Entropía: " + (int) entropia);
        productionLabel.setText("Producción: " + (int) produccion);
        levelLabel.setText("Nivel: " + clicks);
    }
}
