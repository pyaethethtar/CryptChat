package com.example.cryptchat.utils

import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.data.vos.UserVO

fun UserVO.toUserMap() : HashMap<String, Any?>{
    val userMap = hashMapOf<String, Any?>(
        "user_id" to this.userId,
        "first_name" to this.firstName,
        "last_name" to this.lastName,
        "phone_no" to this.phoneNo,
        "pin" to this.pin,
        "profile_image" to this.profileImage,
        "public_string" to this.publicString
//        "contact_list" to this.contactList,
//        "friend_list" to this.friendList
    )
    return userMap
}

fun ChatVO.toChatMap() : HashMap<String, Any>{
    val chatMap = hashMapOf<String, Any>(
        "chat_id" to this.chatId,
        "user_1" to this.user1,
        "user_2" to this.user2,
        "user_ids" to this.userIds
    )
    return chatMap
}

fun MessageVO.toMessageMap() : HashMap<String, Any?>{
    val messageMap = hashMapOf<String, Any?>(
        "message" to this.message,
        "photo_message" to this.photoMessage,
        "timestamp" to this.timestamp,
        "from_user_id" to this.fromUserId
//        "user_type" to this.userType,
//        "is_date_changed" to this.isDateChanged,
//        "is_last_message" to this.isLastMessage
    )
    return messageMap
}