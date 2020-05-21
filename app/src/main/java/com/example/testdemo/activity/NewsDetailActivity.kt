package com.example.testdemo.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testdemo.R
import com.example.testdemo.responce.NewsListResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_news_detail.*


class NewsDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val intent = intent
        val args = intent.getBundleExtra("BUNDLE")
        val data = args.getSerializable("ArrayList") as NewsListResponse.Article

        initViews(data)

    }

    private fun initViews(data: NewsListResponse.Article) {
        Picasso.get()
            .load(data.urlToImage)
            .into(ivNewsDetail);

        tvAuthor.text = data.author.toString()
        tvDetailDescription.text = data.description
        tvContent.text = data.content.toString()
        back.setOnClickListener {
            finish()
        }


    }


}
