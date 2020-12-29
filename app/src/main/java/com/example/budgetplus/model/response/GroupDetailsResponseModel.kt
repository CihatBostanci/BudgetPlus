package com.example.budgetplus.model.response

import android.os.Parcel
import android.os.Parcelable


class GroupDetailsResponseModel() : ArrayList<GroupDetailsResponseModelItem>(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupDetailsResponseModel> {
        override fun createFromParcel(parcel: Parcel): GroupDetailsResponseModel {
            return GroupDetailsResponseModel(parcel)
        }

        override fun newArray(size: Int): Array<GroupDetailsResponseModel?> {
            return arrayOfNulls(size)
        }
    }
}