package com.unacademy.workerservice.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.unacademy.workerservice.callbacks.Task
import com.unacademy.workerservice.service.ServiceWorker
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.util.concurrent.TimeUnit


/**
 * Created by Vijayalaxmi Katagi on 2020-05-15.
 */
class MainActivityViewModel: ViewModel() {
    var threadList=ArrayList<Thread>()
    var serviceWorker1 = ServiceWorker("service_worker_1",threadList)
    var serviceWorker2 = ServiceWorker("service_worker_2",threadList)
    var firstImageBitmap=MutableLiveData<Bitmap>()
    var secondImageBitmap=MutableLiveData<Bitmap>()
    val ImageOne="https://i.picsum.photos/id/1025/4951/3301.jpg"
    val ImageTwo="https://i.picsum.photos/id/180/2400/1600.jpg"
    private var client: OkHttpClient?=null
    init {
        client = OkHttpClient()
        client?.setConnectTimeout(30, TimeUnit.SECONDS);
        client?.setReadTimeout(30, TimeUnit.SECONDS);
    }

    fun fetchImage1AndSet() {
          serviceWorker1.addTask(object : Task<Bitmap> {
            override fun onExecuteTask(): Bitmap? {
                return fetchImages(ImageOne)
            }
            override fun onTaskComplete(result: Bitmap?) {
                firstImageBitmap.postValue(result)
            }
        })
    }



    fun fetchImage2AndSet() {
        serviceWorker2.addTask(object : Task<Bitmap> {
            override fun onExecuteTask(): Bitmap? {
                return fetchImages(ImageTwo)
            }
            override fun onTaskComplete(result: Bitmap?) {
                secondImageBitmap.postValue(result)
            }
        })
    }


    fun fetchImages(imageUrl:String):Bitmap?{
        try {
            val request = Request.Builder().url(imageUrl).build()
            val response = client?.newCall(request)?.execute()
            if (response?.code()?:1500 <HTTP_BAD_REQUEST) {
                return BitmapFactory.decodeStream(response?.body()?.byteStream())
            }else{
                return null
            }
        } catch (e: Exception) {
            return null
        }
    }

    fun stopThreads() {
        Log.d("VM : ","Viewmode cleared invoked")
        for(thread in threadList){
            thread.interrupt()
        }
    }
}