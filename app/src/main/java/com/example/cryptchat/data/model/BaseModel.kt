package com.example.cryptchat.data.model

import android.content.Context
import com.example.cryptchat.persistence.db.CryptChatDB

abstract class BaseModel {

    protected lateinit var mTheDB : CryptChatDB

    fun initDatabase(context: Context){
        mTheDB = CryptChatDB.getDBInstance(context)
    }

}