package com.example.budgetplus.di.Interceptors

import com.example.budgetplus.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

fun createHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.BASIC
    }