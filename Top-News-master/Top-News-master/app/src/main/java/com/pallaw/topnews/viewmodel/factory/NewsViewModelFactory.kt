package com.pallaw.firebasegallery.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pallaw.topnews.data.repository.ArticlePagedListRepository

/**
 * Created by Pallaw Pathak on 10/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
class NewsViewModelFactory(
    private val application: Application,
    private val newsRepository: ArticlePagedListRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            Application::class.java,
            ArticlePagedListRepository::class.java
        ).newInstance(application, newsRepository)
    }
}