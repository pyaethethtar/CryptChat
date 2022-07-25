package com.example.cryptchat.persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.persistence.daos.ChatDao
import com.example.cryptchat.persistence.daos.UserDao

@Database(entities = [ChatVO::class], version = 1, exportSchema = false)
abstract class CryptChatDB : RoomDatabase() {

    companion object{
        val DB_NAME = "CRYPTY_CHAT_DB"
        var dbInstance : CryptChatDB ?= null

        fun getDBInstance(context: Context) : CryptChatDB{
            when(dbInstance){
                null -> {
                    dbInstance = Room.databaseBuilder(context, CryptChatDB::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            val i = dbInstance
            return i!!
        }
    }

    abstract fun chatDao() : ChatDao
    //abstract fun userDao() : UserDao
}