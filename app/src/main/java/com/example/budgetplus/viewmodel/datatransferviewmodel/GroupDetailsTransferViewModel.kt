package com.example.budgetplus.viewmodel.datatransferviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.budgetplus.model.response.GroupDetailsResponseModel
import com.example.budgetplus.model.response.UserInfoResponseModel
import com.example.budgetplus.viewmodel.BaseViewModel

class GroupDetailsTransferViewModel:BaseViewModel() {
    private val  groupDetailsResponseModel = MutableLiveData<GroupDetailsResponseModel>()

    fun setGroupDetails(item: GroupDetailsResponseModel) {
        groupDetailsResponseModel.value = item
    }

    val _groupDetailsResponseModel: LiveData<GroupDetailsResponseModel>
        get() = groupDetailsResponseModel

}