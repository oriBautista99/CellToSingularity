package view.mocks;

import javax.swing.tree.DefaultMutableTreeNode;

public class GameDataMock {
    public static ResourceData getResources() {
        // variación aleatoria o fija
        return new ResourceData(randomEnt(), randomProd(), lvl());
    }
    public static String[] getShopItems() {
        return new String[]{"Mutación A", "Recurso B", "Avance C"};
    }
    public static DefaultMutableTreeNode getEvoTree() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Lecho Primitivo");
        DefaultMutableTreeNode a = new DefaultMutableTreeNode("Organismos unicelulares");
        root.add(a);
        a.add(new DefaultMutableTreeNode("Eucariotas"));
        // …
        return root;
    }
    // Métodos auxiliares para simular
}
