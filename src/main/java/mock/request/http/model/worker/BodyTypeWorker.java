package mock.request.http.model.worker;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 *  请求体执行类
 * @author guokai
 * @version 1.0
 */
public abstract class BodyTypeWorker {

    public abstract RequestBody buildRequestBody();

    public static class NoneWorker extends BodyTypeWorker{
        /**
         * 没有请求体
         */
        @Override
        public RequestBody buildRequestBody() {
            return null;
        }
    }

    public static class FormDataWorker extends BodyTypeWorker {
        @Override
        public RequestBody buildRequestBody() {
            new FormBody.Builder().add("username", "admin")
                    .add("password", "admin");
            return null;
        }
    }

    public static class UrlEncodedWorker extends BodyTypeWorker {
        @Override
        public RequestBody buildRequestBody() {
            return null;
        }
    }

    public static class BinaryWorker extends BodyTypeWorker{
        @Override
        public RequestBody buildRequestBody() {
            return null;
        }
    }

}
