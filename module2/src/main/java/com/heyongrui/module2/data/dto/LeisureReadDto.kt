package com.heyongrui.module2.data.dto

import android.os.Parcel
import android.os.Parcelable

data class LeisureReadDto(
        var _id: String? = "",
        var content: String? = "",
        var cover: String? = "",
        var crawled: Long,
        var created_at: String? = "",
        var deleted: Boolean,
        var published_at: String? = "",
        var raw: String? = "",
        var site: Site? = null,
        var title: String? = "",
        var uid: String? = "",
        var url: String? = "") : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Site::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(content)
        parcel.writeString(cover)
        parcel.writeLong(crawled)
        parcel.writeString(created_at)
        parcel.writeByte(if (deleted) 1 else 0)
        parcel.writeString(published_at)
        parcel.writeString(raw)
        parcel.writeParcelable(site, flags)
        parcel.writeString(title)
        parcel.writeString(uid)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LeisureReadDto> {
        override fun createFromParcel(parcel: Parcel): LeisureReadDto {
            return LeisureReadDto(parcel)
        }

        override fun newArray(size: Int): Array<LeisureReadDto?> {
            return arrayOfNulls(size)
        }
    }

     data class Site(
            var cat_cn: String? = "",
            var cat_en: String? = "",
            var desc: String? = "",
            var feed_id: String? = "",
            var icon: String? = "",
            var id: String? = "",
            var name: String? = "",
            var subscribers: Int,
            var type: String? = "",
            var url: String? = "") : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(cat_cn)
            parcel.writeString(cat_en)
            parcel.writeString(desc)
            parcel.writeString(feed_id)
            parcel.writeString(icon)
            parcel.writeString(id)
            parcel.writeString(name)
            parcel.writeInt(subscribers)
            parcel.writeString(type)
            parcel.writeString(url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Site> {
            override fun createFromParcel(parcel: Parcel): Site {
                return Site(parcel)
            }

            override fun newArray(size: Int): Array<Site?> {
                return arrayOfNulls(size)
            }
        }
    }
}