package com.example.cryptchat.persistence.daos

import androidx.room.*
import com.example.cryptchat.data.vos.ChatVO

@Dao
interface ChatDao {

    @Query("Select * From chats")
    fun getAllChats() : List<ChatVO>

    @Query("Select * From chats Where chatId=:chatId")
    fun getChatById(chatId : String) : ChatVO

    @Insert
    fun addAllChatList(chats : List<ChatVO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNewChat(chat : ChatVO)

    @Query("Delete From chats")
    fun deleteAllChats()

    @Delete
    fun deleteChat(chat : ChatVO)
}