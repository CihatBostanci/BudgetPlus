package com.example.budgetplus.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetplus.model.request.LoginRequestBodyModel
import com.example.budgetplus.model.request.RegisterRequestBodyModel
import com.example.budgetplus.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class SignUpViewModel(private val signUpRepository: SignUpRepository) : ViewModel() {

    private val _registerAccount = MutableLiveData<Resource<ResponseBody>>()
    val registerAccount: LiveData<Resource<ResponseBody>>
        get() = _registerAccount

    private val _authenticate = MutableLiveData<Resource<ResponseBody>>()
    val authenticate: LiveData<Resource<ResponseBody>>
        get() = _authenticate

    fun registerAccount(registerRequestBodyModel: RegisterRequestBodyModel) {
        _registerAccount.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _registerAccount.postValue(signUpRepository.accountRegister(registerRequestBodyModel))
        }
    }

    fun authenticateAccount(loginRequestBodyModel: LoginRequestBodyModel) {
        _authenticate.postValue(Resource.loading(data = null))
        viewModelScope.launch(Dispatchers.IO) {
            _authenticate.postValue(signUpRepository.authenticateAccount(loginRequestBodyModel))
        }
    }

}