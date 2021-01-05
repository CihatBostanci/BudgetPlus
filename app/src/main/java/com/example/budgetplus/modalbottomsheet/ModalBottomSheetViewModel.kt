package com.example.budgetplus.modalbottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetplus.model.request.AddExpenseTransactionRequestBody
import com.example.budgetplus.model.request.AddTransferTransactionRequestBodyModel
import com.example.budgetplus.model.request.GroupAddRequestBodyModel
import com.example.budgetplus.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class ModalBottomSheetViewModel(private val modalBottomSheetRepository: ModalBottomSheetRepository) :
    ViewModel() {

    private val _groupAdd = MutableLiveData<Resource<ResponseBody>>()
    val groupAdd: LiveData<Resource<ResponseBody>>
        get() = _groupAdd

    private val _transactionAdd = MutableLiveData<Resource<ResponseBody>>()
    val transactionAdd: LiveData<Resource<ResponseBody>>
        get() = _transactionAdd

    fun addExpense(
        addExpenseTransactionRequestBody: AddExpenseTransactionRequestBody
    ) {
        _transactionAdd.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _transactionAdd.postValue(
                modalBottomSheetRepository.addExpense(
                    addExpenseTransactionRequestBody
                )
            )
        }
    }

    fun addGroup(
        addRequestBodyModel: GroupAddRequestBodyModel
    ) {
        _groupAdd.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _groupAdd.postValue(modalBottomSheetRepository.addGroup(addRequestBodyModel))
        }
    }

}