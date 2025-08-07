package view.panel;

import controller.GameController;
import model.ElementoEvolutivo;
import model.Jugador;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourcePanel extends JPanel {

    private Map<String, JLabel> etiquetasRecursos = new HashMap<>();
    private Map<String, ImageIcon> iconos;
    private final JLabel productionLabel= new JLabel();
    private final JLabel levelLabel     = new JLabel();
    private final JLabel entropiaLabel = new JLabel();
    private final JPanel recursoProd = new JPanel();


    public ResourcePanel(GameController gameController) {

        setLayout(new FlowLayout(FlowLayout.CENTER,30,10));
        setBackground(Color.DARK_GRAY);

        recursoProd.setLayout(new BoxLayout(recursoProd, BoxLayout.Y_AXIS));
        recursoProd.setOpaque(false);
        recursoProd.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));


        add(recursoProd);
        //add(productionLabel);
        add(levelLabel);

        // Cargar íconos desde resources
        iconos = new HashMap<>();
        iconos.put("Entropia", cargarIcono("entropia.png"));
        iconos.put("Ideas",  cargarIcono("idea.png"));
        iconos.put("Clicks",  cargarIcono("click.png"));
        iconos.put("Nivel",  cargarIcono("nivel.png"));

        // Simular actualización periódica
        Timer t = new Timer(500, e -> refresh(gameController));
        t.start();

    }

    private void refresh(GameController gameController) {

        Jugador jugador = gameController.getJugador();
        double entropia = jugador.getCantidadRecurso("Entropia");
        double produccion = jugador.getElementosActivos().stream()
                .mapToDouble(ElementoEvolutivo::getProduccionBase).sum();
        int clicks = jugador.getTotalClicks();

        actualizarEtiqueta(entropiaLabel, "Entropia", entropia);
        actualizarEtiqueta(productionLabel, "Clicks", produccion);

        recursoProd.add(entropiaLabel);
        recursoProd.add(productionLabel);
        actualizarEtiqueta(levelLabel, "Nivel", clicks);

        // falta condicionar para las ideas

        System.out.println("SE ACTUALIZA LA ENTROPIA - refresh - ResourcePanel");
    }

    private void actualizarEtiqueta(JLabel label, String tipo, double cantidad) {
        // estilos
        if(tipo.equals("Clicks")) {
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setForeground(Color.GRAY);
            label.setHorizontalTextPosition(SwingConstants.CENTER);
            label.setVerticalTextPosition(SwingConstants.BOTTOM);
            label.setText(String.format(" %.2f", cantidad) + "/s");
        }else{
            label.setFont(new Font("Arial", Font.BOLD, 22));
            label.setForeground(Color.WHITE);
            label.setIcon(iconos.get(tipo));
            label.setText(String.format(" %.1f", cantidad));
        }

    }

    private ImageIcon cargarIcono(String nombreArchivo) {
        try {
            URL url = getClass().getClassLoader().getResource("icons/" + nombreArchivo);
            if (url != null) {
                Image imagen = new ImageIcon(url).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
                return new ImageIcon(imagen);
            } else {
                System.err.println("No se encontró el icono: " + nombreArchivo);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
