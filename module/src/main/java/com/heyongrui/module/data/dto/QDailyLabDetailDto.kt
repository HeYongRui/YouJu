package com.heyongrui.module.data.dto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class QDailyLabDetailDto(
        var image: String,
        var post: Post,
        var questions: List<Question>,
        var share: Share,
        var type: Int
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Post::class.java.classLoader),
            parcel.createTypedArrayList(Question),
            parcel.readParcelable(Share::class.java.classLoader),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeParcelable(post, flags)
        parcel.writeTypedList(questions)
        parcel.writeParcelable(share, flags)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QDailyLabDetailDto> {
        private const val serialVersionUID = 512491979308925024L

        override fun createFromParcel(parcel: Parcel): QDailyLabDetailDto {
            return QDailyLabDetailDto(parcel)
        }

        override fun newArray(size: Int): Array<QDailyLabDetailDto?> {
            return arrayOfNulls(size)
        }
    }
}

data class Question(
        var content: String,
        var genre: Int,
        var id: Int,
        var options: List<Option>,
        var title: String
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.createTypedArrayList(Option),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(content)
        parcel.writeInt(genre)
        parcel.writeInt(id)
        parcel.writeTypedList(options)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        private const val serialVersionUID = 593691997323125024L

        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}

data class Option(
        var content: String,
        var id: Int,
        var image: String,
        var perfect: Int,
        var praise_count: Int
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(content)
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeInt(perfect)
        parcel.writeInt(praise_count)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Option> {
        private const val serialVersionUID = 593691997308925024L

        override fun createFromParcel(parcel: Parcel): Option {
            return Option(parcel)
        }

        override fun newArray(size: Int): Array<Option?> {
            return arrayOfNulls(size)
        }
    }
}