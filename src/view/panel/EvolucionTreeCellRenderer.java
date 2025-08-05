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

        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

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

        return label;
    }


}
