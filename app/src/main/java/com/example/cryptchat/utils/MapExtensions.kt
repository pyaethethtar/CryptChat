package com.example.cryptchat.utils

import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.data.vos.UserVO

fun MutableMap<String, Any>.toChatVO() : ChatVO{
    val chatVO = ChatVO()
    this.get("chat_id")?.let {
        chatVO.chatId = it as String
    }
    this.get("user_1")?.let {
        chatVO.user1 = (it as MutableMap<String, Any>).toUserVO()
    }
    this.get("user_2")?.let {
        chatVO.user2 = (it as MutableMap<String, Any>).toUserVO()
    }
    this.get("user_ids")?.let {
        chatVO.userIds = it as ArrayList<String> /* = java.util.ArrayList<kotlin.String> */
    }
    return chatVO
}

fun MutableMap<String, Any>.toMessageVO() : MessageVO{
    val messageVO = MessageVO()
    this.get("message")?.let {
        messageVO.message = it as String
    }
    this.get("photo_message")?.let {
        messageVO.photoMessage = it as String
    }
    this.get("timestamp")?.let {
        messageVO.timestamp = it as Long
    }
    this.get("from_user_id")?.let {
        messageVO.fromUserId = it as String
    }
//    this.get("user_type")?.let {
//        messageVO.userType = it as String
//    }
//    this.get("is_date_changed")?.let {
//        messageVO.isDateChanged = it as Boolean
//    }
//    this.get("is_last_message")?.let {
//        messageVO.isLastMessage = it as Boolean
//    }
    return messageVO
}

fun MutableMap<String, Any>.toUserVO() : UserVO{
    val userVO = UserVO()
    this.get("user_id")?.let {
        userVO.userId = it as String
    }
    this.get("first_name")?.let {
        userVO.firstName = it as String
    }
    this.get("last_name")?.let {
        userVO.lastName = it as String
    }
    this.get("phone_no")?.let {
        userVO.phoneNo = it as String
    }
    this.get("pin")?.let {
        userVO.pin = it as String
    }
    this.get("profile_image")?.let {
        userVO.profileImage = it as String
    }
    this.get("public_string")?.let {
        userVO.publicString = it as String
    }
    return userVO
}