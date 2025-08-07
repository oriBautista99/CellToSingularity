package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ElementoEvolutivo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElementoLoader {

    public static List<ElementoEvolutivo> cargarDesdeJson() {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = ElementoLoader.class.getClassLoader()
                .getResourceAsStream("arbol_evolutivo.json")) {

            if (is == null) {
                throw new RuntimeException("No se encontró el archivo arbol_evolutivo.json en resources");
            }

            return mapper.readValue(is, new TypeReference<List<ElementoEvolutivo>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo el archivo JSON: " + e.getMessage(), e);
        }
    }

    public static List<ElementoEvolutivo> construirJerarquia(List<ElementoEvolutivo> elementos) {
        Map<String, ElementoEvolutivo> mapa = new HashMap<>();
        for (ElementoEvolutivo e : elementos) {
            mapa.put(e.getNombre(), e);
        }

        List<ElementoEvolutivo> raices = new ArrayList<>();
        for (ElementoEvolutivo e : elementos) {
            if (e.getRequisitos() == null || e.getRequisitos().isEmpty()) {
                raices.add(e); // no tiene requisitos, es raíz
            } else {
                for (String nombrePadre : e.getRequisitos()) {
                    ElementoEvolutivo padre = mapa.get(nombrePadre);
                    if (padre != null) {
                        padre.getHijos().add(e);
                    }
                }
            }
        }

        return raices;
    }

}
