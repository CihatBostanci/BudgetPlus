package com.example.budgetplus.service

import com.example.budgetplus.utils.GET_USER_INFO_SERVICE
import okhttp3.ResponseBody
import retrofit2.http.GET

interface UserService {


    @GET(GET_USER_INFO_SERVICE)
    suspend fun getUserInfo(
    ): ResponseBody



}