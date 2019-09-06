package com.heyongrui.module.data.dto

import android.os.Parcel
import android.os.Parcelable

data class ZhiHuDailyNewsDto(
        var date: String,//20190904
        var stories: List<StoryBean>,
        var top_stories: List<StoryBean>
) : Parcelable {
    data class StoryBean(
            var ga_prefix: String,
            var id: Int,
            var images: List<String>,//在stories中返回
            var image: String,//在top_stories中返回
            var title: String,
            var type: Int
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readInt(),
                parcel.createStringArrayList(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(ga_prefix)
            parcel.writeInt(id)
            parcel.writeStringList(images)
            parcel.writeString(image)
            parcel.writeString(title)
            parcel.writeInt(type)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<StoryBean> {
            override fun createFromParcel(parcel: Parcel): StoryBean {
                return StoryBean(parcel)
            }

            override fun newArray(size: Int): Array<StoryBean?> {
                return arrayOfNulls(size)
            }
        }
    }


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.createTypedArrayList(StoryBean),
            parcel.createTypedArrayList(StoryBean)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeTypedList(stories)
        parcel.writeTypedList(top_stories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ZhiHuDailyNewsDto> {
        override fun createFromParcel(parcel: Parcel): ZhiHuDailyNewsDto {
            return ZhiHuDailyNewsDto(parcel)
        }

        override fun newArray(size: Int): Array<ZhiHuDailyNewsDto?> {
            return arrayOfNulls(size)
        }
    }
}