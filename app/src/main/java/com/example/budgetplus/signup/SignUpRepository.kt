package com.example.budgetplus.signup

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.model.request.RegisterRequestBodyModel
import com.example.budgetplus.service.AccountService

class SignUpRepository(
    private val accountService: AccountService
) {

    suspend fun accountRegister(registerRequestBodyModel: RegisterRequestBodyModel) = safeApiCall{
            accountService.accountRegister(registerRequestBodyModel)
    }

    suspend fun authenticateAccount(loginRequestBodyModel: LoginRequestBodyModel) = safeApiCall {
            accountService.accountAuthenticate(loginRequestBodyModel)
    }

}