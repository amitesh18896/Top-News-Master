package com.pallaw.topnews.data.remote

import com.pallaw.topnews.data.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Pallaw Pathak on 08/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
interface Api {

    @GET("top-headlines")
    fun fetchNews(@Query("page") page: Int): Single<NewsResponse>
}