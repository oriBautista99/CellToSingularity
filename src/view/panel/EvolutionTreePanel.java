package view.panel;

import controller.GameController;
import model.ElementoEvolutivo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class EvolutionTreePanel extends JPanel {

    public EvolutionTreePanel(GameController gameController) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Arbol Evolutivo");

        for (ElementoEvolutivo el : gameController.getTodosLosElementos()){
            root.add(new DefaultMutableTreeNode(el.getNombre()));
        }

        JTree tree = new JTree(root);
        add(new JScrollPane(tree));

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            };
            System.out.println("Seleccionado: " + node.getUserObject());
                //gameController.get().onEvoNodeSelected(node.getUserObject());
        });
    }
}
