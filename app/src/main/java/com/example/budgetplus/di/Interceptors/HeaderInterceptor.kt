package com.example.budgetplus.di.Interceptors

import android.os.Build
import com.example.budgetplus.BudgetPlusApplication
import com.example.budgetplus.manager.SharedPreferencesManager.get
import com.example.budgetplus.utils.TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val baseRequest = chain.request()
        val request =baseRequest.newBuilder()
        request.method(baseRequest.method(), baseRequest.body())
            .addHeader(
                "Authorization",
                "Bearer " + BudgetPlusApplication.sharedPreferencesManager[TOKEN, ""]
            )
        return chain.proceed(request.build())
    }
}