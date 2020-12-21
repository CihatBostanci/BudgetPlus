package com.example.budgetplus.repository

import com.example.budgetplus.model.request.ChangePasswordRequestBodyModel
import com.example.budgetplus.model.request.ConfirmRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.model.request.RegisterRequestBodyModel
import retrofit2.http.Body

object AccountRepository : BaseRepository() {

    private val apiAccountService = RetrofilBuilder.apiAccountService

    suspend fun registerAccount(
        registerRequestBodyModel: RegisterRequestBodyModel
    ) = apiAccountService.accountRegister(registerRequestBodyModel)

    suspend fun loginAccount(
        loginRequestBodyModel: LoginRequestBodyModel,
        token: String
    ) = apiAccountService.accountLogin(loginRequestBodyModel)

    suspend fun authenticateAccount(loginRequestBodyModel: LoginRequestBodyModel) =
        apiAccountService.accountAuthenticate(loginRequestBodyModel)

    suspend fun accountConfirmEmail(confirmRequestBodyModel: ConfirmRequestBodyModel) =
        apiAccountService.accountConfirmEmail(confirmRequestBodyModel)

    suspend fun updateVerificationCode(loginRequestBodyModel: LoginRequestBodyModel) =
        apiAccountService.updateVerificationCode(loginRequestBodyModel)

    suspend fun resetPassword(email: String) = apiAccountService.resetPassword(email)

    suspend fun controlResetCode(email: String, resetCode: String) =
        apiAccountService.controlResetCode(email, resetCode)

    suspend fun changePassword(
        changePasswordRequestBodyModel: ChangePasswordRequestBodyModel
    ) = apiAccountService.changePassword(changePasswordRequestBodyModel)

    suspend fun getUserInfo() = apiAccountService.getUserInfo()
}