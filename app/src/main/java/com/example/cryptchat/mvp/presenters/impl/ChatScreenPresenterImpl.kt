package com.example.cryptchat.mvp.presenters.impl

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.ChatScreenPresenter
import com.example.cryptchat.mvp.views.ChatScreenView
import com.example.cryptchat.utils.RSAUtils
import com.example.cryptchat.utils.USER_AUTHOR
import com.example.cryptchat.utils.USER_OTHER
import java.sql.Timestamp
import java.text.SimpleDateFormat

class ChatScreenPresenterImpl : ChatScreenPresenter, AbstractBasePresenter<ChatScreenView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl
    private lateinit var mChatId : String
    private var mOtherUser = UserVO()

    @SuppressLint("SimpleDateFormat")
    override fun onUiReady(chatId: String) {
        mView?.displayLoading()
        mChatId = chatId

        mModel.getChatById(mChatId, onSuccess = {

            saveChatToDB(it)

            if (it.messages.isNotEmpty()){
                mView?.displayMessages(prepareMessageList(it.messages))
            }

            if (it.user1.userId == mModel.user.userId){
                mOtherUser = it.user2
            }
            else{
                mOtherUser = it.user1
            }
            mView?.displayUserInfo(mOtherUser)
            mView?.hideLoading()

        }, onFailure = {
            mView?.displayError(it, onTapOkay = {})
        })
    }

    override fun onTapOtherProfile(userId: String) {
        mView?.navigateToUserProfile(userId)
    }

    override fun onTapSend(message: String) {
        val messageVO = MessageVO()
        messageVO.timestamp = Timestamp(System.currentTimeMillis()).time
        messageVO.fromUserId = mModel.user.userId

        //save message(author) to db
        messageVO.message = message
        saveMessageToDB(messageVO, mChatId)

        //encrypt message
        messageVO.message = RSAUtils.encrypt(message, mOtherUser.publicString)


        mModel.sendTextMessage(messageVO, mChatId, onSuccess = {
            mView?.clearInputTextBox()
        }, onFailure = {
            mView?.displayError(it, onTapOkay = {})
        })
    }

    override fun onTapSendImage(image: Bitmap) {
        val messageVO = MessageVO()
        messageVO.timestamp = Timestamp(System.currentTimeMillis()).time
        messageVO.fromUserId = mModel.user.userId
        mModel.sendImageMessage(image, messageVO, mChatId, mOtherUser.publicString, onSuccess = {
            Log.d("CryptChat", "Success")
        }, onFailure = {
            mView?.displayError(it, onTapOkay = {})
        })
    }

    override fun onTapBack() {
        mView?.navigateToBack()
    }

    override fun onTapCamera() {
        mView?.openGallery()
    }

    private fun saveMessageToDB(message: MessageVO, chatId: String){
        val chat = mModel.getChatFromDatabase(chatId)
        chat.messages.add(message)
        mModel.saveChatToDatabase(chat)
    }

    private fun saveChatToDB(chatVO: ChatVO){
        if (mModel.getChatFromDatabase(chatVO.chatId)==null){
            val chat = ChatVO()
            chat.chatId = chatVO.chatId
            mModel.saveChatToDatabase(chat)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun prepareMessageList(messageList : List<MessageVO>) : List<MessageVO>{
        val messages = sortedMessageList(messageList)
        val dateFormat = SimpleDateFormat("dd-mm-yy")
        var firstMessageDate : String
        var secondMessageDate : String


        var firstMessage = messages.first()
        for(message in messages){

            //decrypt message
            if (message.message!=null && message.fromUserId!=mModel.user.userId){
                message.message = RSAUtils.decrypt(message.message, mModel.getPrivateStringFromSharedPref()?:" ")
            }
            else if (message.photoMessage!=null && message.fromUserId!=mModel.user.userId){
                message.photoMessage = RSAUtils.decrypt(message.photoMessage, mModel.getPrivateStringFromSharedPref()?:" ")
            }


            if (mOtherUser.profileImage!=null && mOtherUser.profileImage!=""){
                message.otherUserImage = mOtherUser.profileImage
            }

            if (message.fromUserId==mModel.user.userId){
                message.userType = USER_AUTHOR
            }
            else{
                message.userType = USER_OTHER
            }

            if(firstMessage!=message){
                firstMessageDate = dateFormat.format(firstMessage.timestamp)
                secondMessageDate = dateFormat.format(message.timestamp)

                if(firstMessageDate==secondMessageDate){
                    message.isDateChanged = false
                }

                if (firstMessage.userType==message.userType && !message.isDateChanged){
                    messages[messages.indexOf(message)-1].isLastMessage = false
                }
            }

            firstMessage = message
        }
        return messages
    }

    private fun sortedMessageList(messages : List<MessageVO>) : List<MessageVO>{
        val unsortedList = mergeMessages(messages).toMutableList()
        val sortedList = mutableListOf<MessageVO>()
        val size = messages.size

        for (i in 1..size){
            var initialMessage = unsortedList.first()
            for (message in unsortedList){
                if (initialMessage.timestamp>message.timestamp){
                    initialMessage = message
                }
            }
            sortedList.add(initialMessage)
            unsortedList.remove(initialMessage)
        }

        return sortedList.toList()
    }

    private fun mergeMessages(messageList: List<MessageVO>) : List<MessageVO>{
        val messages = arrayListOf<MessageVO>()
        for(message in messageList){
            if (message.fromUserId!=mModel.user.userId){
                messages.add(message)
            }
        }
        val chat = mModel.getChatFromDatabase(mChatId)
        if (chat.messages.isNotEmpty()){
            messages.addAll(chat.messages)
        }

        return messages
    }


}