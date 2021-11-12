package com.pallaw.topnews.ui.news.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pallaw.firebasegallery.viewmodel.factory.NewsViewModelFactory
import com.pallaw.topnews.R
import com.pallaw.topnews.data.model.resourse.Article
import com.pallaw.topnews.data.remote.Api
import com.pallaw.topnews.data.remote.ApiClient
import com.pallaw.topnews.data.repository.ArticlePagedListRepository
import com.pallaw.topnews.util.NetworkState
import com.pallaw.topnews.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news_list.*

/**
 * Created by Pallaw Pathak on 08/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
class NewsListFragment : Fragment(),
    NewsPagedListAdapter.OnNewsItemClickListener {


    private lateinit var newsAdapter: NewsPagedListAdapter
    private lateinit var viewModel: NewsViewModel
    val photoRepository: ArticlePagedListRepository by lazy {
        ArticlePagedListRepository(apiService)
    }

    val apiService: Api by lazy { ApiClient.getClient() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //init viewmodel
        initViewModel()

        //setup news list
        setupPhotoList()
    }

    private fun setupPhotoList() {

        newsAdapter = NewsPagedListAdapter(this)
        with(news_list) {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }

        viewModel.newsPagedList.observe(viewLifecycleOwner, Observer {
            newsAdapter.submitList(it)
        })

        viewModel.networkState?.observe(viewLifecycleOwner, Observer {
            progress_bar.visibility =
                if (viewModel.isSearchListEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (viewModel.isSearchListEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.isSearchListEmpty()) {
                newsAdapter.setNetworkState(it)
            }
        })


    }

    override fun onNewsItemClicked(
        article: Article?
    ) {
        article?.let {

            findNavController().navigate(
                NewsListFragmentDirections.actionNewslistFragmentToNewsDetailFragment(
                    it
                )
            )
        }
    }

    private fun initViewModel() {
        activity?.let {
            viewModel = ViewModelProvider(
                it.viewModelStore,
                NewsViewModelFactory(it.application, photoRepository)
            ).get(NewsViewModel::class.java)
        }
    }

}