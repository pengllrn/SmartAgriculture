package com.base.data.net

import com.base.common.BaseConstant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory private constructor(){
    companion object {
        val instance : RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit : Retrofit
    private val interceptor : Interceptor//okhttp3拦截器

    init {
        interceptor = Interceptor {
            chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type","application/json")
                .addHeader("charset","utf-8")
//                .addHeader("token","xxx")//每次都需要的验证机制，需要的话添加
                .build()
            chain.proceed(request)
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BaseConstant.SERVER_ADDRESS)
            .addConverterFactory(GsonConverterFactory.create())  //添加数据转化工具
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //添加Rx适配器
            .client(initClient())
            .build()
    }

    private fun initClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(initLogInterceptor())  //日志拦截器
            .addInterceptor(interceptor)  //网络拦截器
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10,TimeUnit.SECONDS)
            .build()
    }

    private fun initLogInterceptor():Interceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY //日志输出级别
        return interceptor
    }

    fun <T> create(service : Class<T>):T{
        return retrofit.create(service)
    }


}