package mock.request.core.lang;

import java.util.HashMap;
import java.util.Map;

/**
 *  Map增强
 * @author guokai
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "unused"})
public class MockMap{

    private final Map<Object, Object> container = new HashMap<>();

    public void put(Object key, Object value) {
        container.put(key, value);
    }

    public Object get(Object key) {
        return container.get(key);
    }

    public <T> T get(Object key, Class<T> clz) {
        Object value = container.get(key);
        if (value != null) {
                return (T)value;
        }
        return null;
    }

    public static Builder builder() {
        return new MockMap.Builder();
    }

    public static class Builder{
        private final MockMap mockMap = new MockMap();

        public Builder put(Object key, Object value) {
            mockMap.put(key, value);
            return this;
        }

        public MockMap getInstance() {
            return mockMap;
        }
    }

    @Override
    public String toString() {
        return "MockMap:" + container.toString();
    }
}
