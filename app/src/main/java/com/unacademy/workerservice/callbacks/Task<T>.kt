package com.unacademy.workerservice.callbacks

import android.graphics.Bitmap


/**
 * Created by Vijayalaxmi Katagi on 2020-05-15.
 */
public interface Task<T> {
    fun onExecuteTask(): T?
    fun onTaskComplete(result: Bitmap?)
}