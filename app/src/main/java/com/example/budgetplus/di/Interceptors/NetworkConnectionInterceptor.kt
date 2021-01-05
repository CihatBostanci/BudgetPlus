package com.example.budgetplus.di.Interceptors

import android.content.Context
import android.widget.Toast
import com.example.budgetplus.R
import com.example.budgetplus.extensions.isInternetAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!applicationContext.isInternetAvailable) {
            runBlocking(Dispatchers.Main) {
                Toast.makeText(
                    applicationContext,
                    applicationContext.getString(R.string.alertMessageCheckConnection),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return chain.proceed(chain.request())
    }
}
