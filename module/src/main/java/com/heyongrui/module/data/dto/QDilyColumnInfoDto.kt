package com.heyongrui.module.data.dto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class QDilyColumnInfoDto(
        val authors: List<Author>,
        val column: Column
//    val subscribers: List<Any>
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Author),
            parcel.readParcelable(Column::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(authors)
        parcel.writeParcelable(column, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QDilyColumnInfoDto> {
        private const val serialVersionUID = 4728979093648925024L

        override fun createFromParcel(parcel: Parcel): QDilyColumnInfoDto {
            return QDilyColumnInfoDto(parcel)
        }

        override fun newArray(size: Int): Array<QDilyColumnInfoDto?> {
            return arrayOfNulls(size)
        }
    }
}

data class Author(
        val avatar: String,
        val background_image: String,
        val description: String,
        val id: Int,
        val name: String
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(avatar)
        parcel.writeString(background_image)
        parcel.writeString(description)
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Author> {
        private const val serialVersionUID = 4838979793648925024L

        override fun createFromParcel(parcel: Parcel): Author {
            return Author(parcel)
        }

        override fun newArray(size: Int): Array<Author?> {
            return arrayOfNulls(size)
        }
    }
}