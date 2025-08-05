package view.panel;

import javax.swing.*;
import controller.GameController;

public class ClickButtonPanel extends JPanel {
    private JButton clickBtn = new JButton("¡Click!");
    public ClickButtonPanel(GameController gameController) {
        add(clickBtn);
        clickBtn.addActionListener(e ->
                gameController.hacerClick()
        );
    }
}
