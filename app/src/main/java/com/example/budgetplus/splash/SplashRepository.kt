package com.example.budgetplus.splash

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.service.AccountService
import com.example.budgetplus.service.GroupService

class SplashRepository(
    private val accountService: AccountService,
    private val groupService: GroupService
 ) {

    suspend fun getUserInfos() = safeApiCall{
        accountService.getUserInfo()
    }

    suspend fun getGroupDetails() = safeApiCall {
        groupService.getGroupDetails()
    }


}
