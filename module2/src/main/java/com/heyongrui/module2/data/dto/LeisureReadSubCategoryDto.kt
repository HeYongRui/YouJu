package com.heyongrui.module2.data.dto

import android.os.Parcel
import android.os.Parcelable

data class LeisureReadSubCategoryDto(var _id: String? = "",
                                     var created_at: String? = "",
                                     var icon: String? = "",
                                     var id: String? = "",
                                     var title: String? = "") : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(created_at)
        parcel.writeString(icon)
        parcel.writeString(id)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LeisureReadSubCategoryDto> {
        override fun createFromParcel(parcel: Parcel): LeisureReadSubCategoryDto {
            return LeisureReadSubCategoryDto(parcel)
        }

        override fun newArray(size: Int): Array<LeisureReadSubCategoryDto?> {
            return arrayOfNulls(size)
        }
    }
}