package com.example.cryptchat.mvp.presenters.impl

import android.annotation.SuppressLint
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.cryptchat.data.model.CryptChatModel
import com.example.cryptchat.data.model.CryptChatModelImpl
import com.example.cryptchat.mvp.presenters.AbstractBasePresenter
import com.example.cryptchat.mvp.presenters.OtpPresenter
import com.example.cryptchat.mvp.views.OtpView
import com.example.cryptchat.utils.*
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
class OtpPresenterImpl : OtpPresenter, AbstractBasePresenter<OtpView>() {

    private val mModel : CryptChatModel = CryptChatModelImpl
    private lateinit var auth: FirebaseAuth
    private lateinit var mActivity: FragmentActivity
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onUiReady(activity: FragmentActivity) {
        auth = Firebase.auth
        mActivity = activity
        val phoneNo = mModel.user.phoneNo
        //send otp code
        sendOtpCode(PREFIX_PHONE_NO+phoneNo.removePrefix("09"), mActivity)


        mView?.displayPhoneNumber(phoneNo)
    }

    override fun onTapVerify(otp: String) {
        if (otp!="" && otp.length== OTP_SIZE){
            mView?.displayLoading()
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, otp)
            signInWithPhoneAuthCredential(credential, mActivity)
        }
        else{
            mView?.displayError(EM_INVALID_PIN, onTapOkay = {})
        }
    }

    private fun sendOtpCode(phoneNo : String, activity: FragmentActivity){

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNo)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onCodeSent(
                    verificationId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    Log.d(TAG, "onCodeSent:$verificationId")
                    storedVerificationId = verificationId
                    resendToken = forceResendingToken
                }

                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    Log.d(TAG, "onVerificationCompleted:$phoneAuthCredential")
                    signInWithPhoneAuthCredential(phoneAuthCredential, activity)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    Log.d(TAG, "onVerificationFailed : ${e.message ?: EM_NO_INTERNET}")
                    mView?.displayError(e.message ?: EM_NO_INTERNET, onTapOkay = {})
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, activity: FragmentActivity) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    mView?.hideLoading()
                    mView?.navigateToEnterName()
                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        mView?.displayError(task.exception?.message ?: EM_NO_INTERNET, onTapOkay = {})
                    }
                }
            }
    }
}