package com.example.budgetplus.splash

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.service.AccountService
import com.example.budgetplus.service.GroupService
import com.example.budgetplus.service.UserService

class SplashRepository(
    private val groupService: GroupService,
    private val userService: UserService
 ) {

    suspend fun getUserInfos() = safeApiCall{
        userService.getUserInfo()
    }

    suspend fun getGroupDetails() = safeApiCall {
        groupService.getGroupDetails()
    }


}
