package com.example.budgetplus.service

import com.example.budgetplus.model.request.ChangePasswordRequestBodyModel
import com.example.budgetplus.utils.CHANGE_PASSWORD_SERVICE
import com.example.budgetplus.utils.CONTROL_RESET_CODE_SERVICE
import com.example.budgetplus.utils.RESET_PASSWORD_SERVICE
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface PasswordService {



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



}