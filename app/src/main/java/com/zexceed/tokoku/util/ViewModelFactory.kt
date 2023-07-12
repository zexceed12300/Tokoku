package com.zexceed.tokoku.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zexceed.tokoku.ui.home.HomeViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }

            return INSTANCE as ViewModelFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application) as T
        }
        throw IllegalArgumentException("ViewModel class:: ${modelClass.name}")
    }
}