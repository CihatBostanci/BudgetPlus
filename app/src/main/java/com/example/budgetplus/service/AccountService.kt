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

    @POST(LOGIN_SERVICE)
    suspend fun accountLogin(
        @Body loginRequestBodyModel: LoginRequestBodyModel
    ): ResponseBody

    @POST(AUTHENTICATE_SERVICE)
    suspend fun accountAuthenticate(
        @Body loginRequestBodyModel: LoginRequestBodyModel
    ): ResponseBody

    @POST(CONFIRM_EMAIL_SERVICE)
    suspend fun accountConfirmEmail(
        @Body confirmRequestBodyModel: ConfirmRequestBodyModel
    ): ResponseBody

    @POST(UPDATE_VERIFICATION_SERVICE)
    suspend fun updateVerificationCode(
        @Body loginRequestBodyModel: LoginRequestBodyModel,
    ): ResponseBody

    @POST(RESET_PASSWORD_SERVICE)
    suspend fun resetPassword(
        @Query("email") email: String
    ): ResponseBody

    @POST(CONTROL_RESET_CODE_SERVICE)
    suspend fun controlResetCode(
        @Query("email") email: String,
        @Query("resetCode") resetCode: String
    ): ResponseBody

    @POST(CHANGE_PASSWORD_SERVICE)
    suspend fun changePassword(
       @Body changePasswordRequestBodyModel : ChangePasswordRequestBodyModel
    ): ResponseBody

    @GET(GET_USER_INFO_SERVICE)
    suspend fun getUserInfo(
    ):ResponseBody

}