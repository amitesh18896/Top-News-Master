package com.pallaw.topnews.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.pallaw.topnews.data.model.resourse.Article
import com.pallaw.topnews.data.remote.Api
import com.pallaw.topnews.data.remote.ApiClient
import com.pallaw.topnews.data.remote.ApiClient.FIRST_PAGE
import com.pallaw.topnews.util.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Pallaw Pathak on 10/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
const val DEFAULT_SEARCH_TAG = "android"

class ArticleDataSource(
    private val apiService: Api,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Article>() {

    private var page = FIRST_PAGE
    private var TAG = ArticleDataSource::class.java.simpleName

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {

        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(fetchNews(page, callback))
    }

    private fun fetchNews(
        page: Int,
        callback: LoadInitialCallback<Int, Article>
    ): Disposable {
        return apiService.fetchNews(page)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    callback.onResult(it.articles, null, page + 1)
                    networkState.postValue(NetworkState.LOADED)
                },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e(TAG, it.message)
                }
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(fetchMoreNews(params.key, callback))
    }

    private fun fetchMoreNews(
        page: Int,
        callback: LoadCallback<Int, Article>
    ): Disposable {
        return apiService.fetchNews(page)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    if ((it.totalResults / ApiClient.ITEM_PER_PAGE) >= page) {
                        callback.onResult(it.articles, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    } else {
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }
                },
                {
                    networkState.postValue(NetworkState.ERROR)
                    Log.e(TAG, it.message)
                }
            )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {

    }
}