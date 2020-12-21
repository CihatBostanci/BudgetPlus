package com.example.budgetplus.repository

import com.example.budgetplus.model.request.RegisterRequestBodyModel

object GroupRepository:BaseRepository() {

    private val apiGroupService = RetrofilBuilder.apiGroupService

    suspend fun getGroupDetails() = apiGroupService.getGroupDetails()

}