package helpers.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class JsonConverter {
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Convert JSON to HashMap
     *
     * @param jsonPath to get the file
     * @return Map of String and Object
     */
    public static Map<String, Object> convertJsonFileToHashMap(String jsonPath) {
        String json = convertJSONToString(jsonPath);
        Map<String, Object> data = null;
        MapType type = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);
        try {
            data = mapper.readValue(json, type);
        } catch (IOException exception) {
            log.error("Error happens when read JSON value: ", exception);
        }
        return data;
    }

    /**
     * Convert JSON to string
     *
     * @param jsonPath want to get
     * @return JSON String
     */
    public static String convertJSONToString(String jsonPath) {
        try {
            return FileUtils.readFileToString(new File(jsonPath), "utf-8");
        } catch (IOException exception) {
            log.error("Error happens when read JSON file: ", exception);
            return null;
        }
    }
}
