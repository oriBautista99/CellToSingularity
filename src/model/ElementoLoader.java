package model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.ElementoEvolutivo;

import java.io.InputStream;
import java.util.List;

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

}
