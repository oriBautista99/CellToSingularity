package view.panel;

import model.ElementoEvolutivo;
import model.enums.EstadoElemento;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.Map;

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
            label.setForeground(new Color(225,238,245));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            panel.add(label);

            // Costo del elemento
            JLabel costo = new JLabel();
            costo.setAlignmentX(Component.CENTER_ALIGNMENT);
            costo.setForeground(new Color(0,12,39));
            costo.setFont(new Font("Arial", Font.BOLD, 12));

            // Mostrar costos
            if (elemento.getCosto() != null && !elemento.getCosto().isEmpty()) {
                for (Map.Entry<String, Double> entry : elemento.getCosto().entrySet()) {
                    costo.setText(entry.getValue().toString());
                }
            }
            panel.add(costo);


            // Fondo según el estado
            switch (elemento.getEstado()) {
                case ACTIVE:
                    panel.setBackground(new Color(200, 255, 200, 127)); // Verde claro
                    break;
                case ENABLED:
                    panel.setBackground(new Color(200, 200, 255, 127)); // Azul claro
                    break;
                default:
                    panel.setBackground(new Color(230, 230, 230, 127)); // Gris claro para "BLOCKED"
                    break;
            }

            if (selected) {
                panel.setBorder(BorderFactory.createLineBorder(Color.green, 2));
            }

            return panel;
        }

        return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
    }


}
