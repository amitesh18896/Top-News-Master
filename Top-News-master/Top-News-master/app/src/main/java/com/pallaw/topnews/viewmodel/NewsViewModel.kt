package com.pallaw.topnews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.pallaw.topnews.data.model.resourse.Article
import com.pallaw.topnews.data.repository.ArticlePagedListRepository
import com.pallaw.topnews.util.NetworkState
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Pallaw Pathak on 10/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
class NewsViewModel(
    application: Application,
    newsRepository: ArticlePagedListRepository
) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val searchTag = MutableLiveData<String>()

    val newsPagedList: LiveData<PagedList<Article>> by lazy {
        newsRepository.fetchLiveSearchArticlePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState>? by lazy {
        newsRepository.getNetworkState()
    }

    fun isSearchListEmpty(): Boolean {
        return newsPagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}