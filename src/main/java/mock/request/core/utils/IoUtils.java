package mock.request.core.utils;

import java.io.Closeable;

/**
 * @author guokai
 * @version 1.0
 */
public class IoUtils {

    public static void close(Closeable closeable) {
        if (closeable == null)
            return;
        try {
            closeable.close();
        } catch (Exception e) {
            // do nothing
        }
    }

}
