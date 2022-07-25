package com.example.cryptchat.mvp.presenters.impl

import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.MessageVO
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.ChatListPresenter
import com.example.cryptchat.mvp.views.ChatListView

class ChatListPresenterImpl : ChatListPresenter, AbstractBasePresenter<ChatListView>() {

    private val mModel : CryptChatModel  = CryptChatModelImpl
    private var mUserId : String = ""

    override fun onUiReady() {
        mUserId = mModel.getUserIdFromSharedPref() ?: ""
        mModel.getChatList(mUserId, onSucess = {
            if (it.isNotEmpty()){
                for (chat in it){
                    if (chat.user1.userId == mUserId)   {
                        chat.user1.isAuthor = true
                    }
                }
                mView?.hideLoading()
                mView?.displayChatList(it)
            }
            else{
                mView?.hideLoading()
                mView?.displayEmptyList()
            }
        }, onFailure = {
            mView?.displayError(it, onTapOkay = {})
        })
    }

    override fun onTapChatItem(id: String) {
        mView?.navigateToChatScreen(id)
    }

    private fun filterMessagesAndSaveToDB(chatList : List<ChatVO>){
        val chats = arrayListOf<ChatVO>()
        for (chat in chatList){
            val messageList = arrayListOf<MessageVO>()
            for (message in chat.messages){
                if (message.fromUserId != mUserId){
                    messageList.add(message)
                }
            }
            chat.messages = messageList
            chats.add(chat)
        }
        mModel.saveChatListToDatabase(chats)
    }
}