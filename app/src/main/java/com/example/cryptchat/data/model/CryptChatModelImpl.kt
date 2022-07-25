package com.example.cryptchat.data.model

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Log
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.network.CloudFirestoreDataAgentImpl
import com.example.cryptchat.network.FirebaseAPI
import com.example.cryptchat.utils.*

object CryptChatModelImpl : BaseModel(), CryptChatModel {

    private lateinit var sharedPref : SharedPreferences
    override var mFirebaseAPI: FirebaseAPI = CloudFirestoreDataAgentImpl
    override var user: UserVO = UserVO()
    override var profileImage: Bitmap ?= null
    override var userList: List<UserVO> = listOf()


    override fun createSharedPref(context: Context) {
        sharedPref = context.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)
    }

    override fun saveUserIdInSharedPref(userId: String) {
        val preferenceEditor = sharedPref.edit()
        preferenceEditor.putString(USER_ID, userId)
        preferenceEditor.apply()
    }

    override fun getUserIdFromSharedPref(): String? {
        return sharedPref.getString(USER_ID, null)
    }

    override fun savePrivateString(key: String) {
        val preferenceEditor = sharedPref.edit()
        preferenceEditor.putString(PRIVATE_STRING, key)
        preferenceEditor.apply()
    }

    override fun getPrivateStringFromSharedPref(): String? {
        return sharedPref.getString(PRIVATE_STRING, null)
    }

    override fun isLoggedIn(): Boolean {
        return getUserIdFromSharedPref()!=null
    }

    override fun login(
        user: UserVO,
        profileImage: Bitmap?,
        onSucess: (id: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAPI.login(user, profileImage, onSucess, onFailure)
    }

    override fun getChatList(
        userId: String,
        onSucess: (List<ChatVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAPI.getChatList(userId, onSucess, onFailure)
    }

    override fun getChatById(
        chatId: String,
        onSuccess: (ChatVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAPI.getChatById(chatId, onSuccess, onFailure)
    }

    override fun getChatByUserId(
        userIds: List<String>,
        onSuccess: (ChatVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAPI.getChatByUserId(userIds, onSuccess, onFailure)
    }

    override fun getUser(userId: String, onSucess: (UserVO) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseAPI.getUser(userId, onSucess, onFailure)
    }

    override fun getUserList(
        contactList: List<String>,
        onSuccess: (List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAPI.getUserList(contactList, onSuccess, onFailure)
    }

    override fun sendTextMessage(
        message: MessageVO,
        chatId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAPI.sendTextMessage(message, chatId, onSuccess, onFailure)
    }

    override fun sendImageMessage(
        image: Bitmap,
        message: MessageVO,
        chatId: String,
        publicstring: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAPI.uploadImage(image, DIRECTORY_MESSAGE_IMAGES, onComplete = {
            message.photoMessage = it
            val chat = getChatFromDatabase(chatId)
            chat.messages.add(message)
            saveChatToDatabase(chat)

            //encrypt photo url
            message.photoMessage = RSAUtils.encrypt(it, publicstring)
            mFirebaseAPI.sendTextMessage(message, chatId, onSuccess, onFailure)
        })
       // mFirebaseAPI.sendImageMessage(image, message, chatId, onSuccess, onFailure)
    }

    override fun createNewChat(chatVO: ChatVO, onSuccess: (chatId : String) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseAPI.createNewChat(chatVO, onSuccess, onFailure)
    }

    override fun updateUserInfo(
        user: UserVO,
        profileImage: Bitmap?,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAPI.updateUserInfo(user, profileImage, onSuccess, onFailure)
    }

    override fun saveChatListToDatabase(chatList: List<ChatVO>) {
        val chats = arrayListOf<ChatVO>()
        for (chat in chatList){
            val messageList = arrayListOf<MessageVO>()
            for (message in chat.messages){
                if (message.fromUserId != user.userId){
                    messageList.add(message)
                }
            }
            chat.messages = messageList
            chats.add(chat)
        }
        mTheDB.chatDao().deleteAllChats()
        mTheDB.chatDao().addAllChatList(chats)
    }

    override fun saveChatToDatabase(chatVO: ChatVO) {
        mTheDB.chatDao().addNewChat(chatVO)
    }

    override fun getChatFromDatabase(chatId: String): ChatVO {
        return mTheDB.chatDao().getChatById(chatId)
    }

//    override fun createNewChatWithImage(
//        image: Bitmap,
//        chatVO: ChatVO,
//        onSuccess: (chatId : String) -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        mFirebaseAPI.createNewChatWithImage(image, chatVO, onSuccess, onFailure)
//    }
}