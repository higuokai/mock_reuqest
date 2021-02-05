package mock.request.core.model;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author guokai
 * @version 1.0
 */
public class HttpModel {

    private Request request;

    private Response response;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
