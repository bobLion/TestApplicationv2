package http;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @package http
 * @fileName OKHttpEngine
 * @Author Bob on 2018/4/22 21:40.
 * @Describe TODO
 */

public class OKHttpEngine implements IHttpEngine {


    private static OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    public void get(Context context, String url, Map<String, Object> params, final EngineCallBack callBack) {
        url = HttpUtils.joinParams(url,params);
        Log.i("GET 请求路径：",url);
        Request.Builder requestBuilder = new Request.Builder().url(url).tag(context);
        requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultJson = response.body().string();
                Log.i("GET 返回结果:",resultJson);
                callBack.onSuccess(resultJson);
            }
        });
    }

    @Override
    public void post(Context context,String url, Map<String, Object> params, final EngineCallBack callBack) {
        final String joinUrl = HttpUtils.joinParams(url,params);
        Log.i("post 请求参数:",joinUrl);
        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        Log.i("post 返回结果:",result);
                        callBack.onSuccess(result);
                    }
                }
        );
    }

    /**
     * 组装post请求参数body
     * @param params
     * @return
     */
    protected RequestBody appendBody(Map<String,Object> params){
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder,params);
        return builder.build();
    }

    /***
     * 添加参数
     * @param builder
     * @param params
     */
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if(null != params && !params.isEmpty()){
            for(String key:params.keySet()){
                builder.addFormDataPart(key,params.get(key)+"");
                Object value = params.keySet();
                if(value instanceof File){
                    // 处理文件--> object File
                    File file = (File)value;
                    builder.addFormDataPart(key,file.getName(),RequestBody
                    .create(MediaType.parse(guessMimeType(file.getAbsolutePath())),file));
                }else if(value instanceof List){
                    // 提交的是list集合
                    try {
                        List<File> fileList = (List<File>) value;
                        for(int i = 0;i<fileList.size();i++){
                            // 获取文件
                            File file = fileList.get(i);
                            builder.addFormDataPart(key + i ,file.getName(),RequestBody
                                    .create(MediaType.parse(guessMimeType(file.getAbsolutePath())),file));
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    builder.addFormDataPart(key,value+"");
                }
            }
        }
    }


    /**
     * 猜测文件类型
     * @param path
     * @return
     */
    private String guessMimeType(String path){
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor  = fileNameMap.getContentTypeFor(path);
        if(null == contentTypeFor){
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
