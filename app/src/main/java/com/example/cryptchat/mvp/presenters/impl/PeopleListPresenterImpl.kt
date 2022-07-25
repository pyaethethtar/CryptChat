package com.example.cryptchat.mvp.presenters.impl

import android.util.Log
import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.data.vos.ChatVO
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.PeopleListPresenter
import com.example.cryptchat.mvp.views.PeopleListView

class PeopleListPresenterImpl : PeopleListPresenter, AbstractBasePresenter<PeopleListView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl
    private var mUserList = listOf<UserVO>()

    override fun onUiReady(contacts : ArrayList<String>) {
        mUserList = mModel.userList

        if (mUserList.isNotEmpty()){
            mView?.hideLoading()
            mView?.displayPeopleList(mUserList)
        }
        else{
            requestUserList(contacts)
        }
    }

    override fun onTapPeople(userId: String) {
        val userIds = listOf(mModel.getUserIdFromSharedPref()!!, userId)
        val userIds2 = listOf(userId, mModel.getUserIdFromSharedPref()!!)

        mModel.getChatByUserId(userIds, onSuccess = {
            if (it.chatId==""){
                mModel.getChatByUserId(userIds2, onSuccess = {
                    if (it.chatId==""){
                        createNewChat(userId)
                    }
                    else{
                        mView?.navigateToChatScreen(it.chatId)
                    }
                }, onFailure={
                    mView?.displayError(it, onTapOkay = {})
                })
            }
            else{
                mView?.navigateToChatScreen(it.chatId)
            }
        }, onFailure = {
            mView?.displayError(it, onTapOkay = {})
        })


    }

    private fun createNewChat(userId: String){
        val chatVO = ChatVO()

        //get other user and then create new chat
        mModel.getUser(userId, onSucess = {
            chatVO.user2 = it
            chatVO.user1 = mModel.user
            chatVO.userIds = arrayListOf(chatVO.user1.userId, chatVO.user2.userId)

            mModel.createNewChat(chatVO, onSuccess = {
                mModel.saveChatToDatabase(chatVO)
                mView?.navigateToChatScreen(it)
            }, onFailure = {
                mView?.displayError(it, onTapOkay = {})
            })
        }, onFailure = {
            mView?.displayError(it, onTapOkay = {})
        })
    }

    private fun requestUserList(contactList: List<String>){
        if (contactList.isEmpty()){
            mView?.hideLoading()
            mView?.displayEmptyList()
        }
        else{
            mModel.getUserList(contactList, onSuccess = {
                mView?.hideLoading()
                if (it.isNotEmpty()){
                    mModel.userList = it
                    mView?.displayPeopleList(it)
                }
            }, onFailure = {
                mView?.displayError(it, onTapOkay = {})
            })
        }
    }
}