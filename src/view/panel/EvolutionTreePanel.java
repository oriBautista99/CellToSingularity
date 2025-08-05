package view.panel;

import controller.GameController;
import model.ElementoEvolutivo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EvolutionTreePanel extends JPanel {

    private JTree tree;
    private JButton btnComprar;
    private GameController gameController;

    public EvolutionTreePanel(GameController gameController) {

        this.gameController = gameController;

        setLayout(new BorderLayout());
        DefaultTreeModel root = new DefaultTreeModel(gameController.construirArbolCompleto());
        tree = new JTree(root);
        tree.setCellRenderer(new EvolucionTreeCellRenderer());
        JScrollPane scroll = new JScrollPane(tree);
        add(scroll);



        for (int i = 0; i < tree.getRowCount(); i++) {
            tree.expandRow(i);
        }

        // Botón Comprar
        btnComprar = new JButton("Comprar");
        add(btnComprar, BorderLayout.SOUTH);

        btnComprar.addActionListener(e -> intentarComprarElementoSeleccionado());

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            };
            System.out.println("Seleccionado: " + node.getUserObject());
        });

        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // COMPRA AL DAR DOBLE CLICK
                    int selRow = tree.getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());

                    if (selRow != -1 && selPath != null) {
                        intentarComprarElementoSeleccionado();
                    }

                }
            }
        });


    }

    private void intentarComprarElementoSeleccionado() {
        Object seleccion = tree.getLastSelectedPathComponent();

        if (seleccion instanceof DefaultMutableTreeNode) {
            Object userObj = ((DefaultMutableTreeNode) seleccion).getUserObject();
            System.out.println("Seleccionado: " + userObj);

            if (userObj instanceof ElementoEvolutivo) {
                System.out.println("Seleccionado: " + userObj);
                ElementoEvolutivo elem = (ElementoEvolutivo) userObj;
                System.out.println("ELEMENTO: " + elem.getEstado());

                boolean comprado = gameController.comprarElemento(elem);
                if (comprado) {
                    JOptionPane.showMessageDialog(this, "¡Elemento comprado: " + elem.getNombre() + "!");
                    tree.repaint(); // refresca colores/íconos
                } else {
                    JOptionPane.showMessageDialog(this, "No tienes recursos o el elemento no está habilitado.");
                }
            }
        }
    }

}
