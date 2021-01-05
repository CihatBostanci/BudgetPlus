package com.example.budgetplus.service

import com.example.budgetplus.model.request.GroupAddRequestBodyModel
import com.example.budgetplus.utils.ADD_GROUP_SERVICE
import com.example.budgetplus.utils.GET_GROUP_DETAILS_SERVICE
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GroupService {

    @GET(GET_GROUP_DETAILS_SERVICE)
    suspend fun getGroupDetails(): ResponseBody

    @POST(ADD_GROUP_SERVICE)
    suspend fun addGroup(@Body groupAddRequestBodyModel: GroupAddRequestBodyModel): ResponseBody
}