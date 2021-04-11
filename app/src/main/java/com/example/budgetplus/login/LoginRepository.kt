package com.example.budgetplus.login

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.service.AccountService
import com.example.budgetplus.service.GroupService
import com.example.budgetplus.service.UserService

class LoginRepository(
    private val accountService: AccountService,
    private val groupService: GroupService,
    private val userService: UserService
) {

    suspend fun getUserInfos() = safeApiCall{
        userService.getUserInfo()
    }

    suspend fun getGroupDetails() = safeApiCall {
        groupService.getGroupDetails()
    }

    suspend fun getAuthenticate(loginRequestBodyModel: LoginRequestBodyModel)= safeApiCall {
        accountService.accountAuthenticate(loginRequestBodyModel)
    }
}