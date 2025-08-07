package view.panel;

import controller.GameController;
import model.ElementoEvolutivo;
import model.enums.EstadoElemento;

import javax.swing.*;
import javax.swing.tree.*;
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

        gameController.setOnTreeUpdate(() -> SwingUtilities.invokeLater(this::refrescarArbol));

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

                if( e.getClickCount() == 1){
                    gameController.hacerClick();
                }
            }
        });


    }

    public void refrescarArbol() {
        System.out.println("refrescarArbol");
        DefaultTreeModel model = new DefaultTreeModel(gameController.construirArbolCompleto());
        tree.setModel(model);
        expandUnlockedNodes((DefaultMutableTreeNode) model.getRoot());
        tree.updateUI();
    }


    private void expandUnlockedNodes(DefaultMutableTreeNode node) {
        Object userObject = node.getUserObject();

        if (userObject instanceof ElementoEvolutivo) {
            ElementoEvolutivo elemento = (ElementoEvolutivo) userObject;

            // Si está desbloqueado o activo, expandimos el nodo
            if (elemento.getEstado() == EstadoElemento.ENABLED || elemento.getEstado() == EstadoElemento.ACTIVE) {
                TreePath path = new TreePath(node.getPath());
                tree.expandPath(path);

                // Recorremos hijos
                for (int i = 0; i < node.getChildCount(); i++) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
                    expandUnlockedNodes(child); // Recursión
                }
            }
        } else {
            // Nodo raíz u otro texto, expandir por defecto
            TreePath path = new TreePath(node.getPath());
            tree.expandPath(path);

            // Recorremos hijos
            for (int i = 0; i < node.getChildCount(); i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
                expandUnlockedNodes(child);
            }
        }
    }

    private boolean shouldExpand(DefaultMutableTreeNode node) {
        // Recorre desde la raíz hasta el nodo actual verificando que todos estén desbloqueados
        TreeNode[] path = node.getPath();
        for (TreeNode treeNode : path) {
            Object obj = ((DefaultMutableTreeNode) treeNode).getUserObject();
            if (obj instanceof ElementoEvolutivo) {
                ElementoEvolutivo e = (ElementoEvolutivo) obj;
                if (e.getEstado().equals("BLOCKED")) {
                    return false; // No expandir si algún nodo del camino está bloqueado
                }
            }
        }
        return true;
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
                    refrescarArbol(); // refresca colores/íconos
                } else {
                    JOptionPane.showMessageDialog(this, "No tienes recursos o el elemento no está habilitado.");
                }
            }
        }
    }

}
