package com.heyongrui.module2.data.dto

import android.os.Parcel
import android.os.Parcelable

data class LeisureReadCategoryDto(var _id: String? = "",
                                  var en_name: String? = "",
                                  var name: String? = "",
                                  var rank: Int? = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(en_name)
        parcel.writeString(name)
        parcel.writeValue(rank)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LeisureReadCategoryDto> {
        override fun createFromParcel(parcel: Parcel): LeisureReadCategoryDto {
            return LeisureReadCategoryDto(parcel)
        }

        override fun newArray(size: Int): Array<LeisureReadCategoryDto?> {
            return arrayOfNulls(size)
        }
    }
}