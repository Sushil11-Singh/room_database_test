package com.example.testdemo.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testdemo.R
import com.example.testdemo.activity.NewsDetailActivity
import com.example.testdemo.responce.NewsListResponse
import com.squareup.picasso.Picasso


class NewsListAdapter( val newsList: NewsListResponse,val context: Context) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.news_list_row, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        newsList.articles?.get(position)?.let { holder.bindItems(it) }
        holder.itemView.setOnClickListener {
            var intent=Intent(context,NewsDetailActivity::class.java)
            val args = Bundle()
            args.putSerializable("ArrayList",newsList.articles?.get(position))
            intent.putExtra("BUNDLE", args)

            context.startActivity(intent)
        }

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return newsList.articles?.size!!
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(newsListResponse: NewsListResponse.Article) {
            val tvTitle = itemView.findViewById(R.id.tvTtile) as TextView
            val tvDescription  = itemView.findViewById(R.id.tvDescription) as TextView
            val ivNews  = itemView.findViewById(R.id.ivNews) as ImageView

            tvTitle.text = newsListResponse.source?.name.toString()
            tvDescription.text = newsListResponse.title.toString()
            Picasso.get()
                .load(newsListResponse.urlToImage)

                .into(ivNews);


        }


    }
}

