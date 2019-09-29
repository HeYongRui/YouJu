package com.heyongrui.module.data.dto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class QDailyNewsDto(
        var banners: List<Banner>,
//        var banners_ad: List<Any>,
        var columns: List<Column>,
//        var columns_ad: List<Any>,
//        var featured_article: List<Any>,
        var feeds: List<Feed>,
//        var feeds_ad: List<Any>,
        var has_more: Boolean,
        var last_key: String
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Banner),
            parcel.createTypedArrayList(Column),
            parcel.createTypedArrayList(Feed),
            parcel.readByte() != 0.toByte(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(banners)
        parcel.writeTypedList(columns)
        parcel.writeTypedList(feeds)
        parcel.writeByte(if (has_more) 1 else 0)
        parcel.writeString(last_key)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QDailyNewsDto> {
        private const val serialVersionUID = 592852979308925024L

        override fun createFromParcel(parcel: Parcel): QDailyNewsDto {
            return QDailyNewsDto(parcel)
        }

        override fun newArray(size: Int): Array<QDailyNewsDto?> {
            return arrayOfNulls(size)
        }
    }
}

data class Banner(
        var image: String,
        var post: Post,
        var type: Int
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Post::class.java.classLoader),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeParcelable(post, flags)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Banner> {
        private const val serialVersionUID = 5923979793048925024L

        override fun createFromParcel(parcel: Parcel): Banner {
            return Banner(parcel)
        }

        override fun newArray(size: Int): Array<Banner?> {
            return arrayOfNulls(size)
        }
    }
}

data class Feed(
        var image: String,
        var post: Post,
        var type: Int//0-上图下文 1-左文右图
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Post::class.java.classLoader),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeParcelable(post, flags)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Feed> {
        private const val serialVersionUID = 5128979793048925024L

        override fun createFromParcel(parcel: Parcel): Feed {
            return Feed(parcel)
        }

        override fun newArray(size: Int): Array<Feed?> {
            return arrayOfNulls(size)
        }
    }
}

data class Post(
        var appview: String,
        var column: Column,
        var category: Category,
        var comment_count: Int,
        var comment_status: Boolean,
        var datatype: String,//paper article
        var description: String,
        var film_length: String,
        var genre: Int,
        var id: Int,
        var image: String,
        var page_style: Int,
        var post_id: Int,
        var praise_count: Int,
        var publish_time: Int,
        var start_time: Int,
        var record_count: Int,
        var super_tag: String,
        var title: String
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(Column::class.java.classLoader),
            parcel.readParcelable(Category::class.java.classLoader),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(appview)
        parcel.writeParcelable(column, flags)
        parcel.writeParcelable(category, flags)
        parcel.writeInt(comment_count)
        parcel.writeByte(if (comment_status) 1 else 0)
        parcel.writeString(datatype)
        parcel.writeString(description)
        parcel.writeString(film_length)
        parcel.writeInt(genre)
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeInt(page_style)
        parcel.writeInt(post_id)
        parcel.writeInt(praise_count)
        parcel.writeInt(publish_time)
        parcel.writeInt(start_time)
        parcel.writeInt(record_count)
        parcel.writeString(super_tag)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        private const val serialVersionUID = 5928979793648925024L

        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}

data class Column(
        var column_tag: String,
        var content_provider: String,
        var description: String,
        var genre: Int,
        var icon: String,
        var id: Int,
        var image: String,
        var image_large: String,
        var location: Int,
        var name: String,
        var post_count: Int,
        var share: Share,
        var show_type: Int,
        var sort_time: String,
        var subscribe_status: Boolean,
        var subscriber_num: Int
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readParcelable(Share::class.java.classLoader),
            parcel.readInt(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(column_tag)
        parcel.writeString(content_provider)
        parcel.writeString(description)
        parcel.writeInt(genre)
        parcel.writeString(icon)
        parcel.writeInt(id)
        parcel.writeString(image)
        parcel.writeString(image_large)
        parcel.writeInt(location)
        parcel.writeString(name)
        parcel.writeInt(post_count)
        parcel.writeParcelable(share, flags)
        parcel.writeInt(show_type)
        parcel.writeString(sort_time)
        parcel.writeByte(if (subscribe_status) 1 else 0)
        parcel.writeInt(subscriber_num)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Column> {
        private const val serialVersionUID = 5928979793048925074L

        override fun createFromParcel(parcel: Parcel): Column {
            return Column(parcel)
        }

        override fun newArray(size: Int): Array<Column?> {
            return arrayOfNulls(size)
        }
    }
}

data class Category(
        var id: Int,
        var image_experiment: String,
        var image_lab: String,
        var normal: String,
        var normal_hl: String,
        var title: String
) : Parcelable, Serializable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(image_experiment)
        parcel.writeString(image_lab)
        parcel.writeString(normal)
        parcel.writeString(normal_hl)
        parcel.writeString(title)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        private const val serialVersionUID = 5928979793048925024L

        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}

data class Share(
        var image: String,
        var text: String,
        var title: String,
        var url: String
) : Parcelable, Serializable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(text)
        parcel.writeString(title)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Share> {
        private const val serialVersionUID = 5928979793048925064L

        override fun createFromParcel(parcel: Parcel): Share {
            return Share(parcel)
        }

        override fun newArray(size: Int): Array<Share?> {
            return arrayOfNulls(size)
        }
    }
}