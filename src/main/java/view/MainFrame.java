package view;

import controller.GameController;
import view.panel.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(GameController gameController) {
        super("CellToSingularity");
        setLayout(new BorderLayout());

        // Inyectar el gameControlle en los paneles
        //add(new ClickButtonPanel(gameController), BorderLayout.WEST);
        ResourcePanel resourceBar = new ResourcePanel(gameController);
        gameController.setResourceBarPanel(resourceBar);
        add(resourceBar, BorderLayout.NORTH);

        add(new EvolutionTreePanel(gameController, resourceBar), BorderLayout.CENTER);

//        JPanel rightPanel = new JPanel(new BorderLayout());
//        DescriptionPanel descriptionPanel = new DescriptionPanel();
//        gameController.setDescriptionPanel(descriptionPanel);
//        rightPanel.add(descriptionPanel, BorderLayout.SOUTH);
//        add(rightPanel);

        //add(new ShopPanel(gameController), BorderLayout.CENTER);

        // ... configuración básica
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

}