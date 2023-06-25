package helpers.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public enum GenerateDataConfig {

    GENERATE_CONFIG;

    private HashMap<String, String> generateConfig = new HashMap<>();

    /**
     * Convert the json String to a HashMap
     *
     * @param jsonPath path of the JSON file that want to parsing
     * @return HashMap<String, String>
     * @author phoenix
     */
//    TODO: Need to cleanup when we reuse from common utils
    public static Map<String, String> convertJsonFileToHashMap(String jsonPath) {
        final String json = convertJSONToString(jsonPath);
        Map<String, String> data = null;
        final ObjectMapper mapper = new ObjectMapper();
        final MapType type = mapper.getTypeFactory().constructMapType(
                Map.class, String.class, Object.class);
        try {
            data = mapper.readValue(json, type);
        } catch (IOException ex) {
            log.error("Error happens when read JSON value");
            ex.printStackTrace();
        }
        return data;
    }

    /**
     * Convert the json file to a JSON String
     *
     * @param jsonPath path of the JSON file that want to parsing
     * @return String
     * @author phoenix
     * TODO: Need to cleanup when we reuse from common utils
     */
    public static String convertJSONToString(String jsonPath) {
        try {
            return FileUtils.readFileToString(new File(jsonPath), "utf-8");
        } catch (IOException e) {
            log.error("Error happens when read JSON file");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Setup the config for generate date
     * Normally use in the Hook
     *
     * @param configPath a path to “generateConfig.properties” in the project
     */
    public void setGenerateConfig(String configPath) {
        generateConfig = (HashMap<String, String>) convertJsonFileToHashMap(configPath);
    }
}
