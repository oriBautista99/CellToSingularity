package model;

public class Recurso {
    private String tipo;
    private double cantidad;
    private double tasaGeneracion;

    public Recurso(String tipo, double cantidad, double tasaGeneracion) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.tasaGeneracion = tasaGeneracion;
    }

    public String getTipo() {
        return tipo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public double getTasaGeneracion() {
        return tasaGeneracion;
    }

    public void setTasaGeneracion(double tasaGeneracion) {
        this.tasaGeneracion = tasaGeneracion;
    }

    public void aumentarCantidad(double cantidad) {
        this.cantidad += cantidad;
    }

    public boolean consumir(double cantidad) {
        if (this.cantidad >= cantidad) {
            this.cantidad -= cantidad;
            return true;
        }
        return false;
    }

    public void reiniciar() {
        this.cantidad = 0;
        this.tasaGeneracion = 0;
    }
}
