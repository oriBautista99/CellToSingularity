package view.panel;

import javax.swing.*;

public class ClickButtonPanel extends JPanel {
    private JButton clickBtn = new JButton("¡Click!");
    public ClickButtonPanel() {
        add(clickBtn);
        clickBtn.addActionListener(e ->
                GameController.get().onCellClick()
        );
    }
}
