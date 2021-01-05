package com.example.budgetplus.forgetpassword

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.model.request.ChangePasswordRequestBodyModel
import com.example.budgetplus.service.AccountService

class ForgetPasswordRepository(
    private val accountService: AccountService
) {

    suspend fun resetPassword(email: String) = safeApiCall {
        accountService.resetPassword(email)
    }

    suspend fun controlResetCode(email: String, resetCode: String) = safeApiCall {
        accountService.controlResetCode(email, resetCode)
    }

    suspend fun changePassword(
        changePasswordRequestBodyModel: ChangePasswordRequestBodyModel
    ) = safeApiCall {
        accountService.changePassword(changePasswordRequestBodyModel)
    }


}
