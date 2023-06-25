package helpers;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static java.lang.ThreadLocal.withInitial;
@Slf4j
public enum TestContext {
    CONTEXT;
    private final ThreadLocal<Map<String, Object>> testContexts = withInitial(HashMap::new);

    /**
     * Get value from test context
     *
     * @param key key of stored test context
     */
    public <T> T get(String key) {
        return (T) testContexts.get().get(key);
    }

    /**
     * Set value value to test context
     *
     * @param key    key of stored test context
     * @param object object of stored test context
     */
    public <T> void set(String key, T object) {
        testContexts.get().put(key, object);
    }
}
