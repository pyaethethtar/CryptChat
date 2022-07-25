package com.example.cryptchat.data.vos

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.PropertyName

//@Entity(tableName = "users")
@IgnoreExtraProperties
data class UserVO(


    @get:PropertyName("user_id") @set:PropertyName("user_id")
    var userId : String = "",

    @get:PropertyName("first_name") @set:PropertyName("first_name")
    var firstName : String = "",

    @get:PropertyName("last_name") @set:PropertyName("last_name")
    var lastName : String ?= "",

    @get:PropertyName("phone_no") @set:PropertyName("phone_no")
    var phoneNo : String = "",

    @get:PropertyName("pin") @set:PropertyName("pin")
    var pin : String = "",

    @get:PropertyName("profile_image") @set:PropertyName("profile_image")
    var profileImage : String ?= "",

    @get:PropertyName("public_string") @set:PropertyName("public_string")
    var publicString: String = "",

//    @get:PropertyName("contact_list") @set:PropertyName("contact_list")
//    var contactList : List<UserVO> = listOf(),
//
//    @get:PropertyName("friend_list") @set:PropertyName("friend_list")
//    var friendList : List<UserVO> = listOf(),

    var isAuthor : Boolean = false
)
