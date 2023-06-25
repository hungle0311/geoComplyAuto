package helpers.map;

import java.util.Map;

public class MapHelper {
    public static String getNestedString(Map map, String... keys) {
        Object value = map;
        for (String key : keys) {
            value = ((Map) value).get(key);
        }
        return value.toString();
    }
}
