import view.MainFrame;

public class Main {
    public static void main(String[] args) {
        // Inicializar lógica/modelo si hace falta...

        // Arrancar la interfaz
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame.launch();
        });
    }
}