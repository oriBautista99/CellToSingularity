package view.panel;

import model.ElementoEvolutivo;
import model.enums.EstadoElemento;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class EvolucionTreeCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {

        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
        Object userObject = nodo.getUserObject();

        if (userObject instanceof ElementoEvolutivo) {
            ElementoEvolutivo elemento = (ElementoEvolutivo) userObject;

            // Panel personalizado con layout vertical
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            panel.setOpaque(true);

            // Imagen (grande)
            String path = elemento.getImagenPath();
            if (path != null) {
                try {
                    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
                    Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(img));
                    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    panel.add(imageLabel);
                } catch (Exception e) {
                    System.err.println("No se pudo cargar la imagen para: " + elemento.getNombre());
                }
            }

            // Texto (nombre del elemento)
            JLabel label = new JLabel(elemento.getNombre());
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setFont(new Font("Arial", Font.BOLD, 12));
            panel.add(label);

            // Fondo según el estado
            switch (elemento.getEstado()) {
                case ACTIVE:
                    panel.setBackground(new Color(200, 255, 200)); // Verde claro
                    break;
                case ENABLED:
                    panel.setBackground(new Color(200, 200, 255)); // Azul claro
                    break;
                default:
                    panel.setBackground(new Color(230, 230, 230)); // Gris claro para "BLOCKED"
                    break;
            }

            if (selected) {
                panel.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
            }

            return panel;
        }

        // Por defecto (no es un ElementoEvolutivo)
        return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
//----
       /* JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
            Object obj = nodo.getUserObject();

            if (obj instanceof ElementoEvolutivo) {
                ElementoEvolutivo elem = (ElementoEvolutivo) obj;

                EstadoElemento estado = elem.getEstado();
                switch (estado) {
                    case BLOCKED:
                        label.setForeground(Color.GRAY);
                        label.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
                        break;
                    case ENABLED:
                        label.setForeground(Color.ORANGE);
                        label.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
                        break;
                    case ACTIVE:
                        label.setForeground(new Color(0, 128, 0)); // Verde oscuro
                        label.setIcon(UIManager.getIcon("FileView.hardDriveIcon"));
                        break;
                }
                label.setText(elem.getNombre()); // Asegúrate de ver el nombre limpio
            }
        }

        return label;*/
// ------
    }


}
