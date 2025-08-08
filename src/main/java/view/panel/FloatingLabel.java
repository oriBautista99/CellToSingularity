package view.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FloatingLabel extends JLabel {
    private Timer timer;
    private int y;

    public FloatingLabel(String texto, ImageIcon icono, int x, int yInicial) {
        super(texto);
        this.y = yInicial;

        // Configurar el JLabel
        if (icono != null) {
            setIcon(icono);
            setHorizontalTextPosition(SwingConstants.RIGHT);
            setIconTextGap(5);
        }

        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(new Color(205, 227, 242, 255));
        setOpaque(false);
        setBounds(x, y, 150, 40);


        timer = new Timer(20, (ActionEvent e) -> {
            y -= 2;
            setLocation(x, y);

            Color current = getForeground();
            int alpha = current.getAlpha() - 10;
            if (alpha <= 0) {
                timer.stop();
                Container parent = getParent();
                if (parent != null) {
                    parent.remove(this);
                    parent.repaint();
                }
            } else {
                setForeground(new Color(current.getRed(), current.getGreen(), current.getBlue(), alpha));
            }
        });

        timer.start();
    }
}
