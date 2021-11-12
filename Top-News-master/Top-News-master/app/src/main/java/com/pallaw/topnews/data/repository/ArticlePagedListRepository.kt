package com.pallaw.topnews.data.repository

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.pallaw.topnews.data.model.resourse.Article
import com.pallaw.topnews.data.remote.Api
import com.pallaw.topnews.data.remote.ApiClient.ITEM_PER_PAGE
import com.pallaw.topnews.util.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Pallaw Pathak on 21/04/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
class ArticlePagedListRepository(private val apiService: Api) {

    lateinit var newsPagedList: LiveData<PagedList<Article>>
    lateinit var articleDataSourceFactory: ArticleDataSourceFactory

    fun fetchLiveSearchArticlePagedList(
        compositeDisposable: CompositeDisposable
    ): LiveData<PagedList<Article>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(ITEM_PER_PAGE)
            .build()

        articleDataSourceFactory = ArticleDataSourceFactory(
            apiService,
            compositeDisposable
        )

        newsPagedList = LivePagedListBuilder(articleDataSourceFactory, config).build()
        return newsPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap(newsPagedList, Function {
            articleDataSourceFactory.newsLiveDataSource.value?.networkState
        })
    }
}