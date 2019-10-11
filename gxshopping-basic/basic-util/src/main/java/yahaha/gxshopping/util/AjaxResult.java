package yahaha.gxshopping.util;

public class AjaxResult {

    private Boolean success = true;

    private String message = "成功";

    //若需要返回对象，直接封装到这
    private Object restObj;

    public static AjaxResult me() {
        return new AjaxResult();
    }

    public Boolean getSuccess() {
        return success;
    }

    public AjaxResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public AjaxResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getRestObj() {
        return restObj;
    }

    public AjaxResult setRestObj(Object restObj) {
        this.restObj = restObj;
        return this;
    }
}
