package com.example.testdemo.activity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.testdemo.R
import com.example.testdemo.adapter.NewsListAdapter
import com.example.testdemo.api.RequestInterface
import com.example.testdemo.responce.NewsListResponse
import com.example.testdemo.room_modal.AppDatabase
import com.example.testdemo.room_modal.DatabaseClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_news_list.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class NewsListActivity : AppCompatActivity() {
    private val TAG = NewsListActivity::class.java.simpleName

    private var mCompositeDisposable: CompositeDisposable? = null

    private var mAndroidArrayList: ArrayList<NewsListResponse>? = null
    private var adapter: NewsListAdapter? = null

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        if (isOnline()) {
            mCompositeDisposable = CompositeDisposable()

            initRecyclerView()

            loadJSON()

        } else {
            getTasks();


        }


    }

    private fun getTasks() {
        class GetTask() : AsyncTask<Void, Void, NewsListResponse>() {

            override fun doInBackground(vararg params: Void?): NewsListResponse {
                var newResponce = DatabaseClient
                    .getInstance(getApplicationContext())
                    .getAppDatabase()
                    .newsDao()
                    .getContacts();


                return newResponce
                // ...
            }

            override fun onPreExecute() {
                super.onPreExecute()
                // ...
            }

            override fun onPostExecute(result: NewsListResponse?) {
                super.onPostExecute(result)
                // ...
                initRecyclerView()
                if (result!=null) {
                    adapter = result?.let { NewsListAdapter(it, this@NewsListActivity) }
                    rv_news.adapter = adapter
                } else {
                    Toast.makeText(getApplicationContext(), "Please connect to internet", Toast.LENGTH_LONG).show();

                }

            }
        }

        val st = GetTask()
        st.execute()
    }

    private fun initRecyclerView() {
        rv_news.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv_news.layoutManager = layoutManager
    }

    private fun loadJSON() {

        val requestInterface = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestInterface::class.java)

        mCompositeDisposable?.add(
            requestInterface.getNews()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
        )


    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }

    private fun onResponse(response: NewsListResponse) {
        progress_bar.visibility = View.GONE
        rv_news.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@NewsListActivity)
            adapter = NewsListAdapter(response, this@NewsListActivity)

            saveinDb(response)

        }
    }

    private fun saveinDb(response: NewsListResponse) {
        class SaveTask() : AsyncTask<Void, Void, String>() {
            override fun doInBackground(vararg params: Void?): String? {

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .newsDao()
                    .insert(response);

                return null
                // ...
            }

            override fun onPreExecute() {
                super.onPreExecute()
                // ...
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                // ...
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        val st = SaveTask()
        st.execute()
    }
    /* fun isInternetOn(context: Context): Observable<Boolean?>? {
         val connectivityManager =
             context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
         val activeNetworkInfo = connectivityManager.activeNetworkInfo
         return Observable.just(activeNetworkInfo != null && activeNetworkInfo.isConnected)
     }*/

    fun isOnline(): Boolean {
        val cm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}








