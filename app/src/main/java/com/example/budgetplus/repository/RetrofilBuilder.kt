package com.example.budgetplus.repository

import android.content.Context
import com.example.budgetplus.BudgetPlusApplication
import com.example.budgetplus.BuildConfig
import com.example.budgetplus.manager.SharedPreferencesManager.get
import com.example.budgetplus.repository.service.AccountService
import com.example.budgetplus.repository.service.GroupService
import com.example.budgetplus.repository.service.TransactionService
import com.example.budgetplus.utils.TOKEN
import com.example.budgetplus.utils.baseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.GeneralSecurityException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class RetrofilBuilder {

    companion object {


        private var context: Context? = null

        @Volatile
        private var INSTANCE: RetrofilBuilder? = null

        private fun getRetrofit(context: Context?): Retrofit {

            try {

                val httpClient: OkHttpClient.Builder = getUnsafeOkHttpClient()

                return Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


            } catch (e: GeneralSecurityException) {
                throw RuntimeException(e)
            }

        }

        private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                    object : X509TrustManager {
                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(
                            chain: Array<X509Certificate>,
                            authType: String
                        ) {
                        }

                        override fun getAcceptedIssuers(): Array<X509Certificate> {
                            return arrayOf()
                        }
                    }
                )

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())

                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }


                builder.connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS) // write timeout
                    .readTimeout(60, TimeUnit.SECONDS) // read timeout
                    .addInterceptor(interceptor)
                    .followRedirects(true)
                    .followSslRedirects(true)
                    .addInterceptor { chain ->
                        val newRequest = chain.request().newBuilder()
                            .addHeader(
                                "Authorization",
                                "Bearer " + BudgetPlusApplication.sharedPreferencesManager[TOKEN, ""]
                            )
                            .build()
                        chain.proceed(newRequest)
                    }
                return builder

            } catch (e: Exception) {
                throw java.lang.RuntimeException(e)
            }
        }

        fun getInstance(context: Context?): RetrofilBuilder =
            INSTANCE ?: synchronized(this) {
                this.context = context
                INSTANCE ?: buildRetrofit(this.context).also { INSTANCE = it }
            }

        private fun buildRetrofit(context: Context?): RetrofilBuilder {
            apiAccountService = getRetrofit(context).create(AccountService::class.java)
            apiTransactionService = getRetrofit(context).create(TransactionService::class.java)
            apiGroupService = getRetrofit(context).create(GroupService::class.java)
            return RetrofilBuilder()

        }

        // Api Service
        lateinit var apiAccountService: AccountService

        lateinit var apiTransactionService: TransactionService

        lateinit var apiGroupService: GroupService

    }
}