package com.example.budgetplus.modalbottomsheet

import com.example.budgetplus.di.safeApiCall
import com.example.budgetplus.model.request.AddExpenseTransactionRequestBody
import com.example.budgetplus.model.request.GroupAddRequestBodyModel
import com.example.budgetplus.service.GroupService
import com.example.budgetplus.service.TransactionService

class ModalBottomSheetRepository(
    private val groupService: GroupService,
    private val transactionService: TransactionService
) {

    suspend fun addGroup(addRequestBodyModel: GroupAddRequestBodyModel) = safeApiCall {
        groupService.addGroup(addRequestBodyModel)
    }

    suspend fun addExpense(
        addExpenseTransactionRequestBody: AddExpenseTransactionRequestBody
    ) = safeApiCall {
        transactionService.addExpense(addExpenseTransactionRequestBody)
    }
}