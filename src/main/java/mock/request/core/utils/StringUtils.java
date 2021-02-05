package mock.request.core.utils;

/**
 * @author guokai
 * @version 1.0
 */
@SuppressWarnings("unused")
public class StringUtils {

    public static boolean isEmpty(Object source) {
        return source == null || "".equals(source);
    }

    public static boolean isNotEmpty(Object source) {
        return !isEmpty(source);
    }

    /**
     * 拼接
     */
    public static String join(String...args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 带分隔符拼接
     */
    public static String joinWithSplit(String splitSymbol, String...args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            if (isNotEmpty(s)) {
                sb.append(s).append(splitSymbol);
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    private StringUtils() {}

}
