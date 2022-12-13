package com.example.sprint_marvel_serasa.model

import androidx.room.*
import com.example.sprint_marvel_serasa.utils.ErrorString
import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("data")
    val data: Data
)

data class Data(
    @SerializedName("results")
    val results: List<Results>
)

@Entity
data class BookmarkResults(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bookmark_id")
    val id_favorites: Long = 0,
    @ColumnInfo(name = "bookmark")
    val bookmark: Int
)

@Entity
data class Results(
    @PrimaryKey
    @ColumnInfo(name = "char_id")
    @SerializedName("id")
    val char_id: Long,
    @ColumnInfo(name = "char_name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo(name = "char_description")
    @SerializedName("description")
    val description: String,
    @ColumnInfo(name = "char_modified")
    @SerializedName("modified")
    val modified: String,
    @Embedded
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,

    var comicSplitId: List<ComicSplit>?,

    @Ignore
    @SerializedName("comics")
    val comics: Comics?,
) {
    constructor(
        char_id: Long,
        name: String,
        description: String,
        modified: String,
        thumbnail: Thumbnail,
        comicSplitId: List<ComicSplit>?
    ) : this(char_id, name, description, modified, thumbnail, comicSplitId, null)

}

@Entity
data class ComicSplit(
    @PrimaryKey
    @ColumnInfo(name = "id_comic_split")
    val idComicSplit: String
)

data class Thumbnail(
    @ColumnInfo(name = "thumb_path")
    @SerializedName("path")
    val path: String,
    @ColumnInfo(name = "thumb_extension")
    @SerializedName("extension")
    val extension: String
) {
    fun mergeImage(): String {
        if ("$path.$extension" == ErrorString.ERROR_URL_JPG.url || "$path.$extension" == ErrorString.ERROR_URL_GIF.url) {
            return ErrorString.ERROR_CARD_IMAGE.url
        }
        return "$path.$extension"
    }
}

data class Comics(
    @SerializedName("items")
    val items: List<Items>?
)

data class Items(
    @SerializedName("resourceURI")
    val resourceURI: String
)