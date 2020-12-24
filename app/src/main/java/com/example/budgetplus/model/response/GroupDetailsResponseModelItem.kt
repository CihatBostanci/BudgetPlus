package com.example.budgetplus.model.response


import com.google.gson.annotations.SerializedName

data class GroupDetailsResponseModelItem(
    @SerializedName("adminId")
    var adminId: Int,
    @SerializedName("description")
    var description: String?,
    @SerializedName("groupId")
    var groupId: Int,
    @SerializedName("name")
    var name: String?,
    @SerializedName("shareCode")
    var shareCode: String?,
    @SerializedName("transactionInfos")
    var transactionInfos: MutableList<TransactionInfo> ,
    @SerializedName("userInfos")
    var userInfos: MutableList<UserInfo>
){
    override fun toString(): String {
        return "GroupDetailsResponseModelItem(adminId=$adminId, description=$description, groupId=$groupId, name=$name, shareCode=$shareCode, transactionInfos=$transactionInfos, userInfos=$userInfos)"
    }
}