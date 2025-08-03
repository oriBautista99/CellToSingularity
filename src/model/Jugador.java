package model;

import java.util.*;

public class Jugador {
    private String nombre;
    private Map<String, Recurso> recursos; // tipo → recurso (entropía, ideas, etc.)
    private Map<String, ElementoEvolutivo> elementosComprados; // nombre → elemento
    private int clickPower;
    private int totalClicks;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.recursos = new HashMap<>();
        this.elementosComprados = new HashMap<>();
        this.clickPower = 1;
        this.totalClicks = 0;

        // Inicializar recursos básicos
        recursos.put("Entropía", new Recurso("Entropía", 0, 0));
        recursos.put("Ideas", new Recurso("Ideas", 0, 0));
    }

    public String getNombre() {
        return nombre;
    }

    public int getClickPower() {
        return clickPower;
    }

    public void hacerClick() {
        totalClicks++;
        aumentarRecurso("Entropía", clickPower);
    }

    public void aumentarRecurso(String tipo, double cantidad) {
        if (!recursos.containsKey(tipo)) {
            recursos.put(tipo, new Recurso(tipo, 0, 0));
        }
        recursos.get(tipo).aumentarCantidad(cantidad);
    }

    public double getCantidadRecurso(String tipo) {
        return recursos.getOrDefault(tipo, new Recurso(tipo, 0, 0)).getCantidad();
    }

    public boolean tieneRecursos(Map<String, Double> costo) {
        for (Map.Entry<String, Double> entry : costo.entrySet()) {
            String tipo = entry.getKey();
            double requerido = entry.getValue();
            if (getCantidadRecurso(tipo) < requerido) {
                return false;
            }
        }
        return true;
    }

    public void descontarRecursos(Map<String, Double> costo) {
        for (Map.Entry<String, Double> entry : costo.entrySet()) {
            String tipo = entry.getKey();
            double cantidad = entry.getValue();
            recursos.get(tipo).aumentarCantidad(-cantidad);
        }
    }

    public void comprarElemento(ElementoEvolutivo elemento) {
        if (tieneRecursos(elemento.getCosto())) {
            descontarRecursos(elemento.getCosto());
            elemento.activar();
            elementosComprados.put(elemento.getNombre(), elemento);
        }
    }

    public Set<String> getElementosDesbloqueados() {
        return elementosComprados.keySet();
    }

    public Collection<ElementoEvolutivo> getElementosActivos() {
        List<ElementoEvolutivo> activos = new ArrayList<>();
        for (ElementoEvolutivo e : elementosComprados.values()) {
            if (e.estaActivo()) {
                activos.add(e);
            }
        }
        return activos;
    }

    public Map<String, Recurso> getRecursos() {
        return recursos;
    }

    public int getTotalClicks() {
        return totalClicks;
    }

    public void reiniciar() {
        totalClicks = 0;
        elementosComprados.clear();
        recursos.clear();
        recursos.put("Entropía", new Recurso("Entropía", 0, 0));
        recursos.put("Ideas", new Recurso("Ideas", 0, 0));
        clickPower = 1;
    }

}
