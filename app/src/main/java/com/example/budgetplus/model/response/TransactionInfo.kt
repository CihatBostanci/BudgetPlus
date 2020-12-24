package com.example.budgetplus.model.response


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class TransactionInfo(
    @SerializedName("adderId")
    var adderId: Int,
    @SerializedName("adderName")
    var adderName: String?,
    @SerializedName("adderSurname")
    var adderSurname: String?,
    @SerializedName("amount")
    var amount: Int,
    @SerializedName("createTime")
    var createTime: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("relatedUsers")
    var relatedUsers: MutableList<RelatedUser>,
    @SerializedName("transactionId")
    var transactionId: Int,
    @SerializedName("type")
    var type: String?
)