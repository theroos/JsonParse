package com.example.theroos.jsonparse

import android.app.DownloadManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

val url = "jsonplaceholder.typicode.com/posts"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fetchbutton = findViewById<Button>(R.id.fetch_button)



        fetchbutton.setOnClickListener(View.OnClickListener {
            //Toast.makeText(this,"Data Fetch",Toast.LENGTH_LONG).show()
            if (checkForInternet(this)) {
                //Toast.makeText(this, "Yess.. Internet Connected", Toast.LENGTH_SHORT).show()
                fetchingdatafrominternet()
            } else {
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun checkForInternet(mainActivity: MainActivity): Boolean {
        val connectivityManager = mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                else -> false
            }
        }
        else {
            @Suppress("DEPRECATION") val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun fetchingdatafrominternet() {
        val queue = Volley.newRequestQueue(this)
        val reques = StringRequest(Request.Method.GET, url,
            { response ->
                val data = response.toString()
                var jArray = JSONArray(data)
                for(i in 0.. jArray.length()-1){
                    var jobject = jArray.getJSONObject(i)
                    var userid = jobject.getInt("userId")
                    var id = jobject.getInt("id")
                    var title = jobject.getInt("title")
                    var body = jobject.getInt("body")
                    Log.e("userId",userid.toString())
                    Log.e("id",id.toString())
                    Log.e("title",title.toString())
                    Log.e("body",body.toString())
                }
                //Log.e("Error",response.toString())
            }, {  })
        queue.add(reques)
    }
}