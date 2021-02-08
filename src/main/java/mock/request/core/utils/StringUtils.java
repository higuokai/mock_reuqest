package mock.request.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

    public static String formatJsonString(String text) throws Exception {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
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
