package view.panel;

import view.mocks.GameDataMock;

import javax.swing.*;
import java.awt.*;

public class ResourcePanel extends JPanel {
    private final JLabel entropyLabel   = new JLabel("Entropía: 0");
    private JLabel productionLabel= new JLabel("Producción: 0");
    private JLabel levelLabel     = new JLabel("Nivel: 1");

    public ResourcePanel() {
        setLayout(new GridLayout(3,1));
        add(entropyLabel);
        add(productionLabel);
        add(levelLabel);
        // Simular actualización periódica
        Timer t = new Timer(500, e -> refresh(GameDataMock.getResources()));
        t.start();
    }

    private void refresh(ResourceData d) {
        entropyLabel.setText("Entropía: " + d.entropy);
        productionLabel.setText("Producción: " + d.production);
        levelLabel.setText("Nivel: " + d.level);
    }
}
