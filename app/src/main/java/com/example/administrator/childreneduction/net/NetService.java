package com.example.administrator.childreneduction.net;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class NetService {
    private static final int DEFAULT_TIME_OUT = 5;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;

    private static NetService mNetService;
    private static Retrofit.Builder mBuilder;

    /**
     *
     */
    private NetService() {
        mBuilder = new Retrofit.Builder()
                .client(initOkhttp().build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NetContent.BASE_URL);
    }

    /**
     * @return
     */
    private OkHttpClient.Builder initOkhttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        if (!cookies.isEmpty()){
                            //保存到共享参数值

                        }
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        return null;
                    }
                });
        return builder;
    }

    /**
     * @return
     */
    public static Retrofit getInstance() {
        if (mNetService == null) {
            synchronized (NetService.class) {
                if (mNetService == null) {
                    mNetService = new NetService();
                }
            }
        }
        return mBuilder.build();
    }

    /**
     * @param service
     * @param <T>
     * @return
     */
    public static <T> T create(Class<T> service) {
        return getInstance().create(service);
    }


}
