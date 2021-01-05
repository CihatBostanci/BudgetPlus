package com.example.budgetplus.login

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.service.AccountService
import com.example.budgetplus.service.GroupService

class LoginRepository(
    private val accountService: AccountService,
    private val groupService: GroupService
) {

    suspend fun getUserInfos() = safeApiCall{
        accountService.getUserInfo()
    }

    suspend fun getGroupDetails() = safeApiCall {
        groupService.getGroupDetails()
    }

    suspend fun getAuthenticate(loginRequestBodyModel: LoginRequestBodyModel)= safeApiCall {
        accountService.accountAuthenticate(loginRequestBodyModel)
    }
}