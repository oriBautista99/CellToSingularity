package view.panel;

import javax.swing.*;
import java.awt.*;

public class DescriptionPanel extends JPanel {
    private JTextArea descriptionArea;

    public DescriptionPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Descripción"));
        //setPreferredSize(new Dimension(300, 100));

        descriptionArea = new JTextArea(2,100); // Altura de 4 líneas
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setDescripcion(String descripcion) {
        descriptionArea.setText(descripcion != null ? descripcion : "Sin descripción.");
        descriptionArea.setCaretPosition(0);
        revalidate();
        repaint();
    }
}
