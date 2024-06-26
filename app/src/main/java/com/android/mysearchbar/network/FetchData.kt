package com.android.mysearchbar.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import kotlin.coroutines.cancellation.CancellationException

class FetchData {

    // ui scope
    private final val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

//    private fun uiScope() {
//        uiScope.launch
//        {
//            try {
//                  fetchData()
//            } catch (e: Exception) {
//                if (e is CancellationException) {
//                    println("Request was cancelled")
//                } else {
//                    println("Error: ${e.message}")
//                }
//            }
//        }
//    }


    private val client = OkHttpClient()
    private var call: Call? = null


    fun fetchData() {
        val request = Request.Builder()
            .url("https://api.example.com/endpoint")
            .build()

        call = client.newCall(request)
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (call.isCanceled()) {
                    println("Request was cancelled")
                } else {
                    println("Error: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    println("Data fetched: $responseData")
                } else {
                    println("Error: ${response.message}")
                }
            }
        })
    }

    fun onDestroy() {
        job.cancel() // Cancel the coroutine when the activity is destroyed
    }

}