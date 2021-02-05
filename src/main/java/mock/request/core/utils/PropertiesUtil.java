package mock.request.core.utils;

import com.intellij.ide.util.PropertiesComponent;

/**
 * @author guokai
 * @version 1.0
 */
public class PropertiesUtil {

    public static void set(String key, String value){
        PropertiesComponent.getInstance().setValue(key, value);
    }

    public static String get(String key) {
        return PropertiesComponent.getInstance().getValue(key);
    }

    public static void delete(String key) {
        PropertiesComponent.getInstance().unsetValue(key);
    }

}
