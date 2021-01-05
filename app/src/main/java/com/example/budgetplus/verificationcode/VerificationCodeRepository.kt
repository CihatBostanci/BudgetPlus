package com.example.budgetplus.verificationcode

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.model.request.ConfirmRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.service.AccountService

class VerificationCodeRepository(
    private val accountService: AccountService
) {

    suspend fun accountConfirmEmail(confirmRequestBodyModel: ConfirmRequestBodyModel) =
        safeApiCall {
            accountService.accountConfirmEmail(confirmRequestBodyModel)
        }


    suspend fun updateVerificationCode(loginRequestBodyModel: LoginRequestBodyModel) =
        safeApiCall {
            accountService.updateVerificationCode(loginRequestBodyModel)
        }
}