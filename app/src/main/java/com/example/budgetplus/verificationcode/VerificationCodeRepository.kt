package com.example.budgetplus.verificationcode

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.model.request.ConfirmRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.service.AccountService
import com.example.budgetplus.service.EmailVerificationService

class VerificationCodeRepository(
    private val emailVerificationService: EmailVerificationService
) {

    suspend fun accountConfirmEmail(confirmRequestBodyModel: ConfirmRequestBodyModel) =
        safeApiCall {
            emailVerificationService.accountConfirmEmail(confirmRequestBodyModel)
        }


    suspend fun updateVerificationCode(loginRequestBodyModel: LoginRequestBodyModel) =
        safeApiCall {
            emailVerificationService.updateVerificationCode(loginRequestBodyModel)
        }
}