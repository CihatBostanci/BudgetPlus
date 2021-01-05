package com.example.budgetplus.verificationcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetplus.model.request.ConfirmRequestBodyModel
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class VerificationCodeViewModel(
    private val verificationCodeRepository: VerificationCodeRepository
) : ViewModel() {


    private val _accountConfirmEmail = MutableLiveData<Resource<ResponseBody>>()
    val accountConfirmEmail: LiveData<Resource<ResponseBody>>
        get() = _accountConfirmEmail

    private val _updateVerificationCode = MutableLiveData<Resource<ResponseBody>>()
    val updateVerificationCode: LiveData<Resource<ResponseBody>>
        get() = _updateVerificationCode

    fun accountConfirmEmail(confirmRequestBodyModel: ConfirmRequestBodyModel) {
        _accountConfirmEmail.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _accountConfirmEmail.postValue(verificationCodeRepository
                .accountConfirmEmail(confirmRequestBodyModel))
        }
    }

    fun updateVerificationCode(loginRequestBodyModel: LoginRequestBodyModel){
        _updateVerificationCode.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _updateVerificationCode.postValue(verificationCodeRepository
                .updateVerificationCode(loginRequestBodyModel))
        }
    }

}