package com.example.budgetplus.repository

import com.example.budgetplus.model.request.AddExpenseTransactionRequestBody

object TransactionRepository : BaseRepository() {

    private val apiTransactionService = RetrofilBuilder.apiTransactionService

    suspend fun addExpense(addExpenseTransactionRequestBody: AddExpenseTransactionRequestBody)
            = apiTransactionService.addExpense(addExpenseTransactionRequestBody  )

}