package helpers;

import com.jayway.jsonpath.DocumentContext;

import java.util.List;
import java.util.Map;

public class JsonReader {
    private final DocumentContext jsonDocument;
    JsonReader(final DocumentContext documentContext) {jsonDocument = documentContext;}

    public String asString(final String keyPath) { return jsonDocument.read(keyPath).toString();}

    public List<?> asList(final String keyPath) {return jsonDocument.read(keyPath);}

    public Map<?,?> asMap(final String keyPath) {return jsonDocument.read(keyPath);}
}
