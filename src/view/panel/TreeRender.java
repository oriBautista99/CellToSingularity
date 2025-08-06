package view.panel;


import model.ElementoEvolutivo;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.stream.Collectors;

public class TreeRender extends DefaultTreeCellRenderer {

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        JLabel label = (JLabel) super.getTreeCellRendererComponent(
                tree, value, selected, expanded, leaf, row, hasFocus);

        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();

        if (userObject instanceof ElementoEvolutivo) {
            ElementoEvolutivo elemento = (ElementoEvolutivo) userObject;

            String path = elemento.getImagenPath();
            String costo = elemento.getCosto().entrySet().stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(" | "));
            if (path != null) {
                try {
                    ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
                    if (icon != null) {
                        Image image = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                        label.setText("<html><div style='text-align:center'>" + elemento.getNombre() +
                                "<br><small style='color:gray'>" + costo + "</small></div></html>");
                        label.setIcon(new ImageIcon(image));
                    }
                } catch (Exception e) {
                    System.err.println("No se pudo cargar la imagen para: " + elemento.getNombre());
                }
            }
        }

        return label;
    }

}
