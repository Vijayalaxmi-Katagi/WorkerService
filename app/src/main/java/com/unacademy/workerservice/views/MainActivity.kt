package com.unacademy.workerservice.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.unacademy.workerservice.R
import com.unacademy.workerservice.utils.Util
import com.unacademy.workerservice.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Vijayalaxmi Katagi on 2020-05-15.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mainViewModel: MainActivityViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }
    private fun init() {
        initViews()
        initObjects()
        setObservers();
    }

    fun initViews() {
        buttonFirst.setOnClickListener(this)
        buttonSecond.setOnClickListener(this)

    }

    private fun initObjects() {
        mainViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    private fun setObservers() {
        mainViewModel?.firstImageBitmap?.observe(this, Observer {
            progressOne.visibility=View.GONE
            firstImageView.setImageBitmap(it);
        })
        mainViewModel?.secondImageBitmap?.observe(this, Observer {
            progressTwo.visibility=View.GONE
            secondImageView.setImageBitmap(it);
        })
    }


    override fun onClick(v: View?) {
        if (!checkNetworkAvailable()) {
            Toast.makeText(
                this,
                this.getString(R.string.network_not_available),
                Toast.LENGTH_LONG
            ).show()
            return
        }
        when (v?.id) {
            R.id.buttonFirst -> {
                progressOne.visibility=View.VISIBLE
                mainViewModel?.fetchImage1AndSet()
            }
            R.id.buttonSecond -> {
                progressTwo.visibility=View.VISIBLE
                mainViewModel?.fetchImage2AndSet()
            }
        }
    }

    fun checkNetworkAvailable(): Boolean {
        return Util.isNetworkAvailable(this)
    }

    override fun onDestroy() {
        mainViewModel?.stopThreads()
        super.onDestroy()

    }
}
