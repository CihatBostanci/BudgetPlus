package com.example.budgetplus.di

import com.example.budgetplus.di.Interceptors.HeaderInterceptor
import com.example.budgetplus.di.Interceptors.NetworkConnectionInterceptor
import com.example.budgetplus.di.Interceptors.createHttpLoggingInterceptor
import com.example.budgetplus.service.*
import com.example.budgetplus.utils.baseUrl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single { createHttpLoggingInterceptor() }
    single { HeaderInterceptor() }
    single { NetworkConnectionInterceptor(context = get()) }
    single(named("safe")) {
        createOkHttpClient(
            isSafe = false,
            headerInterceptor = get(),
            networkConnectionInterceptor = get(),
            httpLoggingInterceptor = get(),
        )
    }
    single(named("unsafe")){
        createOkHttpClient(
            isSafe = false,
            headerInterceptor = get(),
            networkConnectionInterceptor = get(),
            httpLoggingInterceptor = get(),
        )
    }


    //Create Web Service with BuildConfig.API_ADDRESS
    single { createWebService<GroupService>(okHttpClient = get(qualifier = named("safe")), url = baseUrl) }
    single { createWebService<TransactionService>(okHttpClient = get(qualifier = named("safe")), url = baseUrl) }
    single { createWebService<AccountService>(okHttpClient = get(qualifier = named("safe")), url = baseUrl) }
    single { createWebService<EmailVerificationService>(okHttpClient = get(qualifier = named("safe")), url = baseUrl) }
    single { createWebService<JoinRequestService>(okHttpClient = get(qualifier = named("safe")), url = baseUrl) }
    single { createWebService<PasswordService>(okHttpClient = get(qualifier = named("safe")), url = baseUrl) }
    single { createWebService<UserService>(okHttpClient = get(qualifier = named("safe")), url = baseUrl) }


}