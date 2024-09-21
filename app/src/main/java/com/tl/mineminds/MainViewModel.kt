package com.tl.mineminds

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tl.mineminds.ui.screen.ScreenNames

class MainViewModel:ViewModel() {

    private var sharedPreferences: SharedPreferences? = null


    var currentRoute: MutableLiveData<String> = MutableLiveData(ScreenNames.LOGIN.routeName)
        private set
    var username: MutableLiveData<String> = MutableLiveData("")
        private set
    var userToken: MutableLiveData<String> = MutableLiveData("")
        private set

    fun initSharedPref(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    fun onUsernameEntered(username: String) {
        this.username.postValue(username)
        currentRoute.postValue(ScreenNames.MAIN.routeName)
    }

}