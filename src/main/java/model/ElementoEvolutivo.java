package model;

import model.enums.EstadoElemento;
import java.util.*;

public class ElementoEvolutivo {
    private String nombre;
    private String descripcion;
    private Map<String, Double> costo; // Recurso → cantidad
    private List<String> requisitos; // Nombres de otros elementos requeridos
    private double produccionBase;
    private String tipoRecursoProducido;
    private EstadoElemento estado;
    private int nivelMax;
    private int nivelActual;
    private int tiempoDesbloqueo; // En segundos
    private double multiplicadorMejora = 1.0;
    private String imagenPath;
    private List<ElementoEvolutivo> hijos = new ArrayList<>();

    public ElementoEvolutivo() {}

    public ElementoEvolutivo(String nombre,String descripcion, Map<String, Double> costo, List<String> requisitos,
                             double produccion, String tipoRecursoProducido,
                             int nivelMax, int tiempoDesbloqueo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.requisitos = requisitos;
        this.produccionBase = produccion;
        this.tipoRecursoProducido = tipoRecursoProducido;
        this.estado = EstadoElemento.BLOCKED;
        this.nivelMax = nivelMax;
        this.nivelActual = 0;
        this.tiempoDesbloqueo = tiempoDesbloqueo;
    }

    public int getNivelMax() {
        return nivelMax;
    }

    public void setNivelMax(int nivelMax) {
        this.nivelMax = nivelMax;
    }

    public List<ElementoEvolutivo> getHijos() {
        return hijos;
    }

    public void setHijos(List<ElementoEvolutivo> hijos) {
        this.hijos = hijos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public EstadoElemento getEstado() {
        return estado;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public String getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
    }

    public double getProduccionActual() {
        return produccionBase * Math.pow(1.15, nivelActual) * multiplicadorMejora;
    }

    public void aplicarMultiplicador(double extra) {
        multiplicadorMejora *= extra;
    }

    public double getProduccionBase() {
        return produccionBase;
    }

    public void setProduccionBase(double produccionBase) {
        this.produccionBase = produccionBase;
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

    public void setRequisitos(List<String> requisitos) {
        this.requisitos = requisitos;
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
        // deberia verificar que tiene los elementos evolutivos anteriores y la cantidad de recurso para desbloquear
        // o crear otro estado que sea parar manejar visibilidad y otro habilitado
        for (String requisito : requisitos) {
            if (!elementosActivos.contains(requisito)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
