package com.unacademy.workerservice.service

import android.graphics.Bitmap
import com.unacademy.workerservice.callbacks.Task

/**
 * Created by Vijayalaxmi Katagi on 2020-05-15.
 */
class ServiceWorker(name: String,threadList:ArrayList<Thread>):Runnable {
    private var threadName: String? = null
    var thread: Thread? = null
    var taskCallback : Task<Bitmap>?=null
    init {
        this.threadName = name
        thread = Thread(this, name)
        threadList.add(thread as Thread)
    }
    override fun run() {
        val bitmap=taskCallback?.onExecuteTask()
        taskCallback?.onTaskComplete(bitmap)
    }

    fun addTask(taskCallback : Task<Bitmap>){
        this.taskCallback=taskCallback
        thread?.start()

    }
}
git