package corelin.plugins.library.http;

import okhttp3.*;

import java.io.IOException;

/**
 * get和post请求
 * 地址http://mc.resources.heallin.cn/module/main?game_version=游戏版本&version=当前前置版本&server_version=服务器版本
 * 获取对应游戏版本和服务端和插件版本的主要开发模块  使用get方法
 * @author 择忆霖心
 * @时间 2021/2/19 21:07
 */
public class HttpClient {


    public String get(String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            return call.execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
