package com.example.cryptchat.network

import android.graphics.Bitmap
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.data.vos.UserVO

interface FirebaseAPI {

    fun login(user: UserVO, profileImage: Bitmap?, onSuccess:(id:String)->Unit, onFailure:(String)->Unit)
    fun getChatList(userId: String, onSuccess: (List<ChatVO>) -> Unit, onFailure: (String) -> Unit)
    fun getChatById(chatId: String, onSuccess: (ChatVO) -> Unit, onFailure: (String) -> Unit)
    fun getChatByUserId(userIds: List<String>, onSuccess: (ChatVO) -> Unit, onFailure: (String) -> Unit)
    fun getUser(userId: String, onSuccess: (UserVO) -> Unit, onFailure: (String) -> Unit)
    fun getUserList(contactList: List<String>, onSuccess: (List<UserVO>) -> Unit, onFailure: (String) -> Unit)
    fun sendTextMessage(message: MessageVO, chatId: String, onSuccess:()->Unit, onFailure: (String) -> Unit)
    fun sendImageMessage(image: Bitmap, message: MessageVO, chatId: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun createNewChat(chatVO: ChatVO, onSuccess: (chatId : String) -> Unit, onFailure: (String) -> Unit)
    fun uploadImage(image: Bitmap, directory:String, onComplete:(String)->Unit)
    fun updateUserInfo(user: UserVO, profileImage: Bitmap?, onSuccess:()->Unit, onFailure:(String)->Unit)
    //fun createNewChatWithImage(image: Bitmap, chatVO: ChatVO, onSuccess: (chatId : String) -> Unit, onFailure: (String) -> Unit)
}