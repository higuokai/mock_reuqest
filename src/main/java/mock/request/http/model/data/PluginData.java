package mock.request.http.model.data;

import java.io.Serializable;

/**
 * @author guokai
 * @version 1.0
 */
public class PluginData implements Serializable {

    private String method;

    private String url;

    public PluginData(String url) {
        this.url = url;
    }

    private Object params;

    private Object headers;

    private Object data;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
