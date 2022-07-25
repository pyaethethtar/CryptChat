package com.example.cryptchat.mvp.presenters.impl

import android.graphics.Bitmap
import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.data.vos.UserVO
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.ProfileEditPresenter
import com.example.cryptchat.mvp.views.ProfileEditView
import com.example.cryptchat.utils.EM_INVALID_NAME


class ProfileEditPresenterImpl : ProfileEditPresenter,
    AbstractBasePresenter<ProfileEditView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl
    private lateinit var mUser : UserVO


    override fun onUiReady() {
        mUser = mModel.user
        mView?.displayProfileInfo(mUser.firstName, mUser.lastName, mUser.profileImage)
    }

    override fun onTapBack() {
        mView?.navigateToBack()
    }

    override fun onTapCamera() {
        mView?.openGallery()
    }

    override fun onTapSave(firstName: String, lastName: String?, profileImage: Bitmap?) {
        if (firstName!=""){
            mView?.displayLoading()
            val userVO = mUser
            userVO.firstName = firstName
            userVO.lastName = lastName
            mModel.updateUserInfo(userVO, profileImage, onSuccess = {
                mView?.displaySuccessDialog("Successfully Updated!", onTapOkay = {
                    saveUserInModel()
                    mView?.navigateToMain()
                })
            }, onFailure = {
                mView?.displayError(it, onTapOkay = {})
            })
        }
        else{
            mView?.displayError(EM_INVALID_NAME, onTapOkay = {})
        }
    }

    private fun saveUserInModel(){
        mModel.getUser(mModel.user.userId, onSucess = {
            mModel.user = it
        }, onFailure = {
            mView?.displayError(it, onTapOkay = {})
        })
    }
}