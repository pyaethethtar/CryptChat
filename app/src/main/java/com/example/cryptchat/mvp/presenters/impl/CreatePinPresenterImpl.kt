package com.example.cryptchat.mvp.presenters.impl

import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.CreatePinPresenter
import com.example.cryptchat.mvp.views.CreatePinView
import com.example.cryptchat.utils.EM_INVALID_PIN
import com.example.cryptchat.utils.PIN_MIN_SIZE

class CreatePinPresenterImpl : CreatePinPresenter, AbstractBasePresenter<CreatePinView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl

    override fun onTapNext(pin: String) {
        if (pin!="" && pin.length>= PIN_MIN_SIZE){
            mModel.user.pin = pin
            mView?.navigateToConfirmPin()
        }
        else{
            mView?.displayError(EM_INVALID_PIN, onTapOkay = {})
        }
    }
}