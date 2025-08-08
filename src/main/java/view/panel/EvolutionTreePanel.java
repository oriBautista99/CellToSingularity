package view.panel;

import controller.GameController;
import model.ElementoEvolutivo;
import model.enums.EstadoElemento;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class EvolutionTreePanel extends JPanel {

    private JTree tree;
    private Image backgroundImage;
    private GameController gameController;
    private ResourcePanel resourcePanel;
    private DescriptionPanel descriptionPanel;

    public EvolutionTreePanel(GameController gameController, ResourcePanel resourcePanel, DescriptionPanel descriptionPanel) {

        this.gameController = gameController;
        this.resourcePanel = resourcePanel;
        this.descriptionPanel = descriptionPanel;

        URL bgUrl = getClass().getResource("/img/background2.png");
        if (bgUrl != null) {
            this.backgroundImage = new ImageIcon(bgUrl).getImage();
        } else {
            System.err.println("⚠️ No se encontró la imagen de fondo en /img/background.jpg. Colócala en src/main/resources/img/");
            this.backgroundImage = null;
        }

        setLayout(new BorderLayout());
        //arbol
        DefaultTreeModel root = new DefaultTreeModel(gameController.construirArbolCompleto());
        tree = new JTree(root);
        tree.setCellRenderer(new EvolucionTreeCellRenderer());
        JScrollPane scroll = new JScrollPane(tree);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        tree.setOpaque(false);
        tree.setBackground(new Color(0, 0, 0, 63));

        gameController.setOnTreeUpdate(() -> SwingUtilities.invokeLater(this::refrescarArbol));

        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            };

            if (node != null && node.getUserObject() instanceof ElementoEvolutivo) {
                ElementoEvolutivo elemento = (ElementoEvolutivo) node.getUserObject();
                System.out.println(elemento.getDescripcion());
                descriptionPanel.setDescripcion(elemento.getDescripcion());
            } else {
                descriptionPanel.setDescripcion(null);
            }

            //System.out.println("Seleccionado: " + node.getUserObject());
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
                    double cantidad = gameController.getJugador().getClickPower();
                    gameController.hacerClick();
                    resourcePanel.refresh(gameController);

                    Component root = SwingUtilities.getRoot(EvolutionTreePanel.this);
                    if (root instanceof JFrame) {
                        JFrame frame = (JFrame) root;
                        JLayeredPane layeredPane = ((JFrame) SwingUtilities.getRoot(EvolutionTreePanel.this)).getLayeredPane();

                        Point panelLocation = tree.getLocationOnScreen();
                        Point mouseLocation = e.getLocationOnScreen();
                        int relativeX = mouseLocation.x - panelLocation.x;
                        int relativeY = mouseLocation.y - panelLocation.y;

                        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/icons/entropia.png"));
                        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                        ImageIcon iconoEntropia = new ImageIcon(imagenEscalada);

                        FloatingLabel label = new FloatingLabel("+"+cantidad, iconoEntropia, relativeX, relativeY);

                        layeredPane.add(label, JLayeredPane.POPUP_LAYER);
                        layeredPane.setComponentZOrder(label, 0);
                    }


                }
            }
        });


    }

    public void refrescarArbol() {
        DefaultTreeModel model = new DefaultTreeModel(gameController.construirArbolCompleto());
        tree.setModel(model);
        expandUnlockedNodes((DefaultMutableTreeNode) model.getRoot());
        tree.updateUI();
    }


    private void expandUnlockedNodes(DefaultMutableTreeNode node) {
        Object userObject = node.getUserObject();

        if (userObject instanceof ElementoEvolutivo) {
            ElementoEvolutivo elemento = (ElementoEvolutivo) userObject;

            if (elemento.getEstado() == EstadoElemento.ENABLED || elemento.getEstado() == EstadoElemento.ACTIVE) {
                TreePath path = new TreePath(node.getPath());
                tree.expandPath(path);

                for (int i = 0; i < node.getChildCount(); i++) {
                    DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
                    expandUnlockedNodes(child); // Recursión
                }
            }
        } else {
            TreePath path = new TreePath(node.getPath());
            tree.expandPath(path);

            for (int i = 0; i < node.getChildCount(); i++) {
                DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
                expandUnlockedNodes(child);
            }
        }
    }

    private boolean shouldExpand(DefaultMutableTreeNode node) {
        // verifica que todos estén desbloqueados
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

            if (userObj instanceof ElementoEvolutivo) {
                System.out.println("Seleccionado: " + userObj);
                ElementoEvolutivo elem = (ElementoEvolutivo) userObj;

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
