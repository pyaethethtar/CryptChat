package com.example.cryptchat.data.vos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cryptchat.persistence.typeconverters.MessageVOTypeConverter
import com.example.cryptchat.persistence.typeconverters.StringTypeConverter
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

@Entity(tableName = "chats")
@TypeConverters(StringTypeConverter::class, MessageVOTypeConverter::class)
@IgnoreExtraProperties
data class ChatVO(

    @PrimaryKey
    @get:PropertyName("chat_id") @set:PropertyName("chat_id")
    var chatId: String= "",

    @Embedded(prefix = "user1_")
    @get:PropertyName("user_1") @set:PropertyName("user_1")
    var user1 : UserVO = UserVO(),

    @Embedded(prefix = "user2_")
    @get:PropertyName("user_2") @set:PropertyName("user_2")
    var user2 : UserVO = UserVO(),

    @get:PropertyName("user_ids") @set:PropertyName("user_ids")
    var userIds : ArrayList<String> = arrayListOf(),

    @get:PropertyName("messages") @set:PropertyName("messages")
    var messages : ArrayList<MessageVO> = arrayListOf()

    )
