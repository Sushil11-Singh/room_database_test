package com.example.testdemo.responce

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "news")
class NewsListResponse : Serializable{

    @PrimaryKey(autoGenerate = false)
    private var id = 0

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    @SerializedName("status")
    @Expose

    lateinit var status: String

    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null

    @SerializedName("articles")
    @Expose
    var articles: List<Article>? = null

    class Article:Serializable {
        @SerializedName("source")
        @Expose
        var source: Source? = null

        @SerializedName("author")
        @Expose
        var author: String? = null

        @SerializedName("title")
        @Expose
        var title: String? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("urlToImage")
        @Expose
        var urlToImage: String? = null

        @SerializedName("publishedAt")
        @Expose
        var publishedAt: String? = null

        @SerializedName("content")
        @Expose
        var content: String? = null

    }
    class Source:Serializable {

        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

    }
}