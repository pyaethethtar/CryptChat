package com.example.cryptchat.persistence.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringTypeConverter {

    @TypeConverter
    fun toJsonString(changedStringList : ArrayList<String>) : String{
        return Gson().toJson(changedStringList)
    }

    @TypeConverter
    fun toArrayList(changedString: String) : ArrayList<String>{
        val stringType = object : TypeToken<ArrayList<String>>(){}.type
        return Gson().fromJson(changedString, stringType)
    }
}