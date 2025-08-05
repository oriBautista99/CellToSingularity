package view.panel;

import view.mocks.GameDataMock;

import javax.swing.*;

public class ShopPanel extends JPanel {
    private JList<String> itemsList;
    public ShopPanel() {
        itemsList = new JList<>(GameDataMock.getShopItems());
        add(new JScrollPane(itemsList));
        JButton buyBtn = new JButton("Comprar");
        add(buyBtn);
        buyBtn.addActionListener(e -> {
            String selected = itemsList.getSelectedValue();
            GameController.get().onBuy(selected);
        });
    }
}
