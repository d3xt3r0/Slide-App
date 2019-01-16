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
                    .url("https://api.nytimes.com/svc/topstories/v2/home.json?api-key=GK1ZH1wjlEE0yf71q9vvtrjriGJqYWng")
                    .build()

            val client = OkHttpClient()

            val response = client.newCall(request).execute()


            if (response.body() != null) {
                val responseString = response.body()!!.string()
                /*val json1 = JSONObject(responseString)
                val json = json1.getJSONArray("results")


                val news = ArrayList<News>()

                for (i in 0..json.length() - 1) {
                    val newsItem = News(
                            json.getJSONObject(i).getString("title"),
                            json.getJSONObject(i).getString("abstract"),
                           // json.getJSONObject(i).getJSONArray("multimedia").getJSONObject(4).getString("url")

                    )

                    news.add(newsItem)
                }*/


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
                    /*Log.d("TETR",news.toString())*/
                }


               /* val keys = json.keys()
                while (keys.hasNext()) {
                    val key = keys.next().toString()
                    val newItem = News(JSONObject(json.get(key).toString()).getString("title"),
                            JSONObject(json.get(key).toString()).getString("desc"),
                            JSONObject(json.get(key).toString()).getString("link"),
                            JSONObject(json.get(key).toString()).getString("time"))
                    news.add(newItem)

                   // Log.d("TEQT",JSONObject(json.get(key).toString()).getString("link"))

                }*/

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
