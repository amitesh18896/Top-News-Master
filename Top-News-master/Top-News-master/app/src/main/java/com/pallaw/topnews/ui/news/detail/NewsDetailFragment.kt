package com.pallaw.topnews.ui.news.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.pallaw.topnews.R
import com.pallaw.topnews.data.model.resourse.Article
import com.pallaw.topnews.util.TimeConverter
import com.pallaw.topnews.util.loadImage
import kotlinx.android.synthetic.main.fragment_news_detail.*

/**
 * Created by Pallaw Pathak on 08/05/20. - https://www.linkedin.com/in/pallaw-pathak-a6a324a1/
 */
class NewsDetailFragment : Fragment() {

    private var item: Article? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val bundle = NewsDetailFragmentArgs.fromBundle(it)
            item = bundle.item
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item?.let { article ->
            img_detail.loadImage(article.urlToImage)
            txt_detail_title.text = article.title
            txt_detail_author.text = article.getAuthorName() ?: ""
            txt_detail_date.text = TimeConverter.getTimeAgo(article.publishedAt) ?: ""
            txt_detail_des.text = article.description
            article.content?.let { txt_detail_content.text = Html.fromHtml(it) }

            btn_detail_web.setOnClickListener {
                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                startActivity(browserIntent)

            }
        }
    }

}
