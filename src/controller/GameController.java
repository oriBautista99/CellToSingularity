package controller;

import model.*;
import model.enums.EstadoElemento;
import util.TimerManager;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.*;

public class GameController {
    private Jugador jugador;
    private List<ElementoEvolutivo> todosLosElementos;
    private List<Mejora> mejorasDisponibles;
    private TimerManager timerManager;

    private List<ElementoEvolutivo> arbolEvolutivoRaiz;
    private boolean desbloqueo = false;

    public GameController(Jugador jugador, List<ElementoEvolutivo> elementosIniciales) {
        this.jugador = jugador;
        this.mejorasDisponibles = new ArrayList<>();
        this.timerManager = new TimerManager();
        // Carga el árbol jerárquico
        this.todosLosElementos= ElementoLoader.cargarDesdeJson();
        this.arbolEvolutivoRaiz = new ArrayList<>();
        recolectarTodosLosElementos(this.todosLosElementos, this.arbolEvolutivoRaiz);
        iniciarTimer();
    }

    private void iniciarTimer() {
        timerManager.iniciarTimer(() -> {
            generarRecursosPasivos();
            verificarDesbloqueos();
        },1000);
    }

    private void recolectarTodosLosElementos(List<ElementoEvolutivo> origen, List<ElementoEvolutivo> acumulador) {
        for (ElementoEvolutivo e : origen) {
            acumulador.add(e);
            recolectarTodosLosElementos(e.getHijos(), acumulador);
        }
    }

    private void generarRecursosPasivos() {
        for (ElementoEvolutivo e : jugador.getElementosActivos()) {
            String tipo = e.getTipoRecursoProducido();
            double cantidad = e.getProduccionActual();
            jugador.aumentarRecurso(tipo, cantidad);
        }
    }

    private void verificarDesbloqueos() {
        for (ElementoEvolutivo e : arbolEvolutivoRaiz) {
            if (e.getEstado() == EstadoElemento.BLOCKED) {
                boolean cumplidos = e.requisitosCumplidos(jugador.getElementosDesbloqueados());
                if (cumplidos) {
                    e.desbloquear();
                    desbloqueo = true;
                    System.out.println("🔓 " + e.getNombre() + " fue desbloqueado.");
                }
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
            jugador.comprarElemento(elem); // descuenta recursos
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

    public DefaultMutableTreeNode construirNodo(ElementoEvolutivo elemento) {
        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(elemento); // Usa .toString()
        for (ElementoEvolutivo hijo : elemento.getHijos()) {
            nodo.add(construirNodo(hijo));
        }
        return nodo;
    }

    public DefaultMutableTreeNode construirArbolCompleto() {
        DefaultMutableTreeNode raizVisual = new DefaultMutableTreeNode("Evolucion");

        for (ElementoEvolutivo raiz : arbolEvolutivoRaiz) {
            raizVisual.add(construirNodo(raiz));
        }

        return raizVisual;
    }
}
