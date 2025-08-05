package view.panel;

import controller.GameController;
import model.ElementoEvolutivo;

import javax.swing.*;
import java.util.Optional;

public class ShopPanel extends JPanel {
    private JList<String> itemsList;

    public ShopPanel(GameController gameController) {
        itemsList = new JList<>(
                gameController.getTodosLosElementos().stream()
                        .map(ElementoEvolutivo::getNombre)
                        .toArray(String[]::new)
        );
        add(new JScrollPane(itemsList));

        JButton buyBtn = new JButton("Comprar");
        add(buyBtn);

        buyBtn.addActionListener(e -> {
            String nombre = itemsList.getSelectedValue();
            Optional<ElementoEvolutivo> opt = gameController.getTodosLosElementos().stream()
                    .filter(el -> el.getNombre().equals(nombre))
                    .findFirst();
            opt.ifPresent(gameController.getJugador()::comprarElemento);
            //String selected = itemsList.getSelectedValue();
            //GameController.get().onBuy(selected);
        });
    }
}
