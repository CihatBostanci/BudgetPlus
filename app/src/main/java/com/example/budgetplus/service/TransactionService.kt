package com.example.budgetplus.service

import com.example.budgetplus.model.request.AddExpenseTransactionRequestBody
import com.example.budgetplus.model.request.AddTransferTransactionRequestBodyModel
import com.example.budgetplus.utils.ADD_EXPENSE
import com.example.budgetplus.utils.ADD_TRANSFER
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionService {

    @POST(ADD_EXPENSE)
    suspend fun addExpense(
        @Body addExpenseTransactionRequestBody: AddExpenseTransactionRequestBody
    ): ResponseBody

    @POST(ADD_TRANSFER)
    suspend fun addTransfer(
        @Body addTransferTransactionRequestBodyModel: AddTransferTransactionRequestBodyModel
    ): ResponseBody

}