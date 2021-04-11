package com.example.budgetplus.service

import com.example.budgetplus.model.request.ConfirmRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.utils.CONFIRM_EMAIL_SERVICE
import com.example.budgetplus.utils.UPDATE_VERIFICATION_SERVICE
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailVerificationService {

    @POST(CONFIRM_EMAIL_SERVICE)
    suspend fun accountConfirmEmail(
            @Body confirmRequestBodyModel: ConfirmRequestBodyModel
    ): ResponseBody

    @POST(UPDATE_VERIFICATION_SERVICE)
    suspend fun updateVerificationCode(
            @Body loginRequestBodyModel: LoginRequestBodyModel,
    ): ResponseBody


}