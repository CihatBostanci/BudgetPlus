package com.example.budgetplus.service

import com.example.budgetplus.model.request.ChangePasswordRequestBodyModel
import com.example.budgetplus.model.request.ConfirmRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.model.request.RegisterRequestBodyModel
import com.example.budgetplus.utils.*
import okhttp3.ResponseBody
import retrofit2.http.*

interface AccountService {

    @POST(REGISTER_SERVICE)
    suspend fun accountRegister(
        @Body registerRequestBodyModel: RegisterRequestBodyModel
    ): ResponseBody

    @POST(AUTHENTICATE_SERVICE)
    suspend fun accountAuthenticate(
            @Body loginRequestBodyModel: LoginRequestBodyModel
    ): ResponseBody

    @POST(LOGIN_SERVICE)
    suspend fun accountLogin(
        @Body loginRequestBodyModel: LoginRequestBodyModel
    ): ResponseBody






}