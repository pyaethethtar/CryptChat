package com.example.cryptchat.data.model

import android.content.Context
import android.graphics.Bitmap
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.network.FirebaseAPI

interface CryptChatModel {

    var mFirebaseAPI : FirebaseAPI
    var user : UserVO
    var profileImage : Bitmap?
    var userList : List<UserVO>

    fun createSharedPref(context: Context)
    fun saveUserIdInSharedPref(userId: String)
    fun getUserIdFromSharedPref() : String?
    fun savePrivateString(key : String)
    fun getPrivateStringFromSharedPref() : String?
    fun isLoggedIn() : Boolean



    fun login(user: UserVO, profileImage: Bitmap?, onSucess:(id:String)->Unit, onFailure:(String)->Unit)
    fun getChatList(userId: String, onSucess: (List<ChatVO>) -> Unit, onFailure: (String) -> Unit)
    fun getChatById(chatId: String, onSuccess: (ChatVO) -> Unit, onFailure: (String) -> Unit)
    fun getChatByUserId(userIds: List<String>, onSuccess: (ChatVO) -> Unit, onFailure: (String) -> Unit)
    fun getUser(userId: String, onSucess: (UserVO) -> Unit, onFailure: (String) -> Unit)
    fun getUserList(contactList: List<String>, onSuccess: (List<UserVO>) -> Unit, onFailure: (String) -> Unit)
    fun sendTextMessage(message: MessageVO, chatId: String, onSuccess:()->Unit, onFailure: (String) -> Unit)
    fun sendImageMessage(image: Bitmap, message: MessageVO, chatId: String, publicstring: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)
    fun createNewChat(chatVO: ChatVO, onSuccess: (chatId : String) -> Unit, onFailure: (String) -> Unit)
    fun updateUserInfo(user: UserVO, profileImage: Bitmap?, onSuccess:()->Unit, onFailure:(String)->Unit)
    //fun createNewChatWithImage(image: Bitmap, chatVO: ChatVO, onSuccess: (chatId : String) -> Unit, onFailure: (String) -> Unit)

    fun saveChatListToDatabase(chatList: List<ChatVO>)
    fun saveChatToDatabase(chatVO: ChatVO)
    fun getChatFromDatabase(chatId: String) : ChatVO
}