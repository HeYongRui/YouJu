package com.heyongrui.module2.data.dto

import android.os.Parcel
import android.os.Parcelable

data class HistoryTodayDto(
        var msg: String,//success
        var retCode: String,//200
        var result: List<HistoryTodayBean>) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(HistoryTodayBean)) {
    }

    data class HistoryTodayBean(
            var id: String,//569881b6590146d407332c49
            var title: String,
            var date: String,//20190918
            var month: Int,//9
            var day: Int,//18
            var event: String) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(id)
            parcel.writeString(title)
            parcel.writeString(date)
            parcel.writeInt(month)
            parcel.writeInt(day)
            parcel.writeString(event)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<HistoryTodayBean> {
            override fun createFromParcel(parcel: Parcel): HistoryTodayBean {
                return HistoryTodayBean(parcel)
            }

            override fun newArray(size: Int): Array<HistoryTodayBean?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(msg)
        parcel.writeString(retCode)
        parcel.writeTypedList(result)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HistoryTodayDto> {
        override fun createFromParcel(parcel: Parcel): HistoryTodayDto {
            return HistoryTodayDto(parcel)
        }

        override fun newArray(size: Int): Array<HistoryTodayDto?> {
            return arrayOfNulls(size)
        }
    }
}