package com.example.budgetplus.di

import com.example.budgetplus.utils.DEFAULTERRORMESSAGE
import com.example.budgetplus.utils.Resource
import com.example.budgetplus.utils.errorHTTP400Handler
import okhttp3.ResponseBody
import retrofit2.HttpException


suspend fun <T : Any> safeApiCall(apiCall: suspend () -> T): Resource<T> {

    return try {
        val response = apiCall()
        Resource.success(response)

    } catch (ex: Exception) {
        when (ex) {
            is HttpException -> {
                when (ex.code()) {
                    400 -> {
                        Resource.error(
                            data = null,
                            message = errorHTTP400Handler(ex)?.message ?: DEFAULTERRORMESSAGE
                        )

                    }
                    401 -> {
                        Resource.error(
                            data = null,
                            message = ex.message()
                        )

                    }
                    else ->
                        Resource.error(
                            data = null,
                            message = DEFAULTERRORMESSAGE
                        )

                }
            }
            else -> Resource.error(data = null, DEFAULTERRORMESSAGE)
        }
    }
}


