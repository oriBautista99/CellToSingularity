package view;

import view.panel.ClickButtonPanel;
import view.panel.EvolutionTreePanel;
import view.panel.ResourcePanel;
import view.panel.ShopPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("CellToSingularity");
        setLayout(new BorderLayout());
        add(new ClickButtonPanel(), BorderLayout.NORTH);
        add(new ResourcePanel(), BorderLayout.WEST);
        add(new EvolutionTreePanel(), BorderLayout.CENTER);
        add(new ShopPanel(), BorderLayout.EAST);
        // ... configuración básica
    }

}