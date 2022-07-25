package com.example.cryptchat

import android.app.Application
import com.example.cryptchat.data.model.CryptChatModelImpl

class CryptChatApp : Application() {

    override fun onCreate() {
        super.onCreate()
        CryptChatModelImpl.initDatabase(applicationContext)
        CryptChatModelImpl.createSharedPref(applicationContext)
    }
}