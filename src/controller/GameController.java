package controller;

import model.*;
import model.enums.EstadoElemento;

import java.util.*;

public class GameController {
    private Jugador jugador;
    private List<ElementoEvolutivo> todosLosElementos;
    private Timer timer;

    public GameController(Jugador jugador, List<ElementoEvolutivo> elementosIniciales) {
        this.jugador = jugador;
        this.todosLosElementos = elementosIniciales;
        iniciarTimer();
    }

    private void iniciarTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                generarRecursosPasivos();
                verificarDesbloqueos();
            }
        }, 0, 1000); // cada 1 segundo
    }

    private void generarRecursosPasivos() {
        for (ElementoEvolutivo e : jugador.getElementosActivos()) {
            String tipo = e.getTipoRecursoProducido();
            double cantidad = e.getProduccion();
            jugador.aumentarRecurso(tipo, cantidad);
        }
    }

    private void verificarDesbloqueos() {
        for (ElementoEvolutivo e : todosLosElementos) {
            if (e.getEstado() == EstadoElemento.BLOCKED && e.requisitosCumplidos(jugador.getElementosDesbloqueados())) {
                e.desbloquear();
            }
        }
    }

    public void detener() {
        if (timer != null) timer.cancel();
    }

    public Jugador getJugador() {
        return jugador;
    }

    public List<ElementoEvolutivo> getTodosLosElementos() {
        return todosLosElementos;
    }

    public void hacerClick() {
        jugador.hacerClick();
    }

}
