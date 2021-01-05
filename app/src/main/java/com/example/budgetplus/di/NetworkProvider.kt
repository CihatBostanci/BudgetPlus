package com.example.budgetplus.di

import com.example.budgetplus.BuildConfig
import com.example.budgetplus.di.Interceptors.HeaderInterceptor
import com.example.budgetplus.di.Interceptors.NetworkConnectionInterceptor
import com.grapesnberries.curllogger.CurlLoggerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val READ_TIMEOUT_IN_MILLISECONDS = 60000L
private const val CONNECT_TIMEOUT_IN_MILLISECONDS = 60000L


fun createOkHttpClient(isSafe: Boolean,
                       httpLoggingInterceptor: HttpLoggingInterceptor,
                       headerInterceptor: HeaderInterceptor,
                       networkConnectionInterceptor: NetworkConnectionInterceptor,
                       ): OkHttpClient {
    val httpClient = OkHttpClient.Builder()

    if(!isSafe)
        httpClient.hostnameVerifier { _, _ -> true }


    return with(httpClient) {

        connectTimeout(CONNECT_TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
        readTimeout(READ_TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS)
        addInterceptor(headerInterceptor)
        addInterceptor(httpLoggingInterceptor)
        addInterceptor(networkConnectionInterceptor)
        if (BuildConfig.DEBUG) addInterceptor(CurlLoggerInterceptor())
        build()
    }
}


inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    return with(Retrofit.Builder()) {
        baseUrl(url)
        client(okHttpClient)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }.create(T::class.java)
}
