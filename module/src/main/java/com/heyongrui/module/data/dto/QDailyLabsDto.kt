package com.heyongrui.module.data.dto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class QDailyLabsDto(
        var feeds: List<Feed>,
//    var feeds_ad: List<Any>,
        var has_more: Boolean,
        var last_key: String,
        var paper_topics: List<PaperTopic>
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Feed),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.createTypedArrayList(PaperTopic)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(feeds)
        parcel.writeByte(if (has_more) 1 else 0)
        parcel.writeString(last_key)
        parcel.writeTypedList(paper_topics)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QDailyLabsDto> {
        private const val serialVersionUID = 592852997308925024L

        override fun createFromParcel(parcel: Parcel): QDailyLabsDto {
            return QDailyLabsDto(parcel)
        }

        override fun newArray(size: Int): Array<QDailyLabsDto?> {
            return arrayOfNulls(size)
        }
    }
}

data class PaperTopic(
        var id: Int,
        var insert_content: List<InsertContent>,
        var insert_location: Int
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(InsertContent),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeTypedList(insert_content)
        parcel.writeInt(insert_location)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PaperTopic> {
        private const val serialVersionUID = 472852979308925024L

        override fun createFromParcel(parcel: Parcel): PaperTopic {
            return PaperTopic(parcel)
        }

        override fun newArray(size: Int): Array<PaperTopic?> {
            return arrayOfNulls(size)
        }
    }
}

data class InsertContent(
        var description: String,
        var icon: String,
        var id: Int,
        var image: String,
        var title: String
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(icon)
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InsertContent> {
        private const val serialVersionUID = 593352979308925024L

        override fun createFromParcel(parcel: Parcel): InsertContent {
            return InsertContent(parcel)
        }

        override fun newArray(size: Int): Array<InsertContent?> {
            return arrayOfNulls(size)
        }
    }
}