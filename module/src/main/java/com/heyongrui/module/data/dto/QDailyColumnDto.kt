package com.heyongrui.module.data.dto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class QDailyColumnDto(
        var columns: List<Column>,
//    var columns_ad: List<Any>,
        var has_more: Boolean,
        var last_key: String
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Column),
            parcel.readByte() != 0.toByte(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(columns)
        parcel.writeByte(if (has_more) 1 else 0)
        parcel.writeString(last_key)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QDailyColumnDto> {
        private const val serialVersionUID = 592852979308925424L

        override fun createFromParcel(parcel: Parcel): QDailyColumnDto {
            return QDailyColumnDto(parcel)
        }

        override fun newArray(size: Int): Array<QDailyColumnDto?> {
            return arrayOfNulls(size)
        }
    }
}