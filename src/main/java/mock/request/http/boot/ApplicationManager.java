package mock.request.http.boot;

import mock.request.core.lang.MockMap;

/**
 * @author guokai
 * @version 1.0
 */
@SuppressWarnings("unused")
public class ApplicationManager {

    private static ApplicationManager applicationManager;

    private final MockMap mockMap;

    public void register(Object object, Class<?> clz) {
        if (object == null) {
            return;
        }
        mockMap.put(clz.getName(), object);
    }

    public <T> T getBean(Class<T> clz) {
        if (clz == null) {
            return null;
        }
        String key = clz.getName();
        return mockMap.get(key, clz);
    }

    public static ApplicationManager getInstance() {
        if (applicationManager == null) {
            synchronized (ApplicationManager.class) {
                if (applicationManager == null) {
                    applicationManager = new ApplicationManager();
                }
            }
        }
        return applicationManager;
    }

    private ApplicationManager() {
        mockMap = MockMap.builder().getInstance();
    }

}
