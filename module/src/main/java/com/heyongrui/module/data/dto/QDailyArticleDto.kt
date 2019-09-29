package com.heyongrui.module.data.dto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class QDailyArticleDto(
        val feeds: List<Feed>,
        val has_more: Boolean,
        val last_key: String
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Feed),
            parcel.readByte() != 0.toByte(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(feeds)
        parcel.writeByte(if (has_more) 1 else 0)
        parcel.writeString(last_key)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QDailyArticleDto> {
        private const val serialVersionUID = 5928979654148925074L

        override fun createFromParcel(parcel: Parcel): QDailyArticleDto {
            return QDailyArticleDto(parcel)
        }

        override fun newArray(size: Int): Array<QDailyArticleDto?> {
            return arrayOfNulls(size)
        }
    }
}