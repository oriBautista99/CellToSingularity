package controller;

import model.*;
import model.enums.EstadoElemento;
import util.TimerManager;

import java.util.*;

public class GameController {
    private Jugador jugador;
    private List<ElementoEvolutivo> todosLosElementos;
    private List<Mejora> mejorasDisponibles;
    private TimerManager timerManager;

    public GameController(Jugador jugador, List<ElementoEvolutivo> elementosIniciales) {
        this.jugador = jugador;
        this.todosLosElementos = elementosIniciales;
        this.mejorasDisponibles = new ArrayList<>();
        this.timerManager = new TimerManager();
        iniciarTimer();
    }

    private void iniciarTimer() {
        timerManager.iniciarTimer(() -> {
            generarRecursosPasivos();
            verificarDesbloqueos();
        },1000);
    }

    private void generarRecursosPasivos() {
        for (ElementoEvolutivo e : jugador.getElementosActivos()) {
            String tipo = e.getTipoRecursoProducido();
            double cantidad = e.getProduccionActual();
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
        timerManager.detener();
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

    public boolean comprarElemento(ElementoEvolutivo elem) {
        if(elem.getEstado() == EstadoElemento.ENABLED && jugador.tieneRecursos(elem.getCosto())) {
            jugador.descontarRecursos(elem.getCosto());
            jugador.comprarElemento(elem); // cambiar a activos
            elem.activar();
            return true;
        }
        return false;
    }

    // por si se reinicia o hay nueva etapa
    public void reiniciarJuego() {
        jugador.reiniciar(); //metodo en Jugador que borra progreso
        for (ElementoEvolutivo e : todosLosElementos) {
            e.reiniciar(); // vuelve a estado BLOQUEADO, nivel 1
        }
    }

    public void aplicarMejora(Mejora m) {
        m.aplicar(jugador);
    }
}
