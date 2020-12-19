package com.example.budgetplus.repository.service

import com.example.budgetplus.model.request.ChangePasswordRequestBodyModel
import com.example.budgetplus.model.request.ConfirmRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.model.request.RegisterRequestBodyModel
import com.example.budgetplus.utils.*
import okhttp3.ResponseBody
import retrofit2.http.*

interface AccountService {

    @POST(REGISTERSERVICE)
    suspend fun accountRegister(
        @Body registerRequestBodyModel: RegisterRequestBodyModel
    ): ResponseBody

    @POST(LOGINSERVICE)
    suspend fun accountLogin(
        @Body loginRequestBodyModel: LoginRequestBodyModel,
    ): ResponseBody

    @POST(AUTHENTICATESERVICE)
    suspend fun accountAuthenticate(
        @Body loginRequestBodyModel: LoginRequestBodyModel
    ): ResponseBody

    @POST(CONFIRMEMAILSERVICE)
    suspend fun accountConfirmEmail(
        @Body confirmRequestBodyModel: ConfirmRequestBodyModel
    ): ResponseBody

    @POST(UPDATEVERIFICATIONSERVICE)
    suspend fun updateVerificationCode(
        @Body loginRequestBodyModel: LoginRequestBodyModel,
    ): ResponseBody

    @POST(RESETPASSWORDSERVICE)
    suspend fun resetPassword(
        @Query("email") email: String
    ): ResponseBody

    @POST(CONTROLRESETCODESERVICE)
    suspend fun controlResetCode(
        @Query("email") email: String,
        @Query("resetCode") resetCode: String
    ): ResponseBody

    @POST(CHANGEPASSWORDSERVICE)
    suspend fun changePassword(
       @Body changePasswordRequestBodyModel : ChangePasswordRequestBodyModel
    ): ResponseBody

    @GET(GETUSERINFOSERVICE)
    suspend fun getUserInfo():ResponseBody

}