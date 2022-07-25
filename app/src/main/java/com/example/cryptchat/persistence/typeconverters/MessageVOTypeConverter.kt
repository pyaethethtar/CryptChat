package com.example.cryptchat.persistence.typeconverters

import androidx.room.TypeConverter
import com.example.cryptchat.data.vos.MessageVO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MessageVOTypeConverter {

    @TypeConverter
    fun toJsonString(messages : ArrayList<MessageVO>) : String{
        return Gson().toJson(messages)
    }

    @TypeConverter
    fun toArrayList(messagesString: String) : ArrayList<MessageVO>{
        val messagesType = object : TypeToken<ArrayList<MessageVO>>(){}.type
        return Gson().fromJson(messagesString, messagesType)
    }
}