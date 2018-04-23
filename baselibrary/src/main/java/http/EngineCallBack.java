package http;

/**
 * @package http
 * @fileName EngineCallBack
 * @Author Bob on 2018/4/22 21:38.
 * @Describe TODO
 */

public interface EngineCallBack {

    /**
     * 请求错误
     * @param e
     */
    public void onError(Exception e);



    /**
     * 请求成功
     * @param result
     */
    public void onSuccess(String result);

    /**
     * 默认的
     */
    public final EngineCallBack DEFAULT_CALL_BACK  = new EngineCallBack() {
        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
