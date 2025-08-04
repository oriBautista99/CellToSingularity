package model;

import java.util.Optional;


public class Mejora {
    private String nombre;
    private double multiplicador;
    private String tipoMejora; // Ej: "produccion", "costo", "clickPower"
    private Optional<String> objetivoElemento; // Nombre del elemento evolutivo (si aplica)

    public Mejora(String nombre, double multiplicador, String tipoMejora, String objetivoElemento) {
        this.nombre = nombre;
        this.multiplicador = multiplicador;
        this.tipoMejora = tipoMejora;
        this.objetivoElemento = Optional.ofNullable(objetivoElemento);
    }

    public String getNombre() {
        return nombre;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public String getTipoMejora() {
        return tipoMejora;
    }

    public Optional<String> getObjetivoElemento() {
        return objetivoElemento;
    }

    // aplica la mejora
    public void aplicar(Jugador jugador) {
        switch (tipoMejora.toLowerCase()) {
            case "produccion":
                objetivoElemento.ifPresent(nombreElem -> {
                    for (ElementoEvolutivo elem : jugador.getElementosComprados()) {
                        if (elem.getNombre().equals(nombreElem)) {
                           // elem.setProduccion(elem.getProduccion() * multiplicador);
                            elem.aplicarMultiplicador(multiplicador);
                        }
                    }
                });
                break;

            case "costo":
                // se Podría implementar lógica para reducir costos
                break;

            case "clickpower":
                jugador.setClickPower(jugador.getClickPower() * multiplicador);
                break;

            default:
                System.out.println("Tipo de mejora desconocido: " + tipoMejora);
                break;
        }
    }

}
