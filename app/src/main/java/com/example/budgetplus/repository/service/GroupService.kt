package com.example.budgetplus.repository.service

import com.example.budgetplus.utils.GETGROUPDETAILSSERVICE
import okhttp3.ResponseBody
import retrofit2.http.GET

interface GroupService {

    @GET(GETGROUPDETAILSSERVICE)
    suspend fun getGroupDetails(): ResponseBody


}