package com.pallaw.topnews.data.model


import com.google.gson.annotations.SerializedName
import com.pallaw.topnews.data.model.resourse.Article

/**
 * Created by Pallaw Pathak on 08/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
data class NewsResponse(
    @SerializedName("articles")
    var articles: List<Article> = listOf(),
    @SerializedName("status")
    var status: String = "",
    @SerializedName("totalResults")
    var totalResults: Int = 0
) {
}