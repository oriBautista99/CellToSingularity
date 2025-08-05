package view;

import controller.GameController;
import view.panel.ClickButtonPanel;
import view.panel.EvolutionTreePanel;
import view.panel.ResourcePanel;
import view.panel.ShopPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(GameController gameController) {
        super("CellToSingularity");
        setLayout(new BorderLayout());

        // Inyectar el gameControlle en los paneles
        add(new ClickButtonPanel(gameController), BorderLayout.NORTH);
        add(new ResourcePanel(gameController), BorderLayout.WEST);
        add(new EvolutionTreePanel(gameController), BorderLayout.CENTER);
        //add(new ShopPanel(gameController), BorderLayout.CENTER);

        // ... configuración básica
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

}