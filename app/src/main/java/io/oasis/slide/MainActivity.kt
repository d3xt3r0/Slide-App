package io.oasis.slide

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.oasis.slide.R.id.vPager
import io.oasis.slide.database.News
import io.oasis.slide.database.NewsComparator
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBarMain.visibility = View.VISIBLE

        doAsync {

            val request = Request.Builder()
                    .url("Replace with your Developer key")
                    .build()

            val client = OkHttpClient()

            val response = client.newCall(request).execute()


            if (response.body() != null) {
                val responseString = response.body()!!.string()


                val json  = JSONObject(responseString)
                Log.d("debug",json.toString())
                val result = json.getJSONArray("results")
                var url = "res/drawable/bg.png"
                val news = ArrayList<News>()


                for(i in 0..result.length()-1)    {
                    val title = result.getJSONObject(i).getString("title")
                    val abstract = result.getJSONObject(i).getString("abstract")//***/
                    val time = result.getJSONObject(i).getString("published_date")


                    val multimedia = result.getJSONObject(i).getJSONArray("multimedia")

                    val link = result.getJSONObject(i).getString("url")
                    for (i in 0..multimedia.length()-1) {
                        url = multimedia.getJSONObject(i).getString("url")

                    }

                    val newItem = News(title,abstract,url,link,time)
                    news.add(newItem)
                }


                uiThread {

                    Collections.sort(news,NewsComparator())
                    Collections.reverse(news)
                    val adapter = VerticlePagerAdapter(this@MainActivity, news)
                    vPager.adapter = adapter
                    progressBarMain.visibility = View.GONE
                }


            }
        }

    }

}
