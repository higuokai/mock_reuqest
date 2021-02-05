package mock.request.core.lang;

import java.io.Serializable;

/**
 * @author guokai
 * @version 1.0
 */
public class Result implements Serializable {

    public Throwable cause;

    public MockMap data;

    public int success;

    public Result(int success) {
        if (success < -1 || success > 1) {
            throw new IllegalArgumentException("success state can not be :" + success);
        }
        this.success = success;
    }

    public static Result fail() {
        return new Result(-1);
    }

    public boolean isSuccess() {
        return success > -1;
    }

    public boolean isFail() {
        return success == -1;
    }

    public Throwable getCause() {
        return cause;
    }

    public static Result success() {
        return new Result(1);
    }

    public static Result success(MockMap data) {
        return new Result(1, data);
    }

    public Result(int success, MockMap data, Throwable cause) {
        this.cause = cause;
        this.data = data;
        this.success = success;
    }

    public Result(int success, MockMap data) {
        this(success, data, null);
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public MockMap getData() {
        return data;
    }

    public void setData(MockMap data) {
        this.data = data;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
