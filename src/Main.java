import controller.GameController;
import model.*;
import model.enums.EstadoElemento;

import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
    // Crear recurso base
    Recurso entropia = new Recurso("Entropía", 0, 0);

    // Crear jugador
    Jugador jugador = new Jugador("Jugador 1");
        jugador.agregarRecurso(entropia);

    // Crear elementos evolutivos iniciales
    ElementoEvolutivo celula = new ElementoEvolutivo(
            "Célula",
            Map.of("Entropía", 10.0),
            new ArrayList<>(),
            1.0,
            "Entropía",
            EstadoElemento.ENABLED,
            10,
            1,
            0
    );

    ElementoEvolutivo mitosis = new ElementoEvolutivo(
            "Mitosis",
            Map.of("Entropía", 50.0),
            List.of("Célula"),
            2.0,
            "Entropía",
            EstadoElemento.BLOCKED,
            10,
            1,
            0
    );

    List<ElementoEvolutivo> elementos = new ArrayList<>();
        elementos.add(celula);
        elementos.add(mitosis);

    // Iniciar motor del juego
    GameController engine = new GameController(jugador, elementos);

    Scanner scanner = new Scanner(System.in);
    boolean activo = true;

        System.out.println("🧬 Bienvenido a Cell to Singularity (versión consola)");
        System.out.println("Escribe 'click' para generar entropía manual. 'comprar <nombre>' para desbloquear elementos. 'salir' para terminar.\n");

        while (activo) {
        System.out.println("Entropía: " + jugador.getCantidadRecurso("Entropía"));
        System.out.print("> ");
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("click")) {
            engine.hacerClick();
            System.out.println("¡Click registrado! Entropía + " + jugador.getClickPower());
        } else if (input.startsWith("comprar ")) {
            String nombreElemento = input.substring(8).trim();
            Optional<ElementoEvolutivo> elem = elementos.stream()
                    .filter(e -> e.getNombre().equalsIgnoreCase(nombreElemento))
                    .findFirst();

            if (elem.isPresent()) {
                jugador.comprarElemento(elem.get());
            } else {
                System.out.println("❌ Elemento no encontrado.");
            }
        } else if (input.equalsIgnoreCase("salir")) {
            activo = false;
        } else {
            System.out.println("Comando no reconocido.");
        }

        // Pequeña pausa para legibilidad
        Thread.sleep(500);
    }

        engine.detener();
        System.out.println("Juego finalizado.");
    }

}
