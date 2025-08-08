package view;

import controller.GameController;
import view.panel.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(GameController gameController) {
        super("CellToSingularity");
        setLayout(new BorderLayout());

        // encabezado
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(14, 40, 115)); // gris oscuro

        ImageIcon logo = new ImageIcon(getClass().getResource("/img/title.png"));
        JLabel titleLabel = new JLabel("", logo, JLabel.CENTER);

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        headerPanel.add(titleLabel);

        //add(headerPanel, BorderLayout.NORTH);

        // Inyectar el gameControlle en los paneles
        ResourcePanel resourceBar = new ResourcePanel(gameController);
        gameController.setResourceBarPanel(resourceBar);
        //add(resourceBar, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(resourceBar, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        DescriptionPanel descriptionPanel = new DescriptionPanel();
        gameController.setDescriptionPanel(descriptionPanel);

        EvolutionTreePanel tree = new EvolutionTreePanel(gameController, resourceBar, descriptionPanel);
        add(tree, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        gameController.setDescriptionPanel(descriptionPanel);
        rightPanel.add(descriptionPanel, BorderLayout.SOUTH);
        add(descriptionPanel, BorderLayout.SOUTH);

        // configuracion basica
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 920);
        setVisible(true);
    }

}