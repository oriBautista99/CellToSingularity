package model;

import model.enums.EstadoElemento;
import java.util.*;

public class ElementoEvolutivo {
    private String nombre;
    private Map<String, Double> costo; // Recurso → cantidad
    private List<String> requisitos; // Nombres de otros elementos requeridos
    private double produccion;
    private String tipoRecursoProducido;
    private EstadoElemento estado;
    private int nivelMax;
    private int nivelActual;
    private int tiempoDesbloqueo; // En segundos

    public ElementoEvolutivo(String nombre, Map<String, Double> costo, List<String> requisitos,
                             double produccion, String tipoRecursoProducido,
                             int nivelMax, int tiempoDesbloqueo) {
        this.nombre = nombre;
        this.costo = costo;
        this.requisitos = requisitos;
        this.produccion = produccion;
        this.tipoRecursoProducido = tipoRecursoProducido;
        this.estado = EstadoElemento.BLOCKED;
        this.nivelMax = nivelMax;
        this.nivelActual = 0;
        this.tiempoDesbloqueo = tiempoDesbloqueo;
    }

    public String getNombre() {
        return nombre;
    }

    public EstadoElemento getEstado() {
        return estado;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public double getProduccion() {
        return produccion * nivelActual;
    }

    public String getTipoRecursoProducido() {
        return tipoRecursoProducido;
    }

    public Map<String, Double> getCosto() {
        return costo;
    }

    public List<String> getRequisitos() {
        return requisitos;
    }

    public boolean puedeMejorar() {
        return nivelActual < nivelMax;
    }

    public void activar() {
        estado = EstadoElemento.ACTIVE;
    }

    public void desbloquear() {
        if (estado == EstadoElemento.BLOCKED) {
            estado = EstadoElemento.ENABLED;
        }
    }

    public void mejorar() {
        if (puedeMejorar()) {
            nivelActual++;
        }
    }

    public boolean estaDisponible() {
        return estado == EstadoElemento.ENABLED;
    }

    public boolean estaActivo() {
        return estado == EstadoElemento.ACTIVE;
    }

    public void reiniciar() {
        estado = EstadoElemento.BLOCKED;
        nivelActual = 0;
    }

    public boolean requisitosCumplidos(Set<String> elementosActivos) {
        for (String requisito : requisitos) {
            if (!elementosActivos.contains(requisito)) {
                return false;
            }
        }
        return true;
    }
}
