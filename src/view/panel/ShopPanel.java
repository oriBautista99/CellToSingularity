package view.panel;

import controller.GameController;
import model.ElementoEvolutivo;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.Optional;

public class ShopPanel extends JPanel {

    public ShopPanel(GameController gameController) {

        setLayout(new BorderLayout());

        DefaultTreeModel modelo = new DefaultTreeModel(gameController.construirArbolCompleto());
        JTree arbol = new JTree(modelo);
        arbol.setCellRenderer(new EvolucionTreeCellRenderer());
        JScrollPane scroll = new JScrollPane(arbol);
        add(scroll, BorderLayout.WEST);

        for (int i = 0; i < arbol.getRowCount(); i++) {
            arbol.expandRow(i);
        }

       /* itemsList = new JList<>(
                gameController.getTodosLosElementos().stream()
                        .map(ElementoEvolutivo::getNombre)
                        .toArray(String[]::new)
        );
        add(new JScrollPane(itemsList));

        JButton buyBtn = new JButton("Comprar");
        add(buyBtn);*/

        /* buyBtn.addActionListener(e -> {
            String nombre = itemsList.getSelectedValue();
            Optional<ElementoEvolutivo> opt = gameController.getTodosLosElementos().stream()
                    .filter(el -> el.getNombre().equals(nombre))
                    .findFirst();
            opt.ifPresent(gameController.getJugador()::comprarElemento);
            //String selected = itemsList.getSelectedValue();
            //GameController.get().onBuy(selected);
        });*/
    }
}
