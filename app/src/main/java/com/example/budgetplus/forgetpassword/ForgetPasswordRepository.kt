package com.example.budgetplus.forgetpassword

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.model.request.ChangePasswordRequestBodyModel
import com.example.budgetplus.service.PasswordService

class ForgetPasswordRepository(
    private val passwordService: PasswordService
) {

    suspend fun resetPassword(email: String) = safeApiCall {
        passwordService.resetPassword(email)
    }

    suspend fun controlResetCode(email: String, resetCode: String) = safeApiCall {
        passwordService.controlResetCode(email, resetCode)
    }

    suspend fun changePassword(
        changePasswordRequestBodyModel: ChangePasswordRequestBodyModel
    ) = safeApiCall {
        passwordService.changePassword(changePasswordRequestBodyModel)
    }


}
