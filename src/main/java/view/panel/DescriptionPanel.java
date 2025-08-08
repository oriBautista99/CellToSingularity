package view.panel;

import javax.swing.*;
import java.awt.*;

public class DescriptionPanel extends JPanel {
    private JTextArea descriptionArea;

    public DescriptionPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Descripción"));
        descriptionArea = new JTextArea(4, 20); // Altura de 4 líneas
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
    }

    public void setDescripcion(String descripcion) {
        descriptionArea.setText(descripcion != null ? descripcion : "Sin descripción.");
    }
}
