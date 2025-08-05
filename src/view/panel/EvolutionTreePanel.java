package view.panel;

import view.mocks.GameDataMock;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class EvolutionTreePanel extends JPanel {
    private JTree tree;
    public EvolutionTreePanel() {
        DefaultMutableTreeNode root = GameDataMock.getEvoTree();
        tree = new JTree(root);
        add(new JScrollPane(tree));

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            GameController.get().onEvoNodeSelected(node.getUserObject());
        });
    }
}
