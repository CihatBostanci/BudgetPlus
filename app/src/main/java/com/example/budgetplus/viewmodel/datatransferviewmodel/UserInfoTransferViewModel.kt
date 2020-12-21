package com.example.budgetplus.viewmodel.datatransferviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.viewmodel.BaseViewModel

class UserInfoTransferViewModel : BaseViewModel() {

    private val  userInfoResponseModel = MutableLiveData<UserInfoResponseModel>()

    fun setUserInfo(item: UserInfoResponseModel) {
        userInfoResponseModel.value = item
    }

    val _userInfoResponseModel: LiveData<UserInfoResponseModel>
        get() = userInfoResponseModel

}