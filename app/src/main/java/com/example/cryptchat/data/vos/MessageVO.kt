package com.example.cryptchat.data.vos

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

@IgnoreExtraProperties
data class MessageVO(

    @get:PropertyName("message") @set:PropertyName("message")
    var message : String ?= null,

    @get:PropertyName("photo_message") @set:PropertyName("photo_message")
    var photoMessage : String ?= null,

    @get:PropertyName("timestamp") @set:PropertyName("timestamp")
    var timestamp : Long = 0L,

    @get:PropertyName("from_user_id") @set:PropertyName("from_user_id")
    var fromUserId : String = "",

    @get:PropertyName("user_type") @set:PropertyName("user_type")
    var userType : String ?= null,

    @get:PropertyName("is_date_changed") @set:PropertyName("is_data_changed")
    var isDateChanged : Boolean = true,

    @get:PropertyName("is_last_message") @set:PropertyName("is_last_message")
    var isLastMessage : Boolean = true,

    var otherUserImage : String ?= null
)
